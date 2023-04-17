package com.example.termproject.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.termproject.R;
import com.example.termproject.framework.BitmapPool;
import com.example.termproject.framework.Sprite;

import com.example.termproject.framework.BaseScene;

public class Hero extends Sprite {
    private static final float FIGHTER_X = 4.5f;
    private static final float FIGHTER_Y = 14.8f;
    private static final float FIGHTER_WIDTH = 72 * 0.0243f; //1.75f;
    private static final float FIGHTER_HEIGHT = 80 * 0.0243f; //1.75f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 10.0f;
    private static final float FIGHTER_LEFT = FIGHTER_WIDTH / 2;
    private static final float FIGHTER_RIGHT = 9.0f - FIGHTER_WIDTH / 2;

    private float tx;
    private RectF targetRect = new RectF();
    private Bitmap sparkBitmap;
    private RectF sparkRect = new RectF();
    private static final float SPARK_WIDTH = 50 * 0.0243f;
    private static final float SPARK_HEIGHT = 30 * 0.0243f;
    private static final float SPARK_OFFSET = 0.7f;
    private static final float FIRE_INTERVAL = 0.25f;
    private static final float SPARK_DURATION = 0.1f;
    private float accumulatedTime;

    public Hero() {
        super(R.mipmap.fighter, FIGHTER_X, FIGHTER_Y, FIGHTER_WIDTH, FIGHTER_HEIGHT);
        sparkBitmap = BitmapPool.get(R.mipmap.laser_0);
        tx = x;
    }

    public void setTargetPosition(float tx, float ty) {
        this.tx = tx;
        targetRect.set(
                tx - TARGET_RADIUS, FIGHTER_Y - TARGET_RADIUS,
                tx + TARGET_RADIUS, FIGHTER_Y + TARGET_RADIUS);
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
        if (x < FIGHTER_LEFT) x = tx = FIGHTER_LEFT;
        if (x > FIGHTER_RIGHT) x = tx = FIGHTER_RIGHT;
        fixDstRect();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (accumulatedTime < SPARK_DURATION) {
            sparkRect.set(x - SPARK_WIDTH/2, y - SPARK_HEIGHT/2 - SPARK_OFFSET,
                    x + SPARK_WIDTH/2, y + SPARK_HEIGHT/2 - SPARK_OFFSET);
            canvas.drawBitmap(sparkBitmap, null, sparkRect, null);
        }
    }
}
