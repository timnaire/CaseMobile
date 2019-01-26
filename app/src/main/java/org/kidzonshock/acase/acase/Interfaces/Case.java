package org.kidzonshock.acase.acase.Interfaces;

import org.kidzonshock.acase.acase.Models.SigninLawyer;
import org.kidzonshock.acase.acase.Models.SigninResponse;
import org.kidzonshock.acase.acase.Models.SignupLawyer;
import org.kidzonshock.acase.acase.Models.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Case {

    String BASE_URL = "http://case-legal-aid.appspot.com/";

    @Headers("Content-Type: application/json")
    @POST("lawyer/signup")
    Call<SignupResponse> signupLawyer(@Body SignupLawyer body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/signin")
    Call<SigninResponse> signinLawyer(@Body SigninLawyer body);

}
