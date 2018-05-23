package com.example.ivanovnv.spacex.LaunchFragment;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class LaunchAdapter extends RecyclerView.Adapter<LaunchViewHolder> {

    private List<Launch> mLaunches = new ArrayList<>();
    private int mLastLoadedFlightNumber = Integer.MAX_VALUE;
    private Lock mLaunchesLock = new ReentrantLock();


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

    public Function<List<Launch>, SingleSource<Integer>> updateFromDataBase = launches -> {
        final int newCount[] = new int[1];

        int lastCommentPosition = mLaunches.size() - 1;


        for (Launch launch : launches) {
            if (mLaunches.indexOf(launch) == -1) {
                mLaunches.add(launch);
                newCount[0]++;
                mLastLoadedFlightNumber = launch.getFlight_number();
            }
        }

        if (newCount[0] != 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() ->
                    this.notifyItemRangeInserted(lastCommentPosition + 1, newCount[0])
            );
        }

        Log.d("TAG", "apply: thread id" + Thread.currentThread().getId());
        return Single.just(newCount[0]);
    };

    public Function<List<Launch>, Publisher<Integer>> updateFromDataBaseFlowable = launches -> {
        final int newCount[] = new int[1];

        int lastCommentPosition = mLaunches.size() - 1;

        for (Launch launch : launches) {
            if (mLaunches.indexOf(launch) == -1) {
                mLaunches.add(launch);
                newCount[0]++;
                mLastLoadedFlightNumber = launch.getFlight_number();
            }
        }

//        if (newCount[0] != 0) {
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(() ->
//                    this.notifyItemRangeInserted(lastCommentPosition + 1, newCount[0])
//            );
//        }

        Log.d("TAG", "apply: thread id" + Thread.currentThread().getId());
        return Flowable.just(newCount[0]);
    };

    public int getLastLoadedFlightNumber() {
        return mLastLoadedFlightNumber;
    }

    public void addLaunches(List<Launch> launches) {
        final int newCount[] = new int[1];

        int lastCommentPosition = mLaunches.size();


        for (Launch launch : launches) {
            if (mLaunches.indexOf(launch) == -1) {
                mLaunchesLock.lock();
                try {
                    mLaunches.add(launch);
                    newCount[0]++;
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    mLaunchesLock.unlock();
                }

                mLastLoadedFlightNumber = launch.getFlight_number();
            }
        }

        if (newCount[0] != 0) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() ->
                    this.notifyItemRangeInserted(lastCommentPosition + 1, newCount[0])
            );
        }
    }
}
