package com.lacolinares.catchtheball.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.lacolinares.catchtheball.R;

/**
 * Created by Colinares on 5/31/2019.
 */

public class SoundPlayer {

    private AudioAttributes mAudioAttributes;
    private final int SOUND_POOL_MAX = 3;

    private static SoundPool mSoundPool;
    private static int mHitCyanSound;
    private static int mHitWhiteSound;
    private static int mHitRedSound;

    public SoundPlayer(Context context){
        // SoundPool is deprecated in API lvl 21 (Lollipop)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mAudioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(mAudioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        }else {
            mSoundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        mHitCyanSound = mSoundPool.load(context, R.raw.cyan, 1);
        mHitWhiteSound = mSoundPool.load(context, R.raw.white, 1);
        mHitRedSound = mSoundPool.load(context, R.raw.red, 1);
    }

    public void playHitCyanSound(){
        mSoundPool.play(mHitCyanSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playHitWhiteSound(){
        mSoundPool.play(mHitWhiteSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void playHitRedSound(){
        mSoundPool.play(mHitRedSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
