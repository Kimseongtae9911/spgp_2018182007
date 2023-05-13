package com.example.termproject.MonsterSurvival.game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;

import com.example.termproject.MonsterSurvival.app.MainActivity;
import com.example.termproject.MonsterSurvival.app.TitleActivity;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Button;
import com.example.termproject.MonsterSurvival.framework.IGameObject;
import com.example.termproject.MonsterSurvival.framework.ITouchable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

import java.util.ArrayList;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private static final Hero hero = new Hero();
    private final InfiniteScrollBackground background;
    private Timer timer = new Timer();
    private JoyStick joyStick;

    public enum Layer {
        bg1, monster, player, UI, touch, controller, COUNT
    }

    public MainScene(Context context) {
        initLayers(Layer.COUNT);
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

        //pause object
        addPauseObject(new Button(R.mipmap.resume_btn, Metrics.game_width / 2, 5.0f, 7.f, 3.f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = false;
                return true;
            }
        }));

        addPauseObject(new Button(R.mipmap.exit_btn, Metrics.game_width / 2, 8.0f, 6.3f, 3.3f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = false;
                context.startActivity(new Intent(context, TitleActivity.class));
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.pause_btn, 9.0f - 0.6f, 0.6f, 1.f, 1.f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                pause = true;
                return true;
            }
        }));

        add(Layer.touch, joyStick);
        add(Layer.UI, timer);
        add(Layer.controller, new MonsterGenerator());
        add(Layer.controller, new CollisionChecker());
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
        super.update(elapsedNanos);
        background.setSpeedX(hero.getMoveX());
        background.setSpeedY(hero.getMoveY());
    }

    public static float getPlayerX() {return hero.getX();}
    public static float getPlayerY() {return hero.getY();}

    public static float getPlayerMoveX() {return hero.getMoveX();}
    public static float getPlayerMoveY() {return hero.getMoveY();}

}
