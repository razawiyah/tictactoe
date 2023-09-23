package com.example.googlemaptest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap myMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addresses= null;
    private  final  static int REQUEST_CODE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Enable the My Location layer, which will show a blue dot on the user's current location.
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myMap.setMyLocationEnabled(true);

            // Request location updates and handle location changes
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17.0f));

                                // You can also add a marker at the user's location if needed
                                myMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location"));
                            }
                        }
                    });
        } else {
            // Request location permission if not granted.
            askPermission();
        }

        LatLng jkrMalaysia = new LatLng(3.1540, 101.6900);
        float zoomLevel = 17.0f; // Specify the desired zoom level here
        myMap.addMarker(new MarkerOptions().position(jkrMalaysia).title("Ibu Pejabat JKR Malaysia"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jkrMalaysia, zoomLevel));

        LatLng p1 = new LatLng(3.155174, 101.689536);
        LatLng p2 = new LatLng(3.153766, 101.688797);
        LatLng p3 = new LatLng(3.152230, 101.691199);
        LatLng p4 = new LatLng(3.152706, 101.691518);

        PolygonOptions polygonOptions = new PolygonOptions()
                .add(p1)
                .add(p2)
                .add(p3)
                .add(p4);

        Polygon polygon = myMap.addPolygon(polygonOptions);
        polygon.setStrokeColor(Color.BLUE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
            else {
                Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}