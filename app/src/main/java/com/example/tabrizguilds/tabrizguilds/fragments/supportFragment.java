package com.example.tabrizguilds.tabrizguilds.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.navigationDrawerActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class supportFragment extends Fragment {

    private WebView webView;
    private LinearLayout lytDisconnect;
    private RelativeLayout relativeMenu;

    public supportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        lytDisconnect = (LinearLayout) view.findViewById(R.id.lytDisconnect);
        relativeMenu = (RelativeLayout) view.findViewById(R.id.relativeMenu);
        app.check = 3;

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        if (!app.isInternetOn()) {
            webView.setVisibility(View.GONE);
            lytDisconnect.setVisibility(View.VISIBLE);
        }


        relativeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getActivity(), navigationDrawerActivity.class);
                startActivity(mapIntent);
                getActivity().overridePendingTransition(R.anim.bottom_to_top, R.anim.stay);
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.clearCache(false);

        webView.setWebViewClient(new WebViewClient(){
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         view.loadUrl(url);
                                         return super.shouldOverrideUrlLoading(view, url);
                                     }
                                 }

        );
        webView.loadUrl(app.supportAddr + "support/support.html");

//        String customHtml = "<html><body><h1>Hello, WebView</h1></body></html>";
//        webView.loadData(customHtml, "text/html", "UTF-8");
        return view;
    }


    public void setWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.clearCache(false);

        webView.setWebViewClient(new WebViewClient(){
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         view.loadUrl(url);
                                         return super.shouldOverrideUrlLoading(view, url);
                                     }
                                 }

        );
        webView.loadUrl("http://gsharing.ir/support/support.html");
    }

}
