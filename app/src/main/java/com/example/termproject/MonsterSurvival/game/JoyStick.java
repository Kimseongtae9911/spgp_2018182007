package com.example.termproject.MonsterSurvival.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaTimestamp;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.termproject.MonsterSurvival.framework.IGameObject;
import com.example.termproject.MonsterSurvival.framework.ITouchable;
import com.example.termproject.MonsterSurvival.framework.Metrics;

public class JoyStick extends SurfaceView implements SurfaceHolder.Callback, IGameObject, ITouchable {
    private static final String TAG = JoyStick.class.getSimpleName();;
    private float centerX;
    private float centerY;
    private float baseRadius;
    private float hatRadius;
    private Paint basePaint;
    private Paint hatPaint;
    private float xValue;
    private float yValue;
    private JoystickListener joystickCallback;

    public JoyStick(Context context) {
        super(context);
        init(null, 0);
    }

    public JoyStick(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public JoyStick(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        baseRadius = (Metrics.toGameX(1));
        hatRadius = (Metrics.toGameY(0.5f));

        centerX = Metrics.game_width / 2;
        centerY = Metrics.game_height /5 * 4;

        basePaint = new Paint();
        basePaint.setColor(Color.GRAY);
        basePaint.setStyle(Paint.Style.FILL);

        hatPaint = new Paint();
        hatPaint.setColor(Color.BLUE);
        hatPaint.setStyle(Paint.Style.FILL);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        invalidate();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void update() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, baseRadius, basePaint);
        canvas.drawCircle(xValue * baseRadius + centerX, yValue * baseRadius + centerY, hatRadius, hatPaint);
    }

    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() != e.ACTION_UP)
        {
            float x = Metrics.toGameX(e.getX());
            float y = Metrics.toGameY(e.getY());
            float displacement = (float)Math.sqrt((Math.pow(x - centerX, 2)) + Math.pow(y - centerY, 2));
            if(displacement < baseRadius)
            {
                xValue = (x - centerX) / baseRadius;
                yValue = (y - centerY) / baseRadius;
            }
            else
            {
                xValue = (x - centerX) / displacement;
                yValue = (y - centerY) / displacement;
            }
            invalidate();
            if (joystickCallback != null) {
                joystickCallback.onJoystickMoved(xValue, yValue);
            }
        }
        else {
            xValue = 0;
            yValue = 0;
            invalidate();
            if (joystickCallback != null) {
                joystickCallback.onJoystickMoved(xValue, yValue);
            }
        }

        return true;
    }

    public interface JoystickListener {
        void onJoystickMoved(float xPercent, float yPercent);
    }

    public void setJoystickListener(JoystickListener joystickListener) {
        this.joystickCallback = joystickListener;
    }
}
