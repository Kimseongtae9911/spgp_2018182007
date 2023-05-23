package com.example.termproject.MonsterSurvival.framework;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y =y;
    }

    public float dotProduct(float x2, float y2) {
        return x * x2 + y * y2;
    }
}
