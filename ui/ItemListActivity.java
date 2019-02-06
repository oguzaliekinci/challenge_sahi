package com.sahibinden.challenge.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sahibinden.challenge.R;
import com.sahibinden.challenge.api.TwitterAuthService;
import com.sahibinden.challenge.api.entities.Tweet;
import com.sahibinden.challenge.base.ApiApplication;
import com.sahibinden.challenge.base.BaseActivity;
import com.sahibinden.challenge.base.PagingAdapter;
import com.sahibinden.challenge.base.PagingScrollListener;
import com.sahibinden.challenge.base.ViewModelProviderFactory;
import com.sahibinden.challenge.util.Utilities;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends BaseActivity<ItemListViewModel> implements View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private boolean loading = false;
    private ArrayList<Tweet> tweetList;
    LinearLayoutManager linearLayoutManager;
    public static final int STATUS_CODE = 10000;

    RecyclerView recyclerView;
    PagingAdapter adapter = null /*new PagingAdapter(this, mTwoPane) {
    }*/;
    private ImageView imageView;
    private ItemListViewModel itemListViewModel;

    @Override
    public ItemListViewModel getViewModel() {
        ViewModelProviderFactory factory = new  ViewModelProviderFactory(itemListViewModel,(ApiApplication)getApplicationContext(), TwitterAuthService.getInstance());
        itemListViewModel = ViewModelProviders.of(this, factory).get(ItemListViewModel.class);
        return itemListViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemListViewModel.retrieveHomeTimeLine(null);

        imageView = findViewById(R.id.toolbarImageView);
        imageView.setVisibility(View.VISIBLE);

        imageView.setOnClickListener(this);


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setupRecyclerView();
        recyclerView.addOnScrollListener(new PagingScrollListener(linearLayoutManager) {
            @Override
            protected void loadMore() {
                loading = true;
                String nextResultId = Utilities.retrieveNextResultId((ArrayList<Tweet>) tweetList);
                if(!nextResultId.equals("0")){
                    itemListViewModel.retrieveHomeTimeLine(nextResultId);

                }
            }

            @Override
            public boolean isLoading() {
                return loading;
            }
        });
        itemListViewModel.getTweetList().observe(this, tweetList ->{
            this.tweetList = tweetList;
            updateRecyclerView(tweetList);
        });
    }
private void updateRecyclerView(ArrayList<Tweet> tweets){
    loading = false;
    adapter.addAll(tweets);
}
    private void setupRecyclerView() {

        adapter = new PagingAdapter(this, mTwoPane);
        recyclerView.setAdapter(adapter) ;

    }

    @Override
    public void onClick(View v) {

        if(v == imageView){
            ((ApiApplication)getApplicationContext()).getUserPreferences().clearData(getApplicationContext());

            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
