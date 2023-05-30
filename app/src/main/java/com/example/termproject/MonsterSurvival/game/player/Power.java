package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.monster.Monster;

import java.util.Random;

public class Power extends Button implements IRecyclable {
    public Power(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height, callback);
    }
    static Power get(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {

        Power power = (Power) RecycleBin.get(Power.class);
        if (power != null) {

            power.init(cx, cy);
            return power;
        }
        return new Power(bitmapResId, cx, cy, width, height, callback);
    }

    private void init(float cx, float cy) {
        x = cx;
        y =cy;
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
    @Override
    public void onRecycle() {

    }
}
