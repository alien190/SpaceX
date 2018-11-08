package com.example.ivanovnv.spacex.launchSearch.touchHelper;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchCallback extends ItemTouchHelper.Callback {

    private final ItemTouchAdapter mAdapter;

    public ItemTouchCallback(ItemTouchAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = 0;
        final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
//        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
//            ItemTouchViewHolder itemViewHolder = (ItemTouchViewHolder) viewHolder;
//            itemViewHolder.onItemSelected();
//        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
  //      ItemTouchViewHolder itemViewHolder = (ItemTouchViewHolder) viewHolder;
  //      itemViewHolder.onItemClear();
    }
}
