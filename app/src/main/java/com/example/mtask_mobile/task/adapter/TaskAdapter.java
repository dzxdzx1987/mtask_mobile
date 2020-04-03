package com.example.mtask_mobile.task.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mtask_mobile.R;
import com.example.mtask_mobile.com.example.mtask_mobile.vo.Task;
import com.example.mtask_mobile.task.view.TaskActivity;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context mContext;
    private List<Task> mTaskList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView taskOwnerImage;
        TextView taskName;
        TextView taskContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            taskOwnerImage = (ImageView) itemView.findViewById(R.id.task_owner_image);
            taskName = (TextView) itemView.findViewById(R.id.task_name);
            taskContent = (TextView) itemView.findViewById(R.id.task_content);
        }
    }

    public TaskAdapter(List<Task> taskList) {
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.task_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                Task task = mTaskList.get(position);
                Intent intent = new Intent(mContext, TaskActivity.class);
                intent.putExtra(TaskActivity.TASK_NAME, task.getName());
                intent.putExtra(TaskActivity.TASK_CONTENT, task.getContent());
                intent.putExtra(TaskActivity.TASK_OWNER_IMAGE_URL, task.getOwnerImageUrl());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = mTaskList.get(position);
        holder.taskName.setText(task.getName());
        holder.taskContent.setText(task.getContent());
        Glide.with(mContext).load(task.getOwnerImageUrl()).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(holder.taskOwnerImage);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
