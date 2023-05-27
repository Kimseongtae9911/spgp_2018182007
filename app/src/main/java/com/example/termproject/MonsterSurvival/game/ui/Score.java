package com.example.termproject.MonsterSurvival.game.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.framework.objects.Sprite;
import com.example.termproject.R;

public class Score extends Sprite {
    private final float TEXT_SIZE = 2.f;
    private final Paint textPaint = new Paint();
    private int score;

    public Score() {
        super(R.mipmap.score, Metrics.game_width / 2, 12.0f,223 / 30, 236 / 30);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        score = 0;
    }

    public void addScore(int score) {
        this.score += score;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        String str = String.valueOf(score);
        float textWidth = textPaint.measureText(str);
        float textX = (Metrics.game_width - textWidth) / 2;
        canvas.drawText(str, textX, 13.5f, textPaint);
    }
}
