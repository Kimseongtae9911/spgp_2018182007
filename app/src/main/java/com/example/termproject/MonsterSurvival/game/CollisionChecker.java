package com.example.termproject.MonsterSurvival.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.IGameObject;

import java.util.ArrayList;

public class CollisionChecker implements IGameObject {
    Animation shakeAnimation = new TranslateAnimation(0, 10, 0, 0);
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private static final int SHAKE_DURATION = 500;

    public CollisionChecker() {
        shakeAnimation.setDuration(SHAKE_DURATION);
        shakeAnimation.setInterpolator(new CycleInterpolator(5));
    }

    private boolean collides(IBoxCollidable obj1, IBoxCollidable obj2) {
        RectF r1 = obj1.getCollisionRect();
        RectF r2 = obj2.getCollisionRect();

        if (r1.left > r2.right) return false;
        if (r1.top > r2.bottom) return false;
        if (r1.right < r2.left) return false;
        if (r1.bottom < r2.top) return false;

        return true;
    }
    @Override
    public void update() {
        MainScene scene = (MainScene) BaseScene.getTopScene();
        ArrayList<IGameObject> monsters = scene.getObjectsAt(MainScene.Layer.monster);
        ArrayList<IGameObject> players = scene.getObjectsAt(MainScene.Layer.player);
        for (int ei = monsters.size() - 1; ei >= 0; ei--) {
            Monster monster = (Monster) monsters.get(ei);
            Hero player = (Hero)players.get(0);
            if (collides(monster, player)) {
                Log.d(TAG, "Decrease Player HP");
                player.decreaseHp(10);
                if(!(shakeAnimation.hasStarted() && !shakeAnimation.hasEnded()))
                    scene.getView().startAnimation(shakeAnimation);
                break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
