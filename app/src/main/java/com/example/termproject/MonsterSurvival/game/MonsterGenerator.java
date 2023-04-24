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
    private int wave;

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
        wave++;
        Random r = new Random();
        BaseScene scene = BaseScene.getTopScene();
        for (int i = 0; i < 5; i++) {
            int level = (wave + 15) / 10 - r.nextInt(3);
            if (level < 0) level = 0;
            scene.add(MainScene.Layer.monster, Monster.get(i, level));
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
