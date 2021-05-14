package com.vbuecker.chucknorrisio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.vbuecker.chucknorrisio.datasource.CategoryRemoteDataSource;
import com.vbuecker.chucknorrisio.model.CategoryItem;
import com.vbuecker.chucknorrisio.presentation.CategoryPresenter;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private GroupAdapter adapter;
    private CategoryPresenter presenter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        RecyclerView recyclerView = findViewById(R.id.rv_main);

        adapter = new GroupAdapter();

        adapter.setOnItemClickListener((item, view) -> {
           Intent intent = new Intent(MainActivity.this, JokeActivity.class);
           intent.putExtra(JokeActivity.CATEGORY_KEY, ((CategoryItem) item).getCategoryName());

            startActivity(intent);

        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CategoryRemoteDataSource dataSource = new CategoryRemoteDataSource();
        new CategoryPresenter(this, dataSource).requestAll();

     //   populateItems();
    }


    public void showProgressBar(){
        if(progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.loading));
            progress.setIndeterminate(true);
            progress.setCancelable(false);
        }
        progress.show();
    }

    public void hideProgessBar(){
        if(progress != null){
            progress.hide();
        }
    }

    public void showCategories(List<CategoryItem> categoryItems){
        adapter.addAll(categoryItems);
        adapter.notifyDataSetChanged();
    }
    public void showFailure(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
/*
    private void populateItems() {
        List<CategoryItem> items = new ArrayList<>();
        items.add(new CategoryItem("Cat1", 0x1F442CBA));
        items.add(new CategoryItem("Cat2", 0xEB73C8C8));
        items.add(new CategoryItem("Cat3", 0xF442CBAB));
        items.add(new CategoryItem("Cat4", 0xEB73C8C8));
        items.add(new CategoryItem("Cat5", 0xFF00FBFF));
        items.add(new CategoryItem("Cat6", 0xFF00FDBF));
        items.add(new CategoryItem("Cat7", 0xFF00FFFF));
        items.add(new CategoryItem("Cat8", 0xFF05FFFB));

        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }
    */


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_home){
        }else if(id == R.id.nav_view) {
        }else if (id == R.id.nav_gallery) {
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}