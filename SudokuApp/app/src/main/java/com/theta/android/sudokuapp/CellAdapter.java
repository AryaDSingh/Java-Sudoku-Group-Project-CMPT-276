package com.theta.android.sudokuapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import androidx.core.content.ContextCompat;


public class CellAdapter extends BaseAdapter {
    private final Context mContext;
    private final int mCount = 9*9;

    public CellAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        Button gridCell;
        String boardLayout = //should get this from a txt file
                "000000000" +
                "000003085" +
                "001020000" +
                "000507000" +
                "004000100" +
                "090000000" +
                "500000073" +
                "002010000" +
                "000040009";

        if (convertView == null) {
            gridCell = new Button(mContext);
            gridCell.setLayoutParams(new GridView.LayoutParams(100, 100));
            gridCell.setText(boardLayout.substring(pos, pos+1));
            gridCell.setBackgroundColor(ContextCompat.getColor(mContext, R.color.purple_200));

        }
        else {
            gridCell = (Button) convertView;
        }
        return gridCell;
    }

}
