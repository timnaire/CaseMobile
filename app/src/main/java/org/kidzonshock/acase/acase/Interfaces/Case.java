package org.kidzonshock.acase.acase.Interfaces;

import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.GetLawPractice;
import org.kidzonshock.acase.acase.Models.SigninLawyer;
import org.kidzonshock.acase.acase.Models.SigninResponse;
import org.kidzonshock.acase.acase.Models.SignupLawyer;
import org.kidzonshock.acase.acase.Models.UpdateEmail;
import org.kidzonshock.acase.acase.Models.UpdateLawyerInfo;
import org.kidzonshock.acase.acase.Models.UpdatePassword;
import org.kidzonshock.acase.acase.Models.UpdatePicture;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Case {

    String BASE_URL = "http://case-legal-aid.appspot.com/";

    @Headers("Content-Type: application/json")
    @POST("lawyer/signup")
    Call<CommonResponse> signupLawyer(@Body SignupLawyer body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/signin")
    Call<SigninResponse> signinLawyer(@Body SigninLawyer body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/profile-picture")
    Call<CommonResponse> updatePicture(@Path("lawyer_id") String lawyer_id, @Body UpdatePicture body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/profile-information")
    Call<CommonResponse> updateInfo(@Path("lawyer_id") String lawyer_id, @Body UpdateLawyerInfo body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/change-email")
    Call<CommonResponse> updateEmail(@Path("lawyer_id") String lawyer_id, @Body UpdateEmail body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/change-password")
    Call<CommonResponse> updatePassword(@Path("lawyer_id") String lawyer_id, @Body UpdatePassword body );

    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/get-lawyer-practice")
    Call<GetLawPractice> getPractice(@Path("lawyer_id") String lawyer_id);

}
