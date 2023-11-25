package com.example.myapplication.list;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.ContentAdapter;
import com.example.myapplication.R;
import com.example.myapplication.date.Content;
import com.example.myapplication.date.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FloatingActionButton fbtn;
    private BottomSheetDialog bottomSheetDialog;
    private TextView textView;
    private NavigationView navigationView;

    private DrawerLayout drawerLayout;
    private TextView tv_username;


    TextView delete;
    private List<Content> contentList = new ArrayList<>();
    private MenuItem item;
//    public interface Callback{
//
//         void removeItem();
//    }
//
//    private Callback callback ;
//
//    public void setCallback(Callback callback ){
//        this.callback = callback ;
//    }


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

        drawerLayout = findViewById(R.id.drawerLayout);


//        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
//        if (navigationMenuView != null) {
//            navigationMenuView.setVerticalScrollBarEnabled(false);
//        }





        initNavMenu();

    }


    public  void initContents() {
        contentList = LitePal.findAll(Content.class);
        Log.d("initContents: ", contentList.toString());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ContentAdapter adapter = new ContentAdapter(contentList,getSupportFragmentManager());
//        adapter.setClickCallback(new ContentAdapter.ClickCallback() {
//            @Override
//            public void onClick() {
//                ModalBottomSheet bottomSheet = new ModalBottomSheet();
//                bottomSheet.show(getSupportFragmentManager(),"ModalBottomSheet");
//            }
//        });
        recyclerView.setAdapter(adapter);
    }

    protected void onRestart() {
//        contentList = LitePal.findAll(Content.class);
        super.onRestart();
        Log.d("onRestart:", contentList.toString());
        initContents();
        //把修改后的传过来

    }

//    protected int getLayoutResID(){
//        return R.layout.activity_list;
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (bottomSheetDialog != null && bottomSheetDialog.getWindow() != null && bottomSheetDialog.getWindow().isFloating()) {
//            bottomSheetDialog.dismiss();
//        }
//    }

    private void initNavMenu(){
        navigationView = findViewById(R.id.navigationView);
        tv_username = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        User user = LitePal.where("islogin = ?","1").findFirst(User.class);
        tv_username.setText(user.getUsername());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.changed_uesrname ) {

                }
                if (item.getItemId() == R.id.changed_password ) {

                }
                if(item.getItemId() == R.id.total_number_item ){

                }
                if(item.getItemId() == R.id.completed_item){

                }

                item.setCheckable(false);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ViewGroup.LayoutParams mLayoutParams = navigationView.getLayoutParams();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 280, getResources().getDisplayMetrics());
        mLayoutParams.width = width;
        navigationView.setLayoutParams(mLayoutParams);

    }



}



