package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.RecycleBin;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;

public class CoolTime extends Button implements IRecyclable {
    public CoolTime(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height, callback);
    }
    static CoolTime get(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {

        CoolTime coolTime = (CoolTime) RecycleBin.get(CoolTime.class);
        if (coolTime != null) {

            coolTime.init(cx, cy);
            return coolTime;
        }
        return new CoolTime(bitmapResId, cx, cy, width, height, callback);
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
