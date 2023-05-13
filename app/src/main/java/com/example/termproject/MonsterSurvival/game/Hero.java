package com.example.termproject.MonsterSurvival.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

public class Hero extends AnimSprite implements IBoxCollidable {
    private static final float HERO_X = 4.5f;
    private static final float HERO_Y = 14.8f;
    private static final float HERO_WIDTH = 46 * 0.02f;
    private static final float HERO_HEIGHT = 42 * 0.02f;
    private static final float TARGET_RADIUS = 0.5f;
    private static final float SPEED = 5.0f;
    private static final float HERO_LEFT = HERO_WIDTH / 2 + 1.0f;
    private static final float HERO_RIGHT = Metrics.game_width - HERO_WIDTH / 2 - 1.0f;
    private static final float HERO_UP = HERO_HEIGHT / 2 + 1.5f;
    private static final float HERO_DOWN = Metrics.game_height- HERO_HEIGHT / 2 - 1.5f;
    private static final String TAG = Hero.class.getSimpleName();

    //Player Stats
    private static final float maxHp = 100;
    private float curHp = 100;
    private float power = 10;
    private float speed = SPEED;
    private float defense = 10;
    private float cooltime = 0;

    //Player Stats

    private float dx = 0;
    private float dy = 0;

    private float moveX = 0;
    private float moveY = 0;
    protected RectF collisionRect = new RectF();
    public Hero() {
        super(R.mipmap.hero, Metrics.game_width /2, Metrics.game_height / 2, HERO_WIDTH, HERO_HEIGHT, 2, 10, 6);
    }

    public void setDir(float dx, float dy) {this.dx= dx; this.dy =dy;}

    public float getMoveX() {return moveX;}
    public float getMoveY() {return moveY;}
    public float getX() {return x;}
    public float getY() {return y;}

    public void decreaseHp(float power) {curHp -= power;}
    @Override
    public void update() {
        super.update();

        float time = BaseScene.frameTime;

        x += dx * speed * time;
        y += dy * speed * time;

        if (x < HERO_LEFT) {
            moveX = -1;
            x = HERO_LEFT;
        }
        else if (x > HERO_RIGHT) {
            moveX = 1;
            x = HERO_RIGHT;
        }
        else {
            moveX = 0;
        }
        if (y < HERO_UP) {
            moveY = -1;
            y = HERO_UP;
        }
        else if (y > HERO_DOWN) {
            moveY = 1;
            y = HERO_DOWN;
        }
        else{
            moveY = 0;
        }

        fixDstRect();
        collisionRect.set(dstRect);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public RectF getCollisionRect() {return collisionRect;}
}
