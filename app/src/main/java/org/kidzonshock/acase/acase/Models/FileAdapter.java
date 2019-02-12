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

public class FileAdapter extends BaseAdapter {

    Context context;
    ArrayList<FileModel> list;
    LayoutInflater inflater;

    public FileAdapter(Context context, ArrayList<FileModel> list) {
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

        view = (view == null) ? inflater.inflate(R.layout.layout_item_file,null) : view;

        ImageView iv = view.findViewById(R.id.fileImg);
        TextView filename = view.findViewById(R.id.fileName);
        FileModel f = list.get(position);

        RequestOptions options = new RequestOptions()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(context).load(f.getImg()).apply(options).into(iv);
        filename.setText(f.getFilename());
        return view;
    }
}
