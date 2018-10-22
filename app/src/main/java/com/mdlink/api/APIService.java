package com.mdlink.api;

import com.google.gson.JsonObject;
import com.mdlink.model.DoctorPortalRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {
    @Multipart
    @POST("doctor")
    Call<JsonObject> postDoctorRequest(
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part("phone_no") RequestBody phone_no,
            @Part MultipartBody.Part signature,
            @Part MultipartBody.Part medical_certificate
    );
}
