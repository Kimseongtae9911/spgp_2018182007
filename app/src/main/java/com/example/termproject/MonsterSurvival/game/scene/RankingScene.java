package com.example.termproject.MonsterSurvival.game.scene;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;

import com.example.termproject.MonsterSurvival.app.TitleActivity;
import com.example.termproject.MonsterSurvival.framework.BaseScene;
import com.example.termproject.MonsterSurvival.framework.GameView;
import com.example.termproject.MonsterSurvival.framework.interfaces.IGameObject;
import com.example.termproject.MonsterSurvival.framework.interfaces.ITouchable;
import com.example.termproject.MonsterSurvival.framework.objects.Button;
import com.example.termproject.MonsterSurvival.framework.objects.Sprite;
import com.example.termproject.MonsterSurvival.framework.util.Data;
import com.example.termproject.MonsterSurvival.framework.util.Metrics;
import com.example.termproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RankingScene extends BaseScene {
    private final float RANKING_ITEM_HEIGHT = 1.5f;
    private final float RANKING_ITEM_MARGIN = 0.5f;
    private final float RANKING_TEXT_SIZE = 0.4f;
    private ArrayList<String> rankingList = new ArrayList<>();
    private Paint paint = new Paint();

    public enum Layer {
        bg, touch, COUNT
    }

    public RankingScene(Context context, GameView view) {
        Metrics.setGameSize(9.0f, 16.0f);
        initLayers(RankingScene.Layer.COUNT);
        String json = Data.loadJSON(context, "ranking.json");
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    int score = jsonObject.getInt("score");
                    String rankingItem = String.format("%d. %s: Score: %d", i + 1, name, score);
                    rankingList.add(rankingItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Data.clearRanking(context);
        add(RankingScene.Layer.bg, new Sprite(R.mipmap.ranking, Metrics.game_width / 2, Metrics.game_height / 2, 9.0f, 9.0f));
        add(RankingScene.Layer.touch, new Button(R.mipmap.powerlobby_btn, Metrics.game_width / 2, 15.0f, 3.475f, 1.875f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                context.startActivity(new Intent(context, TitleActivity.class));
                return true;
            }
        }));

        paint.setColor(Color.BLACK);
        paint.setTextSize(RANKING_TEXT_SIZE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public boolean onTouchEvent(MotionEvent event) {
        ArrayList<IGameObject> objects = layers.get(PowerScene.Layer.touch.ordinal());
        for (IGameObject obj : objects) {
            if (!(obj instanceof ITouchable)) {
                continue;
            }
            boolean processed = ((ITouchable) obj).onTouchEvent(event);
            if (processed) return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float y = 1;

        for (int i = 0; i < rankingList.size(); i++) {
            String rankingItem = rankingList.get(i);
            for(int j = 0; j < rankingItem.length(); ++j) {
                canvas.drawText(String.valueOf(rankingItem.charAt(j)), 0.5f + j * 0.3f, y, paint);
            }
            y += (RANKING_ITEM_HEIGHT + RANKING_ITEM_MARGIN);
        }
    }
}
