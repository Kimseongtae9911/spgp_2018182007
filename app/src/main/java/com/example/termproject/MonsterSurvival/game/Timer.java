package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.example.termproject.MonsterSurvival.BuildConfig;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IGameObject;
import com.example.termproject.MonsterSurvival.framework.Metrics;

import java.util.ArrayList;
import java.util.List;

public class Timer implements IGameObject {
    private float time = 0.f;
    private final Paint timePaint;
    private final float TEXT_SIZE = Metrics.toGameY(0.2f);
    private final float LETTER_SPACING = -0.5f;
    private Paint.FontMetrics fontMetrics;
    private float centerX;
    private float centerY;
    public Timer() {
        timePaint = new Paint();
        timePaint.setColor(Color.WHITE);
        timePaint.setTextSize(TEXT_SIZE);
        if (TEXT_SIZE < 1.0f) {
            float scale = 1.0f / TEXT_SIZE;
            timePaint.setTextSize(timePaint.getTextSize() * scale);
        }

        timePaint.setTypeface(Typeface.DEFAULT_BOLD);
        timePaint.setLetterSpacing(LETTER_SPACING);

        fontMetrics = timePaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        centerY = textHeight;
    }

    public float getTime() {
        return time;
    }
    public void addTime(float add) {
        time += add;
    }

    public int getDigitCount(int number) {
        String numberString = String.valueOf(number);
        return numberString.length();
    }

    public void update() {
        time += BaseScene.frameTime;
    }

    public void draw(Canvas canvas) {
        int second = (int)(time);
        String timeString = String.format("%d", second);
        float textWidth = timePaint.measureText(timeString);
        centerX = (Metrics.game_width - textWidth) / 2f;

        if(getDigitCount(second) % 2 == 0) {
            canvas.drawText(timeString, centerX - LETTER_SPACING / 2, centerY, timePaint);
        }
        else {
            canvas.drawText(timeString, centerX - LETTER_SPACING, centerY, timePaint);
        }
    }
}
