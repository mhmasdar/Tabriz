package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.models.MenuModel;

import java.util.List;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class menuDialogAdapter extends RecyclerView.Adapter<menuDialogAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<MenuModel> menuList;


    public menuDialogAdapter(Context context, List<MenuModel> menuList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.menuList = menuList;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_menu_list, parent, false);
        myViewHolder holder = new myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        final MenuModel currentObj = menuList.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMenuName;
        private TextView txtMenuPrice;

        int position;
        public MenuModel current;

        myViewHolder(View itemView) {
            super(itemView);

            txtMenuName = (TextView) itemView.findViewById(R.id.txtMenuName);
            txtMenuPrice = (TextView) itemView.findViewById(R.id.txtMenuPrice);

        }

        private void setData(MenuModel current, int position) {

            this.txtMenuName.setText(current.Name);
            this.txtMenuPrice.setText(current.Price + " ریال");

            this.position = position;
            this.current = current;

        }

    }
}
