package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.kidzonshock.acase.acase.Models.Setting;
import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class SettingAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Setting> titles;

    public SettingAdapter(Context context, ArrayList<Setting> titles) {
        this.context = context;
        this.titles = titles;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View titleHandler = convertView;
        titleHandler = (titleHandler == null) ? inflater.inflate(R.layout.layout_item_setting,null) : titleHandler;
        TextView title = titleHandler.findViewById(R.id.setting_title);
        Setting setting = titles.get(position);
        title.setText(setting.getTitle());
        return titleHandler;
    }
}
