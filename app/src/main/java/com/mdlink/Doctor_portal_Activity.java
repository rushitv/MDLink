package com.mdlink;

import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlink.api.APIService;
import com.mdlink.api.RestAPIClent;
import com.mdlink.drawing.MyDrawView;
import com.mdlink.model.DoctorPortalRequest;
import com.mdlink.util.Constants;
import com.mdlink.util.FileUtil;
import com.mdlink.util.ValidationsUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class Doctor_portal_Activity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;
    private static final int WRITE_STORAGE_PERMISSION_REQUEST_CODE = 3;
    private static final int ACTIVITY_CHOOSE_FILE = 2;
    private static final String[] PERMISSION_PICTURES = {
//            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private String TAG = getClass().getSimpleName();
    EditText ed1, edtSelectFile, edtSignPopup;
    TextView singup, signcture;
    RelativeLayout rlSignPad;
    MyDrawView myDrawView;
    private String filePath;
    private TextView tvSignaturePath, tvSelectedFilePath;
    private Button btnSubmit;

    APIService apiService =
            RestAPIClent.getClient().create(APIService.class);
    Calendar calendarTime;
    TimePickerDialog timepickerdialog;
    private CheckBox checkboxSunday,checkboxMonday,checkboxTuesday,checkboxWednesday,
            checkboxThursday,checkboxFriday,checkboxSaturday;
    private EditText edtAvailMorningTime;
    String format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_portal_);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtAvailMorningTime = findViewById(R.id.edtAvailMorningTime);
        edtAvailMorningTime.setOnClickListener(this);

        checkboxSunday = findViewById(R.id.checkboxSunday);
        checkboxMonday = findViewById(R.id.checkboxMonday);
        checkboxTuesday = findViewById(R.id.checkboxTuesday);
        checkboxWednesday = findViewById(R.id.checkboxWednesday);
        checkboxThursday = findViewById(R.id.checkboxThursday);
        checkboxFriday = findViewById(R.id.checkboxFriday);
        checkboxSaturday = findViewById(R.id.checkboxSaturday);

        edtSignPopup = findViewById(R.id.doc_selectfile);
        edtSignPopup.setOnClickListener(this);

        singup = (TextView) findViewById(R.id.singup);
        edtSelectFile = (EditText) findViewById(R.id.doc_file);
        edtSelectFile.setOnClickListener(this);
        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Doctor_portal_Activity.this, Login_Doctor.class);
                startActivity(intent);

            }
        });
        tvSignaturePath = findViewById(R.id.tvSignaturePath);
        tvSelectedFilePath = findViewById(R.id.tvSelectedFilePath);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Doctor_portal_Activity.this, Login_Doctor.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //openFilesDirectory();
                }
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilesDirectory();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        if (requestCode == ACTIVITY_CHOOSE_FILE) {
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Uri imageUri = FileUtil.writeInputSteamToCache(Doctor_portal_Activity.this, inputStream);
                if (null != imageUri) {
                    Log.i(TAG, "filePath>>>>>>>>" + imageUri);
                    tvSelectedFilePath.setText(imageUri.toString());
                    //LogHelper.debug(TAG, LogHelper.SUCCESS + " onActivityResult :: " + "imageUri : " + imageUri)
                    return;
                }
            } catch (Exception e) {
                //LogHelper.error(TAG, LogHelper.FAILURE + " onActivityResult :: " + e.getMessage());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.doc_selectfile:
                OpenSignPad();
                break;
            case R.id.doc_file:
                if (checkPermissionForReadWriteExtertalStorage(Doctor_portal_Activity.this)) {
                    openFilesDirectory();
                } else {
                    try {
                        requestPermissionForReadWriteExtertalStorage(Doctor_portal_Activity.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.edtAvailMorningTime:
                calendarTime = Calendar.getInstance();
                int CalendarHour, CalendarMinute;
                CalendarHour = calendarTime.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendarTime.get(Calendar.MINUTE);
                timepickerdialog = new TimePickerDialog(Doctor_portal_Activity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = " AM";
                                } else if (hourOfDay == 12) {
                                    format = " PM";

                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = " PM";
                                } else {
                                    format = " AM";
                                }
                                Log.i(TAG,">>>>>>"+hourOfDay + ":" + ValidationsUtil.getPaddedNumber(minute) + format);
                                //DisplayTime.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

                break;
            case R.id.btnSubmit:
                ArrayList<String> stringArrayList = new ArrayList<>();
                if(checkboxSunday.isChecked()){
                    stringArrayList.add("0");
                }
                if(checkboxMonday.isChecked()){
                    stringArrayList.add("1");
                }
                if(checkboxTuesday.isChecked()){
                    stringArrayList.add("2");
                }
                if(checkboxWednesday.isChecked()){
                    stringArrayList.add("3");
                }
                if(checkboxThursday.isChecked()){
                    stringArrayList.add("4");
                }
                if(checkboxFriday.isChecked()){
                    stringArrayList.add("5");
                }
                if(checkboxSaturday.isChecked()){
                    stringArrayList.add("6");
                }

                StringBuilder checkboxExtract = new StringBuilder();
                for(String checkVal : stringArrayList){
                    checkboxExtract.append(checkVal);
                    checkboxExtract.append(Constants.SEPARATOR);
                }

                String availabledays = checkboxExtract.toString();


                //pass it like this
                File fileSignature = new File(tvSignaturePath.getText().toString());
                RequestBody requestFileSignature =
                        RequestBody.create(MediaType.parse("multipart/form-data"), fileSignature);

                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part mpSignature =
                        MultipartBody.Part.createFormData("signature", fileSignature.getName(), requestFileSignature);

                File fileCertificate = new File(tvSignaturePath.getText().toString());
                RequestBody requestFileCertificate =
                        RequestBody.create(MediaType.parse("multipart/form-data"), fileCertificate);

                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part mpCertificate =
                        MultipartBody.Part.createFormData("medical_certificate", fileSignature.getName(), requestFileCertificate);

                RequestBody email =
                        RequestBody.create(MediaType.parse("multipart/form-data"), "rushit@gmail.com");
                RequestBody available_day =
                        RequestBody.create(MediaType.parse("multipart/form-data"), availabledays);
                RequestBody name =
                        RequestBody.create(MediaType.parse("multipart/form-data"), "rushit@gmail.com");
                RequestBody phone_no =
                        RequestBody.create(MediaType.parse("multipart/form-data"), "rushit@gmail.com");

                callAPI(email, name, phone_no,mpSignature, mpCertificate);

                break;
        }
    }

    private void OpenSignPad() {
        LayoutInflater factory = LayoutInflater.from(this);

        final View deleteDialogView = factory.inflate(R.layout.sign_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setContentView(deleteDialogView);
        rlSignPad = deleteDialog.findViewById(R.id.relSign);
        myDrawView = new MyDrawView(this);
        rlSignPad.addView(myDrawView);

        deleteDialog.setView(deleteDialogView);
        deleteDialogView.findViewById(R.id.btnSignSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                Bitmap b = loadLargeBitmapFromView(rlSignPad);
                rlSignPad.setDrawingCacheEnabled(false); // clear drawing cache

                File pictureFile = FileUtil.getOutputMediaFile(Doctor_portal_Activity.this);
                if (pictureFile == null) {
                    Log.d(TAG, "Error creating media file, check storage permissions: ");// e.getMessage());
                    deleteDialog.dismiss();
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "File not found: " + e.getMessage());
                } catch (IOException e) {
                    Log.d(TAG, "Error accessing file: " + e.getMessage());
                }
                tvSignaturePath.setText(pictureFile.getAbsolutePath());
                deleteDialog.dismiss();
            }
        });
        deleteDialogView.findViewById(R.id.btnSignClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDrawView.clear();
                //deleteDialog.dismiss();
            }
        });

        deleteDialog.show();
    }

    public static Bitmap loadLargeBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(b);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(c);
        return b;
    }

    private void openFilesDirectory() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("image/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    public boolean checkPermissionForReadWriteExtertalStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    public void requestPermissionForReadWriteExtertalStorage(Context context) throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context,
                    PERMISSION_PICTURES,
                    WRITE_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void callAPI(RequestBody email, RequestBody name, RequestBody phone_no,
                         MultipartBody.Part signature, MultipartBody.Part medical_certificate) {

        Call<JsonObject> callToGetUserProfile = apiService.postDoctorRequest(email, name,phone_no, signature, medical_certificate);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG,">>>>>>>>>>>>>"+response.toString());
                Log.i(TAG,">>>>>>>>>>>>>"+response.body());
                if(response.code() == 200){
                    Toast.makeText(Doctor_portal_Activity.this,"",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.fillInStackTrace();
            }
        });

    }
}
