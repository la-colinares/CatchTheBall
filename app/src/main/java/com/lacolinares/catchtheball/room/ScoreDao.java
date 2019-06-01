package com.lacolinares.catchtheball.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lacolinares.catchtheball.util.AppConstants;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Colinares on 5/31/2019.
 */
@Dao
public interface ScoreDao {

    @Query("SELECT * FROM " + AppConstants.TBL_NAME + " ORDER BY id DESC")
    Flowable<List<TblScores>> getScores();

    @Insert
    void insert(TblScores tblScores);
}
