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
    private static final float HERO_RIGHT = 9.0f - HERO_WIDTH / 2;
    private static final String TAG = Hero.class.getSimpleName();;

    private float tx;

    public Hero() {
        super(R.mipmap.hero, Metrics.game_width /2, Metrics.game_height / 2, HERO_WIDTH, HERO_HEIGHT, 2, 10, 6);
        tx = x;
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
    }

    @Override
    public void update() {
        super.update();

        float time = BaseScene.frameTime;
        if (tx >= x) {
            x += SPEED * time;
            if (x > tx) {
                x = tx;
            }
        } else {
            x -= SPEED * time;
            if (x < tx) {
                x = tx;
            }
        }
        if (x < HERO_LEFT) x = tx = HERO_LEFT;
        if (x > HERO_RIGHT) x = tx = HERO_RIGHT;
        fixDstRect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
