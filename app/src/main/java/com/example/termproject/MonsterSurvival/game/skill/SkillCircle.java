package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;
import com.example.termproject.R;

public class SkillCircle extends Skill implements IBoxCollidable, IRecyclable {
    static final float CIRCLE_WIDTH = 3.0f;
    static final float CIRCLE_HEIGHT = 3.0f;
    private final float[] SIZE_OFFSET = {0.5625f, 0.28125f, 0.f};
    public boolean collision = true;
    private float collisionTime = 0.f;
    private float createTime = 0.f;

    public SkillCircle(float x, float y, int power) {
        super(R.mipmap.skill3, Metrics.game_width /2, Metrics.game_height / 2, CIRCLE_WIDTH, CIRCLE_HEIGHT, 1, 5, 3);
        imageSize = bitmap.getWidth() / 3;
        makeSourceRects();
        init(x, y, power);
    }

    static SkillCircle get(float x, float y, int power) {
        SkillCircle barrier = (SkillCircle) RecycleBin.get(SkillCircle.class);
        if (barrier != null) {
            barrier.fixDstRect();
            barrier.init(x, y, power);
            return barrier;
        }
        return new SkillCircle(x, y, power);
    }

    private void init(float x, float y, int power) {
        this.x = x; this.y =y;
        this.power = (int)(power * 0.8);
        active = true;
        collision = true;
        collisionTime = 0.f;
        createTime = 0.f;
    }

    private void makeSourceRects() {
        srcRects = makeRects(0, 1, 2);
    };
    protected Rect[] makeRects(int... indices) {
        Rect[] rects = new Rect[indices.length];
        for (int i = 0; i < indices.length; ++i) {
            int idx = indices[i];
            int l = (idx % 100) * (imageSize);
            int t = (idx / 100) * (bitmap.getHeight());
            rects[i] = new Rect(l, t, l + imageSize, t + bitmap.getHeight());
        }
        return rects;
    }

    @Override
    public void update() {
        super.update();
        MainScene scene = (MainScene) BaseScene.getTopScene();
        createTime += BaseScene.frameTime;

        if(!collision) {
            collisionTime += BaseScene.frameTime;
            if (collisionTime >= 0.3f) {
                collision = true;
                collisionTime = 0.f;
            }
        }

        x = scene.getPlayerX();
        y = scene.getPlayerY();

        fixDstRect();
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % 3;

        obb.set(x, y, CIRCLE_WIDTH /2 - SIZE_OFFSET[frameIndex], CIRCLE_HEIGHT / 2 - SIZE_OFFSET[frameIndex], 0.f);

        if(createTime >= 1.0f) {
            scene.remove(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        long now = System.currentTimeMillis();
        float time = (now - createdOn) / 1000.0f;
        int frameIndex = Math.round(time * fps) % 3;

        canvas.drawBitmap(bitmap, srcRects[frameIndex], dstRect, null);
    }
}
