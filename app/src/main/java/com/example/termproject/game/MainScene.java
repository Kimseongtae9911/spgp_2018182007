package com.example.termproject.game;

import android.view.MotionEvent;

import com.example.termproject.R;

import com.example.termproject.framework.BaseScene;
import com.example.termproject.framework.Metrics;

public class MainScene extends BaseScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final Hero hero;

    public enum Layer {
        bg1, enemy, bullet, player, bg2, ui, controller, COUNT
    }

    private Score score;
    public MainScene() {
        initLayers(Layer.COUNT);
        hero = new Hero();
        add(Layer.player, hero);
        add(Layer.bg1, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        add(Layer.bg2, new VertScrollBackground(R.mipmap.clouds, 0.4f));
        score = new Score();
        add(Layer.ui, score);
        //add(Layer.controller, new EnemyGenerator());
        //add(Layer.controller, new CollisionChecker());
    }

    public void addScore(int amount) {
        score.add(amount);
    }
    public int getScore() {
        return score.getScore();
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
