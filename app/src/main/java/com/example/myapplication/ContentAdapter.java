package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.date.Content;
import com.example.myapplication.list.Add_list;
import com.example.myapplication.list.ModalBottomSheet;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
   private FragmentManager fragmentManager;
   private LinearLayoutManager layoutManager;

    private List<Content> list;
//    定义了一个私有的列表，该列表只能存储Content类型的对象，并且该列表被命名为list//内部

    public ContentAdapter (List<Content> list,LinearLayoutManager layoutManager, FragmentManager fragmentManager){
        this.layoutManager = layoutManager;
        this.list = list;
        this.fragmentManager = fragmentManager;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView describes;
        TextView date;

        DrawerLayout dl;

        RelativeLayout item;
        TextView top;


        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_);
            describes = view.findViewById(R.id.describes);
            date = view.findViewById(R.id.date_picker_actions);
            dl = view.findViewById(R.id.dl);
            item = view.findViewById(R.id._item);
            top = view.findViewById(R.id.top);
            dl.setScrimColor(Color.TRANSPARENT);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.content_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.item.setOnLongClickListener(v -> {
            int position = holder.getAdapterPosition();
            Content contentView = list.get(position);
            Intent intent = new Intent(context, Add_list.class);
            intent.putExtra("con", contentView);
            context.startActivity(intent);
            return false;

//            这段代码的作用是为 `holder.contentview` 设置一个长按监听器。当用户长按 `contentview` 时，会执行相应的操作。
//            1. `holder.contentview.setOnLongClickListener(v -> {`：为 `holder.contentview` 设置一个长按监听器。
//                2. `Context context = parent.getContext();`：获取父视图的上下文对象，即包含 `holder.contentview` 的视图的上下文。
//                3. `int position = holder.getAdapterPosition();`：获取当前 `holder` 的适配器位置。
//                4. `Content contentView = mContentList.get(position);`：从 `mContentList` 中获取与当前位置对应的 `Content` 对象。
//                5. `Intent intent = new Intent(context, Add_list.class);`：创建一个意图对象，将上下文和目标活动的类 `Add_list.class` 作为参数传递。
//                6. `intent.putExtra("con", contentView);`：将获取到的 `contentView` 对象作为额外数据放入意图中，键为 "con"。这样，在启动 `Add_list` 活动时，可以通过 `getIntent().getSerializableExtra("con")` 获取到传递的 `contentView` 对象。
//                7. `context.startActivity(intent);`：使用上下文启动 `Add_list` 活动，将意图传递给它。
//                8. `return true;`：返回 `true`，表示长按事件已被处理，不会触发其他点击事件。
//                综上所述，这段代码的作用是：当用户长按 `holder.contentview` 时，创建一个意图对象，将选中的 `Content` 对象作为额外数据放入意图中，并启动 `Add_list` 活动。这样，用户可以在 `Add_list` 活动中对选中的内容进行进一步操作。
        });
        holder.item.setOnClickListener(v -> {
            ModalBottomSheet bottomSheet = new ModalBottomSheet();
            bottomSheet.setOnFinishClickListener(() -> {
                int p = holder.getAbsoluteAdapterPosition();
                Content content = list.get(p);
                if (!content.isFinish()){
                    content.setFinish(true);
                }else {
                    content.setFinish(false);
                    content.setToDefault("isFinish");
                }
                content.update(content.getId());
                notifyItemChanged(p);
                bottomSheet.dismiss();
            });
            bottomSheet.setOnClickListener(v1 -> {
                removeItem(holder.getAdapterPosition());
                bottomSheet.dismiss();
            });
            bottomSheet.show(fragmentManager, "ModalBottomSheet");
        });

        holder.dl.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                holder.item.setTranslationX(-(drawerView.getMeasuredWidth() * slideOffset));
            }


            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        holder.top.setOnClickListener(v -> {
            Content content = list.get(holder.getAbsoluteAdapterPosition());
            if (content.isPinned()){
                holder.dl.closeDrawers();
                //关闭抽屉
                content.setPinned(false);
                content.setToDefault("isPinned");
//                名为 "ispinned" 的内容设置为默认值false
            }else {
                holder.dl.closeDrawers();
                content.setPinned(true);
            }
            content.update(content.getId());
            list.sort(new PinnedComparator());
            notifyDataSetChanged();
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position0) {
        int position = holder.getAbsoluteAdapterPosition();
        Content content = list.get(position);

        holder.top.setText(content.isPinned()?"取消置顶":"置顶");

        if (content.isFinish()){
            SpannableString spannableTitle = new SpannableString(content.getTitle());
            spannableTitle.setSpan(new StrikethroughSpan(), 0, spannableTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString spannableDescribes = new SpannableString(content.getDescribes());
            spannableDescribes.setSpan(new StrikethroughSpan(), 0, spannableDescribes.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            SpannableString spannableDate = new SpannableString(content.getDate());
            spannableDate.setSpan(new StrikethroughSpan(), 0, spannableDate.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            Log.d( "onBindViewHolder: ","ugihhghu");
            holder.title.setText(spannableTitle);
            holder.describes.setText(spannableDescribes);
            holder.date.setText(spannableDate);
        }else {
            holder.title.setText(content.getTitle());
            holder.describes.setText(content.getDescribes());
            holder.date.setText(content.getDate());
        }

//                           这段代码的作用是根据给定的位置从 `mContentList` 中获取对应的 `Content` 对象，并将该对象的属性值设置到相应的视图控件中。
//                    下面是对代码的逐行解释：
//                    1. `Content content = mContentList.get(position);`：从 `mContentList` 中获取指定位置 `position` 处的 `Content` 对象，并将其赋值给 `content` 变量。
//                    2. `holder.title.setText(content.getTitle());`：将 `content` 对象的标题（通过 `getTitle()` 方法获取）设置到 `holder` 中的 `title` 视图控件中。这样，`title` 视图控件就会显示相应的标题文本。
//                    3. `holder.describes.setText(content.getDescribes());`：将 `content` 对象的描述（通过 `getDescribes()` 方法获取）设置到 `holder` 中的 `describes` 视图控件中。这样，`describes` 视图控件就会显示相应的描述文本。
//                    4. `holder.date.setText(content.getDate());`：将 `content` 对象的日期（通过 `getDate()` 方法获取）设置到 `holder` 中的 `date` 视图控件中。这样，`date` 视图控件就会显示相应的日期文本。
//                    综上所述，这段代码的作用是根据给定位置获取 `mContentList` 中的 `Content` 对象，并将该对象的标题、描述和日期分别设置到对应的视图控件中，以便在界面上显示相关信息。

        if(content.isOver()){
            holder.title.setTextColor(Color.RED);
            holder.describes.setTextColor(Color.RED);
            holder.date.setTextColor(Color.RED);
        }else {
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeItem(int position) {
        Content content = list.get(position);
        content.delete();
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

}
