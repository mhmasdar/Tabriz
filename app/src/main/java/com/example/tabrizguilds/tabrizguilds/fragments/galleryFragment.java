package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.TouchImageView;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.db.DatabaseHelper;
import com.example.tabrizguilds.tabrizguilds.imageActivity;
import com.example.tabrizguilds.tabrizguilds.models.ImgModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class galleryFragment extends Fragment {


    private Dialog dialog;
    private ImageView[] img = new ImageView[9];
//    private ImageView img2;
//    private ImageView img3;
//    private ImageView img4;
//    private ImageView img5;
//    private ImageView img6;
//    private ImageView img7;
//    private ImageView img8;
//    private ImageView img9;
    private TouchImageView layout;
    private LinearLayout lytLoading;
    private android.app.FragmentManager fm;

    List<ImgModel> imgList;
    int idRow;
    int i;

    public galleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initView(view);

        Bundle args = getArguments();
        idRow = args.getInt("ID");

        DatabaseCallback databaseCallback = new DatabaseCallback(getContext());
        databaseCallback.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);



        fm = getActivity().getFragmentManager();


        img[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(0).Name);
                startActivity(intent);
            }
        });
        img[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(1).Name);
                startActivity(intent);
            }
        });
        img[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(2).Name);
                startActivity(intent);
            }
        });
        img[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(3).Name);
                startActivity(intent);
            }
        });
        img[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(4).Name);
                startActivity(intent);
            }
        });
        img[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(5).Name);
                startActivity(intent);
            }
        });
        img[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(6).Name);
                startActivity(intent);
            }
        });
        img[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(7).Name);
                startActivity(intent);
            }
        });
        img[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), imageActivity.class);
                intent.putExtra("ImgName", imgList.get(8).Name);
                startActivity(intent);
            }
        });


        return view;
    }

    private void initView(View view) {
        img[1] = (ImageView) view.findViewById(R.id.img2);
        img[2] = (ImageView) view.findViewById(R.id.img3);
        img[3] = (ImageView) view.findViewById(R.id.img4);
        img[4] = (ImageView) view.findViewById(R.id.img5);
        img[5] = (ImageView) view.findViewById(R.id.img6);
        img[6] = (ImageView) view.findViewById(R.id.img7);
        img[7] = (ImageView) view.findViewById(R.id.img8);
        img[8] = (ImageView) view.findViewById(R.id.img9);
        img[0] = (ImageView) view.findViewById(R.id.img1);
    }


    public class DatabaseCallback extends AsyncTask<Object, Void, Void> {


        private DatabaseHelper databaseHelper;
        private Context context;

        public DatabaseCallback(Context context) {
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imgList = new ArrayList<>();
            databaseHelper = new DatabaseHelper(context);
        }


        @Override
        protected Void doInBackground(Object... objects) {

//            imgList = databaseHelper.selectPlacesImages(mainType, idRow, 1);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (imgList != null) {
                if (imgList.size() > 0) {
                    for (int i = 0; i < imgList.size(); i++) {
                        img[i].setVisibility(View.VISIBLE);
                        if (imgList.get(i).Name != null) {
                            if (!imgList.get(i).Name.equals("")) {
                                Glide.with(context).load(app.imgMainAddr + app.placesImgAddr + imgList.get(i).Name).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(img[i]);
                            }
                        }
                    }
                }
            }

        }



    }

}
