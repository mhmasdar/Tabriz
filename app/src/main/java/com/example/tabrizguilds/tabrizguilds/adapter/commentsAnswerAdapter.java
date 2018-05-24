package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.CommentModel;

import java.util.List;

/**
 * Created by mohamadHasan on 30/11/2017.
 */

public class commentsAnswerAdapter extends RecyclerView.Adapter<commentsAnswerAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CommentModel> commentList;

    public commentsAnswerAdapter(Context context, List<CommentModel> commentList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.commentList = commentList;
    }

    @Override
    public commentsAnswerAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comments_answer_list, parent, false);
        commentsAnswerAdapter.myViewHolder holder = new commentsAnswerAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(commentsAnswerAdapter.myViewHolder holder, int position) {

        final CommentModel currentObj = commentList.get(position);
        holder.setData(currentObj, position);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }



    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtCommentBody;

        int position;
        public CommentModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommentBody = (TextView) itemView.findViewById(R.id.txtCommentBody);
        }

        private void setData(CommentModel current, int position) {

            this.txtName.setText(current.name);
            this.txtCommentBody.setText(current.body);

            this.position = position;
            this.current = current;

        }

    }

}