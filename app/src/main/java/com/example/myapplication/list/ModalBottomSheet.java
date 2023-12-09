package com.example.myapplication.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.date.Content;
import com.example.myapplication.loginandregister.LoginActivity;
import com.example.myapplication.loginandregister.RegisterActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.litepal.LitePal;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener listener;

    public interface OnFinishClickListener {
        void onTitleFinished();
    }

    private OnFinishClickListener onFinishClickListener;

    public void setOnFinishClickListener(OnFinishClickListener listener) {
        onFinishClickListener = listener;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView delete = view.findViewById(R.id._detele);
        TextView finish = view.findViewById(R.id._finish);
        TextView focus = view.findViewById(R.id._focus );

        delete.setOnClickListener(v -> {
            if (listener != null) listener.onClick(v);
        });
        finish.setOnClickListener(v -> {
            if (onFinishClickListener != null) {
                onFinishClickListener.onTitleFinished();
            }
        });
        focus.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, Focus_activity.class);
            startActivity(intent);
        });

    }

    public ModalBottomSheet() {
    }

    public ModalBottomSheet(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static final String TAG = "ModalBottomSheet";

}