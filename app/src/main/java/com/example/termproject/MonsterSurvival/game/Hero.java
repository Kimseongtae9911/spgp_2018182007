package com.example.termproject.MonsterSurvival.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

public class Hero extends AnimSprite {
    private static final float HERO_X = 4.5f;
    private static final float HERO_Y = 14.8f;
    private static final float HERO_WIDTH = 72 * 0.0243f; //1.75f;
    private static final float HERO_HEIGHT = 80 * 0.0243f; //1.75f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 10.0f;
    private static final float HERO_LEFT = HERO_WIDTH / 2;
    private static final float HERO_RIGHT = Metrics.game_width - HERO_WIDTH / 2;
    private static final float HERO_UP = HERO_HEIGHT / 2;
    private static final float HERO_DOWN = Metrics.game_height- HERO_HEIGHT / 2;
    private static final String TAG = Hero.class.getSimpleName();

    private float dx = 0;
    private float dy = 0;

    public Hero() {
        super(R.mipmap.hero, Metrics.game_width /2, Metrics.game_height / 2, HERO_WIDTH, HERO_HEIGHT, 2, 10, 6);
        Log.d(TAG, "Hero Create: " + this.y);
    }

    public void setDir(float dx, float dy) {this.dx= dx; this.dy =dy;}

    @Override
    public void update() {
        super.update();

        float time = BaseScene.frameTime;

        x += dx * SPEED * time;
        y += dy * SPEED * time;

        if (x < HERO_LEFT) x = HERO_LEFT;
        if (x > HERO_RIGHT) x = HERO_RIGHT;
        if (y < HERO_UP) y = HERO_UP;
        if (y > HERO_DOWN) y = HERO_DOWN;

        fixDstRect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
