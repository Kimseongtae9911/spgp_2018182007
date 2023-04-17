package com.example.termproject.MonsterSurvival.game;

import android.content.Context;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Hero hero;
    private JoyStick joyStick;

    public enum Layer {
        bg1, player, bg2, UI, COUNT
    }

    public MainScene(Context context) {
        initLayers(Layer.COUNT);
        hero = new Hero();
        add(Layer.player, hero);
        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, 0.4f));

        joyStick = new JoyStick(context);
        joyStick.setJoystickListener(new JoyStick.JoystickListener() {
            @Override
            public void onJoystickMoved(float xPercent, float yPercent) {

            }
        });
        add(Layer.UI, joyStick);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                joyStick.onTouch(event);
                float x = Metrics.toGameX(event.getX());
                float y = Metrics.toGameY(event.getY());
                hero.setTargetPosition(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                joyStick.onTouch(event);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
