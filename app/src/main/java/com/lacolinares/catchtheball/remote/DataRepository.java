package com.lacolinares.catchtheball.remote;

import androidx.room.Room;
import android.content.Context;

import com.lacolinares.catchtheball.room.ScoreDatabase;
import com.lacolinares.catchtheball.room.TblScores;
import com.lacolinares.catchtheball.util.AppConstants;
import com.lacolinares.catchtheball.util.DateTimeUtil;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Colinares on 5/31/2019.
 */

public class DataRepository {

    private Context mContext;
    private ScoreDatabase mScoreDatabase;

    public DataRepository(Context mContext) {
        this.mContext = mContext;
        mScoreDatabase = Room.databaseBuilder(mContext, ScoreDatabase.class, AppConstants.DB_NAME).build();
    }

    public void insertScore(final InsertCallBack callback, final int score) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                TblScores tblScores = new TblScores(AppConstants.DEF_USER, score, DateTimeUtil.getTime(), DateTimeUtil.getDate());
                mScoreDatabase.getScoreDao().insert(tblScores);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onComplete() {
                        callback.onSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError();
                    }
                });
    }

    public void getScores(final GetScoreCallBack callBack){
        mScoreDatabase.getScoreDao().getScores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack::getScores);
    }

}
