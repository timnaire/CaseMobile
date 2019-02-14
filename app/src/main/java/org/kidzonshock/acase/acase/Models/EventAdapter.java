package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class EventAdapter extends BaseAdapter {

    Context context;
    ArrayList<EventModel> list;
    LayoutInflater inflater;

    public EventAdapter(Context context, ArrayList<EventModel> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        view = (view == null) ? inflater.inflate(R.layout.layout_item_event,null) : view;

        TextView eventTitle = view.findViewById(R.id.eventTitle);
        TextView eventWith = view.findViewById(R.id.eventWith);
        TextView eventDate = view.findViewById(R.id.eventDate);
        EventModel e = list.get(position);

        eventTitle.setText(e.getEventTitle());
        eventWith.setText(e.getEventWith());
        eventDate.setText(e.getEventDate());

        return view;
    }
}
