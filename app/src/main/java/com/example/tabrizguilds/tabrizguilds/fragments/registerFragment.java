package com.example.tabrizguilds.tabrizguilds.fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabrizguilds.tabrizguilds.R;
import com.example.tabrizguilds.tabrizguilds.app;
import com.example.tabrizguilds.tabrizguilds.services.WebService;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class registerFragment extends Fragment {

    private Button btnRegister, btnPhoneSubmit, btnCodeSubmit;
    private EditText edtName, edtLName, edtMobile, edtEmail, edtPass, edtPhone;
    Dialog dialog2;
    LinearLayout lytSms, lytCodeAccept, lytRegister;
    EditText edtCode;
    TextView txtTryAgain;

    CallBackPhone callBackPhone;

    String codeFromServer, CodeFromUser;

    private BroadcastReceiver smsBroadcastReceiver;
    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    Bundle bundle;

    String IMEI = "0", macAddress;

    public registerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initView(view);

//        if (!isReadSmsAllowed())
//            requestSmsPermission();
//        smsBroadcastReceiver = new BroadcastReceiver() {
//
//            String code;
//            Context context;
//            private final String serverPhone = "+9810000007707077";
//
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.i("smsBroadcastReceiver", "onReceive1");
//                this.context = context;
//
//                // Retrieves a map of extended data from the intent.
//                bundle = intent.getExtras();
//
//                try {
//
//                    if (bundle != null) {
//
//                        final Object[] pdusObj = (Object[]) bundle.get("pdus");
//
//                        for (int i = 0; i < pdusObj.length; i++) {
//
//                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
//                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();
//
//                            String senderNum = phoneNumber;
//                            String message = currentMessage.getDisplayMessageBody();
//
//                            Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
//
//
//                            if(senderNum.equals(serverPhone)){
//
//                                if (!message.equals("")) {
//                                    //code = message.substring(0, message.indexOf(":"));
//                                    code = message.substring(message.indexOf(":") + 1);
//                                    edtCode.setText(code);
//                                }
//                            }
//                        } // end for loop
//                    } // bundle is null
//                } catch (Exception e) {
//                    Log.e("SmsReceiver", "Exception smsReceiver" + e);
//                }
//            }
//        };


//        if (!isReadStateAllowed()) {
//            requestStoragePermission();
//        }

        txtTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callBackPhone != null)
                    if (callBackPhone.getStatus() == AsyncTask.Status.RUNNING)
                        callBackPhone.cancel(true);

                lytSms.setVisibility(View.VISIBLE);
                lytRegister.setVisibility(View.GONE);
                lytCodeAccept.setVisibility(View.GONE);

            }
        });

        btnPhoneSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPhone.getText().toString().equals("")) {
//                    String sub = edtPhone.getText().toString().substring(0,2);
                    if (edtPhone.getText().toString().length() == 11 && edtPhone.getText().toString().substring(0, 2).equals("09")) {

                        callBackPhone = new CallBackPhone();
                        callBackPhone.execute();

                    } else
                        Toast.makeText(getContext(), "شماره تلفن صحیح نیست", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), "لطفا شماره تلفن را وارد کنید", Toast.LENGTH_LONG).show();
            }
        });

        btnCodeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtCode.getText().toString().equals("")) {
//                    String sub = edtPhone.getText().toString().substring(0,2);
                    if (edtCode.getText().toString().equals(codeFromServer)) {

                        lytSms.setVisibility(View.GONE);
                        lytRegister.setVisibility(View.VISIBLE);
                        lytCodeAccept.setVisibility(View.GONE);

                    } else
                        Toast.makeText(getContext(), "کدوارد شده صحیح نیست", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getContext(), "لطفا کد دریافت شده را وارد کنید", Toast.LENGTH_LONG).show();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtName.getText().toString().equals("") && !edtLName.getText().toString().equals("") && !edtPhone.getText().toString().equals("") && !edtEmail.getText().toString().equals("") && !edtPass.getText().toString().equals("")) {
                    if (edtEmail.getText().toString().contains("@") && edtEmail.getText().toString().contains(".")) {
                        if (edtPhone.getText().toString().length() == 11) {


//                                TelephonyManager telephonyManager = (TelephonyManager) app.context.getSystemService(app.context.TELEPHONY_SERVICE);
//                                IMEI = telephonyManager.getDeviceId();
////                Toast.makeText(getContext() , "sent" , Toast.LENGTH_SHORT).show();
//
//                                WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//                                WifiInfo wInfo = wifiManager.getConnectionInfo();
//                                macAddress = wInfo.getMacAddress();


                            WebServiceCallBack callBack = new WebServiceCallBack();
                            callBack.execute();


                        } else {
                            Toast.makeText(getContext(), "شماره تلفن نا معتبر است", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "ایمیل نا معتبر است", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "لطفا فیلد ها را کامل کنید", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }


    private void initView(View view) {
        edtName = view.findViewById(R.id.edtName);
        edtLName = view.findViewById(R.id.edtLName);
        edtMobile = view.findViewById(R.id.edtMobile);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPass = view.findViewById(R.id.edtPass);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnPhoneSubmit = view.findViewById(R.id.btnPhoneSubmit);
        lytSms = view.findViewById(R.id.lytSms);
        lytCodeAccept = view.findViewById(R.id.lytCodeAccept);
        lytRegister = view.findViewById(R.id.lytRegister);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtCode = view.findViewById(R.id.edtCode);
        txtTryAgain = view.findViewById(R.id.txtTryAgain);
        btnCodeSubmit = view.findViewById(R.id.btnCodeSubmit);
    }

    private class WebServiceCallBack extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String result;
        String name, lName, mobile, email, pass;

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

            name = edtName.getText().toString();
            lName = edtLName.getText().toString();
            mobile = edtPhone.getText().toString();
            mobile = mobile.substring(1);
            email = edtEmail.getText().toString();
            pass = edtPass.getText().toString();
        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postRegisterInfo(app.isInternetOn(), name, lName, mobile, email, pass);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            dialog2.dismiss();

            if (result != null) {

                if (Integer.parseInt(result) > 0) {

                    SharedPreferences prefs = getContext().getSharedPreferences("MYPREFS", 0);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("LogIn_Check", true);
                    editor.putInt("UserId", Integer.parseInt(result));
                    editor.putString("UserName", name);
                    editor.putString("UserLName", lName);
                    editor.putString("UserMobile", mobile);
                    editor.putString("UserEmail", email);
                    editor.putString("UserPass", pass);
                    editor.apply();

//                    Intent i = new Intent(getActivity(), MainActivity.class);
//                    startActivity(i);
                    getActivity().finish();

                } else if (Integer.parseInt(result) == 0) {
                    Toast.makeText(getContext(), "ناموفق", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(result) == -1) {
                    Toast.makeText(getContext(), "ایمیل تکراری است", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "خطا در برقراری ارتباط", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CallBackPhone extends AsyncTask<Object, Void, Void> {

        private WebService webService;
        String userPhone;
        String result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            webService = new WebService();

//            dialog2 = new Dialog(getContext());
//            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog2.setContentView(R.layout.dialog_waiting);
//            dialog2.setCancelable(true);
//            dialog2.setCanceledOnTouchOutside(true);
//            dialog2.show();

            userPhone = edtPhone.getText().toString();

            lytSms.setVisibility(View.GONE);
            lytRegister.setVisibility(View.GONE);
            lytCodeAccept.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Object... params) {

            result = webService.postUserPhone(app.isInternetOn(), userPhone);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

//            dialog2.dismiss();

            if (result != null) {

                if (Integer.parseInt(result) > 0) {

                    codeFromServer = result;
                    //Toast.makeText(getContext(), codeFromServer, Toast.LENGTH_LONG).show();

                } else codeFromServer = "0";

            } else codeFromServer = "-1";
        }
    }

    private boolean isReadSmsAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestSmsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_SMS)) {
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_SMS}, 23);
    }


    //permission for imei and mac address

    private boolean isReadStateAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_PHONE_STATE)) {
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, 23);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 23) {
        }
    }


}
