package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.MainScene;
import com.example.termproject.R;

public class SkillFlyFire extends Skill implements IBoxCollidable, IRecyclable {
    static final float FLYFIRE_WIDTH = 0.77f; //25.5
    static final float FLYFIRE_HEIGHT = 1.f; //33
    static final float FALL_SPEED = 20.f;
    public boolean collision = true;
    private float collisionTime = 0.f;
    private float createTime = 0.f;

    private float targetY = 0.f;
    private boolean fall = true;
    public SkillFlyFire(float x, float y, int power) {
        super(R.mipmap.skill4, Metrics.game_width /2, Metrics.game_height / 2, FLYFIRE_WIDTH, FLYFIRE_HEIGHT, 1, 10, 8);
        imageSize = bitmap.getWidth() / 8;
        init(x, y, power);
        makeSourceRects();
    }

    static SkillFlyFire get(float x, float y, int power) {
        SkillFlyFire thunder = (SkillFlyFire) RecycleBin.get(SkillFlyFire.class);
        if (thunder != null) {
            thunder.fixDstRect();
            thunder.init(x, y, power);
            return thunder;
        }
        return new SkillFlyFire(x, y, power);
    }

    private void init(float x, float y, int power) {
        this.x = x; targetY =y; this.y = 0.f;
        this.power = (int)(power * 1.5f);
        active = true;
        collision = true;
        collisionTime = 0.f;
        createTime = 0.f;
        fall = true;
    }

    private void makeSourceRects() {
        srcRects = makeRects(0, 1, 2, 3, 4, 5, 6, 7);
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

        if(y < targetY && fall) {
            y += FALL_SPEED * BaseScene.frameTime;
            if(y >= targetY) {
                y = targetY;
                fall = false;
            }
            fixDstRect();
            return;
        }

        createTime += BaseScene.frameTime;

        if(!collision) {
            collisionTime += BaseScene.frameTime;
            if (collisionTime >= 0.2f) {
                collision = true;
                collisionTime = 0.f;
            }
        }

        fixDstRect();

        x += (-scene.getPlayerSpeed() * scene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-scene.getPlayerSpeed() * scene.getPlayerMoveY() * BaseScene.frameTime);

        obb.set(x, y, FLYFIRE_WIDTH / 2, FLYFIRE_HEIGHT / 2, 0.f);

        if(createTime >= 4.0f) {
            scene.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % 8;

        canvas.drawBitmap(bitmap, srcRects[frameIndex], dstRect, null);
    }
}
