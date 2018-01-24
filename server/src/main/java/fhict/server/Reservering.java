package fhict.server;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;


import com.microsoft.graph.concurrency.ICallback;
import com.microsoft.graph.core.ClientException;
import com.microsoft.graph.extensions.Attendee;
import com.microsoft.graph.extensions.EmailAddress;
import com.microsoft.graph.extensions.Event;
import com.microsoft.graph.extensions.IUserCollectionPage;
import com.microsoft.graph.extensions.User;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import fhict.mylibrary.Appointment;
import fhict.mylibrary.Room;
import fhict.server.GraphAPI.GraphServiceController;
import fhict.server.GraphAPI.PostDataAsync;
import fhict.server.Sockets.CommandList.UserCommandI;

public class Reservering extends AppCompatActivity implements SearchView.OnQueryTextListener{


    SearchView svPersons;
    private ListView lvPersons;
    private ListView lv;
    private ListView lvAttendees;
    ArrayList<Appointment> appointments = null;
    ArrayAdapter<Appointment> arrayAdapter = null;
    ArrayAdapter<String> arrayAdapterAttendees = null;
    List<String> attendees = null;
    SearchListAdapter adapter;
    private TextView tvThisRoom;
    // Init button group
    private Button[] btn = new Button[4];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btn15min, R.id.btn30min, R.id.btn45min, R.id.btn1hour};
    // Reservetime
    private int reserveTime = 0;
    private fhict.mylibrary.User selectedUser;
    final List<fhict.mylibrary.User> users = new ArrayList<>();
    //UserRepo userController = new UserRepo(new UserExContext(this));
    GraphServiceController serviceController;
    private Room thisRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservering1);
        thisRoom = (Room)getIntent().getSerializableExtra("Room");
        serviceController = new GraphServiceController();
        svPersons = (SearchView)findViewById(R.id.sVPersons);
        lvPersons = (ListView)findViewById(R.id.lvPersons);
        attendees = new ArrayList<String>();
        arrayAdapterAttendees = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, attendees);
        appointments = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appointments);

        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = (fhict.mylibrary.User)adapter.getItem(i);
            }
        });
        svPersons.setOnQueryTextListener(this);
        tvThisRoom = (TextView)findViewById(R.id.textViewRoom);
        selectedUser = null;
        // Set button group
        for(int i = 0; i < btn.length; i++)
        {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackground(getResources().getDrawable(R.drawable.reservebutton));
            btn[i].setTextColor(Color.parseColor("#FF535353"));
        }
        btn_unfocus = btn[0];
        tvThisRoom.setText("Reserved Room: " + thisRoom.getName());

        serviceController.apiUsers(new ICallback<IUserCollectionPage>() {
            @Override
            public void success(IUserCollectionPage iUserCollectionPage) {
                List<fhict.mylibrary.User> theirUsers = userConvert(iUserCollectionPage.getCurrentPage());
                for(fhict.mylibrary.User r : theirUsers)
                {
                    users.add(r);
                }
                adapter = new SearchListAdapter(getApplicationContext(), users);
                lvPersons.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure(ClientException ex) {

            }
        });

        lvPersons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = (fhict.mylibrary.User)adapter.getItem(i);
                svPersons.setQuery(selectedUser.getName(), false);
            }
        });



    }


    private ArrayList<fhict.mylibrary.User> userConvert(List<User> users)
    {
        ArrayList<fhict.mylibrary.User> newUsers = new ArrayList<>();
        for(User user: users)
        {
            if(!user.displayName.toUpperCase().contains("ROOM") && !user.displayName.contains("MOD Administrator")) {
                newUsers.add(new fhict.mylibrary.User(user.displayName, user.mail));
                Log.d("Server", "Sending user: " + user.displayName);
            }
        }
        return newUsers;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void btnReserveer(View v){
        if(selectedUser != null && reserveTime != 0) {
            fhict.mylibrary.User user = selectedUser;
            Appointment newApp = new Appointment("Meeting by: " + user.getName(), DateTime.now(), DateTime.now().plusMinutes(reserveTime));
            newApp.addAttendee(new fhict.mylibrary.User(selectedUser.getName(), selectedUser.getEmail()));
            newApp.addAttendee(new fhict.mylibrary.User(thisRoom.getName(), "Room@M365B679737.onmicrosoft.com"));
            scheduleMeeting(newApp);
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("Room", thisRoom);
            startActivity(i);
        }

    }

    public void btnCancel(View v){
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Room", thisRoom);
        startActivity(i);
    }

    private void scheduleMeeting(final Appointment appointment)
    {
        new PostDataAsync().execute(appointment);
    }


    // Code to use the buttons in a group
    public void btn15min(View v)
    {
        setFocus(btn_unfocus, btn[0]);
        reserveTime = 15;
    }

    public void btn30min(View v)
    {
        setFocus(btn_unfocus, btn[1]);
        reserveTime = 30;
    }

    public void btn45min(View v)
    {
        setFocus(btn_unfocus, btn[2]);
        reserveTime = 45;
    }

    public void btn1hour(View v)
    {
        setFocus(btn_unfocus, btn[3]);
        reserveTime = 60;
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.parseColor("#FF535353"));
        btn_unfocus.setBackground(getResources().getDrawable(R.drawable.reservebutton));
        btn_focus.setTextColor(Color.parseColor("#FF118AD3"));
        btn_focus.setBackground(getResources().getDrawable(R.drawable.reservebuttonselected));
        this.btn_unfocus = btn_focus;
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String text = s;
        adapter.filter(text);
        return false;
    }

}
