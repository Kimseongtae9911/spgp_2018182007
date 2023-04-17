package com.example.termproject.game;

import android.graphics.Canvas;

import com.example.termproject.framework.Sprite;
import com.example.termproject.framework.BaseScene;
import com.example.termproject.framework.Metrics;

public class VertScrollBackground extends Sprite {
    private final float speed;
    private final float height;
    private float scroll;

    public VertScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId, Metrics.game_width / 2, Metrics.game_height / 2, Metrics.game_width, Metrics.game_height);
        this.height = bitmap.getHeight() * Metrics.game_width / bitmap.getWidth();
        setSize(Metrics.game_width, height);
        this.speed = speed;
    }

    @Override
    public void update() {
        scroll += speed * BaseScene.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        float curr = scroll % height;
        if (curr > 0) curr -= height;
        while (curr < Metrics.game_height) {
            dstRect.set(0, curr, Metrics.game_width, curr + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += height;
        }
    }
}
