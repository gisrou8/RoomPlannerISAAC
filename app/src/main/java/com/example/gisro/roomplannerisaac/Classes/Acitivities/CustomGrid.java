package com.example.gisro.roomplannerisaac.Classes.Acitivities;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gisro.roomplannerisaac.R;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Room;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by BePul on 20-12-2017.
 */

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private List<Room> rooms;

    public CustomGrid(Context c, List<Room> rooms)
    {
        this.mContext = c;
        this.rooms = rooms;
    }


    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            TextView tvfloor = (TextView) grid.findViewById(R.id.tvFloor);
            TextView tvPersons = (TextView) grid.findViewById(R.id.tvPersons);
            TextView tvRoom = (TextView)grid.findViewById(R.id.tvRoom);
            TextView tvTime = (TextView)grid.findViewById(R.id.tvTime);
            Button btRoom = (Button) grid.findViewById(R.id.btnRoom);
            tvfloor.setText("Floor: " + rooms.get(position).getFloor());
            tvPersons.setText("Persons: " + rooms.get(position).getPersons());
            tvTime.setText(rooms.get(position).getState().toString());
            tvRoom.setText(rooms.get(position).getName());
            btRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, MainActivity.class);
                    i.putExtra("Room", rooms.get(position));
                    mContext.startActivity(i);
                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}






