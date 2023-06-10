package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Canvas;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Sound;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;
import com.example.termproject.MonsterSurvival.game.monster.Monster;
import com.example.termproject.R;

import java.util.ArrayList;

public class SkillGenerator implements IGameObject {
    private float[] SKILL_GEN_INTERVAL = {3.0f, 5.0f, 9.0f, 15.0f, 9.0f, 20.0f};
    private static final String TAG = Monster.class.getSimpleName();
    private float[] times = {0.f, 0.f, 0.f, 0.f, 0.f, 0.f};

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        float coolTime = (100 - scene.getPlayer().getCoolTime()) * 0.01f;
        for(int i = 0; i < times.length; ++i) {
            times[i] += BaseScene.frameTime;
            if (times[i] > SKILL_GEN_INTERVAL[i] * coolTime) {
                generate(i);
                times[i] = 0.f;
            }
        }

        if(((MainScene)BaseScene.getTopScene()).getPlayer().getBarrier()) {
            times[5] -= BaseScene.frameTime;
        }
    }

    private void generate(int num) {
        MainScene scene = (MainScene)BaseScene.getTopScene();
        switch(num) {
            case 0:
                generateMissile(scene);
                Sound.playEffect(R.raw.missile, 1.0f);
                break;
            case 1:
                generateFireBall(scene);
                Sound.playEffect(R.raw.fireball, 1.5f);
                break;
            case 2:
                generateCircle(scene);
                Sound.playEffect(R.raw.circle, 3.0f);
                break;
            case 3:
                generateFlyFire(scene);
                Sound.playEffect(R.raw.flyfire, 4.0f);
                break;
            case 4:
                generateThunder(scene);
                Sound.playEffect(R.raw.thunder, 1.0f);
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
    private void generateFlyFire(MainScene scene) {
        float playerX = scene.getPlayerX();
        float playerY = scene.getPlayerY();

        float distance = 2.0f;

        for (int i = 0; i < 4; i++) {
            float angle = (float) (Math.random() * 2 * Math.PI);
            float offsetX = (float) (Math.cos(angle) * distance);
            float offsetY = (float) (Math.sin(angle) * distance);
            float fireX = playerX + offsetX;
            float fireY = playerY + offsetY;

            scene.add(MainScene.Layer.skill, SkillFlyFire.get(fireX, fireY, scene.getPlayerPower()));
        }
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
