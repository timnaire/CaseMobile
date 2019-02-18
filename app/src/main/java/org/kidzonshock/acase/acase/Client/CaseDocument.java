package org.kidzonshock.acase.acase.Client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.DeleteFileModel;
import org.kidzonshock.acase.acase.Models.DeleteFileModelClient;
import org.kidzonshock.acase.acase.Models.Document;
import org.kidzonshock.acase.acase.Models.FileAdapter;
import org.kidzonshock.acase.acase.Models.FileModel;
import org.kidzonshock.acase.acase.Models.GetDocument;
import org.kidzonshock.acase.acase.Models.GetDocumentResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CaseDocument extends AppCompatActivity {
    String case_id,client_id;

    private final String TAG = "CaseDocument";

    GridView grid;
    ArrayList<FileModel> list = new ArrayList<>();
    FileAdapter adapter;

    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_document);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Documents");

        Intent prev = getIntent();
        case_id = prev.getStringExtra("case_id");

        client_id = PreferenceDataClient.getLoggedInClientid(CaseDocument.this);
        grid = findViewById(R.id.publicDocument);
        loading = findViewById(R.id.linlaHeaderProgress);
        loading.setVisibility(View.VISIBLE);

        publicDocument();
        registerForContextMenu(grid);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mycase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){

        } else if(id == R.id.add){
            Intent intent = new Intent(CaseDocument.this,FileUploadClient.class);
            intent.putExtra("case_id", case_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_document,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(adapter.getFilename(info.position));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.delete_file:
                AlertDialog.Builder ab = new AlertDialog.Builder(CaseDocument.this);
                ab.setTitle("Delete");
                ab.setMessage("Are you sure you want to delete " + adapter.getFilename(info.position));
                ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileId = adapter.getFileId(info.position);
                        String fileUploadedId = adapter.uploadedBy(info.position);
                        Toast.makeText(CaseDocument.this, fileUploadedId, Toast.LENGTH_SHORT).show();
                        if(fileUploadedId.equals(client_id)) {
                            adapter.removeItem(info.position);
                            adapter.notifyDataSetChanged();
                            deleteFileNow(fileId);
                        }else {
                            Toast.makeText(CaseDocument.this, "Unauthorized!, You can't delete this file.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ab.show();
        }
        return super.onContextItemSelected(item);
    }

    private void deleteFileNow(String fileId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.deleteFile(new DeleteFileModel(fileId));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(!resp.isError()){
                    Toast.makeText(CaseDocument.this, "File deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CaseDocument.this, "File was not deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(CaseDocument.this, "Something went wrong while deleting, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void publicDocument() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<GetDocumentResponse> getDocumentCall = service.publicDocumentClient(client_id,new GetDocument(case_id));
        getDocumentCall.enqueue(new Callback<GetDocumentResponse>() {
            @Override
            public void onResponse(Call<GetDocumentResponse> call, Response<GetDocumentResponse> response) {
                GetDocumentResponse documents = response.body();
                loading.setVisibility(View.GONE);
                if(!documents.isError()){
                    ArrayList<Document> list_files = response.body().getFile();
                    String fileId,dlUrl,fileName, fileType, filePrivacy,uploadedBy;
                    for(int i=0; i < list_files.size(); i++){
                        fileId = list_files.get(i).getFile_id();
                        fileName = list_files.get(i).getFileName();
                        dlUrl = list_files.get(i).getCaseFile();
                        fileType = list_files.get(i).getFileType();
                        filePrivacy = list_files.get(i).getFilePrivacy();
                        uploadedBy = list_files.get(i).getUploaded_by();
                        list.add(new FileModel(fileId,R.drawable.icons8_document_48_color,fileName,uploadedBy));
                    }
                    adapter = new FileAdapter(CaseDocument.this,list);
                    grid.setAdapter(adapter);
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(CaseDocument.this, documents.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetDocumentResponse> call, Throwable t) {
                Toast.makeText(CaseDocument.this, "Unable to list documents...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
