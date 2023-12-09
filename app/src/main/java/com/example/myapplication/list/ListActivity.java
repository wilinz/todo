package com.example.myapplication.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.ContentAdapter;
import com.example.myapplication.OuterAdapter;
import com.example.myapplication.R;
import com.example.myapplication.date.Content;
import com.example.myapplication.date.Title;
import com.example.myapplication.date.User;
import com.example.myapplication.menu.Changed_password;
import com.example.myapplication.menu.Changed_username;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import kotlin.collections.CollectionsKt;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton fbtn;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private TextView tv_username;
    private ImageButton imageButton;
    RecyclerView outerRecyclerView;

    private List<Content> contentList = new ArrayList<>();
    private List<Title> titleList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fbtn = (FloatingActionButton) findViewById(R.id.float_button);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListActivity.this, Add_list.class);
                startActivity(intent);
            }
        });

        initContents();
        initTitles();

        drawerLayout = findViewById(R.id.drawerLayout);
        imageButton = findViewById(R.id._avatar);
//        recycleView = findViewById(R.id.innerRecyclerView);
        outerRecyclerView = findViewById(R.id.outerRecyclerView);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openNavigationView();
            }
        });


    }

    private void openNavigationView() {
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        drawerLayout.openDrawer(GravityCompat.START);
        navigationView = findViewById(R.id.navigationView);
        tv_username = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        User user = LitePal.where("islogin = ?", "1").findFirst(User.class);
        tv_username.setText(user.getUsername());
        //                    这行代码的作用是使用 LitePal 数据库框架来获取名为 "Content" 的表中的记录数量，并将结果存储在 `itemCount` 变量中。
//                    1. `LitePal` 是一个轻量级的开源数据库框架，用于在 Android 应用程序中进行数据库操作。
//                    2. `count()` 是 LitePal 框架提供的一个方法，用于获取指定表中的记录数量。
//                    3. `"Content"` 是表的名称，它指定了我们要获取记录数量的表的名称。在这个例子中，假设存在一个名为 "Content" 的表。
//                    4. `LitePal.count("Content")` 调用了 `count()` 方法来获取 "Content" 表中的记录数量，并将结果作为整数返回。
//                    5. `int itemCount = LitePal.count("Content");` 将获取到的记录数量赋值给 `itemCount` 变量，以便后续使用。


        Menu menu = navigationView.getMenu();
        MenuItem totalNumberItem = menu.findItem(R.id.total_number_item);
        int itemCount = LitePal.count("content");
        totalNumberItem.setTitle("待办事项总数：" + itemCount);

        MenuItem completeditem = menu.findItem(R.id.completed_item);
        int deletedItemCount = LitePal.where("isFinish = ?", "1").count(Content.class);
        completeditem.setTitle("已完成事项数：" + deletedItemCount);

        MenuItem overdueitem = menu.findItem(R.id.overdue_item);
        int overdueItemCount = LitePal.where("isOver = ?", "1").count(Content.class);
        overdueitem.setTitle("已过期事项数：" + overdueItemCount);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.changed_uesrname) {
                    Intent intent = new Intent(ListActivity.this, Changed_username.class);
                    startActivity(intent);
                }
                if (item.getItemId() == R.id.changed_password) {
                    Intent intent = new Intent(ListActivity.this, Changed_password.class);
                    startActivity(intent);
                }

                item.setCheckable(false);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ViewGroup.LayoutParams mLayoutParams = navigationView.getLayoutParams();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
        mLayoutParams.width = width;
        navigationView.setLayoutParams(mLayoutParams);

    }


    public void initContents() {
        contentList = LitePal.order("ispinned desc").order("datetime('date') desc").find(Content.class);
        Log.d("initContents: ", contentList.toString());
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.innerRecyclerView);
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

    protected void onRestart() {
//        contentList = LitePal.findAll(Content.class);
        super.onRestart();
        Log.d("onRestart:", contentList.toString());
        initContents();

    }

    public void initTitles() {
        List<Content> categoryList = LitePal.select("category").find(Content.class);
        titleList = CollectionsKt.map(categoryList, content -> new Title(content.getCategory(), false));
        titleList = CollectionsKt.distinct(titleList);
//        titleList = new ArrayList<>();
//        for (int i = 0; i < categoryList.size(); i++) {
//            titleList.add(new Title(categoryList.get(i), false));
//        }
//        LitePal.select("category").find(String.class)：这行代码使用 LitePal 数据库框架从数据库中选择所有的类别（在这里假设类别是以字符串形式存储的），并将它们以字符串列表的形式返回给 categoryList。
//
//titleList = CollectionsKt.map(categoryList, category -> new Title(category, false))：这行代码使用 Kotlin 的 map 函数将 categoryList 中的每个类别转换为 Title 对象，并将这些对象放入 titleList 中。在这里，对于每个类别，都会创建一个新的 Title 对象，其中 isExpand 属性被设置为 false。
//
//综合起来，这段代码的作用是从数据库中获取类别列表，然后将每个类别转换为 Title 对象，并将这些对象存储在 titleList 中

        Log.d("initTitles: ", titleList.toString());
        RecyclerView outerRecyclerView = (RecyclerView) findViewById(R.id.outerRecyclerView);
        LinearLayoutManager outerLayoutManager = new LinearLayoutManager(this);
//        outerRecyclerView.setLayoutManager(outerLayoutManager);
        //设置成竖直
        outerLayoutManager.setOrientation(RecyclerView.VERTICAL);
        outerRecyclerView.setLayoutManager(outerLayoutManager);
        //子项分割线
        outerRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        OuterAdapter outerAdapter = new OuterAdapter(titleList,getSupportFragmentManager());
        outerRecyclerView.setAdapter(outerAdapter);
//        Log.d("initTitles: ", titleList.toString())：这行代码将 titleList 转换为字符串并打印到日志中，以便在调试时查看 titleList 的内容。
//
//RecyclerView outerRecyclerView = (RecyclerView) findViewById(R.id.outerRecyclerView)：这行代码从布局中查找具有特定 ID（R.id.outerRecyclerView）的 RecyclerView 控件，并将其实例化为 outerRecyclerView 对象。
//
//LinearLayoutManager outerLayoutManager = new LinearLayoutManager(this)：这行代码创建一个线性布局管理器 outerLayoutManager，用于管理外部 RecyclerView 的布局。
//
//outerRecyclerView.setLayoutManager(outerLayoutManager)：这行代码将 outerLayoutManager 设置为外部 RecyclerView 的布局管理器，以便在外部 RecyclerView 中管理项的布局排列。
//
//OuterAdapter outerAdapter = new OuterAdapter(titleList,getSupportFragmentManager())：这行代码创建一个名为 outerAdapter 的适配器，用于将数据绑定到外部 RecyclerView 中。titleList 是用于填充外部 RecyclerView 的数据，getSupportFragmentManager() 可能用于在 Adapter 中处理 Fragment。
//
//outerRecyclerView.setAdapter(outerAdapter)：这行代码将 outerAdapter 设置为外部 RecyclerView 的适配器，以便在外部 RecyclerView中显示数据。
//
//综合起来，这段代码的作用是初始化外部 RecyclerView，设置其布局管理器和适配器，最终将数据显示在外部 RecyclerView 中
    }
}



