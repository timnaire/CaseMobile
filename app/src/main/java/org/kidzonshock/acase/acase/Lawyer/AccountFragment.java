package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageLoader;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.UpdatePicture;
import org.kidzonshock.acase.acase.Models.UpdatePictureResponse;
import org.kidzonshock.acase.acase.R;

import java.io.FileNotFoundException;
import java.security.SecureRandom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class AccountFragment extends Fragment {

    ImageView iv_camera,iv_gallery, lawyer_profile_pic;
    Button btnSavePic;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;
    Uri imagePath;
    String photoName;

    private StorageReference storageReference;
    StorageReference filepath;
    UploadTask uploadTask;

    String lawyer_id,first_name,last_name,email,phone,cityOrMunicipality,office,profile_pic;

    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    final int CAMERA_REQUEST = 123;
    final int GALLERY_REQUEST = 345;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, null);
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
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(160,160).getBitmap();
                    bitmap = Bitmap.createScaledBitmap(bitmap, 160,160,true);
                    lawyer_profile_pic.setImageBitmap(bitmap);
                } catch (FileNotFoundException e){
                    Toast.makeText(getActivity(), "Something wrong while choosing photos", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        storageReference = FirebaseStorage.getInstance().getReference();

        lawyer_id = getArguments().getString("lawyer_id");
        first_name = getArguments().getString("first_name");
        last_name = getArguments().getString("last_name");
        email = getArguments().getString("email");
        phone = getArguments().getString("phone");
        cityOrMunicipality = getArguments().getString("cityOrMunicipality");
        office = getArguments().getString("office");
        profile_pic = getArguments().getString("profile_pic");

        cameraPhoto = new CameraPhoto(getActivity());
        galleryPhoto = new GalleryPhoto(getActivity());

        lawyer_profile_pic = view.findViewById(R.id.lawyer_profile_pic);
        btnSavePic = view.findViewById(R.id.btnSavePic);

        lawyer_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
        btnSavePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagePath != null){
                    filepath = storageReference.child(randomString(16)+"/"+photoName);
                    uploadFile(imagePath);
                } else {
                    Toast.makeText(getActivity(), "Please select a photo!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    // for generating name for folder upon uploading to cloud storage
    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
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


                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Case.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Case service = retrofit.create(Case.class);

                    Call<UpdatePictureResponse> updatePictureResponseCall = service.updatePicture(lawyer_id,new UpdatePicture(downloadUri.toString()));
                    updatePictureResponseCall.enqueue(new Callback<UpdatePictureResponse>() {
                        @Override
                        public void onResponse(Call<UpdatePictureResponse> call, Response<UpdatePictureResponse> response) {
                            UpdatePictureResponse updatePictureResponse = response.body();
                            if(!updatePictureResponse.isError()){
                                Toast.makeText(getActivity(), updatePictureResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), updatePictureResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdatePictureResponse> call, Throwable t) {
                            Toast.makeText(getActivity(), "Unable to save profile picture, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

}
