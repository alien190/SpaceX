package com.example.ivanovnv.spacex.LaunchFragment;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.domain.model.launch.DomainLaunch;
import com.example.ivanovnv.spacex.R;


import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchViewHolder> {

    private List<DomainLaunch> mLaunches = new ArrayList<>();
    private Lock mLaunchesLock = new ReentrantLock();
    private OnItemClickListener mItemClickListener;


    @NonNull
    @Override
    public LaunchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_launch, parent, false);

        return new LaunchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaunchViewHolder holder, int position) {
        holder.bind(mLaunches.get(position));
        holder.setItemClickListener(mItemClickListener);
    }

    @Override
    public int getItemCount() {

        int size = 0;
        if (mLaunches != null) {
            mLaunchesLock.lock();
            try {
                size = mLaunches.size();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                mLaunchesLock.unlock();
            }
        }
        return size;
    }

    public void updateLaunches(List<DomainLaunch> launches) {
        mLaunches.clear();
        mLaunches.addAll(launches);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(int flightNumber);
    }

    public void setItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }
}
