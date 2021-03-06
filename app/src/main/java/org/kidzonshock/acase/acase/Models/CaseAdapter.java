package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kidzonshock.acase.acase.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CaseAdapter extends BaseAdapter {

    private final String TAG = "CaseAdapter";

    String output;
    Context context;
    ArrayList<CaseModel> list;
    LayoutInflater inflater;

    public CaseAdapter(Context context, ArrayList<CaseModel> list) {
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

        view = (view == null) ? inflater.inflate(R.layout.layout_item_case,null) : view;

        TextView case_title = view.findViewById(R.id.case_title);
        TextView case_date = view.findViewById(R.id.case_date);
        TextView case_clientname = view.findViewById(R.id.case_clientname);
        TextView case_status = view.findViewById(R.id.case_status);
        CaseModel c = list.get(position);

        String input = c.getDate_created();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            output = dateFormat.format(dateFormat.parse(input));
            Log.d(TAG,"date : " +output);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        case_date.setText(output);
        case_title.setText(c.getTitle());
        case_clientname.setText(c.getClientName());

        case_status.setText(c.getStatus());

        return view;
    }
}
