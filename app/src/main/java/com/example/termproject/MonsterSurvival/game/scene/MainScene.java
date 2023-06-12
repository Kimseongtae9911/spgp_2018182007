package com.example.termproject.MonsterSurvival.game.scene;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.termproject.MonsterSurvival.app.MainActivity;
import com.example.termproject.MonsterSurvival.app.TitleActivity;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Sound;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.framework.interfaces.ITouchable;
import com.example.termproject.MonsterSurvival.framework.objects.Sprite;
import com.example.termproject.MonsterSurvival.framework.util.CollisionChecker;
import com.example.termproject.MonsterSurvival.framework.util.Data;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.monster.MonsterGenerator;
import com.example.termproject.MonsterSurvival.game.player.Hero;
import com.example.termproject.MonsterSurvival.game.player.StatGenerator;
import com.example.termproject.MonsterSurvival.game.skill.SkillGenerator;
import com.example.termproject.MonsterSurvival.game.ui.Coin;
import com.example.termproject.MonsterSurvival.game.ui.JoyStick;
import com.example.termproject.MonsterSurvival.game.ui.Score;
import com.example.termproject.MonsterSurvival.game.ui.Timer;
import com.example.termproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Paint backgroundPaint = new Paint();
    private final Paint gameOverPaint = new Paint();
    private ArrayList<IGameObject> pauseObject = new ArrayList<>();
    private ArrayList<IGameObject> nextRoundObject = new ArrayList<>();
    private ArrayList<IGameObject> levelUpObject = new ArrayList<>();
    private ArrayList<IGameObject> gameOverObject = new ArrayList<>();
    private boolean nextRound = false;
    private boolean pause = false;
    private boolean levelUp = false;
    private boolean gameOver = false;
    private final Hero hero = new Hero();
    private final InfiniteScrollBackground background;
    private Timer timer = new Timer();
    private JoyStick joyStick;
    private Coin coin = new Coin();
    private Score score = new Score();
    private Context context;
    private int prevCoin;

    GameView view;
    public enum Layer {
        bg1, skill, monster, player, UI, touch, controller, COUNT
    }

    public MainScene(Context context, GameView view) {
        initLayers(Layer.COUNT);
        this.view = view;
        this.context = context;
        hero.reset();
        Sound.playMusic(R.raw.bgm);

        transparentPaint.setColor(Color.GRAY);
        transparentPaint.setAlpha(128);

        backgroundPaint.setColor(Color.BLACK);

        gameOverPaint.setColor(Color.RED);
        gameOverPaint.setTextSize(1.0f);
        gameOverPaint.setLetterSpacing(-0.2f);

        try {
            String json = Data.loadJSON(context, "statInfo.json");
            JSONObject jsonObject = new JSONObject(json);
            for (int i = 0; i < 4; i++) {
                hero.setLevel(jsonObject.getInt("level" + (i + 1)), i);
            }
            prevCoin = jsonObject.getInt("coin");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        add(Layer.player, hero);
        background = new InfiniteScrollBackground(R.mipmap.background);
        add(Layer.bg1, background);

        joyStick = new JoyStick(context);
        joyStick.setJoystickListener(new JoyStick.JoystickListener() {
            @Override
            public void onJoystickMoved(float xPercent, float yPercent) {
                float magnitude = (float)Math.sqrt(xPercent * xPercent + yPercent * yPercent);
                if(magnitude != 0) {
                    xPercent = xPercent / magnitude;
                    yPercent = yPercent / magnitude;
                }
                hero.setDir(xPercent, yPercent);
            }
        });

        addGameOverObject(new Sprite(R.mipmap.game_over, Metrics.game_width / 2, 1.0f, 6.5f, 2.76f));
        addGameOverObject(new Button(R.mipmap.lobby_btn, Metrics.game_width / 2 + 2.0f, Metrics.game_height / 2, 3.78f, 6.36f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                gameOver = false;
                context.startActivity(new Intent(context, TitleActivity.class));
                return true;
            }
        }));
        addGameOverObject(new Button(R.mipmap.restart_btn, Metrics.game_width / 2- 2.0f, Metrics.game_height / 2, 3.78f, 6.36f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                gameOver = false;
                context.startActivity(new Intent(context, MainActivity.class));
                return true;
            }
        }));

        addNextRoundObject(new Button(R.mipmap.lobby_btn, Metrics.game_width / 2- 2.0f, Metrics.game_height / 2, 3.78f, 6.36f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    for (int i = 0; i < 4; i++) {
                        jsonObject.put("level" + (i + 1), hero.getLevel(i));
                    }
                    jsonObject.put("coin", coin.getNum() + prevCoin);

                    Data.writeJSONToFile(context, "statInfo.json", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                showNameInputDialog(score.getScore() + (int) (timer.getTime()) * 10, context);

                return true;
            }
        }));
        addNextRoundObject(new Button(R.mipmap.nextround_btn, Metrics.game_width / 2 + 2.0f, Metrics.game_height / 2, 3.78f, 6.36f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                nextRound = false;
                timer.addTime(1.f);
                return true;
            }
        }));

        //pause object
        addPauseObject(new Button(R.mipmap.resume_btn, Metrics.game_width / 2, 4.0f, 7.f, 3.f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = false;
                return true;
            }
        }));

        addPauseObject(new Button(R.mipmap.exit_btn, Metrics.game_width / 2, 7.0f, 6.3f, 3.3f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = false;
                context.startActivity(new Intent(context, TitleActivity.class));
                return true;
            }
        }));
        addPauseObject(score);

        add(Layer.touch, new Button(R.mipmap.pause_btn, 9.0f - 0.6f, 0.6f, 1.f, 1.f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = true;
                return true;
            }
        }));

        add(Layer.touch, joyStick);
        add(Layer.UI, timer);
        add(Layer.UI, coin);
        add(Layer.controller, new MonsterGenerator());
        add(Layer.controller, new CollisionChecker());
        add(Layer.controller, new SkillGenerator());
        add(Layer.controller, new StatGenerator());
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(pause) {
            for (IGameObject obj : pauseObject) {
                if (!(obj instanceof ITouchable)) {
                    continue;
                }
                boolean processed = ((ITouchable) obj).onTouchEvent(event);
                if (processed) return true;
            }
        }
        else if(nextRound) {
            for (IGameObject obj : nextRoundObject) {
                if (!(obj instanceof ITouchable)) {
                    continue;
                }
                boolean processed = ((ITouchable) obj).onTouchEvent(event);
                if (processed) return true;
            }
        }
        else if(levelUp) {
            for (IGameObject obj : levelUpObject) {
                if (!(obj instanceof ITouchable)) {
                    continue;
                }
                boolean processed = ((ITouchable) obj).onTouchEvent(event);
                if (processed) return true;
            }
        }
        else if(gameOver) {
            for (IGameObject obj : gameOverObject) {
                if (!(obj instanceof ITouchable)) {
                    continue;
                }
                boolean processed = ((ITouchable) obj).onTouchEvent(event);
                if (processed) return true;
            }
        }
        else {
            ArrayList<IGameObject> objects = layers.get(Layer.touch.ordinal());
            for (IGameObject obj : objects) {
                if (!(obj instanceof ITouchable)) {
                    continue;
                }
                boolean processed = ((ITouchable) obj).onTouchEvent(event);
                if (processed) return true;
            }
        }

        return false;
    }

    @Override
    public void update(long elapsedNanos) {
        if(pause)
            return;
        if(nextRound)
            return;
        if(levelUp)
            return;
        if(gameOver)
            return;

        super.update(elapsedNanos);
        background.setSpeedX(hero.getMoveX(), hero.getSpeed());
        background.setSpeedY(hero.getMoveY(), hero.getSpeed());

        if((int)timer.getTime() % 100 == 0 && (int)timer.getTime() != 0) {
            nextRound = true;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(pause) {
            canvas.drawRect(0.f, 0.f, Metrics.game_width, Metrics.game_height, transparentPaint);
            for(IGameObject obj : pauseObject) {
                obj.draw(canvas);
            }
        }

        if(nextRound) {
            canvas.drawRect(0.f, 0.f, Metrics.game_width, Metrics.game_height, transparentPaint);
            for(IGameObject obj : nextRoundObject) {
                obj.draw(canvas);
            }
        }

        if(levelUp) {
            canvas.drawRect(0.f, 0.f, Metrics.game_width, Metrics.game_height, transparentPaint);
            for(IGameObject obj : levelUpObject) {
                if(obj == null)
                    continue;
                obj.draw(canvas);
            }
        }

        if(gameOver) {
            canvas.drawRect(0.f, 0.f, Metrics.game_width, Metrics.game_height, transparentPaint);
            for(IGameObject obj : gameOverObject) {
                if(obj == null)
                    continue;
                obj.draw(canvas);
            }
        }

        int screenWidth = canvas.getWidth();
        int screenHeight = 16;
        Rect backgroundRectLeft = new Rect(-4, -4, 0, screenHeight +4);
        Rect backgroundRectRight = new Rect(screenWidth, -4, screenWidth + 4, screenHeight +4);
        Rect backgroundRectUp = new Rect(-4, -4, screenWidth +4, 0);
        Rect backgroundRectDown = new Rect(-4, screenHeight, screenWidth + 4, screenHeight + 4);

        canvas.drawRect(backgroundRectLeft, backgroundPaint);
        canvas.drawRect(backgroundRectRight, backgroundPaint);
        canvas.drawRect(backgroundRectUp, backgroundPaint);
        canvas.drawRect(backgroundRectDown, backgroundPaint);
    }

    private void showNameInputDialog(final int score, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Enter your name");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                if (!name.isEmpty()) {
                    saveScore(name, score);
                }
                nextRound = false;
                context.startActivity(new Intent(context, TitleActivity.class));
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                nextRound = false;
                context.startActivity(new Intent(context, TitleActivity.class));
            }
        });

        builder.show();
    }

    private void saveScore(String name, int score) {
        String json = Data.loadJSON(getContext(), "ranking.json");
        List<JSONObject> rankingData = new ArrayList<>();

        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    rankingData.add(jsonObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Create a new ranking entry
        JSONObject newEntry = new JSONObject();
        try {
            newEntry.put("name", name);
            newEntry.put("score", score);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Add the new entry to the ranking data
        rankingData.add(newEntry);

        // Sort the ranking data based on the score in descending order
        Collections.sort(rankingData, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject obj1, JSONObject obj2) {
                try {
                    int score1 = obj1.getInt("score");
                    int score2 = obj2.getInt("score");
                    return Integer.compare(score2, score1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        if (rankingData.size() > 5) {
            rankingData = rankingData.subList(0, 5);
        }

        JSONArray updatedArray = new JSONArray();
        for (JSONObject entry : rankingData) {
            updatedArray.put(entry);
        }

        Data.writeJSONToFile(getContext(), "ranking.json", updatedArray.toString());
    }

    public void addPauseObject(IGameObject obj) {
        pauseObject.add(obj);
    }
    public void addNextRoundObject(IGameObject obj) {nextRoundObject.add(obj);}
    public void addLevelUpObject(IGameObject obj) {levelUpObject.add(obj);}
    public void clearLevelUpObject() {levelUpObject.clear();}
    public void addGameOverObject(IGameObject obj) {gameOverObject.add(obj);}
    public void addScore(int num) {score.addScore(num);}
    public GameView getView() {return view;}
    public Context getContext() {return context;}
    public float getPlayerX() {return hero.getX();}
    public float getPlayerY() {return hero.getY();}

    public float getPlayerMoveX() {return hero.getMoveX();}
    public float getPlayerMoveY() {return hero.getMoveY();}
    public float getPlayerSpeed() {return hero.getSpeed();}
    public int getPlayerPower() {return hero.getPower();}
    public Hero getPlayer() {return hero;}
    public Coin getCoin() {return coin;}
    public Timer getTimer() {return timer;}
    public void setLevelUp(boolean levelUp) {this.levelUp = levelUp;}
    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
}
