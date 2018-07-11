package com.yymstaygold.lostandfound.client.Adapter;

import com.victor.library.wheelview.IWheelviewAdapter;

import java.util.List;

public class WheelViewAdapter implements IWheelviewAdapter<String> {

    private List<String> mList;

    public WheelViewAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public String getItemeTitle(int i) {
        if (mList != null) {
            return mList.get(i);
        } else {
            return "";
        }
    }

    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public String get(int index) {
        if (mList != null && index >= 0 && index < mList.size()) {
            return mList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        if (mList != null) {
            mList.clear();
        }
    }
}
