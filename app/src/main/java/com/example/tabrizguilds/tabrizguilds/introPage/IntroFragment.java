package com.example.tabrizguilds.tabrizguilds.introPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;

/**
 * Created by mohamadHasan on 20/07/2017.
 */

public class IntroFragment extends Fragment {

    private static final String PAGE = "page";
    int layoutResId;
    private int mPage;
    private Typeface typeface;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public static IntroFragment newInstance(int page) {
        IntroFragment frag = new IntroFragment();
        Bundle b = new Bundle();
        //b.putInt(BACKGROUND_COLOR, backgroundColor);
        b.putInt(PAGE, page);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a \"" + PAGE + "\" argument!");
        mPage = getArguments().getInt(PAGE);

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/font.ttf");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Select a layout based on the current page

        switch (mPage) {
            case 0:
                layoutResId = R.layout.intro_fragment_layout_1;
                break;
            case 1:
                layoutResId = R.layout.intro_fragment_layout_2;
                break;
            case 2:
                layoutResId = R.layout.intro_fragment_layout_3;
                break;
            case 3:
                layoutResId = R.layout.intro_fragment_layout_4;
                break;
            case 4:
                layoutResId = R.layout.intro_fragment_layout_5;
                break;
            default:
                layoutResId = R.layout.intro_fragment_layout_6;
                break;
        }

        // Inflate the layout resource file
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        // Set the current page index as the View's tag (useful in the PageTransformer)
        view.setTag(mPage);

        if (mPage == 0)
        {
            TextView t = (TextView) view.findViewById(R.id.txtIntro1);
            TextView t1 = (TextView) view.findViewById(R.id.txtIntro1_1);
            t.setTypeface(typeface);
            t1.setTypeface(typeface);

            ImageView img = (ImageView) view.findViewById(R.id.imgIntro1);
            Glide.with(this).load(R.drawable.ic_intro1).into(img);
        }

        if (mPage == 1)
        {
            TextView t = (TextView) view.findViewById(R.id.txtIntro2);
            TextView t1 = (TextView) view.findViewById(R.id.txtIntro2_1);
            t.setTypeface(typeface);
            t1.setTypeface(typeface);

            ImageView img = (ImageView) view.findViewById(R.id.imgIntro2);
            Glide.with(this).load(R.drawable.ic_intro2_1).into(img);
        }

        if (mPage == 2)
        {
            TextView t = (TextView) view.findViewById(R.id.txtIntro3);
            TextView t1 = (TextView) view.findViewById(R.id.txtIntro3_1);
            t.setTypeface(typeface);
            t1.setTypeface(typeface);

            ImageView img = (ImageView) view.findViewById(R.id.imgIntro3);
            Glide.with(this).load(R.drawable.ic_intro3).into(img);
        }

        if (mPage == 3)
        {
            TextView t = (TextView) view.findViewById(R.id.txtIntro4);
            TextView t1 = (TextView) view.findViewById(R.id.txtIntro4_1);
            t.setTypeface(typeface);
            t1.setTypeface(typeface);

            ImageView img = (ImageView) view.findViewById(R.id.imgIntro4);
            Glide.with(this).load(R.drawable.ic_intro4).into(img);
        }

        if (mPage == 4)
        {
            TextView t = (TextView) view.findViewById(R.id.txtIntro5);
            TextView t1 = (TextView) view.findViewById(R.id.txtIntro5_1);
            t.setTypeface(typeface);
            t1.setTypeface(typeface);

            ImageView img = (ImageView) view.findViewById(R.id.imgIntro5);
            Glide.with(this).load(R.drawable.ic_intro5).into(img);
        }



        if (mPage == 5)
        {
            prefs = getContext().getSharedPreferences("login", 0);
            editor = prefs.edit();

            Button btnStart = (Button) view.findViewById(R.id.btnIntro6);
            TextView t = (TextView) view.findViewById(R.id.txtIntro6);

            btnStart.setTypeface(typeface);
            t.setTypeface(typeface);

            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor.putBoolean("firstTime", true);
                    editor.commit();

                    Intent i = new Intent(getContext() , MainActivity.class);
                    getContext().startActivity(i);
                    getActivity().finish();
                }
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
