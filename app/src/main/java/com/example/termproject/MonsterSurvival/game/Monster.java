package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.BitmapPool;
import com.example.termproject.MonsterSurvival.framework.Gauge;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.R;

import java.util.Random;

public class Monster extends AnimSprite implements IRecyclable, IBoxCollidable {
    private static final float SPEED = 2.0f;

    public static final float SIZE = 1.8f;
    public static final float MONSTER_WIDTH = 58.f * 0.015f;
    public static final float MONSTER_HEIGHT = 62.f * 0.015f;
    private static final String TAG = Monster.class.getSimpleName();;

    private int level;
    protected int hp, maxHp;
    protected int power = 5;

    private float dx =0;
    private float dy =0;
    protected RectF collisionRect = new RectF();
    private int imageSize = 0;
    protected Rect[][] srcRects;
    protected Gauge gauge = new Gauge(0.3f, R.color.monster_gauge_fg, R.color.monster_gauge_bg);

    static Monster get(int index, int level) {
        Random r = new Random();
        Monster enemy = (Monster) RecycleBin.get(Monster.class);
        if (enemy != null) {
            int spawnSide = r.nextInt(4);
            switch (spawnSide) {
                case 0: // Left
                    enemy.x = -MONSTER_WIDTH;
                    enemy.y = Metrics.game_height * r.nextFloat();
                    break;
                case 1: // Right
                    enemy.x = Metrics.game_width;
                    enemy.y = Metrics.game_height * r.nextFloat();
                    break;
                case 2: // Top
                    enemy.x = Metrics.game_width * r.nextFloat();
                    enemy.y = -MONSTER_HEIGHT;
                    break;
                case 3: // Bottom
                    enemy.x = Metrics.game_width * r.nextFloat();
                    enemy.y = Metrics.game_height;
                    break;
            }
            enemy.fixDstRect();
            enemy.init(level);
            return enemy;
        }
        return new Monster(index, level);
    }
    private Monster(int index, int level) {
        super(R.mipmap.monster1, (Metrics.game_width / 10) * (2 * index + 1), -SIZE/2, MONSTER_WIDTH, MONSTER_HEIGHT, 2, 10, 8);
        imageSize = bitmap.getWidth() / 8;
        makeSourceRects();
        this.level = level;
        init(level);
    }

    private void makeSourceRects() {
        srcRects = new Rect[][] {
                makeRects(0, 1, 2, 3, 4, 5, 6, 7),                  // Right Move
                makeRects(100, 101, 102, 103, 104, 105, 106, 107)   // Left Move
        };
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * (imageSize);
            int t = (idx / 100) * (bitmap.getHeight() / 2);
            rects[i] = new Rect(l, t + 2, l + imageSize, t + bitmap.getHeight() / 2 + 2);
        }
        return rects;
    }
    private void init(int level) {
        if (level != this.level) {
            this.level = level;
            this.bitmap = BitmapPool.get(R.mipmap.monster1);
            this.power = level * power;
        }
        this.hp = this.maxHp = (level + 1) * 10;
        Random r = new Random();
        int spawnSide = r.nextInt(4);
        switch (spawnSide) {
            case 0: // Left
                this.x = -MONSTER_WIDTH;
                this.y = Metrics.game_height * r.nextFloat();
                break;
            case 1: // Right
                this.x = Metrics.game_width;
                this.y = Metrics.game_height * r.nextFloat();
                break;
            case 2: // Top
                this.x = Metrics.game_width * r.nextFloat();
                this.y = -MONSTER_HEIGHT;
                break;
            case 3: // Bottom
                this.x = Metrics.game_width * r.nextFloat();
                this.y = Metrics.game_height;
                break;
        }
    }


    @Override
    public void update() {
        super.update();

        float dx = MainScene.getPlayerX() - x;
        float dy = MainScene.getPlayerY() - y;
        float dis = (float)Math.sqrt(dx*dx + dy*dy);
        if(dis > 0) {
            dx /= dis;
            dy /= dis;
        }

        x += SPEED * dx * BaseScene.frameTime;
        y += SPEED * dy * BaseScene.frameTime;

        x += (-MainScene.getPlayerSpeed() * MainScene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-MainScene.getPlayerSpeed() * MainScene.getPlayerMoveY() * BaseScene.frameTime);

        fixDstRect();

        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int index = 0;
        if(dx >= 0)
            index = 0;
        else
            index = 1;
        Rect[] rects = srcRects[index];
        int frameIndex = Math.round(time * fps) % rects.length;
        canvas.drawBitmap(bitmap, rects[frameIndex], dstRect, null);

        canvas.save();
        float width = dstRect.width() * 0.75f;
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        gauge.draw(canvas, (float)hp / maxHp);
        canvas.restore();
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {
    }

    public int getScore() {
        return 10 * (level + 1);
    }
    public int getPower() {return power;}
    public float getX() {return x;}
    public float getY() {return y;}

    public boolean decreaseLife(int power) {
        Log.d(TAG, "HP: " + hp);
        hp -= power;
        if (hp <= 0) return true;
        return false;
    }

    public void setDir(float dx, float dy) {this.dx = dx; this.dy = dy;}
}
