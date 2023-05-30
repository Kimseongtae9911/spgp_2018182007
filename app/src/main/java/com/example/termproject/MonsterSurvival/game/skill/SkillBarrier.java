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

public class SkillBarrier extends Skill implements IBoxCollidable, IRecyclable {

    static final float BARRIER_WIDTH = 0.7f;
    static final float BARRIER_HEIGHT = 0.7f;

    public SkillBarrier(float x, float y) {
        super(R.mipmap.skill6, Metrics.game_width /2, Metrics.game_height / 2, BARRIER_WIDTH, BARRIER_HEIGHT, 1, 0, 1);
        imageSize = bitmap.getWidth();
        makeSourceRects();
        init(x, y);
    }

    static SkillBarrier get(float x, float y) {
        SkillBarrier barrier = (SkillBarrier) RecycleBin.get(SkillBarrier.class);
        if (barrier != null) {
            barrier.fixDstRect();
            barrier.init(x, y);
            return barrier;
        }
        return new SkillBarrier(x, y);
    }

    private void init(float x, float y) {
        this.x = x; this.y =y;
        ((MainScene)BaseScene.getTopScene()).getPlayer().setBarrier(true);
    }

    private void makeSourceRects() {
        srcRects = makeRects(0);
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
        MainScene scene = (MainScene) BaseScene.getTopScene();

        x = scene.getPlayerX();
        y = scene.getPlayerY();

        if(!scene.getPlayer().getBarrier()) {
            scene.remove(this);
        }

        fixDstRect();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRects[0], dstRect, null);
    }
}
