package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.MonsterSurvival.game.MainScene;
import com.example.termproject.R;

public class SkillThunder extends Skill implements IBoxCollidable, IRecyclable {
    static final float THUNDER_WIDTH = 1.f;
    static final float THUNDER_HEIGHT = 1.f;
    private float dx, dy;

    public SkillThunder() {
        super(R.mipmap.skill1, Metrics.game_width /2, Metrics.game_height / 2, THUNDER_WIDTH, THUNDER_HEIGHT, 1, 10, 2);
    }

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();

        fixDstRect();

        x += (-scene.getPlayerSpeed() * scene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-scene.getPlayerSpeed() * scene.getPlayerMoveY() * BaseScene.frameTime);

        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);
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
