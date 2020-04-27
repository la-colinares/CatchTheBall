package com.lacolinares.catchtheball.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.lacolinares.catchtheball.util.AppConstants;

/**
 * Created by Colinares on 5/31/2019.
 */

@Entity(tableName = AppConstants.TBL_NAME)
public class TblScores {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "col_name")
    public String name;

    @ColumnInfo(name = "col_score")
    public int score;

    @ColumnInfo(name = "col_time")
    public String time;

    @ColumnInfo(name = "col_date")
    public String date;

    public TblScores(String name, int score, String time, String date) {
        this.name = name;
        this.score = score;
        this.time = time;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
