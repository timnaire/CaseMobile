package org.kidzonshock.acase.acase.Interfaces;

import org.kidzonshock.acase.acase.Models.LoginLawyer;
import org.kidzonshock.acase.acase.Models.LoginResponse;
import org.kidzonshock.acase.acase.Models.RegisterLawyer;
import org.kidzonshock.acase.acase.Models.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Case {

    String BASE_URL = "http://192.168.1.10:8080/";

    @Headers("Content-Type: application/json")
    @POST("signup/lawyer")
    Call<RegisterResponse> registerLawyer(@Body RegisterLawyer body);

    @Headers("Content-Type: application/json")
    @GET("signin/lawyer")
    Call<LoginResponse> loginLawyer(@Body LoginLawyer body);

}
