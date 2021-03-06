package org.kidzonshock.acase.acase.Client;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.Models.UpdatePicture;
import org.kidzonshock.acase.acase.R;

import java.security.SecureRandom;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientChangePicture extends AppCompatActivity {

    ImageView client_profile_pic;
    Button btnSavePicClient;

    final int GALLERY_REQUEST = 345;
    GalleryPhoto galleryPhoto;
    Uri imagePath;
    String photoName;

    private StorageReference storageReference;
    StorageReference filepath;
    UploadTask uploadTask;

    String client_id, profile_pic;

    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    ACProgressFlower dialog;
    RequestOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_change_picture);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile Picture");

        //firebase storage reference
        storageReference = FirebaseStorage.getInstance().getReference();
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        client_profile_pic = findViewById(R.id.client_profile_pic);
        btnSavePicClient = findViewById(R.id.btnSavePicClient);

        dialog = new ACProgressFlower.Builder(ClientChangePicture.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        // get intent extra
        client_id = PreferenceDataClient.getLoggedInClientid(ClientChangePicture.this);
        profile_pic = PreferenceDataClient.getLoggedInProfilePicture(ClientChangePicture.this);

        options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(profile_pic).apply(options).into(client_profile_pic);

        client_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        btnSavePicClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePath != null){
                    dialog.show();
                    filepath = storageReference.child("ProfilePicture/"+randomString(16)+"/"+photoName);
                    uploadFile(imagePath);
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a photo!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GALLERY_REQUEST){
                Uri uri = data.getData();
                imagePath = uri;
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                String[] bits = photoPath.split("/");
                photoName = bits[bits.length-1];
//                try {
//                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(160,160).getBitmap();
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 160,160,true);
//                    lawyer_profile_pic.setImageBitmap(bitmap);
                Glide.with(this).load(photoPath).apply(options).into(client_profile_pic);
//                } catch (FileNotFoundException e){
//                    Toast.makeText(getApplicationContext(), "Something wrong while choosing photos", Toast.LENGTH_SHORT).show();
//                }
            }
        }
    }

    private void uploadFile(Uri fileUrl){
        uploadTask = filepath.putFile(fileUrl);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    final String picuri = downloadUri.toString();
                    profile_pic = picuri;
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Case.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Case service = retrofit.create(Case.class);

                    Call<CommonResponse> updatePictureResponseCall = service.updatePictureClient(client_id,new UpdatePicture(picuri));
                    updatePictureResponseCall.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            dialog.dismiss();
                            CommonResponse updatePictureResponse = response.body();
                            if(!updatePictureResponse.isError()){
                                PreferenceDataClient.setLoggedInProfilePicture(getApplicationContext(),picuri);
                                Toast.makeText(getApplicationContext(), updatePictureResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ClientChangePicture.this, updatePictureResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Unable to save profile picture, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong while uploading your profile.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // for generating name for folder upon uploading to cloud storage
    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }
}
