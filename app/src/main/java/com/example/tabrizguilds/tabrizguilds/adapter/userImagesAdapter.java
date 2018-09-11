package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.imageActivity;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;

import java.util.List;


/**
 * Created by Mohamad Hasan on 2/16/2018.
 */

public class userImagesAdapter extends RecyclerView.Adapter<userImagesAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    List<ImgModel> imgList;

    public userImagesAdapter(Context context, List<ImgModel> imgList) {
        this.context = context;
        this.imgList = imgList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public userImagesAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user_images_list, parent, false);
        userImagesAdapter.myViewHolder holder = new userImagesAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(userImagesAdapter.myViewHolder holder, int position) {

        final ImgModel currentObj = imgList.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, imageActivity.class);
                intent.putExtra("ImgName", currentObj.Name);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        int position;
        public ImgModel current;

        myViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
        }

        private void setData(ImgModel current, int position) {

            if (current.Name != null)
                if (!current.Name.equals(""))
//                    Glide.with(context).load(app.imgMainAddr + getImgAddr(current.type) + current.Name).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img);

            this.position = position;
            this.current = current;

        }

    }

}