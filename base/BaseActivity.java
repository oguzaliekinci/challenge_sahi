package com.sahibinden.challenge.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.sahibinden.challenge.R;

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity implements BaseNavigator {
    private View blockingPane;
    private V viewModel;
    public abstract V getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.action_bar_pane);
        blockingPane = findViewById(R.id.base_activity_blocking_pane);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
       /* toolbar.setTitle(getString(R.string.app_name));*/
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        this.viewModel = viewModel == null ? getViewModel() : viewModel;
        viewModel.isLoading().observe(this, loading ->  syncBlockingPane((Boolean) loading));
        viewModel.getError().observe(this, throwable -> showError((Throwable) throwable));
    }

    @Override
    public void setContentView(int layoutResID) {
        final ViewStub subclassContentViewStub = findViewById(R.id.subclass_view_stub);
        subclassContentViewStub.setLayoutResource(layoutResID);
        subclassContentViewStub.inflate();
    }

    private void syncBlockingPane(@Nullable  Boolean loading) {
        if (loading!= null && loading) {
            new Handler().post(() -> showProgress());

        } else {
            new Handler().post(() -> hideProgress());

        }
    }
    @Override
    public void showProgress() {
        hideProgress();
        blockingPane.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        blockingPane.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        blockingPane.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        blockingPane.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable error) {

    }

    @Override
    public BaseActivity<?> getBaseActivity() {
        return this;
    }


    @Override
    public Context getFragmentContext() {
        return this;
    }

    @Override
    public FragmentManager getHostFragmentManager() {
        return getSupportFragmentManager();
    }
}
