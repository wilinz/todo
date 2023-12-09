package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.date.Content;
import com.example.myapplication.date.Title;

import org.litepal.LitePal;

import java.util.List;

public class OuterAdapter extends RecyclerView.Adapter<OuterAdapter.OuterViewHolder>{
    private List<Title> titles;
//    private ContentAdapter  contentAdapter;


    private FragmentManager fragmentManager;
    private String TAG = "OuterAdapter";

    public OuterAdapter(List<Title> titles, FragmentManager fragmentManager) {
        this.titles = titles;
        this.fragmentManager = fragmentManager;
    }



    public OuterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_title, parent, false);
        return new OuterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OuterViewHolder holder, int position0) {
        int position = holder.getAbsoluteAdapterPosition();
        Title sorttitle = titles.get(position);
        // 设置外部 item 的数据
        String category = sorttitle.getCategory();
        holder.titleTextView.setText(category);

        Log.d("onBindViewHolder: ", String.valueOf(sorttitle.isExpand()));
        if (sorttitle.isExpand()){
            //找到对应该分类的子item来获取item数目
            List<Content> subitems = LitePal.where("category = ?", sorttitle.getCategory()).find(Content.class);
            Log.d(TAG, subitems.toString());
            // 创建内部 RecyclerView 的 Adapter
            LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
            holder.innerRecyclerView.setLayoutManager(layoutManager);
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.itemView.getContext(), layoutManager.getOrientation());
//            holder.innerRecyclerView.addItemDecoration(dividerItemDecoration);

            ContentAdapter contentAdapter = new ContentAdapter(subitems, null, fragmentManager);
            holder.innerRecyclerView.setAdapter(contentAdapter);
            // 设置内部 RecyclerView 的布局管理器

            // 设置内部 RecyclerView 的分割线

            holder.innerRecyclerView.setVisibility(View.VISIBLE); //
        }else {
//            holder.innerRecyclerView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            // 外部 item 展开状态
            sorttitle.setExpand(!sorttitle.isExpand());
            notifyItemChanged(position); // 更新新的item布局
        });


    }

    public int getItemCount() {
        return titles.size();
    }

    public static class OuterViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        RecyclerView innerRecyclerView;

        public OuterViewHolder(View itemView) {
            super(itemView);
            innerRecyclerView = itemView.findViewById(R.id.innerRecyclerView);
            titleTextView = itemView.findViewById(R.id.sort_title);
            //布局管理器
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        设置成竖直
//        manager.setOrientation(RecyclerView.VERTICAL);
//        recycleView.setLayoutManager(manager);
//        子项分割线
//        recycleView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        ContentAdapter contentAdapter = new ContentAdapter(contentList, manager, getSupportFragmentManager());
//        recycleView.setAdapter(contentAdapter);


        }
    }

}

