package com.example.termproject.MonsterSurvival.framework.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import com.example.termproject.MonsterSurvival.framework.GameView;

public class Gauge {
    private Paint fgPaint = new Paint();
    private Paint bgPaint = new Paint();
    public Gauge(float width, int fgColorResId, int bgColorResId) {
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(width);

        bgPaint.setColor(ResourcesCompat.getColor(GameView.res, bgColorResId, null));
        bgPaint.setStrokeCap(Paint.Cap.ROUND);
        fgPaint.setStyle(Paint.Style.STROKE);
        fgPaint.setStrokeWidth(width / 2);
        fgPaint.setColor(ResourcesCompat.getColor(GameView.res, fgColorResId, null));
        fgPaint.setStrokeCap(Paint.Cap.ROUND);
    }
    public void draw(Canvas canvas, float value) {
        canvas.drawLine(0, 0, 1.0f, 0.0f, bgPaint);
        if (value > 0) {
            canvas.drawLine(0, 0, value, 0.0f, fgPaint);
        }
    }
}
