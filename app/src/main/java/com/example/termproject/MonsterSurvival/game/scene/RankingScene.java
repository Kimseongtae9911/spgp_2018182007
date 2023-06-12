package com.example.termproject.MonsterSurvival.game.scene;

import android.content.Context;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.R;

public class RankingScene extends BaseScene {
    GameView view;
    public enum Layer {
        touch, COUNT
    }

    public RankingScene(Context context, GameView view) {
        Metrics.setGameSize(9.0f, 16.0f);
        initLayers(RankingScene.Layer.COUNT);
        this.view = view;

        add(Layer.touch, new Button(R.mipmap.power, Metrics.game_width / 2 - 1.0f, Metrics.game_height / 2 - 2.0f, 3.91f, 6.1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.defense, Metrics.game_width / 2 + 1.0f, Metrics.game_height / 2 - 2.0f, 3.91f, 6.1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.speed, Metrics.game_width / 2 - 1.0f, Metrics.game_height / 2 + 2.0f, 3.91f, 6.1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.cooltime, Metrics.game_width / 2 + 1.0f, Metrics.game_height / 2 + 2.0f, 3.91f, 6.1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                return true;
            }
        }));
    }
}
