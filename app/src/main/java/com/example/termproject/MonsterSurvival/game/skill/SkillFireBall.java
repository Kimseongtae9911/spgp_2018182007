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

public class SkillFireBall extends Skill implements IBoxCollidable, IRecyclable {
    static final float FIREBALL_WIDTH = 1.56f; // 39
    static final float FIREBALL_HEIGHT = 0.84f; // 21
    private final float FIREBALL_SPEED = 4.0f;

    private float dx = 0.f;
    private float dy = 0.f;

    public boolean collision = true;
    private float collisionTime = 0.f;

    public SkillFireBall(float x, float y, float dx, float dy, int power) {
        super(R.mipmap.skill2, Metrics.game_width /2, Metrics.game_height / 2, FIREBALL_WIDTH, FIREBALL_HEIGHT, 1, 20, 2);
        imageSize = bitmap.getWidth() / 2;
        makeSourceRects();
        init(x, y, dx, dy, power);
    }

    static SkillFireBall get(float x, float y, float dx, float dy, int power) {
        SkillFireBall barrier = (SkillFireBall) RecycleBin.get(SkillFireBall.class);
        if (barrier != null) {
            barrier.fixDstRect();
            barrier.init(x, y, dx, dy, power);
            return barrier;
        }
        return new SkillFireBall(x, y, dx, dy, power);
    }

    private void init(float x, float y, float dx, float dy, int power) {
        this.x = x; this.y =y;
        this.dx = dx; this.dy = dy;
        this.power = (int)(power * 1.2);
        active = true;
        collision = true;
        collisionTime = 0.f;
    }

    private void makeSourceRects() {
        srcRects = makeRects(0, 1);
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

        if(!collision) {
            collisionTime += BaseScene.frameTime;
            if (collisionTime >= 0.2f) {
                collision = true;
                collisionTime = 0.f;
            }
        }

        x += dx*(FIREBALL_SPEED * BaseScene.frameTime);
        y += dy*(FIREBALL_SPEED * BaseScene.frameTime);

        x += (-scene.getPlayerSpeed() * scene.getPlayerMoveX() * BaseScene.frameTime);
        y += (-scene.getPlayerSpeed() * scene.getPlayerMoveY() * BaseScene.frameTime);

        fixDstRect();

        float rotationAngle = (float) Math.toDegrees(Math.atan2(dy, dx));
        obb.set(x, y, FIREBALL_WIDTH / 2, FIREBALL_HEIGHT / 2, rotationAngle);

        if(x >= 10.f || y >= 17.f || x <= -2.f || y <= -1.f) {
            active = false;
            collision = true;
            scene.remove(this);
        }
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
