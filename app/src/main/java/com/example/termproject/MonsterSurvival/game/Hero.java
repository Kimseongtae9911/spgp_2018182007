package com.example.termproject.MonsterSurvival.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.Gauge;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.R;

public class Hero extends AnimSprite implements IBoxCollidable {
    private final Paint hpPaint;
    private final char[] hpText = {'H', 'P'};
    private final Paint expPaint;
    private final char[] expText = {'E', 'X', 'P'};
    private final float TEXT_SIZE = 0.7f;
    private static final float HERO_WIDTH = 46 * 0.02f;
    private static final float HERO_HEIGHT = 42 * 0.02f;
    private static final float SPEED = 5.0f;
    private static final float HERO_LEFT = HERO_WIDTH / 2 + 1.0f;
    private static final float HERO_RIGHT = Metrics.game_width - HERO_WIDTH / 2 - 1.0f;
    private static final float HERO_UP = HERO_HEIGHT / 2 + 1.5f;
    private static final float HERO_DOWN = Metrics.game_height- HERO_HEIGHT / 2 - 2.0f;

    private Gauge hpGauge = new Gauge(0.3f, R.color.hero_hpGauge_fg, R.color.hero_hpGauge_bg);
    private Gauge expGauge= new Gauge(0.3f, R.color.hero_expGauge_fg, R.color.hero_expGauge_bg);
    //Player Stats
    private float maxHp = 100;
    private float curHp = 100;
    private float maxExp = 100;
    private float curExp = 0;
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
        hpPaint = new Paint();
        hpPaint.setColor(Color.RED);
        hpPaint.setTextSize(TEXT_SIZE);
        hpPaint.setTypeface(Typeface.DEFAULT_BOLD);

        expPaint = new Paint();
        expPaint.setColor(Color.GREEN);
        expPaint.setTextSize(TEXT_SIZE - 0.1f);
        expPaint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void reset() {
        maxHp = 100;
        curHp = 100;
        maxExp = 100;
        curExp = 0;
        power = 10;
        speed = SPEED;
        defense = 10;
        cooltime = 0;
        dx = 0;
        dy = 0;
        moveX = 0;
        moveY = 0;
        x = Metrics.game_width / 2;
        y = Metrics.game_height / 2;
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
    public RectF getCollisionRect() {return collisionRect;}
}
