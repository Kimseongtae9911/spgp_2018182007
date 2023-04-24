package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.util.Log;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IGameObject;

import java.util.Random;

public class MonsterGenerator implements IGameObject {
    private static final float GEN_INTERVAL = 5.0f;
    private static final String TAG = Monster.class.getSimpleName();
    private float time;
    private float difficultyTime = 0;

    @Override
    public void update() {
        time += BaseScene.frameTime;
        difficultyTime += BaseScene.frameTime;
        if (time > GEN_INTERVAL) {
            generate();
            time -= GEN_INTERVAL;
        }
    }

    private void generate() {
        BaseScene scene = BaseScene.getTopScene();
        for (int i = 0; i < 5; i++) {
            scene.add(MainScene.Layer.monster, Monster.get(i, (int)difficultyTime * 5));
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
