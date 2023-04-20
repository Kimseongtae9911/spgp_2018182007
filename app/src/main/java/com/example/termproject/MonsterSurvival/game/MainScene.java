package com.example.termproject.MonsterSurvival.game;

import android.content.Context;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IGameObject;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

import java.util.ArrayList;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Hero hero;
    private final InfiniteScrollBackground background;
    private JoyStick joyStick;

    public enum Layer {
        bg1, player, UI, COUNT
    }

    public MainScene(Context context) {
        initLayers(Layer.COUNT);
        hero = new Hero();
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
        add(Layer.UI, joyStick);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                joyStick.onTouch(event);
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void update(long elapsedNanos) {
        super.update(elapsedNanos);
        background.setSpeedX(hero.getMoveX());
        background.setSpeedY(hero.getMoveY());
    }
}
