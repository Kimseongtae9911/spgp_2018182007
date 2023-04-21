package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Metrics;
import com.example.termproject.MonsterSurvival.framework.Sprite;

public class InfiniteScrollBackground extends Sprite {
    private static final float SPEED = 10.0f;
    private float speedX;
    private float speedY;
    private final float height;
    private final float width;
    private float scrollHeight;
    private float scrollWidth;
    public InfiniteScrollBackground(int bitmapResId) {
        super(bitmapResId, Metrics.game_width / 2, Metrics.game_height / 2, Metrics.game_width, Metrics.game_height);
        this.width = bitmap.getWidth() * Metrics.game_height / bitmap.getHeight();
        this.height = Metrics.game_height;
        setSize(width, height);
        this.speedX = 0; this.speedY = 0;
    }

    public void setSpeedX(float speed) {speedX = -speed * SPEED;}
    public void setSpeedY(float speed) {speedY = -speed * SPEED;}

    @Override
    public void update() {
        scrollHeight += speedY * BaseScene.frameTime;
        scrollWidth += speedX * BaseScene.frameTime;
    }

    @Override
    public void draw(Canvas canvas) {
        float currY = scrollHeight % height;
        float currX = scrollWidth % width;

        if (currY > 0) currY -= height;
        if(currX > 0) currX -= width;

        while (currX < Metrics.game_width) {
            dstRect.set(currX, currY, currX + width, currY + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            dstRect.set(currX + width, currY, currX + width * 2, currY + height);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            dstRect.set(currX, currY + height, currX + width, currY + height * 2);
            canvas.drawBitmap(bitmap, null, dstRect, null);
            currY += height;
            currX += width;
        }
    }
}
