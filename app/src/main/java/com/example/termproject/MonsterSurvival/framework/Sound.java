package com.example.termproject.MonsterSurvival.framework;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sound {
    private static final int MAX_STREAMS = 8;
    private static List<Integer> activeStreams = new ArrayList<>();
    protected static MediaPlayer mediaPlayer;
    protected static SoundPool soundPool;

    public static void playMusic(int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = MediaPlayer.create(GameView.view.getContext(), resId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }
    public static void stopMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.stop();
        mediaPlayer = null;
    }
    public static void pauseMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.pause();
    }
    public static void resumeMusic() {
        if (mediaPlayer == null) return;
        mediaPlayer.start();
    }

    private static HashMap<Integer, Integer> soundIdMap = new HashMap<>();
    public static void playEffect(int resId, float volume) {
        SoundPool pool = getSoundPool();
        int soundId;
        if (soundIdMap.containsKey(resId)) {
            soundId = soundIdMap.get(resId);
        } else {
            soundId = pool.load(GameView.view.getContext(), resId, 1);
            soundIdMap.put(resId, soundId);
        }

        int priority = 1;
        int loop = 0;
        float rate = 1f;
        int streamId = pool.play(soundId, volume, volume, priority, loop, rate);

        if(streamId != 0) {
            activeStreams.add(streamId);
            checkMaxStreams();
        }
    }

    private static void checkMaxStreams() {
        if (activeStreams.size() > MAX_STREAMS) {
            int oldestStreamId = activeStreams.get(0);
            SoundPool pool = getSoundPool();
            pool.stop(oldestStreamId);
            activeStreams.remove(0);
        }
    }

    private static SoundPool getSoundPool() {
        if (soundPool != null) return soundPool;

        AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attrs)
                .setMaxStreams(MAX_STREAMS)
                .build();
        return soundPool;
    }
}
