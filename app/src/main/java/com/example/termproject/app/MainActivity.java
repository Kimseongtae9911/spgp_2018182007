package com.example.termproject.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.termproject.framework.BaseScene;
import com.example.termproject.framework.GameView;
import com.example.termproject.game.MainScene;
public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        //new MainScene().pushScene();
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