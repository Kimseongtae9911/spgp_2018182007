package com.example.termproject.MonsterSurvival.game.player;

import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;

public class Defense extends Button implements IRecyclable {
    public Defense(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height, callback);
    }

    @Override
    public void onRecycle() {

    }
}