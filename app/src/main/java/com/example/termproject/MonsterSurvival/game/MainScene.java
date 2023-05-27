package com.example.termproject.MonsterSurvival.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.app.TitleActivity;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.framework.interfaces.ITouchable;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.monster.MonsterGenerator;
import com.example.termproject.MonsterSurvival.game.skill.SkillGenerator;
import com.example.termproject.MonsterSurvival.game.ui.Coin;
import com.example.termproject.MonsterSurvival.game.ui.JoyStick;
import com.example.termproject.MonsterSurvival.game.ui.Score;
import com.example.termproject.MonsterSurvival.game.ui.Timer;
import com.example.termproject.R;

import java.util.ArrayList;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private ArrayList<IGameObject> pauseObject = new ArrayList<>();
    private ArrayList<IGameObject> nextRoundObject = new ArrayList<>();
    private boolean nextRound = false;
    private boolean pause = false;
    private final Hero hero = new Hero();
    private final InfiniteScrollBackground background;
    private Timer timer = new Timer();
    private JoyStick joyStick;
    private Coin coin = new Coin();
    private Score score = new Score();

    GameView view;
    public enum Layer {
        bg1, skill, monster, player, UI, touch, controller, COUNT
    }

    public MainScene(Context context, GameView view) {
        initLayers(Layer.COUNT);
        this.view = view;
        hero.reset();
        transparentPaint.setColor(Color.GRAY);
        transparentPaint.setAlpha(128);

        add(Layer.player, hero);
        background = new InfiniteScrollBackground(R.mipmap.background);
        add(Layer.bg1, background);

        joyStick = new JoyStick(context);
        joyStick.setJoystickListener(new JoyStick.JoystickListener() {
            @Override
            public void onJoystickMoved(float xPercent, float yPercent) {
                hero.setDir(xPercent, yPercent);
            }
        });

        addNextRoundObject(new Button(R.mipmap.lobby_btn, Metrics.game_width / 2- 2.0f, Metrics.game_height / 2, 3.78f, 6.36f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                nextRound = false;
                context.startActivity(new Intent(context, TitleActivity.class));
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
        super.update(elapsedNanos);
        background.setSpeedX(hero.getMoveX());
        background.setSpeedY(hero.getMoveY());

        if((int)timer.getTime() % 180 == 0 && (int)timer.getTime() != 0) {
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
    }

    public void addPauseObject(IGameObject obj) {
        pauseObject.add(obj);
    }
    public void addNextRoundObject(IGameObject obj) {nextRoundObject.add(obj);}
    public void addScore(int num) {score.addScore(num);}
    public GameView getView() {return view;}
    public float getPlayerX() {return hero.getX();}
    public float getPlayerY() {return hero.getY();}

    public float getPlayerMoveX() {return hero.getMoveX();}
    public float getPlayerMoveY() {return hero.getMoveY();}
    public float getPlayerSpeed() {return hero.getSpeed();}
    public int getPlayerPower() {return hero.getPower();}
    public Hero getPlayer() {return hero;}
}
