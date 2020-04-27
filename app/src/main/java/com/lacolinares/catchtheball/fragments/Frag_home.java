package com.lacolinares.catchtheball.fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lacolinares.catchtheball.R;
import com.lacolinares.catchtheball.remote.InsertCallBack;
import com.lacolinares.catchtheball.remote.DataRepository;
import com.lacolinares.catchtheball.util.SharedPrefUtil;
import com.lacolinares.catchtheball.util.SoundPlayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Colinares on 5/28/2019.
 */

public class Frag_home extends Fragment implements InsertCallBack {

    private final String TAG = Frag_home.class.getSimpleName();

    @BindView(R.id.label_score)
    TextView mLabelScore;
    @BindView(R.id.game_box)
    ImageView mGameBox;
    @BindView(R.id.btn_play)
    Button mBtnPlay;
    @BindView(R.id.btn_quit)
    Button mBtnQuit;
    @BindView(R.id.start_layout)
    LinearLayout mStartLayout;
    @BindView(R.id.game_frame)
    FrameLayout mGameFrame;
    Unbinder unbinder;
    @BindView(R.id.img_red)
    ImageView mImageRed;
    @BindView(R.id.img_cyan)
    ImageView mImageCyan;
    @BindView(R.id.img_white)
    ImageView mImageWhite;
    @BindView(R.id.label_high_score)
    TextView labelHighScore;
    private View mView;

    private int mFrameHeight, mFrameWidth, mInitFrameWidth;

    //Image
    private Drawable mImageBoxRight, mImageBoxLeft;

    //Size
    private int mBoxSize;

    //Position
    private float mBoxX, mBoxY;
    private float mRedX, mRedY;
    private float mCyanX, mCyanY;
    private float mWhiteX, mWhiteY;

    //Score
    private int mScore, mHighScore, mTimeCount;

    //Class
    private Timer mTimer;
    private Handler mHandler = new Handler();
    private SoundPlayer mSoundPlayer;

    //Status
    private boolean start_flag = false;
    private boolean action_flag = false;
    private boolean white_flag = false;

    private SharedPrefUtil mSharedPref;

    private DataRepository mDataRepository;

    public Frag_home() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_home, null, false);
        unbinder = ButterKnife.bind(this, mView);

        mSoundPlayer = new SoundPlayer(getContext());
        mDataRepository = new DataRepository(getContext());

        mImageBoxLeft = getActivity().getResources().getDrawable(R.drawable.box_left);
        mImageBoxRight = getActivity().getResources().getDrawable(R.drawable.box_right);

        mSharedPref = new SharedPrefUtil(getContext());

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHighScore = mSharedPref.getHighScore();
        labelHighScore.setText("High Score: " + mHighScore);

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (start_flag) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        action_flag = true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        action_flag = false;
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.btn_play, R.id.btn_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                start_flag = true;
                mStartLayout.setVisibility(View.INVISIBLE);

                if (mFrameHeight == 0) {
                    mFrameHeight = mGameFrame.getHeight();
                    mFrameWidth = mGameFrame.getWidth();
                    mInitFrameWidth = mFrameWidth;

                    mBoxSize = mGameBox.getHeight();
                    mBoxX = mGameBox.getX();
                    mBoxY = mGameBox.getY();
                }

                mFrameWidth = mInitFrameWidth;

                mGameBox.setX(0.0f);
                mImageRed.setY(3000.0f);
                mImageCyan.setY(3000.0f);
                mImageWhite.setY(3000.0f);

                mRedY = mImageRed.getY();
                mCyanY = mImageCyan.getY();
                mWhiteY = mImageWhite.getY();

                components_visible();

                mTimeCount = 0;
                mScore = 0;
                mLabelScore.setText("Score: 0");

                mTimer = new Timer();
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (start_flag) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try{
                                        change_position();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }, 0, 20);
                break;
            case R.id.btn_quit:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    getActivity().finishAndRemoveTask();
                }else {
                    getActivity().finish();
                }
                break;
        }
    }

    private void change_position() {

        //Add time count
        mTimeCount += 20;

        // Cyan
        mCyanY += 12;

        float cyanCenterX = mCyanX + mImageCyan.getWidth() / 2;
        float cyanCenterY = mCyanY + mImageCyan.getHeight() / 2;

        if (hitCheck(cyanCenterX, cyanCenterY)){
            mCyanY = mFrameHeight + 100;
            mScore += 10;
            mSoundPlayer.playHitCyanSound();
        }

        if (mCyanY > mFrameHeight){
            mCyanY = -100;
            mCyanX = (float) Math.floor(Math.random() * (mFrameWidth - mImageCyan.getWidth()));
        }
        mImageCyan.setX(mCyanX);
        mImageCyan.setY(mCyanY);

        // White
        if (!white_flag && mTimeCount % 10000 == 0){
            white_flag = true;
            mWhiteY = -20;
            mWhiteX = (float) Math.floor(Math.random() * (mFrameWidth - mImageWhite.getWidth()));
        }

        if (white_flag){
            mWhiteY += 20;
            float whiteCenterX = mWhiteX + mImageWhite.getWidth() / 2;
            float whiteCenterY = mWhiteY + mImageWhite.getHeight() / 2;

            if (hitCheck(whiteCenterX, whiteCenterY)){
                mWhiteY = mFrameHeight + 30;
                mScore += 30;
                //Change Frame Width
                if (mInitFrameWidth > mFrameWidth * 110 / 100){
                    mFrameWidth = mFrameWidth * 110 / 100;
                    change_frame_width(mFrameWidth);
                }
                mSoundPlayer.playHitWhiteSound();
            }
            if (mWhiteY > mFrameHeight) white_flag = false;
            mImageWhite.setX(mWhiteX);
            mImageWhite.setY(mWhiteY);

        }

        // Black
        mRedY += 18;
        float redCenterX = mRedX + mImageRed.getWidth() / 2;
        float redCenterY = mRedY + mImageRed.getHeight() / 2;

        if (hitCheck(redCenterX, redCenterY)){
            mRedY = mFrameHeight + 100;

            // Change Frame Width
            mFrameWidth = mFrameWidth * 80 / 100;
            change_frame_width(mFrameWidth);
            mSoundPlayer.playHitRedSound();
            if (mFrameWidth <= mBoxSize){
                game_over();
            }
        }

        if (mRedY > mFrameHeight){
            mRedY = -100;
            mRedX = (float) Math.floor(Math.random() * (mFrameWidth - mImageRed.getWidth()));
        }

        mImageRed.setX(mRedX);
        mImageRed.setY(mRedY);

        //Move Game Box
        if (action_flag) {
            //Touching
            mBoxX += 14;
            mGameBox.setImageDrawable(mImageBoxRight);
        } else {
            //Releasing
            mBoxX -= 14;
            mGameBox.setImageDrawable(mImageBoxLeft);
        }

        //Check game box position
        if (mBoxX < 0) {
            mBoxX = 0;
            mGameBox.setImageDrawable(mImageBoxRight);
        }

        if (mFrameWidth - mBoxSize < mBoxX) {
            mBoxX = mFrameWidth - mBoxSize;
            mGameBox.setImageDrawable(mImageBoxLeft);
        }

        mGameBox.setX(mBoxX);

        mLabelScore.setText("Score: " + mScore);
    }

    private void game_over() {
        Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT).show();
        mTimer.cancel();
        mTimer = null;
        start_flag = false;

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        change_frame_width(mInitFrameWidth);
        components_invisible();

        if (mScore > mHighScore){
            mHighScore = mScore;
            labelHighScore.setText("High Score: " + mHighScore);

            mSharedPref.setHighScore(mHighScore);

        }
        mDataRepository.insertScore(this, mScore);
    }

    private void change_frame_width(int frame_width) {
        ViewGroup.LayoutParams params = mGameFrame.getLayoutParams();
        params.width = frame_width;
        mGameFrame.setLayoutParams(params);

    }

    private boolean hitCheck(float x, float y) {
        return mBoxX <= x && x <= mBoxX + mBoxSize && mBoxY <= y && y <= mFrameHeight;
    }

    private void components_visible() {
        mLabelScore.setVisibility(View.VISIBLE);
        mGameBox.setVisibility(View.VISIBLE);
        mImageRed.setVisibility(View.VISIBLE);
        mImageCyan.setVisibility(View.VISIBLE);
        mImageWhite.setVisibility(View.VISIBLE);

    }
    private void components_invisible() {

        mStartLayout.setVisibility(View.VISIBLE);
        mLabelScore.setVisibility(View.INVISIBLE);
        mGameBox.setVisibility(View.INVISIBLE);
        mImageRed.setVisibility(View.INVISIBLE);
        mImageCyan.setVisibility(View.INVISIBLE);
        mImageWhite.setVisibility(View.INVISIBLE);

    }


    @Override
    public void onSuccess() {
        Log.e(TAG, "Success");
    }

    @Override
    public void onError() {
        Log.e(TAG, "Error while saving score.");
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
