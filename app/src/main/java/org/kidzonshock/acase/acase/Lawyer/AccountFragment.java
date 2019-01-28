package org.kidzonshock.acase.acase.Lawyer;

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
import android.widget.ListView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;

import org.kidzonshock.acase.acase.Models.Setting;
import org.kidzonshock.acase.acase.R;

import java.security.SecureRandom;
import java.util.ArrayList;

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

    final int GALLERY_REQUEST = 345;

    ListView lv;
    ArrayList<Setting> titles = new ArrayList<>();
    SettingAdapter adapter;

    private static final String TAG = "AccountFragment";
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //firebase storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        //getting the data from bundle in dashboard activty
        lawyer_id = getArguments().getString("lawyer_id");
        first_name = getArguments().getString("first_name");
        last_name = getArguments().getString("last_name");
        email = getArguments().getString("email");
        phone = getArguments().getString("phone");
        cityOrMunicipality = getArguments().getString("cityOrMunicipality");
        office = getArguments().getString("office");
        profile_pic = getArguments().getString("profile_pic");

        galleryPhoto = new GalleryPhoto(getActivity());

//        lawyer_profile_pic = view.findViewById(R.id.lawyer_profile_pic);
//        btnSavePic = view.findViewById(R.id.btnSavePic);
        lv = view.findViewById(R.id.listview);

        lawyer_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

//        titles.add(new Setting("Change Profile Picture"));
////        titles.add(new Setting("Change Profile Information"));
////        titles.add(new Setting("Change Email"));
////        titles.add(new Setting("Change Password"));
//        adapter = new SettingAdapter(getActivity(),titles);
//        lv.setAdapter(adapter);

//        btnSavePic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(imagePath != null){
//                    filepath = storageReference.child(randomString(16)+"/"+photoName);
//                    uploadFile(imagePath);
//                } else {
//                    Toast.makeText(getActivity(), "Please select a photo!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });


    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode == RESULT_OK){
//            if(requestCode == GALLERY_REQUEST){
//                Uri uri = data.getData();
//                imagePath = uri;
//                galleryPhoto.setPhotoUri(uri);
//                String photoPath = galleryPhoto.getPath();
//                String[] bits = photoPath.split("/");
//                photoName = bits[bits.length-1];
//                try {
//                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(160,160).getBitmap();
//                    bitmap = Bitmap.createScaledBitmap(bitmap, 160,160,true);
//                    lawyer_profile_pic.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e){
//                    Toast.makeText(getActivity(), "Something wrong while choosing photos", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//    }

    // for generating name for folder upon uploading to cloud storage
    public String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    // file upload
//    private void uploadFile(Uri fileUrl){
//        uploadTask = filepath.putFile(fileUrl);
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//                // Continue with the task to get the download URL
//                return filepath.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    String picuri = downloadUri.toString();
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl(Case.BASE_URL)
//                            .addConverterFactory(GsonConverterFactory.create())
//                            .build();
//                    Case service = retrofit.create(Case.class);
//
//                    Call<UpdatePictureResponse> updatePictureResponseCall = service.updatePicture(lawyer_id,new UpdatePicture(picuri));
//                    updatePictureResponseCall.enqueue(new Callback<UpdatePictureResponse>() {
//                        @Override
//                        public void onResponse(Call<UpdatePictureResponse> call, Response<UpdatePictureResponse> response) {
//                            UpdatePictureResponse updatePictureResponse = response.body();
//                            Toast.makeText(getActivity(), updatePictureResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<UpdatePictureResponse> call, Throwable t) {
//                            Toast.makeText(getActivity(), "Unable to save profile picture, please try again.", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
//        });
//    }

}
