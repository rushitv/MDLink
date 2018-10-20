package com.mdlink;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Doctor_portal_Activity extends AppCompatActivity {

    EditText ed1,ed2;
    TextView singup,signcture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_portal_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ed1= (EditText) findViewById(R.id.doc_selectfile);
        singup= (TextView) findViewById(R.id.singup);
        ed2= (EditText) findViewById(R.id.doc_file);


        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_portal_Activity.this,Login_Doctor.class);
                startActivity(intent);


            }
        });


        signcture= (TextView) findViewById(R.id.signcture);

        signcture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Doctor_portal_Activity.this,Signacture_Activity.class);
                startActivity(intent);

            }
        });




        ed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Doctor_portal_Activity.this);

                builder.setMessage("Select Photo");
                builder.setMessage("Select Photo");
                builder.setMessage("Select Photo");


                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
        ed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Doctor_portal_Activity.this);

                builder.setMessage("Select Photo");
                builder.setMessage("Select Photo");
                builder.setMessage("Select Photo");


                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Doctor_portal_Activity.this,Login_Doctor.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if (id==android.R.id.home){

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
