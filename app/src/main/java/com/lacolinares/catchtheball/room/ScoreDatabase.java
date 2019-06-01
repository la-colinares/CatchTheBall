package com.lacolinares.catchtheball.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.lacolinares.catchtheball.util.AppConstants;

/**
 * Created by Colinares on 5/31/2019.
 */


@Database(entities = TblScores.class, version = AppConstants.DB_VER)
public abstract class ScoreDatabase extends RoomDatabase {

    public abstract ScoreDao getScoreDao();

}
