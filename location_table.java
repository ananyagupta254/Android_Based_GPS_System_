package edu.angupta.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth.AuthStateListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class location_table extends AppCompatActivity {
    private Button mBtLaunchActivity;
    private EditText city, latitude, longitude;
    String TAG = "Location Table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_location);
        mBtLaunchActivity = (Button) findViewById(R.id.etButton);

        mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickButton(view);
            }
        });
    }

    public void onClickButton (View View){

        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final EditText etLatitude = (EditText) findViewById(R.id.etLatitude);
        final EditText etLongitude = (EditText) findViewById(R.id.etLongitude);
        final Button etButton = (Button) findViewById(R.id.etButton);

        String city = etCity.getText().toString();
        String latitude = etLatitude.getText().toString();
        String longitude = etLongitude.getText().toString();

        //String location_data = mAuth.getCurrentLoc().getLid();
        //DatabaseReference currentLocation = FirebaseDatabase.getInstance().
        //        getReference().child("location").child(location_data);
        Map location = new HashMap<String, String>();

        location.put("city", city);

        location.put("latitude", latitude);
        location.put("longitude", longitude);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43
        location.put("curtime", dateFormat.format(new Date()).toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locations");

        DatabaseReference newChild = myRef.child("location").push();
        //Timestamp stamp = new Timestamp(System.currentTimeMillis(long));
        //Date date=new Date(stamp.toString());
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //sfd.format(date);

        newChild.updateChildren(location);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // [END read_message]

    }

}





