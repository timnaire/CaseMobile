package org.kidzonshock.acase.acase.Client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Lawyer.CaseDocuments;
import org.kidzonshock.acase.acase.Lawyer.FileUpload;
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
            Intent intent = new Intent(CaseDocument.this,FileUpload.class);
            intent.putExtra("case_id", case_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
                    String dlUrl,fileName, fileType, filePrivacy;
                    for(int i=0; i < list_files.size(); i++){
                        fileName = list_files.get(i).getFileName();
                        dlUrl = list_files.get(i).getCaseFile();
                        fileType = list_files.get(i).getFileType();
                        filePrivacy = list_files.get(i).getFilePrivacy();
                        list.add(new FileModel(R.drawable.icons8_document_48,fileName));
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
        getDocumentCall.cancel();
    }
}
