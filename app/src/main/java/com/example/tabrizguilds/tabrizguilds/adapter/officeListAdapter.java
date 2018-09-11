package com.example.tabrizguilds.tabrizguilds.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.fragments.detailsFragment;
import com.example.tabrizguilds.tabrizguilds.fragments.detailsOfficeFragment;
import com.example.tabrizguilds.tabrizguilds.models.PlacesModel;

import java.util.List;

/**
 * Created by mohamadHasan on 12/12/2017.
 */

public class officeListAdapter  extends RecyclerView.Adapter<officeListAdapter.myViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<PlacesModel> placesList;
    private String tblName;
    private int lastPosition = -1;

    public officeListAdapter(Context context, List<PlacesModel> placesList, String tblName) {
        this.context = context;
        this.placesList = placesList;
        mInflater = LayoutInflater.from(context);
        this.tblName = tblName;
    }

    @Override
    public officeListAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_office_list, parent, false);
        officeListAdapter.myViewHolder holder = new officeListAdapter.myViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(officeListAdapter.myViewHolder holder, int position) {

        final PlacesModel currentObj = placesList.get(position);
        holder.setData(currentObj, position);

        setAnimation(holder.itemView, position);

        //holder.rating.setRating(Float.parseFloat("2.0"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsOfficeFragment fragment = new detailsOfficeFragment();
                Bundle args = new Bundle();
                args.putInt("ID", currentObj.id);
                args.putString("TBL_NAME", tblName);
                fragment.setArguments(args);



                MainActivity activity = (MainActivity) context;
                FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_back_enter, R.anim.fragment_bacl_exit);
                ft.replace(R.id.container, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtAddress;
        private ImageView imgNews;
        private TextView txtType;

        int position;
        public PlacesModel current;


        myViewHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtAddress = (TextView) itemView.findViewById(R.id.txtAddress);
            imgNews = (ImageView) itemView.findViewById(R.id.imgNews);
            txtType = (TextView) itemView.findViewById(R.id.txtType);

        }

        public void setData(PlacesModel current, int position) {

//            this.txtName.setText(current.Name);
//            this.txtAddress.setText(current.address);
//            //this.imgNews.setImageResource();
//            if (current.image != null)
//                if (!current.image.equals(""))
//            Glide.with(context).load(app.imgMainAddr + app.officeImgAddr + current.image).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgNews);
//
//            this.txtType.setText(getPlaceType(8, current.type));

            this.position = position;
            this.current = current;

        }


    }

    public String getPlaceType(int mainType, int type) {

        String returnType = "";

        switch (mainType) {
            case 1:
                tblName = "Tbl_Eating";

                switch (type) {
                    case 1:
                        returnType = "رستوران";
                        break;
                    case 2:
                        returnType = "فست فود";
                        break;
                    case 3:
                        returnType = "کافی شاپ";
                        break;
                    case 4:
                        returnType = "شیرینی و آجیل";
                        break;
                    case 5:
                        returnType = "بستنی";
                        break;
                    default:
                }

                break;
            case 2:
                tblName = "Tbl_Shoppings";
                switch (type) {
                    case 1:
                        returnType = "مرکز خرید";
                        break;
                    case 2:
                        returnType = "فروشگاه";
                        break;
                    case 3:
                        returnType = "بازارچه";
                        break;
                    default:
                }
                break;
            case 3:
                tblName = "Tbl_Rests";
                switch (type) {
                    case 1:
                        returnType = "هتل";
                        break;
                    case 2:
                        returnType = "مسافرخانه";
                        break;
                    case 3:
                        returnType = "خوابگاه";
                        break;
                    default:
                }
                break;
            case 4:
                tblName = "Tbl_Tourisms";
                switch (type) {
                    case 1:
                        returnType = "تفریحی";
                        break;
                    case 2:
                        returnType = "میراث فرهنگی";
                        break;
                    case 3:
                        returnType = "پارک";
                        break;
                    case 4:
                        returnType = "جاذبه های طبیعی";
                        break;
                    default:
                }
                break;
            case 5:
                tblName = "Tbl_Culturals";
                switch (type) {
                    case 1:
                        returnType = "موزه";
                        break;
                    case 2:
                        returnType = "تئاتر و سینما";
                        break;
                    case 3:
                        returnType = "جشنواره";
                        break;
                    default:
                }
                break;
            case 6:
                tblName = "Tbl_Transports";
                switch (type) {
                    case 1:
                        returnType = "تاکسی";
                        break;
                    case 2:
                        returnType = "اتوبوس";
                        break;
                    case 3:
                        returnType = "قطار";
                        break;
                    case 4:
                        returnType = "آژانس تلفنی";
                        break;
                    default:
                }
                break;
            case 7:
                tblName = "Tbl_Services";
                switch (type) {
                    case 1:
                        returnType = "سالن ورزشی";
                        break;
                    case 2:
                        returnType = "تعمیر گاه";
                        break;
                    case 3:
                        returnType = "پارکینگ";
                        break;
                    case 4:
                        returnType = "پمپ بنزین";
                        break;
                    case 5:
                        returnType = "مراکز صدور پلاک";
                        break;
                    default:
                }
                break;
            case 8:
                tblName = "Tbl_Offices";
                switch (type) {
                    case 1:
                        returnType = "مسجد و امام زاده";
                        break;
                    case 2:
                        returnType = "دانشگاه";
                        break;
                    case 3:
                        returnType = "ادارات";
                        break;
                    case 4:
                        returnType = "کلانتری";
                        break;
                    case 5:
                        returnType = "بانک";
                        break;
                    default:
                }
                break;
            case 9:
                tblName = "Tbl_Medicals";
                switch (type) {
                    case 1:
                        returnType = "بیمارستان";
                        break;
                    case 2:
                        returnType = "درمانگاه";
                        break;
                    case 3:
                        returnType = "داروخاه";
                        break;
                    case 4:
                        returnType = "کلینیک";
                        break;
                    default:
                }
                break;
            case 10:
                tblName = "Tbl_Events";
                break;
            default:
                tblName = "";
        }

        return returnType;
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

}
