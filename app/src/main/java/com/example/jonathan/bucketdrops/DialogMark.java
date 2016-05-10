package com.example.jonathan.bucketdrops;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jonathan.bucketdrops.adapters.CompleteListener;

/**
 * Created by Jonathan on 5/10/2016.
 */
public class DialogMark extends DialogFragment {

    private ImageButton mBtnClose;
    private Button mBtnCompleted;
    private CompleteListener mCompleteListener;

    private View.OnClickListener mBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_completed:
                    markAsComplete();
                    break;

            }
            dismiss();
        }
    };

    private void markAsComplete() {
        Bundle arguments = getArguments();

        if (mCompleteListener!= null && arguments != null) {
            int postion = arguments.getInt("POSITION");
            mCompleteListener.onComplete(postion);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnClose = (ImageButton) view.findViewById(R.id.btn_close);
        mBtnCompleted = (Button) view.findViewById(R.id.btn_completed);
        mBtnClose.setOnClickListener(mBtnListener);
        mBtnCompleted.setOnClickListener(mBtnListener);

        Bundle arguments = getArguments();
        if(arguments != null) {
            int postion = arguments.getInt("POSITION");

        }
    }

    public void setCompleteListener(CompleteListener completeListener) {
        mCompleteListener = completeListener;
    }
}
