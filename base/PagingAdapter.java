package com.sahibinden.challenge.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sahibinden.challenge.R;
import com.sahibinden.challenge.api.entities.Tweet;
import com.sahibinden.challenge.ui.ItemDetailActivity;
import com.sahibinden.challenge.ui.ItemDetailFragment;
import com.sahibinden.challenge.ui.ItemListActivity;

import java.util.ArrayList;
import java.util.List;


public class PagingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ItemListActivity parentActivity;
    private final ArrayList<Tweet> tweetList;
    private final boolean twoPane;

    public PagingAdapter(ItemListActivity parent, boolean twoPane){
        tweetList = new ArrayList<>();
        parentActivity = parent;
        this.twoPane = twoPane;
    }
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Tweet tweet= (Tweet) view.getTag();
            if (twoPane) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID,tweet);
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                parentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID,tweet);
                parentActivity.startActivityForResult(intent,ItemListActivity.STATUS_CODE);
            }
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Tweet item = getItem(position);
        if(item!=null ){
            ViewHolder textViewHolder = (ViewHolder) holder;
            textViewHolder.idTextView.setText(item.getId_str());
            textViewHolder.contentTextView.setText(item.getText());
            textViewHolder.itemView.setTag(item);
            textViewHolder.itemView.setOnClickListener(mOnClickListener);

        }
    }


    @Override
    public int getItemCount() {
        return tweetList == null ? 0 : tweetList.size();
    }
    public void add(Tweet tweet) {
        tweetList.add(tweet);
        notifyItemInserted(tweetList.size() - 1);
    }

    public void addAll(List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            add(tweet);
        }
    }



    public Tweet getItem(int position) {
        return tweetList.get(position);
    }
    public ArrayList<Tweet> getItems() {
        return tweetList;
    }

    public void  clear(){
        tweetList.clear();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView idTextView;

        final TextView contentTextView;

        ViewHolder(View view) {
            super(view);
            idTextView =  view.findViewById(R.id.id_text);
            contentTextView = view.findViewById(R.id.content);
        }
    }
}
