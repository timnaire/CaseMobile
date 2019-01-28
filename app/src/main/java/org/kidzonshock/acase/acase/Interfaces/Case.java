package org.kidzonshock.acase.acase.Interfaces;

import org.kidzonshock.acase.acase.Models.SigninLawyer;
import org.kidzonshock.acase.acase.Models.SigninResponse;
import org.kidzonshock.acase.acase.Models.SignupLawyer;
import org.kidzonshock.acase.acase.Models.SignupResponse;
import org.kidzonshock.acase.acase.Models.UpdatePicture;
import org.kidzonshock.acase.acase.Models.UpdatePictureResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Case {

    String BASE_URL = "http://case-legal-aid.appspot.com/";

    @Headers("Content-Type: application/json")
    @POST("lawyer/signup")
    Call<SignupResponse> signupLawyer(@Body SignupLawyer body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/signin")
    Call<SigninResponse> signinLawyer(@Body SigninLawyer body);


    @POST("lawyer/{lawyer_id}/profile-picture")
    Call<UpdatePictureResponse> updatePicture(@Path("lawyer_id") String lawyer_id, @Body UpdatePicture body );

    @GET("lawyer/{lawyer_id}/practice")
    Call<RequestBody> getPractice(@Part("lawyer_id") String lawyer_id);


}
