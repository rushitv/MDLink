package com.mdlink.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mdlink.Doctor_Portel_Data;
import com.mdlink.Login_Doctor;
import com.mdlink.MakeServiceCall;
import com.mdlink.Patient_Portal_Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PatientRegisterAsyncTask extends AsyncTask<Void,Void,String> {
    private String TAG = getClass().getSimpleName();
    private Context context;
    private HashMap<String, String> hashMap;
    private ProgressDialog pd;

    public PatientRegisterAsyncTask(Context context, HashMap<String,String> hashMap){
        this.context = context;
        this.hashMap = hashMap;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("Loading..Hold A Second..");
        pd.setCancelable(false);
        pd.show();
    }


    @Override
    protected String doInBackground(Void... voids) {
        return new MakeServiceCall().MakeServiceCall("http://api.themdlink.com/api/v1/patient", MakeServiceCall.POST, hashMap);
        //return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (pd.isShowing()) {
            pd.dismiss();
        }
        try {
            JSONObject json = new JSONObject(s);

            System.out.println(">>>>>>>>>>"+json);

            if (json.getString("status").equalsIgnoreCase("200")) {
                Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();

                JSONObject jsonArray = json.getJSONObject("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    //JSONObject object = jsonArray.getJSONObject("i");
                    //Toast.makeText(Login_Doctor.this,jsonArray.getString("role_id"),Toast.LENGTH_SHORT).show();
                    if (jsonArray.getString("role_id").equalsIgnoreCase("1")) {
                        Intent intent = new Intent(context, Doctor_Portel_Data.class);
                        intent.putExtra("UserName",jsonArray.getString("name"));
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, Patient_Portal_Data.class);
                        intent.putExtra("UserName",jsonArray.getString("name"));
                        context.startActivity(intent);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
