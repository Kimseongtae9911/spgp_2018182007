package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.BitmapPool;
import com.example.termproject.MonsterSurvival.framework.Gauge;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.R;

public class Monster extends AnimSprite implements IRecyclable, IBoxCollidable {
    private static final float SPEED = 2.0f;

    public static final float SIZE = 1.8f;

    private int level;
    protected int hp, maxHp;

    private float dx =0;
    private float dy =0;
    protected RectF collisionRect = new RectF();

    protected Gauge gauge = new Gauge(0.1f, R.color.monster_gauge_fg, R.color.monster_gauge_bg);

    static Monster get(int index, int level) {
        Monster enemy = (Monster) RecycleBin.get(Monster.class);
        if (enemy != null) {
            enemy.x = (Metrics.game_width / 10) * (2 * index + 1);
            enemy.y = -SIZE/2;
            enemy.fixDstRect();
            enemy.init(level);
            return enemy;
        }
        return new Monster(index, level);
    }
    private Monster(int index, int level) {
        super(R.mipmap.monster1, (Metrics.game_width / 10) * (2 * index + 1), -SIZE/2, SIZE, SIZE, 1, 0, 10);
        this.level = level;
        init(level);
    }

    private void init(int level) {
        if (level != this.level) {
            this.level = level;
            this.bitmap = BitmapPool.get(R.mipmap.monster1);
        }
        this.hp = this.maxHp = (level + 1) * 10;
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

        x += (-5 * MainScene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-5 * MainScene.getPlayerMoveY() * BaseScene.frameTime);

        fixDstRect();

        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

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

    public boolean decreaseLife(int power) {
        hp -= power;
        if (hp <= 0) return true;
        return false;
    }

    public void setDir(float dx, float dy) {this.dx = dx; this.dy = dy;}
}
