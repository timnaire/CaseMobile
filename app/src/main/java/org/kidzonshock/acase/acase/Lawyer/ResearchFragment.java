package org.kidzonshock.acase.acase.Lawyer;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.DeleteFileModel;
import org.kidzonshock.acase.acase.Models.Document;
import org.kidzonshock.acase.acase.Models.FileAdapter;
import org.kidzonshock.acase.acase.Models.FileModel;
import org.kidzonshock.acase.acase.Models.GetDocument;
import org.kidzonshock.acase.acase.Models.GetDocumentResponse;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResearchFragment extends Fragment {

    String case_id,lawyer_id;
    private final String TAG = "ResearchFragment";

    GridView grid;
    ArrayList<FileModel> list = new ArrayList<>();
    FileAdapter adapter;

    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_research,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if(args != null)
            case_id = args.getString("case_id");

        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());
        grid = view.findViewById(R.id.researchDocument);
        loading = view.findViewById(R.id.linlaHeaderProgress);
        loading.setVisibility(View.VISIBLE);

        researchDocument();
        registerForContextMenu(grid);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_document,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
//        menu.setHeaderTitle(adapter.getIndexForPosition(info.position));
//        menu.setHeaderTitle(grid)
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.delete_file:
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("Delete");
                ab.setMessage("Are you sure you want to delete " + adapter.getFilename(info.position));
                ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fileId = adapter.getFileId(info.position);
                        adapter.removeItem(info.position);
                        adapter.notifyDataSetChanged();
                        deleteFileNow(fileId);
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
                    Toast.makeText(getActivity(), "File deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "File was not deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong while deleting, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void researchDocument(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<GetDocumentResponse> getDocumentCall = service.researchDocument(lawyer_id,new GetDocument(case_id));
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
                    adapter = new FileAdapter(getActivity(),list);
                    grid.setAdapter(adapter);
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Found 0 file(s)", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetDocumentResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list documents...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
