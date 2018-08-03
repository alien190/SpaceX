package com.example.ivanovnv.spacex.LaunchFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.api.APIutils;
import com.example.data.database.LaunchDao;
import com.example.data.repository.LaunchLocalRepository;
import com.example.data.repository.LaunchRemoteRepository;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.LaunchServiceImpl;
import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DetailLaunchFragment.DetailLaunchFragment;
import com.example.ivanovnv.spacex.R;


import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class LaunchFragment extends Fragment implements LaunchAdapter.OnItemClickListener {

    private static String TAG = LaunchFragment.class.getSimpleName();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LaunchAdapter mLaunchAdapter;
    Subscription mSubscription;
    //Flowable<List<Launch>> mFlowable;
    LinearLayoutManager mLinearLayoutManager;
    View view;
    RecyclerView.OnScrollListener mOnScrollListener;
    // LaunchAdapter.OnItemClickListener mOnItemClickListener;

    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();

        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof LaunchAdapter.OnItemClickListener) {
//            mOnItemClickListener = ((LaunchAdapter.OnItemClickListener) context);
//        }
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.app_name));

        if (view == null) {
            view = inflater.inflate(R.layout.fr_launches_list, container, false);
            mSwipeRefreshLayout = view.findViewById(R.id.swipelayout);
            mRecyclerView = view.findViewById(R.id.rv_main);
            mLaunchAdapter = new LaunchAdapter();
            mLaunchAdapter.setItemClickListener(this);
            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mLaunchAdapter);

            LaunchServiceImpl launchService = new LaunchServiceImpl(new LaunchLocalRepository(getLaunchDao()),
                    new LaunchRemoteRepository(APIutils.getApi()));

            launchService.getLaunches().observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<List<DomainLaunch>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onSuccess(List<DomainLaunch> domainLaunches) {
                            mLaunchAdapter.addLaunches(domainLaunches);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    launchService.refreshLaunches()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                }
            });

//            mFlowable = updateAdapterFromDataBaseFlowable();
//
//            mFlowable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<List<Launch>>() {
//                        @Override
//                        public void onSubscribe(Subscription s) {
//                            mSubscription = s;
//                            s.request(1);
//                            Log.d(TAG, "onSubscribe: ");
//                        }
//
//                        @Override
//                        public void onNext(List<Launch> launches) {
//                            Log.d(TAG, "onNext: launches.flight_number: " + launches.get(0).getFlight_number());
//                            mLaunchAdapter.addLaunches(launches);
//                        }
//
//                        @Override
//                        public void onError(Throwable t) {
//                            t.printStackTrace();
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            Log.d(TAG, "onComplete: ");
//                        }
//                    });
//        }

//        mOnScrollListener = new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int position = mLinearLayoutManager.findLastVisibleItemPosition();
//                if ((position == RecyclerView.NO_POSITION
//                        || position == mLaunchAdapter.getItemCount() - 1)
//                        && mSubscription != null) {
//                    mSubscription.request(1);
//                }
//            }
//        };
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        mSwipeRefreshLayout.setOnRefreshListener(() -> {
//            if (mSubscription != null) mSubscription.request(1);
//            mSwipeRefreshLayout.setRefreshing(false);
//        });

        // mRecyclerView.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void onStop() {
        //  mRecyclerView.removeOnScrollListener(mOnScrollListener);
        super.onStop();
        // if (mSubscription != null) mSubscription.cancel();
        //  mSwipeRefreshLayout.setOnRefreshListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

//    @SuppressLint("CheckResult")
//    private void updateDatabaseFromServer() {
//        APIutils.getApi().getAllPastLaunches()
//                .flatMap((Function<List<Launch>, SingleSource<?>>) launches -> {
//                    getLaunchDao().insertLaunches(launches);
//                    return Single.just(launches);
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(launches ->
//                        updateAdapterFromDataBase(), Throwable::printStackTrace);
//    }
//
//    @SuppressLint("CheckResult")
//    private void updateAdapterFromDataBase() {
//        Single.create((SingleOnSubscribe<List<Launch>>)
//                emitter -> emitter.onSuccess(getLaunchDao().getLaunches()))
//                .flatMap(mLaunchAdapter.updateFromDataBase)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> {
//                    //Toast.makeText(getActivity(), "" + integer, Toast.LENGTH_SHORT).show();
//                }, Throwable::printStackTrace);
//
//
//    }
//
//    @SuppressLint("CheckResult")
//    private Flowable<List<Launch>> updateAdapterFromDataBaseFlowable() {
//        return Flowable.create(emitter -> {
//            int lastFlightNumber = Integer.MAX_VALUE;
//            List<Launch> launches;
//
//            for (launches = getLaunchDao().getLaunchesInRange(lastFlightNumber, 1);
//                 !launches.isEmpty();
//                 launches = getLaunchDao().getLaunchesInRange(lastFlightNumber, 1)) {
//                lastFlightNumber = launches.get(0).getFlight_number();
//                Log.d(TAG, "updateAdapterFromDataBaseFlowable: flightNumber: " + lastFlightNumber);
//                emitter.onNext(launches);
//            }
//            emitter.onComplete();
//        }, BackpressureStrategy.BUFFER);
//
////        return Flowable.defer((Callable<Publisher<List<DomainLaunch>>>) ()
////                -> {
////            Log.d(TAG, "updateAdapterFromDataBaseFlowable: ");
////            return Flowable.just(getLaunchDao().getLaunchesInRange(mLaunchAdapter.getLastLoadedFlightNumber(), 2));
////        }).flatMap((Function<List<DomainLaunch>, Publisher<DomainLaunch>>) launches -> Flowable.fromIterable(launches))
////                //.flatMap(mLaunchAdapter.updateFromDataBaseFlowable)
////                .buffer(1);
//    }

    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }

    @Override
    public void onItemClick(int flightNumber) {
        //Toast.makeText(getActivity(), "" + flightNumber, Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailLaunchFragment.newInstance(flightNumber))
                .addToBackStack(DetailLaunchFragment.class.getSimpleName())
                .commit();
    }
}
