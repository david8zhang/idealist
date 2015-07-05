package com.example.david_000.idealist;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import volley.AppController;

/**
 * Created by david_000 on 7/3/2015.
 */
public class FeedListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems){
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);
        if(imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView ideaTitle = (TextView)convertView.findViewById(R.id.ideaTitle);
        TextView ideaCategory = (TextView)convertView.findViewById(R.id.ideaCategory);
        TextView postingDate = (TextView)convertView.findViewById(R.id.postingDate);
        TextView ideaText = (TextView)convertView.findViewById(R.id.ideaText);

        FeedItem item = feedItems.get(position);

        ideaTitle.setText(item.getIdeaTitle());
        ideaCategory.setText(item.getIdeaCategory());
        ideaText.setText(item.getIdeaText());


        //Timestamp, will implement some other time
        //Converting timestamp into x ago format
/*        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getPostingDate()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        postingDate.setText(timeAgo);*/

        if(!TextUtils.isEmpty(item.getIdeaCategory())){
            ideaCategory.setText(item.getIdeaCategory());
            ideaCategory.setVisibility(View.VISIBLE);
        } else {
            ideaCategory.setVisibility(View.GONE);
        }

        // Feed image, will implement some other time...
/*
        if (item.getImageUrl() != null) {
            ideaImageView.setImageUrl(item.getImageUrl(), imageLoader);
            ideaImageView.setVisibility(View.VISIBLE);
            ideaImageView
                    .setResponseObserver(new ideaImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            ideaImageView.setVisibility(View.GONE);
        }
*/

        return convertView;

    }
}
