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

import com.example.mtask_mobile.vo.Board;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

    private Context mContext;
    private List<Board> mBoardList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        TextView boardName;
        TextView boardDesc;
        TextView boardTaskCnt;
        TextView boardCreator;
        TextView boardCreatedDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            boardName = itemView.findViewById(R.id.board_name_item);
            boardDesc = itemView.findViewById(R.id.board_desc_item);
            boardTaskCnt = itemView.findViewById(R.id.board_task_cnt_item);
            boardCreator = itemView.findViewById(R.id.board_creator_item);
            boardCreatedDate = itemView.findViewById(R.id.board_created_date_item);
        }
    }

    public BoardAdapter(List<Board> boardList) {
        mBoardList = boardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext == null) {
            mContext = viewGroup.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.board_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();

                Board board = mBoardList.get(position);

                Intent intent = new Intent(mContext, BoardActivity.class);
                intent.putExtra("boardId", board.getUuid());
                mContext.startActivity(intent);

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Board board = mBoardList.get(position);
        holder.boardName.setText(board.getBoardName());
        if (board.getDescPlainText() == null || board.getDescPlainText().equals("null")) {
            holder.boardDesc.setText("No Description");
        } else {
            holder.boardDesc.setText(board.getDescPlainText());
        }

        holder.boardTaskCnt.setText(String.valueOf(board.getTaskCnt()));
        holder.boardCreator.setText(board.getConstructorName());
        holder.boardCreatedDate.setText(board.getCreatedDateTime());
    }

    @Override
    public int getItemCount() {
        return mBoardList.size();
    }
}
