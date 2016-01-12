package com.example.chengqi.mycoderepo.expert;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chengqi.mycoderepo.R;

/**
 * Created by archermind on 12/22/15.
 */
public class MyFragment02 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View child = inflater.inflate(R.layout.fragment02, container, false);
        return child;
    }
}