package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.models.SubCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<SubCategoryModel> subCategoryList = new ArrayList<>();

    public SubCategoryAdapter(Context context, List<SubCategoryModel> subCategoryList ) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.subCategoryList = subCategoryList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listitem_subcategory, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        final SubCategoryModel currentObj = subCategoryList.get(position);
        holder.setData(currentObj, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView imgTitle;

        int position;
        public SubCategoryModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            imgTitle = itemView.findViewById(R.id.imgTitle);

        }

        private void setData(SubCategoryModel current, int position) {

            this.txtName.setText(current.SubCategoryName);
            if (current.ImageName != null)
                if (!current.ImageName.equals(""))
                    Glide.with(context).load(app.imgMainAddr + app.subCategoryImgAddr + current.ImageName).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgTitle);

            this.position = position;
            this.current = current;

        }
    }
}