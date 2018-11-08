package com.example.ivanovnv.spacex.di.launch;

import com.example.ivanovnv.spacex.launchSearch.touchHelper.ItemTouchAdapter;
import com.example.ivanovnv.spacex.launchSearch.touchHelper.ItemTouchCallback;

import javax.inject.Inject;
import javax.inject.Provider;

public class ItemTouchCallbackProvider implements Provider<ItemTouchCallback> {

   private ItemTouchAdapter mAdapter;

    @Inject
    public ItemTouchCallbackProvider(ItemTouchAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public ItemTouchCallback get() {
        return new ItemTouchCallback(mAdapter);
    }
}
