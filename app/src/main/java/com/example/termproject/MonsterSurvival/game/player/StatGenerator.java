package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;

public class StatGenerator implements IGameObject {

    static enum Stat {power, speed, defense, cooltime};

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {}
}
