package com.nupt.dzs.wordsreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.common.BaseActivity;
import com.nupt.dzs.wordsreader.http.request.IRequest;
import com.nupt.dzs.wordsreader.http.request.TokenRequest;
import com.nupt.dzs.wordsreader.http.response.TokenResponse;
import com.nupt.dzs.wordsreader.impl.IArticleListView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.model.WordModel;
import com.nupt.dzs.wordsreader.presenter.ArticleListPresenter;
import com.nupt.dzs.wordsreader.ui.adapter.ArticleListAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements IArticleListView, NavigationView.OnNavigationItemSelectedListener {
    ArticleListPresenter presenter;
    FloatingActionButton fab;
    @Bind(R.id.rvArticleList)
    RecyclerView rvArticleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ArticleListPresenter(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        presenter.loadWords();
        presenter.loadFile();

    }

    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置滑动效果 生成回到顶部按钮
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvArticleList.smoothScrollToPosition(0);
            }
        });
        rvArticleList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    fab.setVisibility(View.VISIBLE);
                }else {
                    fab.setVisibility(View.GONE);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void viewDetail(ArticleModel articleModel) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        intent.putExtra("data",articleModel);
        startActivity(intent);
    }

    @Override
    public void loadArticles(List<ArticleModel> articleModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvArticleList.setLayoutManager(linearLayoutManager);
        rvArticleList.setAdapter(new ArticleListAdapter(this,articleModels));
    }

    @Override
    public void loadWords(List<WordModel> wordModels) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }
}
