package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.termproject.MonsterSurvival.framework.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.game.MainScene;
import com.example.termproject.MonsterSurvival.game.Monster;
import com.example.termproject.R;

import java.util.Random;

public class SkillMissile extends Skill implements IRecyclable, IBoxCollidable {
    static final float MISSILE_WIDTH = 2.f;
    static final float MISSILE_HIEGHT = 0.5f;
    private final float MISSILE_SPEED = 5.0f;
    private float dx = 0.f;
    private float dy = 0.f;

    public SkillMissile() {
        super(R.mipmap.skill1, Metrics.game_width /2, Metrics.game_height / 2, MISSILE_WIDTH, MISSILE_HIEGHT, 1, 10, 2);
        imageSize = bitmap.getWidth() / 2;
        makeSourceRects();
    }

    public SkillMissile(float x, float y, float dx, float dy, int power) {
        super(R.mipmap.skill1, x, y, MISSILE_WIDTH, MISSILE_HIEGHT, 1, 10, 2);
        imageSize = bitmap.getWidth() / 2;
        makeSourceRects();
        fixDstRect();
        init(x, y, dx, dy, power);
    }

    public void init(float x, float y, float dx, float dy, int power) {
        this.x = x; this.y = y; this.dx = dx; this.dy = dy;
        active = true;
        this.power = power;
    }
    static SkillMissile get(float x, float y, float dx, float dy, int power) {
        SkillMissile missile = (SkillMissile) RecycleBin.get(SkillMissile.class);
        if (missile != null) {
            missile.fixDstRect();
            missile.init(x, y, dx, dy, power);
            return missile;
        }
        return new SkillMissile(x, y, dx, dy, power);
    }

    private void makeSourceRects() {
        srcRects = makeRects(0, 1);
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; i++) {
            int idx = indices[i];
            int l = (idx % 100) * (imageSize);
            int t = (idx / 100) * (bitmap.getHeight());
            rects[i] = new Rect(l, t, l + imageSize, t + bitmap.getHeight());
        }
        return rects;
    }
    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        x += dx*(MISSILE_SPEED * BaseScene.frameTime);
        y += dy*(MISSILE_SPEED * BaseScene.frameTime);

        x += (-scene.getPlayerSpeed() * scene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-scene.getPlayerSpeed() * scene.getPlayerMoveY() * BaseScene.frameTime);

        fixDstRect();


        float rotationAngle = (float) Math.toDegrees(Math.atan2(dy, dx));
        RectF rotatedCollisionRect = new RectF(collisionRect);
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, x, y);
        matrix.mapRect(rotatedCollisionRect);
        obb.set(x, y, MISSILE_WIDTH / 2, MISSILE_HIEGHT / 2, rotationAngle);

        collisionRect.set(rotatedCollisionRect);
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % 2;

        canvas.save();
        canvas.rotate((float)Math.toDegrees(Math.atan2(dy, dx)), x, y);
        canvas.drawBitmap(bitmap, srcRects[frameIndex], dstRect, null);
        canvas.restore();
    }
}
