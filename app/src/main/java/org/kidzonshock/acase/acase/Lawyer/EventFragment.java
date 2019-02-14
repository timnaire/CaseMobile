package org.kidzonshock.acase.acase.Lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.kidzonshock.acase.acase.Interfaces.Case;
import org.kidzonshock.acase.acase.Models.EventAdapter;
import org.kidzonshock.acase.acase.Models.EventModel;
import org.kidzonshock.acase.acase.Models.EventResponse;
import org.kidzonshock.acase.acase.Models.Events;
import org.kidzonshock.acase.acase.Models.PreferenceDataLawyer;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    String lawyer_id;

    ListView lv;
    ArrayList<EventModel> list = new ArrayList<>();
    EventAdapter adapter;

    LinearLayout loading;
    AdapterView.AdapterContextMenuInfo info;

    private final String TAG = "EventFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_event,null);
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lawyer_id = PreferenceDataLawyer.getLoggedInLawyerid(getActivity());
        lv = view.findViewById(R.id.list_of_events);
        loading = view.findViewById(R.id.linlaHeaderProgress);

        getEvents();
        registerForContextMenu(lv);
        loading.setVisibility(View.VISIBLE);

    }

    private void getEvents() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<EventResponse> eventResponseCall = service.getEventLawyer(lawyer_id);
        eventResponseCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse resp = response.body();
                loading.setVisibility(View.GONE);
                if(isAdded() && !resp.isError()){
                    String eventTitle,eventWith,eventDate,clientName,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice;
                    ArrayList<Events> list_events = response.body().getEvents();
                    for(int i=0; i < list_events.size(); i++){

                        eventTitle = list_events.get(i).getEventTitle();
                        eventDate = list_events.get(i).getEventDate();
                        clientName = list_events.get(i).getClient().getFirst_name() +" "+ list_events.get(i).getClient().getLast_name();
                        clientEmail = list_events.get(i).getClient().getEmail();
                        clientPhone = list_events.get(i).getClient().getPhone();
                        clientAddress = list_events.get(i).getClient().getAddress();
                        //
                        lawyerName = list_events.get(i).getLawyer().getFirst_name() +" "+ list_events.get(i).getLawyer().getLast_name();
                        lawyerEmail = list_events.get(i).getLawyer().getEmail();
                        lawyerPhone = list_events.get(i).getLawyer().getPhone();
                        lawyerOffice = list_events.get(i).getLawyer().getOffice();

                        eventWith = clientName;
                        list.add(new EventModel(eventWith,eventTitle,eventDate,clientName,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice));
                    }
                    adapter = new EventAdapter(getActivity(),list);
                    lv.setAdapter(adapter);
                } else {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No event(s) found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to list events, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_case_context,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list.get(info.position).getEventTitle());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.View:
                Toast.makeText(getActivity(), "View", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Edit:
                break;
            case R.id.Delete:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_mycase, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.search){

        } else if(id == R.id.add){
            Intent createEvent = new Intent(getActivity(),CreateEvent.class);
            startActivityForResult(createEvent,0);
        }
        return super.onOptionsItemSelected(item);
    }
}
