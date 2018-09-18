package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.fragments.newsDetailsFragment;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.NewsModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;
import com.like.LikeButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mohamadHasan on 23/11/2017.
 */

public class newsListAdapter extends RecyclerView.Adapter<newsListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private int lastPosition = -1;
    private List<NewsModel> newsList;
    private static int count = 1;
    private boolean searchFlag;

    public static int selectedPosition = 0;



    public newsListAdapter(Context context, List<NewsModel> newsList, boolean searchFlag) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.newsList = newsList;
        this.searchFlag = searchFlag;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_news_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {

        holder.setListeners();
        setAnimation(holder.itemView, position);

        final NewsModel currentObj = newsList.get(position);
        holder.setData(currentObj, position);

        holder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, newsList.get(position).Title + "\n" + "http://arkatech.ir/");
                context.startActivity(Intent.createChooser(share, "به اشتراک گذاری از طریق..."));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPosition = position;

                app.check = 6;
                newsDetailsFragment fragment = new newsDetailsFragment();

                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);
                args.putInt("Type", currentObj.Type);
                args.putInt("LikeCount", currentObj.likeCount);
                args.putInt("Date", currentObj.Date);
                args.putString("Img", currentObj.Img);
                args.putString("Body", currentObj.Body);
                args.putString("Title", currentObj.Title);
                args.putString("NewsLink", currentObj.externalLink);
                fragment.setArguments(args);

                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        if (holder.position == newsList.size() - 1){
            if (!searchFlag) {
                //Toast.makeText(context, "Last " + position, Toast.LENGTH_LONG).show();
                WebServiceCallBackList callBackList = new WebServiceCallBackList();
                callBackList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtNewsTitle;
        private TextView txtNewsBody;
        private ImageView imgNews;
        private TextView txtCommentCount, txtType;
        private TextView txtDate;
        private TextView txtLikeCount;
        private ImageView imgShare;
        private LikeButton imgLike;

        int position;
        public NewsModel current;

        myViewHolder(View itemView) {
            super(itemView);
            imgShare = (ImageView) itemView.findViewById(R.id.imgShare);
            //imgLike = (LikeButton) itemView.findViewById(R.id.btnLike);
            txtCommentCount = (TextView) itemView.findViewById(R.id.txtCommentCount);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtNewsTitle = (TextView) itemView.findViewById(R.id.txtNewsTitle);
            txtNewsBody = (TextView) itemView.findViewById(R.id.txtNewsBody);
            txtLikeCount = (TextView) itemView.findViewById(R.id.txtLikeCount);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
            txtType = (TextView) itemView.findViewById(R.id.txtType);

        }

        private void setData(NewsModel current, int position) {

            this.txtNewsTitle.setText(current.Title);
            this.txtNewsBody.setText(current.Body);
            //this.txtLikeCount.setText(current.likeCount + "");
            this.txtCommentCount.setText(current.commentCount + "");
            this.txtDate.setText(app.changeDateToString(current.Date));
            this.txtType.setText(getPlaceType(current.Type));
            if (current.Img != null)
                if (!current.Img.equals(""))
                    Glide.with(context).load(app.imgMainAddr + app.newsImgAddr + current.Img).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNews);


            this.position = position;
            this.current = current;

        }

        public void setListeners() {
            Log.i("TAG", "onSetListeners" + position);
            //imgLike.setOnClickListener(this);
            imgShare.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnLike:
                    break;
                case R.id.imgShare:
                    //editItem(position, current);
                    break;
            }
        }


    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    private String getPlaceType(int Type){

        String returnType = "";
        switch (Type){
            case 61:
                returnType = "اقتصادی";
                break;
            case 60:
                returnType = "عمرانی";
                break;
            case 62:
                returnType = "گردشگری";
                break;
            case 63:
                returnType = "فرهنگ و جامعه";
                break;
            default:
        }

        return returnType;
    }

    private class WebServiceCallBackList extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        private List<NewsModel> tmpList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            tmpList = new ArrayList<>();
        }

        @Override
        protected Void doInBackground(Object... params) {

            tmpList = webService.getNews(app.isInternetOn(), count);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (tmpList != null) {

                if (tmpList.size() > 0) {
                    newsList.addAll(tmpList);
                    notifyDataSetChanged();
                    count++;
                } else {
                    //Toast.makeText(getApplicationContext(), "موردی وجود ندارد", Toast.LENGTH_LONG).show();
                }

            } else {
                //Toast.makeText(getApplicationContext(), "اتصال با سرور برقرار نشد", Toast.LENGTH_LONG).show();

            }

        }

    }

}
