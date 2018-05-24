package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.commentsActivity;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.CommentModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.like.LikeButton;

import org.osmdroid.tileprovider.modules.IFilesystemCache;

import java.util.List;

/**
 * Created by mohamadHasan on 27/11/2017.
 */

public class commentsAdapter extends RecyclerView.Adapter<commentsAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private RecyclerView answerRecycler;
    private LinearLayout lytReplyPreview;
    private ImageView btnCloseReply;
    private TextView txtReplyName, txtReplyBody;
    private List<CommentModel> commentList;

    public static int idAnswer = -1;

    private SharedPreferences prefs;
    int idUser;

    public commentsAdapter(Context context, LinearLayout lytReplyPreview, ImageView btnCloseReply, TextView txtReplyBody, TextView txtReplyName, List<CommentModel> commentList) {
        this.context = context;
        this.lytReplyPreview = lytReplyPreview;
        this.btnCloseReply = btnCloseReply;
        this.txtReplyBody = txtReplyBody;
        this.txtReplyName = txtReplyName;
        this.commentList = commentList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_comments_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final myViewHolder holder, int position) {

        final CommentModel currentObj = commentList.get(position);
        holder.setData(currentObj, position);

        btnCloseReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.GONE);
                idAnswer = -1;
            }
        });

        holder.lytReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lytReplyPreview.setVisibility(View.VISIBLE);
                txtReplyName.setText(currentObj.name);
                txtReplyBody.setText(currentObj.body);
                idAnswer = currentObj.id;
            }
        });


        prefs = context.getSharedPreferences("MYPREFS", 0);
        idUser = prefs.getInt("UserId", -1);
        if (idUser > 0){
            if (prefs.getBoolean("CommentLike" + currentObj.id, false))
                holder.btnLike.setLiked(true);
            else
                holder.btnLike.setLiked(false);
        }

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (idUser > 0) {

                    if (prefs.getBoolean("CommentLike" + holder.current.id, false)){
                        holder.btnLike.setLiked(false);
                        holder.txtLikeCount.setText( Integer.parseInt(holder.txtLikeCount.getText().toString()) - 1 + "");
                        WebServiceCallLikeAdd callAdd = new WebServiceCallLikeAdd(holder.current.id, false, holder.btnLike, holder.txtLikeCount);
                        callAdd.execute();
                    }
                    else{
                        holder.btnLike.setLiked(true);
                        holder.txtLikeCount.setText( Integer.parseInt(holder.txtLikeCount.getText().toString()) + 1 + "");
                        WebServiceCallLikeAdd callAdd = new WebServiceCallLikeAdd(holder.current.id, true, holder.btnLike, holder.txtLikeCount);
                        callAdd.execute();
                    }

                }
                else{
                    Intent i = new Intent(context, loginActivity.class);
                    context.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName, txtDate;
        private TextView txtCommentBody;
        private LinearLayout lytReply;
        private LinearLayout lytCommentHeight;
        private LikeButton btnLike;
        private TextView txtLikeCount;

        int position;
        public CommentModel current;

        myViewHolder(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtCommentBody = (TextView) itemView.findViewById(R.id.txtCommentBody);
            lytReply = (LinearLayout) itemView.findViewById(R.id.lytReply);
            answerRecycler = (RecyclerView) itemView.findViewById(R.id.answerRecycler);
            lytCommentHeight = (LinearLayout) itemView.findViewById(R.id.lytCommentHeight);
            btnLike = (LikeButton) itemView.findViewById(R.id.btnLike);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            txtDate = itemView.findViewById(R.id.txtDate);

        }

        private void setData(CommentModel current, int position) {

            this.txtName.setText(current.name);
            this.txtCommentBody.setText(current.body);
            this.txtLikeCount.setText(current.likeCount + "");
            txtDate.setText(app.changeDateToString(current.date));

            if (current.answers.size() > 0)
                setUpRecyclerView(current.answers);

            this.position = position;
            this.current = current;

        }

    }

    private void setUpRecyclerView(List<CommentModel> answerList) {

        commentsAnswerAdapter adapter = new commentsAnswerAdapter(context, answerList);
        answerRecycler.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(context);
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        answerRecycler.setLayoutManager(mLinearLayoutManagerVertical);
    }


    private class WebServiceCallLikeAdd extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        boolean isLike;
        int id;
        LikeButton btnLike;
        TextView txtLikeCount;

        public WebServiceCallLikeAdd(int id, boolean isLike, LikeButton btnLike, TextView txtLikeCount){
            this.isLike = isLike;
            this.id = id;
            this.txtLikeCount = txtLikeCount;
            this.btnLike = btnLike;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            // id is for place
            result = webService.postCommentLikeDisLike(app.isInternetOn(),id , isLike);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (result != null) {

                if (result.equals("true")) {
                    if (isLike) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("CommentLike" + id, true);
                        editor.apply();
                    }
                    else if (!isLike){
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("CommentLike" + id, false);
                        editor.apply();
                    }
                }
                else {

                    if (isLike){
                        Toast.makeText(context, "ثبت پسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(false);
                        txtLikeCount.setText( Integer.parseInt(txtLikeCount.getText().toString()) - 1 + "");
                    }
                    else if (!isLike){
                        Toast.makeText(context, "ثبت نپسندیدن نا موفق", Toast.LENGTH_LONG).show();
                        btnLike.setLiked(true);
                        txtLikeCount.setText( Integer.parseInt(txtLikeCount.getText().toString()) + 1 + "");
                    }

                }

            } else {
                Toast.makeText(context, "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();
                if (isLike){
                    btnLike.setLiked(false);
                    txtLikeCount.setText( Integer.parseInt(txtLikeCount.getText().toString()) - 1 + "");
                }
                else if (!isLike){
                    btnLike.setLiked(true);
                    txtLikeCount.setText( Integer.parseInt(txtLikeCount.getText().toString()) + 1 + "");
                }
            }

        }

    }


}
