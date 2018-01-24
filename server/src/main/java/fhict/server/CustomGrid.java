package fhict.server;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.server.GraphAPI.DeleteAppointmentAsync;
import fhict.server.GraphAPI.GraphServiceController;

/**
 * Created by BePul on 20-12-2017.
 */

public class CustomGrid extends BaseAdapter {

    private Context mContext;
    private List<Room> rooms;
    private String buttonName;
    private GraphServiceController serviceController;

    public CustomGrid(Context c, List<Room> rooms, String buttonName)
    {
        this.mContext = c;
        this.rooms = rooms;
        this.buttonName = buttonName;
        serviceController = new GraphServiceController();
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
            TextView tvFloor = (TextView) grid.findViewById(R.id.tvFloor);
            TextView tvPersons = (TextView) grid.findViewById(R.id.tvPersons);
            TextView tvRoom = (TextView)grid.findViewById(R.id.tvRoom);
            TextView tvTime = (TextView)grid.findViewById(R.id.tvTime);
            Button btRoom = (Button) grid.findViewById(R.id.btnRoom);
            btRoom.setText(buttonName);
            tvFloor.setText("FLOOR" + " " + rooms.get(position).getFloor());
            tvPersons.setText(rooms.get(position).getPersons() + " " + "PERSON");
            if(buttonName.equals("Reserve"))
            {
                tvTime.setText(rooms.get(position).getState().toString());
            }
            else
            {
                tvTime.setText("Appointments: " + String.valueOf(rooms.get(position).getAppointments().size()));
                //tvTime.setText(rooms.get(position).getAppointments().get(0).getReserveringsTijd().toString("\"HH:mm  dd-MM-yyyy\"") + " \n " + rooms.get(position).getAppointments().get(0).getReserveringEind().toString("\"HH:mm  dd-MM-yyyy\""));
            }
            tvRoom.setText(rooms.get(position).getName());
            btRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(buttonName.equals("Reserve"))
                    {
                        Intent i = new Intent(mContext, Reservering.class);
                        i.putExtra("Room", rooms.get(position));
                        mContext.startActivity(i);
                    }
                    else
                    {
                        for(Appointment a : rooms.get(position).getAppointments())
                        {
                            new DeleteAppointmentAsync().execute(a);
                        }
                        Intent i = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(i);

                    }

                }
            });

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}






