package com.example.termproject.MonsterSurvival.app;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.Sound;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.R;
import com.example.termproject.databinding.ActivityTitleBinding;

public class TitleActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    private ActivityTitleBinding binding;

    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Metrics.reset();
        binding = ActivityTitleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mediaPlayer = MediaPlayer.create(this, R.raw.titlebgm);
        mediaPlayer.start();
    }


    public void onBtnStart(View view) {
        mediaPlayer.stop();
        mediaPlayer.release();
        startActivity(new Intent(this, MainActivity.class));

    }

    public void onRankStart(View view) {
        mediaPlayer.stop();
        mediaPlayer.release();
        startActivity(new Intent(this, RankingActivity.class));
    }

    public void onPowerStart(View view) {
        mediaPlayer.stop();
        mediaPlayer.release();
        startActivity(new Intent(this, PowerActivity.class));
    }
}