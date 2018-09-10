package com.example.tabrizguilds.tabrizguilds.fragments;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tabrizguilds.tabrizguilds.MainActivity;
import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.SplashActivity;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.loginActivity;
import com.example.tabrizguilds.tabrizguilds.models.ActionModel;
import com.example.tabrizguilds.tabrizguilds.models.UserModel;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class loginFragment extends Fragment {


    private Button btnUserLogin;
    private EditText edtUserName, edtUserPass;
    private TextView txtForgetPass;
    private Dialog dialog, dialog2;
    LinearLayout lytLoading;

    RadioButton radioPhone, radioEmail;
    EditText edtPhoneEmail;

    public loginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initView(view);

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtUserName.getText().toString().equals("") && !edtUserPass.getText().toString().equals("")) {
                    if (edtUserName.getText().toString().contains("@") && edtUserName.getText().toString().contains(".")) {
                        WebServiceCallBack callBack = new WebServiceCallBack();
                        callBack.execute();
                    }
                    else{
                        Toast.makeText(getContext(), "ایمیل نا معتبر است", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });


        txtForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showdialog();
            }
        });

        return view;
    }


    private void initView(View view) {
        edtUserName = view.findViewById(R.id.edtUserName);
        edtUserPass = view.findViewById(R.id.edtUserPass);
        btnUserLogin = view.findViewById(R.id.btnUserLogin);
        txtForgetPass = view.findViewById(R.id.txtForgetPass);


    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        UserModel result;
        String user, pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

            dialog2 = new Dialog(getContext());
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(R.layout.dialog_waiting);
            dialog2.setCancelable(true);
            dialog2.setCanceledOnTouchOutside(true);
            dialog2.show();

            user = edtUserName.getText().toString();
            pass = edtUserPass.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postLoginInfo(app.isInternetOn(), user, pass);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog2.dismiss();

            if (result != null) {

                if (result.id > 0) {

                    SharedPreferences prefs = getContext().getSharedPreferences("MYPREFS", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("LogIn_Check", true);
                    editor.putInt("UserId", result.id);
                    editor.putString("UserName", result.name);
                    editor.putString("UserLName", result.lName);
                    editor.putString("UserMobile", result.mobile);
                    editor.putString("UserEmail", user);
                    editor.putString("UserPass", pass);
                    editor.apply();

//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    startActivity(i);

                    WebServiceCallBackFavorite callBackF = new WebServiceCallBackFavorite(result.id);
                    callBackF.execute();

                    getActivity().finish();
                    Toast.makeText(getContext(), "ورود موفق به حساب کاربری", Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getContext(), "نام کاربری یا کلمه عبور اشتباه است", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class WebServiceCallBackForget extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String email;
        int type;

        public WebServiceCallBackForget(String email){
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
            lytLoading.setVisibility(View.VISIBLE);

            if (radioEmail.isChecked()){
                type = 1;
            }
            else {
                type = 2;
            }
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.recoverPass(app.isInternetOn(), email, type);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lytLoading.setVisibility(View.INVISIBLE);

            if (result != null) {

                if (Integer.parseInt(result) == 1) {

                    Toast.makeText(getContext(), "کلمه عبور ارسال شد", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
                else if (Integer.parseInt(result) == -1){
                    Toast.makeText(getContext(), "حساب کاربری وجود ندارد", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class WebServiceCallBackFavorite extends AsyncTask<Object, Void, Void> {

        private WebService webService;
//        List<ActionModel> result;
        int id;
        int result;

        public WebServiceCallBackFavorite(int id){
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.getPlaces(app.isInternetOn());

            result = webService.getImages(app.isInternetOn());

            webService.getFavorites(app.isInternetOn(), id);

            webService.getLikesAndRates(app.isInternetOn(), id);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


        }
    }


    private void showdialog() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_forget_pass);
        Button btnSendEmail = (Button) dialog.findViewById(R.id.btnSendEmail);
        Button btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        lytLoading = dialog.findViewById(R.id.lytLoading);
        radioPhone = dialog.findViewById(R.id.radioPhone);
        radioEmail = dialog.findViewById(R.id.radioEmail);
        edtPhoneEmail = dialog.findViewById(R.id.edtPhoneEmail);


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtForgetPass.getText().equals("")){
                    WebServiceCallBackForget callBackForget = new WebServiceCallBackForget(edtPhoneEmail.getText().toString());
                    callBackForget.execute();
                }
                else{
                    Toast.makeText(getContext(), "لطفا ایمیل را وارد کنید" , Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();


        radioEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtPhoneEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                }
            }
        });

        radioPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    edtPhoneEmail.setInputType(InputType.TYPE_CLASS_PHONE);
                }
            }
        });

    }

}
