package com.example.mtask_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mtask_mobile.vo.TaskInfo;

import java.util.List;

public class BoardTaskAdapter extends RecyclerView.Adapter<BoardTaskAdapter.ViewHolder>{

    private Context mContext;
    private List<TaskInfo> mTaskList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        TextView boardTaskName;
        TextView boardTaskDesc;
        TextView boardFolder;
        TextView boardTaskCreator;
        TextView boardTaskCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            boardTaskName = itemView.findViewById(R.id.board_task_title_item);
            boardTaskDesc = itemView.findViewById(R.id.board_task_desc_item);
            boardFolder = itemView.findViewById(R.id.board_fold_item);
            boardTaskCreator = itemView.findViewById(R.id.board_task_creator_item);
            boardTaskCreatedDate = itemView.findViewById(R.id.board_task_created_date_item);
        }
    }

    public BoardTaskAdapter(List<TaskInfo> taskList) {
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.board_task_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                TaskInfo task = mTaskList.get(position);
/*
                Intent intent = new Intent(mContext, BoardActivity.class);
                intent.putExtra("boardId", task.getUuid());
                mContext.startActivity(intent);*/

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskInfo task = mTaskList.get(position);
        holder.boardTaskName.setText(task.getTitle());

        holder.boardTaskDesc.setText(task.getDesc());

        if (!task.isFolder()) {
            holder.boardFolder.setVisibility(View.GONE);
        } else {
            holder.boardFolder.setVisibility(View.VISIBLE);
        }

        holder.boardTaskCreator.setText(task.getConstructorName());
        holder.boardTaskCreatedDate.setText(task.getCreatedDateTime());
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }
}
