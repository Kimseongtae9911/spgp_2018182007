package com.example.termproject.MonsterSurvival.app;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {

    private ActivityTitleBinding binding;

    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Metrics.reset();
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createAnimator();
    }

    private void createAnimator() {
        animator = ValueAnimator.ofFloat(0.0f, 0.5f);
        animator.setDuration(30000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float progress = (Float)valueAnimator.getAnimatedValue();
                float tx = -1 * binding.backgroundImageView.getWidth() * progress;
                binding.backgroundImageView.setTranslationX(tx);
            }
        });
    }

    public void onBtnStart(View view) {
        startActivity(new Intent(this, MainActivity.class));

    }
}