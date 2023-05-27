package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.game.MainScene;
import com.example.termproject.MonsterSurvival.game.monster.Monster;

import java.util.ArrayList;

public class SkillGenerator implements IGameObject {
    private float[] SKILL_GEN_INTERVAL = {3.0f, 5.0f, 3.0f, 9.0f, 9.0f, 20.0f};
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

        if(((MainScene)BaseScene.getTopScene()).getPlayer().getBarrier()) {
            times[5] -= BaseScene.frameTime;
        }
    }

    private void generate(int num) {
        MainScene scene = (MainScene)BaseScene.getTopScene();
        switch(num) {
            case 0:
                generateMissile(scene);
                break;
            case 1:
                generateFireBall(scene);
                break;
            case 2:
                generateCircle(scene);
                break;
            case 3:
                break;
            case 4:
                generateThunder(scene);
                break;
            case 5:
                generateBarrier(scene);
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
    private void generateFireBall(MainScene scene) {
        double angle = Math.random() * 2 * Math.PI;

        float dx = (float) Math.cos(angle);
        float dy = (float) Math.sin(angle);
        float magnitude = (float)Math.sqrt(dx * dx + dy * dy);
        if (magnitude != 0) {
            dx /= magnitude;
            dy /= magnitude;
        }

        scene.add(MainScene.Layer.skill, SkillFireBall.get(scene.getPlayerX(), scene.getPlayerY(), dx, dy, scene.getPlayerPower()));
    }
    private void generateCircle(MainScene scene) {
        scene.add(MainScene.Layer.skill, SkillCircle.get(scene.getPlayerX(), scene.getPlayerY(), scene.getPlayerPower()));
    }
    private void generateThunder(MainScene scene) {
        double angle = Math.random() * 2 * Math.PI;

        float dx = (float) Math.cos(angle);
        float dy = (float) Math.sin(angle);
        float magnitude = (float)Math.sqrt(dx * dx + dy * dy);
        if (magnitude != 0) {
            dx /= magnitude;
            dy /= magnitude;
        }

        scene.add(MainScene.Layer.skill, SkillThunder.get(scene.getPlayerX(), scene.getPlayerY(), dx, dy, scene.getPlayerPower()));
    }

    private void generateBarrier(MainScene scene) {
        scene.add(MainScene.Layer.skill, SkillBarrier.get(scene.getPlayerX(), scene.getPlayerY()));
    }

    @Override
    public void draw(Canvas canvas) {}
}
