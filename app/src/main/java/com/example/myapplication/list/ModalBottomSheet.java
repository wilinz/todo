package com.example.myapplication.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalBottomSheet extends BottomSheetDialogFragment {

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottomsheet_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView delete = view.findViewById(R.id._detele);

        delete.setOnClickListener(v -> {
            if (listener != null) listener.onClick(v);
        });

    }

    public ModalBottomSheet() {
    }

    public ModalBottomSheet(int contentLayoutId) {
        super(contentLayoutId);
    }

    public static final String TAG = "ModalBottomSheet";

}