package com.example.termproject.MonsterSurvival.framework.objects;

import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.framework.interfaces.ITouchable;

public class Button extends Sprite implements ITouchable {
    private final Callback callback;

    public enum Action {
        pressed, released,
    }
    public interface Callback {
        public boolean onTouch(Action action);
    }
    public Button(int bitmapResId, float cx, float cy, float width, float height, Callback callback) {
        super(bitmapResId, cx, cy, width, height);
        this.callback = callback;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = Metrics.toGameX(e.getX());
        float y = Metrics.toGameY(e.getY());
        if (!dstRect.contains(x, y)) {
            return false;
        }

        int action = e.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            callback.onTouch(Action.pressed);
        } else if (action == MotionEvent.ACTION_UP) {
            callback.onTouch(Action.released);
        }
        return true;
    }
}
