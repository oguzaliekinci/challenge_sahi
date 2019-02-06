package com.sahibinden.challenge.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.sahibinden.challenge.api.TwitterAuthService;
import com.sahibinden.challenge.api.entities.Tweet;
import com.sahibinden.challenge.base.BaseViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ItemListViewModel extends BaseViewModel {

    public MutableLiveData<ArrayList<Tweet>> tweetList = new MutableLiveData<>();

    public ItemListViewModel(@NonNull Application application, TwitterAuthService twitterAuthService) {
        super(application, twitterAuthService);
    }

    public void retrieveHomeTimeLine(String nextResultId){
        setLoading(true);

        getTwitterAuthService().sendHomeTimelineRequest(30,nextResultId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Tweet>>() {
                               @Override
                               public void accept(ArrayList<Tweet> tweets) throws Exception {
                                   setLoading(false);
                                   tweetList.setValue(tweets);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   setLoading(false);
                                   setError(throwable.getMessage());
                               }
                           });

                }
   /*
        getTwitterAuthService().sendHomeTimelineRequest(30,nextResultId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tweets -> {
                    setLoading(false);
                    tweetList.setValue(tweets);

                }, throwable -> setLoading(false));*/


    public LiveData<ArrayList<Tweet>> getTweetList() {
        return tweetList;
    }
}
