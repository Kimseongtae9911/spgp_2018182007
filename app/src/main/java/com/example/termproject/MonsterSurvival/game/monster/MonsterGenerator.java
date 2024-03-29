package com.example.termproject.MonsterSurvival.game.monster;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;

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
        for (int i = 0; i < ((int)(difficultyTime / 30)+1) * 7; i++) {
            scene.add(MainScene.Layer.monster, Monster.get(i, (int)(difficultyTime / 30)+1));
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
