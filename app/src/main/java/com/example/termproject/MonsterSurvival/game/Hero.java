package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.example.termproject.MonsterSurvival.framework.objects.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.objects.Gauge;
import com.example.termproject.MonsterSurvival.framework.interfaces.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.framework.util.OrientedBoundingBox;
import com.example.termproject.R;

public class Hero extends AnimSprite implements IBoxCollidable {

    private static final String TAG = Hero.class.getSimpleName();
    private final Paint hpPaint;
    private final char[] hpText = {'H', 'P'};
    private final Paint expPaint;
    private final char[] expText = {'E', 'X', 'P'};
    private final float TEXT_SIZE = 0.7f;
    private static final float HERO_WIDTH = 46 * 0.02f;
    private static final float HERO_HEIGHT = 42 * 0.02f;
    private static final float HERO_LEFT = HERO_WIDTH / 2 + 1.0f;
    private static final float HERO_RIGHT = Metrics.game_width - HERO_WIDTH / 2 - 1.0f;
    private static final float HERO_UP = HERO_HEIGHT / 2 + 1.5f;
    private static final float HERO_DOWN = Metrics.game_height- HERO_HEIGHT / 2 - 2.0f;
    private boolean barrier = false;
    public boolean invincible = false;
    private float invincibleTime = 0.f;
    private int imageSize = 0;
    protected Rect[][] srcRects;
    private Gauge hpGauge = new Gauge(0.3f, R.color.hero_hpGauge_fg, R.color.hero_hpGauge_bg);
    private Gauge expGauge= new Gauge(0.3f, R.color.hero_expGauge_fg, R.color.hero_expGauge_bg);
    //Player Stats
    private float speed = 5.0f;
    private float maxHp = 100;
    private float curHp = 100;
    private int maxExp = 100;
    private int curExp = 0;
    private int power = 10;
    private float defense = 10;
    private float cooltime = 0;
    private boolean animSide = false;
    //Player Stats
    private float dx = 0;
    private float dy = 0;

    private float moveX = 0;
    private float moveY = 0;
    private OrientedBoundingBox obb = new OrientedBoundingBox();
    public Hero() {
        super(R.mipmap.hero, Metrics.game_width /2, Metrics.game_height / 2, HERO_WIDTH, HERO_HEIGHT, 2, 10, 6);
        imageSize = bitmap.getWidth() / 6;
        makeSourceRects();

        hpPaint = new Paint();
        hpPaint.setColor(Color.RED);
        hpPaint.setTextSize(TEXT_SIZE);
        hpPaint.setTypeface(Typeface.DEFAULT_BOLD);

        expPaint = new Paint();
        expPaint.setColor(Color.GREEN);
        expPaint.setTextSize(TEXT_SIZE - 0.1f);
        expPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    private void makeSourceRects() {
        srcRects = new Rect[][] {
                makeRects(0, 1, 2, 3, 4, 5),               // Right Idle
                makeRects(100, 101, 102, 103, 104, 105),   // Right Move
                makeRects(200, 201, 202, 203, 204, 205),   // Left Idle
                makeRects(300, 301, 302, 303, 304, 305),   // Left Move
        };
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * (imageSize);
            int t = (idx / 100) * (bitmap.getHeight() / 4);
            rects[i] = new Rect(l, t + 2, l + imageSize, t + bitmap.getHeight() / 4 + 2);
        }
        return rects;
    }
    public void reset() {
        barrier = false;
        maxHp = 100;
        curHp = 100;
        maxExp = 100;
        curExp = 0;
        power = 10;
        speed = 5.0f;
        defense = 10;
        cooltime = 0;
        dx = 0;
        dy = 0;
        moveX = 0;
        moveY = 0;
        x = Metrics.game_width / 2;
        y = Metrics.game_height / 2;
        invincibleTime = 0.f;
        invincible = false;
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

        if(dx < 0) {
            animSide = false;
        }
        else if(dx > 0) {
            animSide = true;
        }
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

        if(invincible) {
            invincibleTime += BaseScene.frameTime;
            if(invincibleTime >= 0.1f) {
                invincible = false;
                invincibleTime = 0.f;
            }

        }
        fixDstRect();

        obb.set(x, y, HERO_WIDTH / 2, HERO_HEIGHT / 2, 0.f);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = 0;
        if(dx == 0) {
            if(animSide)
                index = 0;
            else
                index = 2;
        }
        else if(dx > 0)
            index = 1;
        else if(dx < 0)
            index = 3;

        Rect[] rects = srcRects[index];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);

        for(int i = 0; i < 2; ++i) {
            canvas.drawText(String.valueOf(hpText[i]), 0.35f + 0.6f * i, Metrics.game_height - 1.25f, hpPaint);
        }
        for(int i = 0; i < 3; ++i) {
            canvas.drawText(String.valueOf(expText[i]), 0.15f + 0.5f * i, Metrics.game_height - 0.3f, expPaint);
        }

        canvas.save();
        float width = Metrics.game_width * 0.5f;
        float height = 2.0f;
        canvas.translate(Metrics.game_width / 2 - width / 2 + 0.2f, Metrics.game_height - 1.5f);
        canvas.scale(width, height);
        hpGauge.draw(canvas, (float)curHp / maxHp);
        canvas.restore();

        canvas.save();
        canvas.translate(Metrics.game_width / 2 - width / 2 + 0.2f, Metrics.game_height - 0.5f);
        canvas.scale(width, height);
        expGauge.draw(canvas, (float)curExp / maxExp);
        canvas.restore();
    }

    @Override
    public OrientedBoundingBox getOBB() {return obb;}

    public void setBarrier(boolean barrier) {
        this.barrier = barrier;
    }
    public boolean getBarrier() {return barrier;}

    public float getSpeed() {return speed;}
    public int getPower() {return power;}
    public void GainExp(int exp) {
        curExp += exp;
        if(curExp >= maxExp) {
            curExp -= maxExp;
            maxExp = (int)(maxExp * 1.2f);
        }
    }
}
