package com.example.termproject.MonsterSurvival.framework.util;

import android.graphics.RectF;

public class OrientedBoundingBox {
    private float centerX;
    private float centerY;
    private float halfWidth;
    private float halfHeight;
    private float rotationAngle;

    public OrientedBoundingBox() {
        this.centerX = 0;
        this.centerY = 0;
        this.halfWidth = 0;
        this.halfHeight = 0;
        this.rotationAngle = 0;
    }
    public OrientedBoundingBox(float centerX, float centerY, float halfWidth, float halfHeight, float rotationAngle) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.rotationAngle = rotationAngle;
    }

    public void set(float centerX, float centerY, float halfWidth, float halfHeight, float rotationAngle) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.rotationAngle = rotationAngle;
    }

    public RectF getBoundingBox() {
        float cos = (float) Math.cos(Math.toRadians(rotationAngle));
        float sin = (float) Math.sin(Math.toRadians(rotationAngle));

        RectF boundingBox = new RectF();
        boundingBox.left = centerX - (halfWidth * cos) - (halfHeight * sin);
        boundingBox.top = centerY + (halfWidth * sin) - (halfHeight * cos);
        boundingBox.right = centerX + (halfWidth * cos) + (halfHeight * sin);
        boundingBox.bottom = centerY - (halfWidth * sin) + (halfHeight * cos);
        return boundingBox;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public float getCenterX() {
        return centerX;
    }
    public float getCenterY() {
        return centerY;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public float getHalfWidth() {
        return halfWidth;
    }
}
