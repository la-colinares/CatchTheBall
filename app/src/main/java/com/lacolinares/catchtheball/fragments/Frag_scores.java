package com.lacolinares.catchtheball.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lacolinares.catchtheball.R;
import com.lacolinares.catchtheball.remote.DataRepository;
import com.lacolinares.catchtheball.remote.GetScoreCallBack;
import com.lacolinares.catchtheball.room.TblScores;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Colinares on 5/28/2019.
 */

public class Frag_scores extends Fragment implements GetScoreCallBack {

    @BindView(R.id.table_layout)
    TableView mTable;
    Unbinder unbinder;
    private View mView;

    private DataRepository mDataRepository;

    private String[] mHeaders = {"Player", "Score", "Time", "Date"};
    private SimpleTableHeaderAdapter mTableHeaderAdapter;
    private SimpleTableDataAdapter mTableDataAdapter;

    public Frag_scores() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.frag_scores, null, false);
        unbinder = ButterKnife.bind(this, mView);

        mTable.setColumnCount(4);

        mTableHeaderAdapter = new SimpleTableHeaderAdapter(getContext(), mHeaders);
        mTableHeaderAdapter.setTextColor(getResources().getColor(R.color.colorWhite));
        mTable.setHeaderBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTable.setHeaderAdapter(mTableHeaderAdapter);

        mDataRepository = new DataRepository(getContext());
        mDataRepository.getScores(this);

        return mView;
    }

    @Override
    public void getScores(List<TblScores> scoresList) {
        String[][] tableData = new String[scoresList.size()][4];

        for (int i = 0; i < scoresList.size(); i++){
            tableData[i][0] = scoresList.get(i).getName();
            tableData[i][1] = scoresList.get(i).getScore()+"";
            tableData[i][2] = scoresList.get(i).getTime();
            tableData[i][3] = scoresList.get(i).getDate();
        }
        mTableDataAdapter = new SimpleTableDataAdapter(getContext(), tableData);
        mTableDataAdapter.setTextColor(getResources().getColor(R.color.colorWhite));
        mTable.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTable.setDataAdapter(mTableDataAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
