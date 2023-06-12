package com.example.termproject.MonsterSurvival.game.scene;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.app.MainActivity;
import com.example.termproject.MonsterSurvival.app.TitleActivity;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.framework.interfaces.ITouchable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.objects.Sprite;
import com.example.termproject.MonsterSurvival.framework.util.Data;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PowerScene extends BaseScene {
    private final float STAT_WIDTH = 3.519f;
    private final float STAT_HEIGHT = 5.49f;
    private final float X_OFFSET = 2.3f;
    private final float Y_OFFSET = 2.0f;
    private final int COIN_PER_LEVEL = 50;
    private int[] levels = {0, 0, 0, 0};
    private int coin = 0;
    MediaPlayer mediaPlayer;
    GameView view;
    Paint paint = new Paint();
    Paint textPaint = new Paint();
    public enum Layer {
        ui, touch, COUNT
    }

    public PowerScene(Context context, GameView view) {
        Metrics.setGameSize(9.0f, 16.0f);
        initLayers(Layer.COUNT);
        this.view = view;
        mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.stat);
        paint.setColor(Color.YELLOW);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(1.0f);

        loadStatInfo(context);

        add(Layer.touch, new Button(R.mipmap.power, Metrics.game_width / 2 - X_OFFSET, 3.0f, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(action == Button.Action.pressed) {
                    if(coin < levels[0]* levels[0] * COIN_PER_LEVEL)
                        return true;

                    coin -= levels[0] * levels[0]* COIN_PER_LEVEL;
                    mediaPlayer.start();
                    if (levels[0] <= 4)
                        levels[0] += 1;
                    saveStatInfo(context);
                }
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.defense, Metrics.game_width / 2 + X_OFFSET, 3.0f, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(action == Button.Action.pressed) {
                    if(coin < levels[1] * levels[1] * COIN_PER_LEVEL)
                        return true;

                    coin -= levels[1]* levels[1] * COIN_PER_LEVEL;
                    mediaPlayer.start();
                    if (levels[1] <= 4)
                        levels[1] += 1;
                    saveStatInfo(context);
                }
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.speed, Metrics.game_width / 2 - X_OFFSET, Metrics.game_height / 2 + Y_OFFSET, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(action == Button.Action.pressed) {
                    if(coin < levels[2] * levels[2]* COIN_PER_LEVEL)
                        return true;

                    coin -= levels[2] * levels[2]* COIN_PER_LEVEL;

                    mediaPlayer.start();
                    if (levels[2] <= 4)
                        levels[2] += 1;
                    saveStatInfo(context);
                }
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.cooltime, Metrics.game_width / 2 + X_OFFSET, Metrics.game_height / 2 + Y_OFFSET, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                if(action == Button.Action.pressed) {
                    if(coin < levels[3] * levels[3] * COIN_PER_LEVEL)
                        return true;

                    coin -= levels[3]* levels[3] * COIN_PER_LEVEL;
                    mediaPlayer.start();
                    if (levels[3] <= 4)
                        levels[3] += 1;
                    saveStatInfo(context);
                }
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.powerlobby_btn, 2.3f, 15.0f, 3.475f, 1.875f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                context.startActivity(new Intent(context, TitleActivity.class));
                return true;
            }
        }));

        add(Layer.ui, new Sprite(R.mipmap.coin, 6.0f, 15.0f, 1.0f, 1.0f));
    }

    public boolean onTouchEvent(MotionEvent event) {
        ArrayList<IGameObject> objects = layers.get(PowerScene.Layer.touch.ordinal());
        for (IGameObject obj : objects) {
            if (!(obj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) obj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(new RectF(5.3f, 14.0f, 8.8f, 16.0f), paint);
        super.draw(canvas);
        canvas.drawText(String.valueOf(levels[0]), 2.0f, 6.5f, textPaint);
        canvas.drawText(String.valueOf(levels[1]), 6.5f, 6.5f, textPaint);
        canvas.drawText(String.valueOf(levels[2]), 2.0f, 13.5f, textPaint);
        canvas.drawText(String.valueOf(levels[3]), 6.5f, 13.5f, textPaint);

        String str = String.valueOf(coin);
        for(int i = 0; i < str.length(); ++i) {
            canvas.drawText(String.valueOf(str.charAt(i)), 6.3f + (i * 0.5f), 15.3f, textPaint);
        }
    }

    private void loadStatInfo(Context context) {
        try {
            String json = Data.loadJSON(context, "statInfo.json");
            if(json == null) {
                for(int i =0; i < 4; ++i) {
                    levels[i] = 0;
                }
                return;
            }
            JSONObject jsonObject = new JSONObject(json);
            for (int i = 0; i < 4; i++) {
                levels[i] = jsonObject.getInt("level" + (i + 1));
            }
            coin = jsonObject.getInt("coin");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Data.clearStatInfo(context);
    }

    private void saveStatInfo(Context context) {
        try {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < 4; i++) {
                jsonObject.put("level" + (i + 1), levels[i]);
            }
            jsonObject.put("coin", coin);

            Data.writeJSONToFile(context, "statInfo.json", jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
