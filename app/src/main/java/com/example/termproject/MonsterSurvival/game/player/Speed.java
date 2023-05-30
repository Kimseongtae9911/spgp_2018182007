package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;

public class Speed extends Button implements IRecyclable {
    public Speed(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height, callback);
    }
    static Speed get(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {

        Speed speed = (Speed) RecycleBin.get(Speed.class);
        if (speed != null) {

            speed.init(cx, cy);
            return speed;
        }
        return new Speed(bitmapResId, cx, cy, width, height, callback);
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
