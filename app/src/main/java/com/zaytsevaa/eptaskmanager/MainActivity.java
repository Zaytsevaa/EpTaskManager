package com.zaytsevaa.eptaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zaytsevaa.eptaskmanager.databinding.ActivityMainBinding;
import com.zaytsevaa.eptaskmanager.db.TaskEntity;
import com.zaytsevaa.eptaskmanager.ui.ContextMenuRecyclerView;
import com.zaytsevaa.eptaskmanager.ui.TaskCardAdapter;
import com.zaytsevaa.eptaskmanager.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.zaytsevaa.eptaskmanager.utilities.Constants.TASK_ID_KEY;

public class MainActivity extends AppCompatActivity
        implements TaskCardAdapter.OnItemClickListener {

    private ContextMenuRecyclerView mRecyclerView;
    private TaskCardAdapter mAdapter;
    private MainViewModel mViewModel;
    private List<TaskEntity> mTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = activityMainBinding.toolbar;
        setSupportActionBar(toolbar);

        initRecyclerView();
        initViewModel();

        FloatingActionButton fab = activityMainBinding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(detailIntent);
            }
        });
    }

    private void initViewModel() {
        Observer<List<TaskEntity>> taskObserver = new Observer<List<TaskEntity>>() {
            @Override
            public void onChanged(List<TaskEntity> taskEntities) {
                mTasks.clear();
                mTasks.addAll(taskEntities);

                if (mAdapter == null) {
                    mAdapter = new TaskCardAdapter(mTasks);
                    mAdapter.setOnItemClickListener(MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                    registerForContextMenu(mRecyclerView);
                } else {
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.mLiveTasks.observe(this, taskObserver);
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.task_list);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.task_item_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        ContextMenuRecyclerView.RecyclerViewContextMenuInfo info =
                (ContextMenuRecyclerView.RecyclerViewContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_task_item_delete: {
                int taskId = mAdapter.getCurrentTaskId(info.position);
                mViewModel.deleteUserById(taskId);
            }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            mViewModel.addSampleData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int userId) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra(TASK_ID_KEY, userId);
        startActivity(detailIntent);
    }
}
