package com.example.ivanovnv.spacex.di.launch;

import android.support.v7.widget.helper.ItemTouchHelper;

import javax.inject.Inject;
import javax.inject.Provider;

public class ItemTouchHelperProvider implements Provider<ItemTouchHelper> {

    private ItemTouchHelper.Callback mCallback;

    @Inject
    public ItemTouchHelperProvider(ItemTouchHelper.Callback callback) {
        mCallback = callback;
    }

    @Override
    public ItemTouchHelper get() {
        return new ItemTouchHelper(mCallback);
    }
}
