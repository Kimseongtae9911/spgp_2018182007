package com.example.termproject.MonsterSurvival.game;

import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Hero hero;

    public enum Layer {
        bg1, player, bg2, COUNT
    }

    public MainScene() {
        initLayers(Layer.COUNT);
        hero = new Hero();
        add(Layer.player, hero);
        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, 0.4f));
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = Metrics.toGameX(event.getX());
                float y = Metrics.toGameY(event.getY());
                hero.setTargetPosition(x, y);
                return true;
        }
        return super.onTouchEvent(event);
    }
}
