package com.example.lab11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button przycisk;
    private EditText nr1,nr2,nr3,nr4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        przycisk = findViewById(R.id.getinfo);

        int[] pola = new int[] {R.id.nr1,R.id.nr2,R.id.nr3,R.id.nr4};

        for (int j : pola) {
            EditText b = findViewById(j);
            b.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  //  if(charSequence.length() == 0) b.setText("0");
                    String zm = String.valueOf(charSequence);
                    if (!(zm.matches("[0-9]+") && Integer.valueOf(zm) <256  && Integer.valueOf(zm) >= 0)) {
                        b.setText("0");
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }



    }



    public void buttonPress(View view){  getIPInfo(); }

    private String getIP() {
        EditText e1 = (EditText) findViewById(R.id.nr1);
        EditText e2 = (EditText) findViewById(R.id.nr2);
        EditText e3 = (EditText) findViewById(R.id.nr3);
        EditText e4 = (EditText) findViewById(R.id.nr4);
        String ip = e1.getText().toString() + "." + e2.getText().toString() + "." +
                e3.getText().toString() + "." + e4.getText().toString();
        return ip;
    }

    private void printInfo(IPInfo info) {
        TextView textView = (TextView) findViewById(R.id.inf1);
        String s;
        if(info == null) s = "Failed";
        else {
            s = "ip: " + info.getIp() + "\n" +
                    "hostname: " + info.getHostname() + "\n" +
                    "city: " + info.getCity() + "\n" +
                    "region: " + info.getRegion() + "\n" +
                    "country: " + info.getCountry() + "\n" +
                    "loc: " + info.getLoc() + "\n" +
                    "org: " + info.getOrg() + "\n" +
                    "postal: " + info.getPostal() + "\n" +
                    "timezone: " + info.getTimezone() + "\n" +
                    "readme: " + info.getReadme();
        }
        textView.setText(s);
    }

    private void getIPInfo(){
        ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class, getIP());
        Call<IPInfo> call = apiInterface.getIPInfo();
        call.enqueue(new Callback<IPInfo>() {
            @Override
            public void onResponse(Call<IPInfo> call, Response<IPInfo> response) {
                printInfo(response.body());
            }
            @Override
            public void onFailure(Call<IPInfo> call, Throwable t) {
                printInfo(null);
            }
        });
    }
}