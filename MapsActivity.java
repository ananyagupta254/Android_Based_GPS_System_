package edu.angupta.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private Button mBtLaunchActivity;
    private String TAG = "MainActivity";
    private GoogleMap mMap;
    Timer timer;
    private final int interval = 1000; // 1 Second
    //private Handler handler = new Handler();
    //private Runnable runnable = new Runnable(){

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //timer = new Timer();
        //timer.schedule(new MyTimer(), 2000, 5000);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        LocationActivity la = new LocationActivity(this);
        //mBtLaunchActivity = (Button) findViewById(R.id.button);
        //mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {

         //       LocationActivity la = new LocationActivity(this);
         //   }
        //});
    }

        public void switchActivity(){
            Intent intent = new Intent(this, location_table.class);
            startActivity(intent);
            //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

            // Capture the layout's TextView and set the string as its text
            //TextView textView = findViewById(R.id.textView);
            //textView.setText(message);
        }


        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.clear();
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void updateLocation(Location loc)
    {
        String locAddress = getAddressFromLocation( loc );
        Log.d(TAG, "Value is: " + loc);
        LatLng ll = new LatLng(loc.getLatitude(), loc.getLongitude());
        mMap.addMarker(new MarkerOptions().position(ll).title(locAddress));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        updateFireBase (ll);
    }

    private String getAddressFromLocation( Location location ) {
        Geocoder geocoder = new Geocoder( this );

        String strAddress = "";
        Address address;
        try {
            address = geocoder
                    .getFromLocation( location.getLatitude(), location.getLongitude(), 1 )
                    .get( 0 );
            strAddress = address.getAddressLine(0) +
                    " " + address.getAddressLine(1) +
                    " " + address.getAddressLine(2);
        }
        catch (IOException e ) {
        }

        return strAddress;
    }

    void updateFireBase (LatLng ll){


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //Calendar cal = Calendar.getInstance();
        //System.out.println(dateFormat.format(cal)); //2016/11/16 12:08:43
        //location.put("curtime", dateFormat.format(new Date()).toString());

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("locations");
//CurrentLocation

        CurrentLocation loc = new CurrentLocation(ll.latitude, ll.longitude,"BUS123");
        myRef.child(loc.busID).setValue(loc);
        DatabaseReference childRef = database.getReference("locations/"+loc.busID);


        // [END read_message]

    }





}
