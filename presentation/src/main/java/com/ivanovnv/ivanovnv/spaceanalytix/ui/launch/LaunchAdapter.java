package com.ivanovnv.ivanovnv.spaceanalytix.ui.launch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivanovnv.domain.model.launch.DomainLaunch;
import com.ivanovnv.ivanovnv.spaceanalytix.R;
import com.ivanovnv.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

import java.util.ArrayList;
import java.util.List;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchViewHolder> {

    private List<DomainLaunch> mLaunches = new ArrayList<>();
    private OnItemClickListener mItemClickListener;
    private ICurrentPreferences mCurrentPreferences;

    public LaunchAdapter(ICurrentPreferences currentPreferences) {
        if (currentPreferences != null) {
            mCurrentPreferences = currentPreferences;
        } else {
            throw new IllegalArgumentException("currentPreferences can't be null");
        }
    }

    @NonNull
    @Override
    public LaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_launch, parent, false);
        return new LaunchViewHolder(view, mCurrentPreferences.getConverter());
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchViewHolder holder, int position) {
        holder.bind(mLaunches.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (mLaunches != null) {
            return mLaunches.size();
        }
        return 0;
    }

    public void updateLaunches(List<DomainLaunch> launches) {
        mLaunches.clear();
        mLaunches.addAll(launches);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int flightNumber, View sharedView);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
