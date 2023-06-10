package com.example.termproject.MonsterSurvival.game.player;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Sound;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;
import com.example.termproject.R;

import java.util.Random;

public class StatGenerator implements IGameObject {
    static enum Stat {power, speed, defense, cooltime};
    static Random r = new Random();
    static final float OFFSET = 0.4f;
    static final float STAT_WIDTH = 2.49f; //274
    static final float STAT_HEIGHT = 3.88f; //427

    static void generateStatButtons() {
        MainScene scene = (MainScene)BaseScene.getTopScene();
        int skip = r.nextInt(Stat.cooltime.ordinal() + 1);

        int count = 0;
        for(int i = 0; i < Stat.cooltime.ordinal() + 1; ++i) {
            if(skip == i)
                continue;
            switch (i) {
                case 0:
                    scene.addLevelUpObject(Power.get(R.mipmap.power, OFFSET + STAT_WIDTH / 2 + (OFFSET + STAT_WIDTH) * count, Metrics.game_height / 2, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            scene.setLevelUp(false);
                            scene.getPlayer().addPower(7);
                            scene.clearLevelUpObject();
                            Sound.playEffect(R.raw.stat, 2.0f);
                            return true;
                        }
                    }));
                    break;
                case 1:
                    scene.addLevelUpObject(Speed.get(R.mipmap.speed, OFFSET + STAT_WIDTH / 2 + (OFFSET + STAT_WIDTH) * count, Metrics.game_height / 2, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            scene.setLevelUp(false);
                            scene.getPlayer().addSpeed(0.5f);
                            scene.clearLevelUpObject();
                            Sound.playEffect(R.raw.stat, 2.0f);
                            return true;
                        }
                    }));
                    break;
                case 2:
                    scene.addLevelUpObject(Defense.get(R.mipmap.defense, OFFSET + STAT_WIDTH / 2 + (OFFSET + STAT_WIDTH) * count, Metrics.game_height / 2, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            scene.setLevelUp(false);
                            scene.getPlayer().addDefense(3);
                            scene.clearLevelUpObject();
                            Sound.playEffect(R.raw.stat, 2.0f);
                            return true;
                        }
                    }));
                    break;
                case 3:
                    scene.addLevelUpObject(CoolTime.get(R.mipmap.cooltime, OFFSET + STAT_WIDTH / 2 + (OFFSET + STAT_WIDTH) * count, Metrics.game_height / 2, STAT_WIDTH, STAT_HEIGHT, new Button.Callback() {
                        @Override
                        public boolean onTouch(Button.Action action) {
                            scene.setLevelUp(false);
                            scene.getPlayer().addCoolTime(3);
                            scene.clearLevelUpObject();
                            Sound.playEffect(R.raw.stat, 2.0f);
                            return true;
                        }
                    }));
                    break;
            }
            count++;
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Canvas canvas) {}
}
