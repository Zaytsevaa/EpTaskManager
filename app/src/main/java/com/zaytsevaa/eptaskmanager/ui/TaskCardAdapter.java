package com.zaytsevaa.eptaskmanager.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zaytsevaa.eptaskmanager.R;
import com.zaytsevaa.eptaskmanager.databinding.TaskItemBinding;
import com.zaytsevaa.eptaskmanager.db.TaskEntity;

import java.util.List;

public class TaskCardAdapter extends RecyclerView.Adapter<TaskCardAdapter.ViewHolder> {

    private OnItemClickListener mListener;
    private List<TaskEntity> mTasks;


    public interface OnItemClickListener {
        void onItemClick(int userId);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public int getCurrentTaskId(int position) {
        return mTasks.get(position).getId();
    }

    public TaskCardAdapter(List<TaskEntity> tasks) {
        this.mTasks = tasks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskItemBinding taskItemBinding = DataBindingUtil.
                inflate(inflater, R.layout.task_item, parent, false);

        return new ViewHolder(taskItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskEntity currentTask = mTasks.get(position);
        holder.taskItemBinding.setTasks(currentTask);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TaskItemBinding taskItemBinding;

        public ViewHolder(@NonNull final TaskItemBinding taskItemBinding) {
            super(taskItemBinding.getRoot());

            this.taskItemBinding = taskItemBinding;

            View view = taskItemBinding.getRoot();
            view.setLongClickable(true);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        int taskId = mTasks.get(position).getId();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(taskId);
                        }
                    }
                }
            });
        }
    }
}
