package org.kidzonshock.acase.acase.Lawyer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kidzonshock.acase.acase.Models.CaseModel;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class CaseAdapter extends BaseAdapter {

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

        case_title.setText(c.getTitle());
        case_clientname.setText(c.getClientName());
        case_date.setText(c.getDate_created());
        case_status.setText(c.getStatus());

        return view;
    }
}
