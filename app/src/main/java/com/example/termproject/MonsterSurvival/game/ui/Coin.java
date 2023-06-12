package com.example.termproject.MonsterSurvival.game.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.res.ResourcesCompat;

import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.objects.Sprite;
import com.example.termproject.R;

public class Coin extends Sprite {
    static final float COIN_SIZE = 1.0f;
    static final float Y_OFFSET = 0.2f;
    private final float TEXT_SIZE = 0.6f;
    private final float LETTER_SPACE = 0.3f;

    private int num;
    private final Paint textPaint = new Paint();
    private final Paint rectPaint = new Paint();
    public Coin() {
        super(R.mipmap.coin, COIN_SIZE, COIN_SIZE - Y_OFFSET, COIN_SIZE, COIN_SIZE);

        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //textPaint.setLetterSpacing(LETTER_SPACE);
        rectPaint.setColor(ResourcesCompat.getColor(GameView.res, R.color.coin_bg, null));
        num = 0;
    }

    public void addCoin(int coin) {
        num += coin;
    }
    public int getNum() {return num;}

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(COIN_SIZE / 2, COIN_SIZE / 2 - Y_OFFSET, COIN_SIZE * 3.0f, COIN_SIZE + COIN_SIZE / 2.0f- Y_OFFSET, rectPaint);
        super.draw(canvas);

        String str = String.valueOf(num);
        for(int i = 0; i < str.length(); ++i) {
            canvas.drawText(String.valueOf(str.charAt(i)), COIN_SIZE + 0.45f + (i * LETTER_SPACE), COIN_SIZE, textPaint);
        }
    }
}
