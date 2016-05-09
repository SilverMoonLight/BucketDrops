package com.example.jonathan.bucketdrops.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jonathan.bucketdrops.R;
import com.example.jonathan.bucketdrops.beans.Drop;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by Jonathan on 5/9/2016.
 */
public class AdapterDrops extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM = 0;
    public static final int FOOTER = 1;

    private LayoutInflater mInflater;
    private RealmResults<Drop> mResults;
    private AddListener mAddListener;

    public AdapterDrops(Context context, RealmResults<Drop> results, AddListener addListener) {
        mInflater = LayoutInflater.from(context);
        update(results);
        mAddListener = addListener;
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
            DropHolder dropHolder = new DropHolder(view);
            return dropHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DropHolder) {
            DropHolder dropHolder = (DropHolder) holder;
            Drop drop = mResults.get(position);
            dropHolder.mTextWhat.setText(drop.getWhat());
        }

    }

    @Override
    public int getItemCount() {
        return mResults.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if(mResults == null || position < mResults.size()) {
            return ITEM;
        } else {
            return FOOTER;
        }
    }

    public static class DropHolder extends RecyclerView.ViewHolder {

        TextView mTextWhat;
        TextView mTextWhen;
        public DropHolder(View itemView) {
            super(itemView);
            mTextWhat = (TextView) itemView.findViewById(R.id.tv_what);
            mTextWhen = (TextView) itemView.findViewById(R.id.tv_when);

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
