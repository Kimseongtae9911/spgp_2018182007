package com.example.termproject.MonsterSurvival.game.skill;

import android.graphics.Rect;

import com.example.termproject.MonsterSurvival.framework.objects.AnimSprite;
import com.example.termproject.MonsterSurvival.framework.interfaces.IBoxCollidable;
import com.example.termproject.MonsterSurvival.framework.interfaces.IRecyclable;
import com.example.termproject.MonsterSurvival.framework.util.OrientedBoundingBox;

public class Skill extends AnimSprite implements IRecyclable, IBoxCollidable {
    protected int imageSize = 0;
    protected OrientedBoundingBox obb = new OrientedBoundingBox();
    protected Rect[] srcRects;
    protected boolean active = false;
    protected int power = 0;
    public Skill(int bitmapResId, float cx, float cy, float width, float height, int heightFrame, int fps, int frameCount) {
        super(bitmapResId, cx, cy, width, height, heightFrame, fps, frameCount);
        imageSize = bitmap.getWidth() / 2;
    }

    @Override
    public OrientedBoundingBox getOBB() {return obb;}
    @Override
    public void onRecycle() {
    }

    public boolean getActive() {return active;}
    public void setActive(boolean active) {this.active = active;}
    public int getPower() {return power;}
}
