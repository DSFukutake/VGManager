package com.vgmanager;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.vgmanager.xmlpacks.Details;


import java.util.List;

/**
 * Created by Daniel on 4/9/2015.
 * List Adapter for displaying game info with the format: Cover image - Name - Platform
 */
public class BasicInfoAdapter extends ArrayAdapter<Details> {
    private final Context context;
    private final List<Details> values;

    public BasicInfoAdapter(Context context, List<Details> values){
        super(context, R.layout.basic_info_rows_layout,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final String url = Config.IMG_REQUEST_BASE+values.get(position).getBoxArtUrl();

        if(convertView == null){
            convertView = inflater.inflate(R.layout.basic_info_rows_layout,null);
            holder = new ViewHolder();
            holder.title = (TextView)convertView.findViewById(R.id.gametitle);
            holder.platform = (TextView)convertView.findViewById(R.id.gamePlatform);
            holder.image = (ImageView)convertView.findViewById(R.id.gameCover);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        //populate the element of the list.

        Ion.with(holder.image).load(url);
        holder.title.setText(values.get(position).getGameTitle());
        holder.platform.setText(values.get(position).getPlatformName());
        return convertView;

    }

    //Holder class for each element of the list.
    private class ViewHolder{
        TextView title;
        TextView platform;
        ImageView image;
    }


}
