package org.kidzonshock.acase.acase.Interfaces;

import org.kidzonshock.acase.acase.Models.AddCase;
import org.kidzonshock.acase.acase.Models.AddFCMToken;
import org.kidzonshock.acase.acase.Models.AddFile;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.CreateEventModelClient;
import org.kidzonshock.acase.acase.Models.CreateEventModelLawyer;
import org.kidzonshock.acase.acase.Models.DeleteCase;
import org.kidzonshock.acase.acase.Models.DeleteEvent;
import org.kidzonshock.acase.acase.Models.DeleteFileModel;
import org.kidzonshock.acase.acase.Models.DeleteFileModelClient;
import org.kidzonshock.acase.acase.Models.EditCase;
import org.kidzonshock.acase.acase.Models.EventResponse;
import org.kidzonshock.acase.acase.Models.Feedback;
import org.kidzonshock.acase.acase.Models.GetCase;
import org.kidzonshock.acase.acase.Models.GetDocument;
import org.kidzonshock.acase.acase.Models.GetDocumentResponse;
import org.kidzonshock.acase.acase.Models.GetLawPractice;
import org.kidzonshock.acase.acase.Models.ClientPaymentModel;
import org.kidzonshock.acase.acase.Models.ListClient;
import org.kidzonshock.acase.acase.Models.ListLawyer;
import org.kidzonshock.acase.acase.Models.PaymentModel;
import org.kidzonshock.acase.acase.Models.PreAppointResponse;
import org.kidzonshock.acase.acase.Models.SigninBody;
import org.kidzonshock.acase.acase.Models.SigninResponseClient;
import org.kidzonshock.acase.acase.Models.SigninResponseLawyer;
import org.kidzonshock.acase.acase.Models.SignupClient;
import org.kidzonshock.acase.acase.Models.SignupLawyer;
import org.kidzonshock.acase.acase.Models.UpdateClientInfo;
import org.kidzonshock.acase.acase.Models.UpdateEmail;
import org.kidzonshock.acase.acase.Models.UpdateEvent;
import org.kidzonshock.acase.acase.Models.UpdateLawyerInfo;
import org.kidzonshock.acase.acase.Models.UpdatePassword;
import org.kidzonshock.acase.acase.Models.UpdatePicture;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Case {
    String BASE_URL = "http://case-legal-aid.appspot.com/";

//    POST REQUEST
//    account mgt module
    @Headers("Content-Type: application/json")
    @POST("lawyer/signup")
    Call<CommonResponse> signupLawyer(@Body SignupLawyer body);

    @Headers("Content-Type: application/json")
    @POST("client/signup")
    Call<CommonResponse> signupClient(@Body SignupClient body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/signin")
    Call<SigninResponseLawyer> signinLawyer(@Body SigninBody body);

    @Headers("Content-Type: application/json")
    @POST("client/signin")
    Call<SigninResponseClient> signinClient(@Body SigninBody body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/profile-picture")
    Call<CommonResponse> updatePicture(@Path("lawyer_id") String lawyer_id, @Body UpdatePicture body );

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/profile-picture")
    Call<CommonResponse> updatePictureClient(@Path("client_id") String client_id, @Body UpdatePicture body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/profile-information")
    Call<CommonResponse> updateInfo(@Path("lawyer_id") String lawyer_id, @Body UpdateLawyerInfo body );

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/account-setting/profile-information")
    Call<CommonResponse> updateInfoClient(@Path("client_id") String client_id, @Body UpdateClientInfo body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/change-email")
    Call<CommonResponse> updateEmail(@Path("lawyer_id") String lawyer_id, @Body UpdateEmail body );

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/account-setting/change-email")
    Call<CommonResponse> updateEmailClient(@Path("client_id") String client_id, @Body UpdateEmail body );

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/account-setting/change-password")
    Call<CommonResponse> updatePassword(@Path("lawyer_id") String lawyer_id, @Body UpdatePassword body );

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/account-setting/change-password")
    Call<CommonResponse> updatePasswordClient(@Path("client_id") String client_id, @Body UpdatePassword body );

//    case module
    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/newcase")
        Call<CommonResponse> addCase(@Path("lawyer_id") String lawyer_id, @Body AddCase body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/edit-case")
    Call<CommonResponse> editCase(@Path("lawyer_id") String lawyer_id, @Body EditCase body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/delete-case")
    Call<CommonResponse> deleteCase(@Path("lawyer_id") String lawyer_id, @Body DeleteCase body);

//    notificaiton module
    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/fcm-token")
    Call<ResponseBody> lawyer_fcm_token(@Path("lawyer_id") String lawyer_id, @Body AddFCMToken body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/fcm-token")
    Call<ResponseBody> client_fcm_token(@Path("client_id") String client_id, @Body AddFCMToken body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{client_id}/pre-appoint-response")
    Call<ResponseBody> appointResponse(@Path("client_id") String client_id, @Body PreAppointResponse body);

//    document module
    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/add-file")
    Call<CommonResponse> addFile(@Path("lawyer_id") String lawyer_id, @Body AddFile body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/add-file")
    Call<CommonResponse> addFileClient(@Path("client_id") String client_id, @Body AddFile body);

    @Headers("Content-Type: application/json")
    @POST("delete-file")
    Call<CommonResponse> deleteFile(@Body DeleteFileModel body);

    @Headers("Content-Type: application/json")
    @POST("client/delete-file")
    Call<CommonResponse> deleteFileClient(@Body DeleteFileModelClient body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/list-all-file")
    Call<GetDocumentResponse> allDocument(@Path("lawyer_id") String lawyer_id, @Body GetDocument body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/list-research")
    Call<GetDocumentResponse> researchDocument(@Path("lawyer_id") String lawyer_id, @Body GetDocument body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/list-public-documents")
    Call<GetDocumentResponse> publicDocument(@Path("lawyer_id") String lawyer_id, @Body GetDocument body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/list-public-documents")
    Call<GetDocumentResponse> publicDocumentClient(@Path("client_id") String client_id, @Body GetDocument body);

//    payment module

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/subscribe")
    Call<CommonResponse> lawyerSubscribe(@Path("lawyer_id") String lawyer_id, @Body PaymentModel body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/payment")
    Call<CommonResponse> clientPayment(@Path("client_id") String client_id, @Body ClientPaymentModel body);


//    events module
    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/add-event")
    Call<CommonResponse> createEventLawyer(@Path("lawyer_id") String lawyer_id, @Body CreateEventModelLawyer body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/add-event")
    Call<CommonResponse> createEventClient(@Path("client_id") String client_id, @Body CreateEventModelClient body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/delete-event")
    Call<CommonResponse> deleteEventClient(@Path("client_id") String client_id, @Body DeleteEvent body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/delete-event")
    Call<CommonResponse> deleteEventLawyer(@Path("lawyer_id") String lawyer_id, @Body DeleteEvent body);

    @Headers("Content-Type: application/json")
    @POST("lawyer/{lawyer_id}/update-event")
    Call<CommonResponse> updateEventLawyer(@Path("lawyer_id") String lawyer_id, @Body UpdateEvent body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/update-event")
    Call<CommonResponse> updateEventClient(@Path("client_id") String client_id, @Body UpdateEvent body);

    @Headers("Content-Type: application/json")
    @POST("client/{client_id}/lawyer/feedback")
    Call<CommonResponse> sendFeedback(@Path("client_id") String client_id, @Body Feedback body);

//    GET REQUEST
    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/get-lawyer-practice")
    Call<GetLawPractice> getPractice(@Path("lawyer_id") String lawyer_id);

    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/mobile/get-case")
    Call<GetCase> getCases(@Path("lawyer_id") String lawyer_id);

    @Headers("Content-Type: application/json")
    @GET("client/{client_id}/get-case")
    Call<GetCase> getCasesClient(@Path("client_id") String client_id);

    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/list-client")
    Call<ListClient> listClient(@Path("lawyer_id") String lawyer_id);

    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/pre-appoint-notification")
    Call<CommonResponse> notify(@Path("lawyer_id") String lawyer_id);

    @Headers("Content-Type: application/json")
    @GET("client/{client_id}/list-lawyer")
    Call<ListLawyer> listLawyer(@Path("client_id") String client_id);

    @Headers("Content-Type: application/json")
    @GET("lawyer/{lawyer_id}/get-event")
    Call<EventResponse> getEventLawyer(@Path("lawyer_id") String lawyer_id);

    @Headers("Content-Type: application/json")
    @GET("client/{client_id}/get-event")
    Call<EventResponse> getEventClient(@Path("client_id") String client_id);
}
