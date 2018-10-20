package com.mdlink;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class Book_Appoinment extends AppCompatActivity {

    Spinner spinner, spinnerdoc;
    String phamacysel, docsel;
    StringBuilder sb;
    CheckBox c1, c2;
    Button submitbook;
    String[] pharmacy = {"Select pharmacy", "Liguanea Lane Pharmacy-liglanedidp@yahoo.com", "C-cheabowen@gmail.com", "B Pharmacy - unicoreshopping@gmail.com", "Manor Park Pharmacy", "new kingston Pharmacy-nkrxorders@gmail.com", "Musgrave Pharmacy - musgravepharmacyorders@gmail.com"};
    String[] doctor = {"Select Doctor", "JOHN", "A", "b", "c", "d", "E", "F", "G"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__appoinment);
        spinner = (Spinner) findViewById(R.id.book_spinner);
        spinnerdoc = (Spinner) findViewById(R.id.doc_spinner);

        submitbook = (Button) findViewById(R.id.book_submit);

        c1 = (CheckBox) findViewById(R.id.checkboxone);
        c2 = (CheckBox) findViewById(R.id.checkboxtwo);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(Book_Appoinment.this, android.R.layout.simple_spinner_dropdown_item, pharmacy);
        spinner.setAdapter(stringArrayAdapter);
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(Book_Appoinment.this, android.R.layout.simple_spinner_dropdown_item, doctor);
        spinnerdoc.setAdapter(Adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                phamacysel = (String) parent.getItemAtPosition(position);

                Toast.makeText(Book_Appoinment.this, phamacysel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerdoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                docsel = parent.getItemAtPosition(position).toString();
                Toast.makeText(Book_Appoinment.this, docsel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        submitbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb = new StringBuilder();

                if (c1.isChecked()) {
                    sb.append(c1.getText().toString());
                    Toast.makeText(Book_Appoinment.this, c1.getText().toString(), Toast.LENGTH_SHORT).show();
                }

                if (c2.isChecked()) {
                    sb.append(c2.getText().toString());
                    Toast.makeText(Book_Appoinment.this, c2.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (new ConnectionCall(Book_Appoinment.this).isConnectingToInternet()) {
            new bookapoiment().execute();

        } else new ConnectionCall(Book_Appoinment.this).connectiondetect();


}
    private class bookapoiment extends AsyncTask<String,String,String> {

        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /*new   ProgressDialog(Book_Appoinment.this);
            pd.setMessage("Loading");
            pd.show();
            pd.dismiss();*/

        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String,String> hashmap =new HashMap<>();

        return     new  MakeServiceCall().MakeServiceCall("",MakeServiceCall.POST,hashmap);
        }

        @Override
        protected void onPostExecute(String s) {
                super.onPostExecute(s);

        }
    }
}
