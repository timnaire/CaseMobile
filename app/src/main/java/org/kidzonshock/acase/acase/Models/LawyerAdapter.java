package org.kidzonshock.acase.acase.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.kidzonshock.acase.acase.R;

import java.util.ArrayList;

public class LawyerAdapter extends BaseAdapter {

    Context context;
    ArrayList<LawyerModel> list;
    LayoutInflater inflater;

    public LawyerAdapter(Context context, ArrayList<LawyerModel> list) {
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

        view = (view == null) ? inflater.inflate(R.layout.layout_item_client,null) : view;

        ImageView iv = view.findViewById(R.id.client_picture);
        TextView lawyer_name = view.findViewById(R.id.client_name);
        TextView lawyer_email = view.findViewById(R.id.client_email);
        LawyerModel l = list.get(position);

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.accounticon)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context).load(l.getProfile_pic()).apply(options).into(iv);
        lawyer_name.setText(l.getName());
        lawyer_email.setText(l.getEmail());
        return view;
    }
}
