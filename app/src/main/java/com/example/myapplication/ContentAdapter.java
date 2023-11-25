package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.date.Content;
import com.example.myapplication.list.Add_list;
import com.example.myapplication.list.ModalBottomSheet;

import java.util.ArrayList;
import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {


    FragmentManager fragmentManager;

    //接口
//    public interface ClickCallback {
//        void onClick();
//    }
//    private ClickCallback clickCallback;
//
//    public void setClickCallback(ClickCallback clickCallback) {
//        this.clickCallback = clickCallback;
//    }


    private List<Content> mContentList;

    public ContentAdapter(List<Content> contentList, FragmentManager manager) {
        mContentList = contentList;
        fragmentManager = manager;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView describes;
        TextView date;

        View contentview;


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            describes = view.findViewById(R.id.describes);
            date = view.findViewById(R.id.date_picker_actions);
            contentview = view;


//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    removeItem(position);
//                }
//            });
//
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context content = parent.getContext();
        View view = LayoutInflater.from(content).inflate(R.layout.content_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentview.setOnLongClickListener(v -> {
            Context context = parent.getContext();
            int position = holder.getAdapterPosition();
            Content contentView = mContentList.get(position);
            Intent intent = new Intent(context, Add_list.class);
            intent.putExtra("con", contentView);
            context.startActivity(intent);
            return true;
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Dialog bottomSheetDialog;
//                bottomSheetDialog = null;
//                TextView textView = bottomSheetDialog.findViewById(R.id.standard_bottom_sheet);
//                bottomSheetDialog.show();
//                ModalBottomSheet bottomSheet = new ModalBottomSheet();
//                bottomSheet.show();.

                ModalBottomSheet bottomSheet = new ModalBottomSheet();
                bottomSheet.setOnClickListener(v1 -> {
                    removeItem(holder.getAdapterPosition());
                    bottomSheet.dismiss();
                });
                bottomSheet.show(fragmentManager, "ModalBottomSheet");

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Content content = mContentList.get(position);
        holder.title.setText(content.getTitle());
        holder.describes.setText(content.getDescribes());
        holder.date.setText(content.getDate());

    }

    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    public void removeItem(int position) {

        Content content = mContentList.get(position);
        content.delete();

        mContentList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }



}
