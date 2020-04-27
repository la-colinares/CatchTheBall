package com.lacolinares.catchtheball.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lacolinares.catchtheball.util.AppConstants;

/**
 * Created by Colinares on 5/31/2019.
 */


@Database(entities = TblScores.class, version = AppConstants.DB_VER, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {

    public abstract ScoreDao getScoreDao();

}
