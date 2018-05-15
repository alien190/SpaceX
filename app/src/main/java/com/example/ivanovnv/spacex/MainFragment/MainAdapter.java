package com.example.ivanovnv.spacex.MainFragment;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private List<Launch> mLaunches = new ArrayList<>();


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_launch, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.bind(mLaunches.get(position));
    }

    @Override
    public int getItemCount() {
        if (mLaunches != null) return mLaunches.size();
        return 0;
    }

    public Function<List<Launch>, SingleSource<Integer>> updateFromDataBase = launches -> {
        final int newCount[] = new int[1];

        int lastCommentPosition = mLaunches.size() - 1;


        for (Launch launch : launches) {
            if (mLaunches.indexOf(launch) == -1) {
                mLaunches.add(launch);
                newCount[0]++;
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

}
