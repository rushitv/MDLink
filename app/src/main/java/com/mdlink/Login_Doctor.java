package com.mdlink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login_Doctor extends AppCompatActivity {

    TextView tv;
    EditText email, password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing__up__doctor);
/*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
*/

        tv = (TextView) findViewById(R.id.singup_submit);
        email = (EditText) findViewById(R.id.login_doc_email);
        password = (EditText) findViewById(R.id.login_doc_password);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length() == 0 || email.getText().toString().matches(emailPattern)) {
                } else {
                    email.setError("Enter Valid Email");
                    return;
                }

                if (password.getText().toString().length() == 0) {
                    password.setError("Password is required!");
                    password.requestFocus();
                }

                if (new ConnectionCall(Login_Doctor.this).connectiondetect()) {

                    new InserData().execute();
                } else {
                    new ConnectionCall(Login_Doctor.this).isConnectingToInternet();
                }


            }
        });
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }*/

    private class InserData extends AsyncTask<String, String, String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pd = new ProgressDialog(Login_Doctor.this);
            pd.setMessage("Loading..Hold A Second..");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("email", email.getText().toString());
            hashMap.put("password", password.getText().toString());


            return new MakeServiceCall().MakeServiceCall("http://api.themdlink.com/api/v1/login", MakeServiceCall.POST, hashMap);
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (pd.isShowing()) {
                pd.dismiss();
            }
            try {
                JSONObject json = new JSONObject(s);

                if (json.getString("status").equalsIgnoreCase("200")) {
                    Toast.makeText(Login_Doctor.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                    JSONObject jsonArray = json.getJSONObject("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        //JSONObject object = jsonArray.getJSONObject("i");
                        //Toast.makeText(Login_Doctor.this,jsonArray.getString("role_id"),Toast.LENGTH_SHORT).show();
                        if (jsonArray.getString("role_id").equalsIgnoreCase("1")) {
                            Intent intent = new Intent(Login_Doctor.this, Doctor_Portel_Data.class);
                            intent.putExtra("UserName",jsonArray.getString("name"));
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Login_Doctor.this, Patient_Portal_Data.class);
                            intent.putExtra("UserName",jsonArray.getString("name"));
                            startActivity(intent);
                        }
                    }

                }

            } catch (JSONException e) {

            }


        }
    }
}
