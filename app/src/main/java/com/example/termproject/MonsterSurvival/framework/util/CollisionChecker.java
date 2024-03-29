package com.example.termproject.MonsterSurvival.framework.util;

import android.graphics.Canvas;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.Sound;
import com.example.termproject.MonsterSurvival.framework.interfaces.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.game.scene.MainScene;
import com.example.termproject.MonsterSurvival.game.monster.Monster;
import com.example.termproject.MonsterSurvival.game.player.Hero;
import com.example.termproject.MonsterSurvival.game.skill.Skill;
import com.example.termproject.MonsterSurvival.game.skill.SkillCircle;
import com.example.termproject.MonsterSurvival.game.skill.SkillFireBall;
import com.example.termproject.MonsterSurvival.game.skill.SkillFlyFire;
import com.example.termproject.MonsterSurvival.game.skill.SkillMissile;
import com.example.termproject.MonsterSurvival.game.skill.SkillThunder;
import com.example.termproject.R;

import java.util.ArrayList;
import java.util.Random;

public class CollisionChecker implements IGameObject {
    private Random randomCoin = new Random();
    Animation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private static final int SHAKE_DURATION = 500;

    public CollisionChecker() {
        shakeAnimation.setDuration(SHAKE_DURATION);
        shakeAnimation.setInterpolator(new CycleInterpolator(5));
    }

    private boolean collideOBB(IBoxCollidable obj1, IBoxCollidable obj2) {
        OrientedBoundingBox obb1 = obj1.getOBB();
        OrientedBoundingBox obb2 = obj2.getOBB();
        // Get the corners of each bounding box
        Vector2[] obb1Corners = getOBBVertices(obb1);
        Vector2[] obb2Corners = getOBBVertices(obb2);

        Vector2 axis = new Vector2((float) Math.cos(Math.toRadians(obb1.getRotationAngle())), (float) Math.sin(Math.toRadians(obb1.getRotationAngle())));
        for (int i = 0; i < 2; i++) {
            float[] obb1Projected = projectOntoAxis(obb1Corners, axis);
            float[] obb2Projected = projectOntoAxis(obb2Corners, axis);

            if (axisSeparation(obb1Projected, obb2Projected))
                return false;

            axis.set(-axis.y, axis.x);
        }

        return true;
    }

    private Vector2[] getOBBVertices(OrientedBoundingBox obb) {
        Vector2[] corners = new Vector2[4];
        float cos = (float) Math.cos(Math.toRadians(obb.getRotationAngle()));
        float sin = (float) Math.sin(Math.toRadians(obb.getRotationAngle()));

        corners[0] = new Vector2(obb.getCenterX() - obb.getHalfWidth() * cos - obb.getHalfHeight() * sin,
                obb.getCenterY() + obb.getHalfWidth() * sin - obb.getHalfHeight() * cos);
        corners[1] = new Vector2(obb.getCenterX() + obb.getHalfWidth() * cos - obb.getHalfHeight() * sin,
                obb.getCenterY() - obb.getHalfWidth() * sin - obb.getHalfHeight() * cos);
        corners[2] = new Vector2(obb.getCenterX() + obb.getHalfWidth() * cos + obb.getHalfHeight() * sin,
                obb.getCenterY() - obb.getHalfWidth() * sin + obb.getHalfHeight() * cos);
        corners[3] = new Vector2(obb.getCenterX() - obb.getHalfWidth() * cos + obb.getHalfHeight() * sin,
                obb.getCenterY() + obb.getHalfWidth() * sin + obb.getHalfHeight() * cos);

        return corners;
    }
    private float[] projectOntoAxis(Vector2[] corners, Vector2 axis) {
        float[] projections = new float[4];
        float min = Float.MAX_VALUE;
        float max = -Float.MAX_VALUE;

        for (int i = 0; i < 4; i++) {
            float dotProduct = corners[i].dotProduct(axis.x, axis.y);
            projections[i] = dotProduct;
            min = Math.min(min, dotProduct);
            max = Math.max(max, dotProduct);
        }

        return new float[] { min, max };
    }

    private boolean axisSeparation(float[] obb1Projected, float[] obb2Projected) {
        return obb1Projected[1] < obb2Projected[0] || obb2Projected[1] < obb1Projected[0];
    }

    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> monsters = scene.getObjectsAt(MainScene.Layer.monster);
        ArrayList<IGameObject> players = scene.getObjectsAt(MainScene.Layer.player);
        ArrayList<IGameObject> skills = scene.getObjectsAt(MainScene.Layer.skill);
        Hero player = (Hero)players.get(0);

        //Monster - Player
        if(!player.invincible) {
            for (int ei = monsters.size() - 1; ei >= 0; ei--) {
                Monster monster = (Monster) monsters.get(ei);

                if (collideOBB(monster, player)) {
                    player.invincible = true;
                    if (player.getBarrier()) {
                        player.setBarrier(false);
                        Sound.playEffect(R.raw.barrier, 4.0f);
                        break;
                    }
                    Sound.playEffect(R.raw.playerhit, 2.0f);
                    player.decreaseHp(monster.getPower());
                    if (!(shakeAnimation.hasStarted() && !shakeAnimation.hasEnded()))
                        scene.getView().startAnimation(shakeAnimation);
                    break;
                }
            }
        }

        //Skill - Monster
        for (int ei = monsters.size() - 1; ei >= 0; ei--) {
            Monster monster = (Monster) monsters.get(ei);
            for(int si = skills.size() - 1; si >= 0; si--) {
                Skill skill = (Skill) skills.get(si);
                if(collideOBB(monster, skill) && skill.getActive()) {
                    if (skill instanceof SkillMissile) {
                        skill.setActive(false);
                        scene.remove(skill);
                    }
                    else if(skill instanceof SkillFireBall) {
                        SkillFireBall fireball = (SkillFireBall)skill;
                        if(!fireball.collision) {
                            break;
                        }
                        fireball.collision = false;
                    }
                    else if(skill instanceof SkillCircle) {
                        SkillCircle circle = (SkillCircle)skill;
                        if(!circle.collision) {
                            break;
                        }
                        circle.collision = false;
                    }
                    else if(skill instanceof SkillFlyFire) {
                        SkillFlyFire flyFire = (SkillFlyFire)skill;
                        if(!flyFire.collision) {
                            break;
                        }
                        flyFire.collision = false;
                    }
                    else if(skill instanceof SkillThunder) {
                        SkillThunder thunder = (SkillThunder) skill;
                        if(!thunder.collision) {
                            break;
                        }
                        thunder.collision = false;
                    }
                    boolean remove = monster.decreaseLife(skill.getPower());
                    if(remove) {
                        scene.remove(monster);
                        scene.getPlayer().GainExp(monster.getLevel() * 10);
                        scene.addScore(monster.getScore());

                        Sound.playEffect(R.raw.monsterdie, 1.0f);
                        scene.getCoin().addCoin(randomCoin.nextInt(monster.getLevel() + 2));
                    }
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
