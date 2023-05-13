package com.example.termproject.MonsterSurvival.framework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.BuildConfig;

import java.sql.Array;
import java.util.ArrayList;

public class BaseScene {
    protected Paint transparentPaint = new Paint();
    private static ArrayList<BaseScene> stack = new ArrayList<>();
    public static float frameTime;
    protected static Handler handler = new Handler();
    private static Paint bboxPaint;
    protected boolean pause = false;
    protected ArrayList<ArrayList<IGameObject>> layers = new ArrayList<>();

    protected ArrayList<IGameObject> pauseObject = new ArrayList<>();
    public static BaseScene getTopScene() {
        int top = stack.size() - 1;
        if (top < 0) return null;
        return stack.get(top);
    }

    public static void popAll() {
        while (!stack.isEmpty()) {
            BaseScene scene = getTopScene();
            scene.popScene();
        }
    }

    public int pushScene() {
        stack.add(this);
        return stack.size();
    }

    public void popScene() {
        stack.remove(this);
        // TODO: additional callback should be called
    }

    protected <E extends Enum<E>> void initLayers(E countEnum) {
        int layerCount = countEnum.ordinal();
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public <E extends Enum<E>> void add(E layerEnum, IGameObject gobj) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<IGameObject> objects = layers.get(layerEnum.ordinal());
                objects.add(gobj);
            }
        });
    }

    public void addPauseObject(IGameObject obj) {
        pauseObject.add(obj);
    }
    public int count() {
        int count = 0;
        for (ArrayList<IGameObject> objects: layers) {
            count += objects.size();
        }
        return count;
    }
    public void update(long elapsedNanos) {
        frameTime = elapsedNanos / 1_000_000_000f;
        for (ArrayList<IGameObject> objects: layers) {
            for (IGameObject gobj : objects) {
                gobj.update();
            }
        }
    }

    public void draw(Canvas canvas) {
        for (ArrayList<IGameObject> objects: layers) {
            for (IGameObject gobj : objects) {
                gobj.draw(canvas);
            }
        }

        if (BuildConfig.DEBUG) {
            if (bboxPaint == null) {
                bboxPaint = new Paint();
                bboxPaint.setStyle(Paint.Style.STROKE);
                bboxPaint.setColor(Color.RED);
            }
            for (ArrayList<IGameObject> objects: layers) {
                for (IGameObject gobj : objects) {
                    if (gobj instanceof IBoxCollidable) {
                        RectF rect = ((IBoxCollidable) gobj).getCollisionRect();
                        canvas.drawRect(rect, bboxPaint);
                    }
                }
            }
        }

        if(pause) {
            canvas.drawRect(0.f, 0.f, Metrics.game_width, Metrics.game_height, transparentPaint);
            for(IGameObject obj : pauseObject) {
                obj.draw(canvas);
            }
        }
    }

    public <E extends Enum> ArrayList<IGameObject> getObjectsAt(E layerEnum) {
        return layers.get(layerEnum.ordinal());
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void remove(IGameObject gobj) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (ArrayList<IGameObject> objects: layers) {
                    boolean removed = objects.remove(gobj);
                    if (removed) {
                        if (gobj instanceof IRecyclable) {
                            RecycleBin.collect((IRecyclable) gobj);
                        }
                        break;
                    }
                }
            }
        });
    }
}
