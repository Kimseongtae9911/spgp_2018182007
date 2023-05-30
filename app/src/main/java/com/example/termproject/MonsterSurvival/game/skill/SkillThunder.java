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

public class SkillThunder extends Skill implements IBoxCollidable, IRecyclable {
    static final float THUNDER_WIDTH = 6.4f;
    static final float THUNDER_HEIGHT = 0.5f;
    private float dx, dy;
    private float time = 0.f;

    public SkillThunder(float x, float y, float dx, float dy, int power) {
        super(R.mipmap.skill5, Metrics.game_width /2, Metrics.game_height / 2, THUNDER_WIDTH, THUNDER_HEIGHT, 1, 0, 1);
        imageSize = bitmap.getWidth();
        init(x, y, dx, dy, power);
        makeSourceRects();
    }

    static SkillThunder get(float x, float y, float dx, float dy, int power) {
        SkillThunder thunder = (SkillThunder) RecycleBin.get(SkillThunder.class);
        if (thunder != null) {
            thunder.fixDstRect();
            thunder.init(x, y, dx, dy, power);
            return thunder;
        }
        return new SkillThunder(x, y, dx, dy, power);
    }

    private void init(float x, float y, float dx, float dy, int power) {
        time = 0.f;
        this.x = x; this.y =y;
        this.dx = dx; this.dy = dy;
        this.power = (int)(power * 1.5f);
        active = true;
    }

    private void makeSourceRects() {
        srcRects = makeRects(0);
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

        time += BaseScene.frameTime;
        if(time >= 0.5f) {
            scene.remove(this);
            active = false;
        }

        fixDstRect();

        x += (-scene.getPlayerSpeed() * scene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-scene.getPlayerSpeed() * scene.getPlayerMoveY() * BaseScene.frameTime);

        float rotationAngle = (float) Math.toDegrees(Math.atan2(dy, dx));
        obb.set(x, y, THUNDER_WIDTH / 2, THUNDER_HEIGHT / 2, rotationAngle);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate((float)Math.toDegrees(Math.atan2(dy, dx)), x, y);
        canvas.drawBitmap(bitmap, srcRects[0], dstRect, null);
        canvas.restore();
    }
}
