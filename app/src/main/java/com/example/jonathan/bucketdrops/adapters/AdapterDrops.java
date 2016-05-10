package com.example.jonathan.bucketdrops.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonathan.bucketdrops.R;
import com.example.jonathan.bucketdrops.beans.Drop;
import com.example.jonathan.bucketdrops.extra.Utils;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Jonathan on 5/9/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    private AddListener mAddListener;
    private Realm mRealm;
    private MarkListener mMarkListener;

    public AdapterDrops(Context context,Realm realm, RealmResults<Drop> results, AddListener addListener, MarkListener markListener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        mAddListener = addListener;
        mRealm = realm;
        mMarkListener = markListener;
    }


    public void update(RealmResults<Drop> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            FooterHolder footerHolder = new FooterHolder(view);
            return footerHolder;
        } else {
            View view = mInflater.inflate(R.layout.row_drop, parent, false);
            DropHolder dropHolder = new DropHolder(view, mMarkListener);
            return dropHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {

            DropHolder dropHolder = (DropHolder) holder;
            if(mResults.get(position).isValid()) {
                Drop drop = mResults.get(position);
                dropHolder.setWhat(drop.getWhat());
                dropHolder.setBackground(drop.isCompleted());
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mResults == null || mResults.isEmpty()) {
            return 0;
        } else {
            return mResults.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if(mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    @Override
    public void onSwipe(int position) {
        if(position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);

        }
    }

    public void markComplete(int position) {
        if(position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).setCompleted(true);
            mRealm.commitTransaction();
            notifyDataSetChanged();
        }
    }

    public static class DropHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextWhat;
        TextView mTextWhen;
        private MarkListener mMarkListener;
        Context mContext;
        View mItemView;

        public DropHolder(View itemView, MarkListener markListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemView = itemView;
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            //mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);
            mMarkListener = markListener;
            mContext = itemView.getContext();

        }

        public void setWhat(String what) {
            mTextWhat.setText(what);
        }

        @Override
        public void onClick(View v) {
            mMarkListener.onMark(getAdapterPosition());
        }

        public void setBackground(boolean completed) {
            Drawable drawable;
            if(completed) {
             drawable = ContextCompat.getDrawable(mContext, R.color.bg_drop_complete);
            } else {
              drawable = ContextCompat.getDrawable(mContext, R.drawable.bg_row_drop);
            }
            Utils.setBackground(mItemView, drawable);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mBtnAdd;

        public FooterHolder(View itemView) {
            super(itemView);
            mBtnAdd = (Button) itemView.findViewById(R.id.btn_footer);
            mBtnAdd.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mAddListener.add();
        }
    }
}
