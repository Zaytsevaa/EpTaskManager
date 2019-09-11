package com.zaytsevaa.eptaskmanager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zaytsevaa.eptaskmanager.databinding.ActivityDetailBinding;
import com.zaytsevaa.eptaskmanager.db.TaskEntity;
import com.zaytsevaa.eptaskmanager.ui.TextOnClickHandler;
import com.zaytsevaa.eptaskmanager.viewmodel.MainViewModel;

import static com.zaytsevaa.eptaskmanager.utilities.Constants.TASK_ID_KEY;

public class DetailActivity extends AppCompatActivity {

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailBinding activityDetailBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initViewModel(activityDetailBinding);

        TextOnClickHandler handler = new TextOnClickHandler(DetailActivity.this, mViewModel);
        activityDetailBinding.setHandler(handler);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mViewModel.mCurrentTask.getValue().getTaskStatus() == TaskEntity.TaskStatus.ARRIVED) {
                    AlertDialog dialog = new AlertDialog.Builder(DetailActivity.this)
                            .setTitle(getResources().getString(R.string.open_close_dialog_text))
                            .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mViewModel.taskTimerSpinner(true);
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mViewModel.taskTimerSpinner(false);
                                }
                            })
                            .create();
                    dialog.show();
                } else {
                    mViewModel.taskTimerSpinner();
                }
            }
        });
    }

    private void initViewModel(ActivityDetailBinding activityDetailBinding) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            mViewModel.setCurrentTask();
        } else {
            int taskId = extras.getInt(TASK_ID_KEY);
            mViewModel.setCurrentTask(taskId);
        }

        activityDetailBinding.setLifecycleOwner(this);
        activityDetailBinding.setViewModel(mViewModel);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            mViewModel.saveTask();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
