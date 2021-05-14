package com.example.wallpaperengine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class WallpaperAdapter extends ArrayAdapter<DataHandler> {
    Context context;
    int resource;
    List<DataHandler> dataHandlers;
    WallpaperAdapter(Context context, int resource, List<DataHandler> list){
        super(context,resource,list);
        this.context = context;
        this.resource = resource;
        this.dataHandlers = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(resource,null,false);
        DataHandler dataList = dataHandlers.get(position);
        TextView textView = view.findViewById(R.id.title);
        ImageView imageView = view.findViewById(R.id.listImage);
        textView.setText(dataList.getTitle());
        Glide.with(context).load(dataList.getThumbnail()).centerCrop()
                .error(R.drawable.no_image).placeholder(R.drawable.ic_loading)
                .into(imageView);
        return view;
    }

}
