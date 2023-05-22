package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IGameObject;
import com.example.termproject.MonsterSurvival.game.Hero;
import com.example.termproject.MonsterSurvival.game.MainScene;
import com.example.termproject.MonsterSurvival.game.Monster;

import java.util.ArrayList;

public class SkillGenerator implements IGameObject {
    private float[] SKILL_GEN_INTERVAL = {3.0f, 5.0f, 9.0f, 9.0f, 20.0f, 15.0f};
    private static final String TAG = Monster.class.getSimpleName();
    private float[] times = {0.f, 0.f, 0.f, 0.f, 0.f, 0.f};
    private float difficultyTime = 0;

    @Override
    public void update() {
        for(int i = 0; i < times.length; ++i) {
            times[i] += BaseScene.frameTime;
            if (times[i] > SKILL_GEN_INTERVAL[i]) {
                generate(i);
                times[i] -= SKILL_GEN_INTERVAL[i];
            }
        }
        difficultyTime += BaseScene.frameTime;

    }

    private void generate(int num) {
        MainScene scene = (MainScene)BaseScene.getTopScene();
        switch(num) {
            case 0:
                generateMissile(scene);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                break;
        }
    }

    private void generateMissile(MainScene scene) {
        float minDist = 100;
        int monID = 0;
        float x = scene.getPlayerX();
        float y = scene.getPlayerY();
        ArrayList<IGameObject> monsters = scene.getObjectsAt(MainScene.Layer.monster);
        if(monsters.size() ==0)
            return;

        for (int ei = monsters.size() - 1; ei >= 0; ei--) {
            Monster monster = (Monster) monsters.get(ei);
            float dist = (float)Math.sqrt(Math.pow(monster.getX() - x, 2.f) + Math.pow(monster.getY() - y, 2.f));
            if( dist < minDist) {
                minDist = dist;
                monID = ei;
            }
        }

        Monster monster = (Monster)monsters.get(monID);
        float monsterX = monster.getX();
        float monsterY = monster.getY();
        float dirX = monsterX - x;
        float dirY = monsterY - y;
        float magnitude = (float) Math.sqrt(dirX * dirX + dirY * dirY);
        if (magnitude != 0) {
            dirX /= magnitude;
            dirY /= magnitude;
        }

        scene.add(MainScene.Layer.skill, SkillMissile.get(x, y, dirX, dirY, scene.getPlayerPower()));
    }

    @Override
    public void draw(Canvas canvas) {}
}
