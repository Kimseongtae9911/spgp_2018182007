package com.example.termproject.MonsterSurvival.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;
import com.example.termproject.MonsterSurvival.game.scene.PowerScene;
import com.example.termproject.MonsterSurvival.game.scene.RankingScene;

public class PowerActivity extends AppCompatActivity {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Metrics.reset();
        gameView = new GameView(this);
        gameView.setFullScreen();
        setContentView(gameView);

        new PowerScene(this, gameView).pushScene();
    }

    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        BaseScene.popAll();
        super.onDestroy();
    }
}
