package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;

public class Defense extends Button implements IRecyclable {
    public Defense(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height, callback);
    }
    static Defense get(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {

        Defense defense = (Defense) RecycleBin.get(Defense.class);
        if (defense != null) {

            defense.init(cx, cy);
            return defense;
        }
        return new Defense(bitmapResId, cx, cy, width, height, callback);
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