package org.kidzonshock.acase.acase.Client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.AddFile;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.io.File;
import java.security.SecureRandom;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FileUploadClient extends AppCompatActivity {

    Button btnSelectFile, btnUpload;
    TextView notification;
    Uri fileselected;
    String case_id,client_id,filename,file_p;


    final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    SecureRandom rnd = new SecureRandom();

    FirebaseStorage storage;
    UploadTask uploadTask;
    StorageReference filepath;

    ACProgressFlower dialog;
    private final String TAG = "FileUploadClient";
    private final AlphaAnimation btnClick = new AlphaAnimation(1F,0.8F);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_upload_client);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("File Upload");

        storage = FirebaseStorage.getInstance();

        Intent prev = getIntent();
        case_id = prev.getStringExtra("case_id");

        client_id = PreferenceDataClient.getLoggedInClientid(FileUploadClient.this);

        btnSelectFile = findViewById(R.id.btnSelectFile);
        btnUpload = findViewById(R.id.btnUpload);
        notification = findViewById(R.id.filenotify);

        dialog = new ACProgressFlower.Builder(FileUploadClient.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text("Please wait")
                .fadeColor(Color.DKGRAY).build();

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                if(ContextCompat.checkSelfPermission(FileUploadClient.this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                    selectFile();
                } else {
                    ActivityCompat.requestPermissions(FileUploadClient.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(btnClick);
                if(fileselected!=null) {
                    dialog.show();
                    file_p = "Public";
                    uploadFile(case_id, filename, fileselected,file_p);
                }else
                    Toast.makeText(FileUploadClient.this, "Please select a file!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadFile(final String case_id, final String filename, Uri pdfURi, final String file_p) {
        StorageReference storageReference = storage.getReference();
        filepath = storageReference.child("CaseDocuments").child(randomString(16)+"/"+filename);
        uploadTask = filepath.putFile(pdfURi);
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
                    Log.d(TAG,downloadUri.toString());
                    final String file = downloadUri.toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Case.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Case service = retrofit.create(Case.class);
                    Call<CommonResponse> commonResponseCall = service.addFileClient(client_id,new AddFile(case_id,file,filename,file_p,client_id));
                    commonResponseCall.enqueue(new Callback<CommonResponse>() {
                        @Override
                        public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                            CommonResponse resp = response.body();
                            dialog.dismiss();
                            notification.setText("No file selected");
                            fileselected = null;
                            if(!resp.isError()){
                                Toast.makeText(FileUploadClient.this, "File uploaded !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FileUploadClient.this, "Please fill up all the fields and try again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommonResponse> call, Throwable t) {
                            dialog.dismiss();
                            Toast.makeText(FileUploadClient.this, "Unable to upload file, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Something went wrong while uploading your profile.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            selectFile();
        } else {
            Toast.makeText(FileUploadClient.this, "Please provide permission..", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFile() {
        String[] mimeTypes =
                {"image/jpeg","image/png","application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx, img
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"),100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            fileselected = data.getData();
            String uriString = fileselected.toString();
//            String[] bits = pdfURi.toString().split("/");
//            filename = bits[bits.length-1];
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(fileselected, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
            }
            filename = displayName;
            notification.setText("File selected is " + displayName);
        } else {
            Toast.makeText(FileUploadClient.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
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
