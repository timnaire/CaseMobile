package org.kidzonshock.acase.acase.Client;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import org.kidzonshock.acase.acase.Models.CommonResponse;
import org.kidzonshock.acase.acase.Models.DeleteEvent;
import org.kidzonshock.acase.acase.Models.EventAdapter;
import org.kidzonshock.acase.acase.Models.EventModel;
import org.kidzonshock.acase.acase.Models.EventResponse;
import org.kidzonshock.acase.acase.Models.Events;
import org.kidzonshock.acase.acase.Models.PreferenceDataClient;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    String client_id;

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
        return inflater.inflate(R.layout.fragment_event_client,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        client_id = PreferenceDataClient.getLoggedInClientid(getActivity());
        lv = view.findViewById(R.id.list_events_client);
        loading = view.findViewById(R.id.linlaHeaderProgress);

        registerForContextMenu(lv);
        getEvents();
        loading.setVisibility(View.VISIBLE);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == parent.getItemIdAtPosition(position)){
                    EventModel event = (EventModel) parent.getItemAtPosition(position);
                    Toast.makeText(getActivity(), "Click !"+event.getEventTitle(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            Intent createEvent = new Intent(getActivity(),CreateEventClient.class);
            startActivityForResult(createEvent,0);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_event_client,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(list.get(info.position).getEventTitle());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.view_event_client:
                Toast.makeText(getActivity(), "View Event!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_event_lawyer:
                Intent editEvent = new Intent(getActivity(), CreateEventClient.class);
                startActivityForResult(editEvent, 1);
                break;
            case R.id.remove_event_client:
                AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                ab.setTitle("Delete");
                ab.setMessage("Are you sure you want to delete " + list.get(info.position).getEventTitle());
                ab.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String event_id = list.get(info.position).getEvent_id();
                        list.remove(info.position);
                        adapter.notifyDataSetChanged();
                        deleteEvent(client_id,event_id);
                    }
                });
                ab.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void getEvents() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<EventResponse> eventResponseCall = service.getEventClient(client_id);
        eventResponseCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                EventResponse resp = response.body();
                loading.setVisibility(View.GONE);
                if(isAdded() && !resp.isError()){
                    String event_id,eventTitle,eventLocation,eventDetails,eventWith,eventDate,eventTime,eventType,clientName,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice;
                    ArrayList<Events> list_events = response.body().getEvents();
                    for(int i=0; i < list_events.size(); i++){
                        event_id = list_events.get(i).getEvent_id();
                        eventTitle = list_events.get(i).getEventTitle();
                        eventLocation = list_events.get(i).getEventLocation();
                        eventDetails = list_events.get(i).getEventDetails();
                        eventDate = list_events.get(i).getEventDate();
                        eventTime = list_events.get(i).getEventTime();
                        eventType = list_events.get(i).getEventType();
                        clientName = list_events.get(i).getClient().getFirst_name() +" "+ list_events.get(i).getClient().getLast_name();
                        clientEmail = list_events.get(i).getClient().getEmail();
                        clientPhone = list_events.get(i).getClient().getPhone();
                        clientAddress = list_events.get(i).getClient().getAddress();
                        //
                        lawyerName = list_events.get(i).getLawyer().getFirst_name() +" "+ list_events.get(i).getLawyer().getLast_name();
                        lawyerEmail = list_events.get(i).getLawyer().getEmail();
                        lawyerPhone = list_events.get(i).getLawyer().getPhone();
                        lawyerOffice = list_events.get(i).getLawyer().getOffice();

                        eventWith = lawyerName;
                        list.add(new EventModel(event_id,eventWith,eventTitle,eventLocation,eventDetails,eventDate,eventTime,eventType,clientName,clientEmail,clientPhone,clientAddress,lawyerName,lawyerEmail,lawyerPhone,lawyerOffice));
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

    private void deleteEvent(String client_id, String event_id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Case.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Case service = retrofit.create(Case.class);
        Call<CommonResponse> commonResponseCall = service.deleteEventClient(client_id,new DeleteEvent(event_id));
        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse resp = response.body();
                if(isAdded() && !resp.isError()) {
                    Toast.makeText(getActivity(), "Event deleted! ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Event was not deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });
    }

}
