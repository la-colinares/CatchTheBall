package com.lacolinares.catchtheball.remote;

import com.lacolinares.catchtheball.room.TblScores;

import java.util.List;

/**
 * Created by Colinares on 5/31/2019.
 */

public interface GetScoreCallBack {

    void getScores(List<TblScores> scoresList);

}
