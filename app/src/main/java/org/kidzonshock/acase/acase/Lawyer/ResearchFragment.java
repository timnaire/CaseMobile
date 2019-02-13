package org.kidzonshock.acase.acase.Lawyer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
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
                    String dlUrl,fileName, fileType, filePrivacy;
                    for(int i=0; i < list_files.size(); i++){
                        fileName = list_files.get(i).getFileName();
                        dlUrl = list_files.get(i).getCaseFile();
                        fileType = list_files.get(i).getFileType();
                        filePrivacy = list_files.get(i).getFilePrivacy();
                        Log.d(TAG,"Filename: "+fileName);
                        list.add(new FileModel(R.drawable.icons8_document_48,fileName));
                    }
                    adapter = new FileAdapter(getActivity(),list);
                    grid.setAdapter(adapter);
                }else{
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), documents.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetDocumentResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list documents...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
