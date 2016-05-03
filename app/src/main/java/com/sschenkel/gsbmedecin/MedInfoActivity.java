package com.sschenkel.gsbmedecin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/**
 * Created by Stéphane on 2/4/2016.
 */
public class MedInfoActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private Medecin leMed;

    private Button call;
    private Button sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medinfo);

        Intent inter = getIntent();
        leMed = Medecin.deserialize(inter.getStringExtra("leMed"));

        TextView nom = (TextView)findViewById(R.id.nom);
        nom.setText(leMed.getNom() + " " + leMed.getPrenom());
        TextView adresse = (TextView)findViewById(R.id.adresse);
        adresse.setText(leMed.getAdresse());
        TextView tel = (TextView)findViewById(R.id.tel);
        tel.setText(leMed.getTel());
        TextView spec = (TextView)findViewById(R.id.specialite);
        spec.setText(leMed.getSpecialite());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        call = (Button)findViewById(R.id.CallButton);
        sms = (Button)findViewById(R.id.SMSButton);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MedInfoActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MedInfoActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                }else{
                    callMed(leMed.getTel().replace(" ", ""));
                }
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + leMed.getTel().replace(" ", "")));
                startActivity(intent);
            }
        });
    }

    private void callMed (String numTel){
        try{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+numTel));
            startActivity(callIntent);
        }catch(SecurityException se){

        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(leMed.getAdresse(), 1);
            if(addressList != null && addressList.size() > 0){
                Address address = addressList.get(0);
                LatLng position = new LatLng(address.getLatitude(), address.getLongitude());
                map.addMarker(new MarkerOptions().position(position).title("Adresse du médecin"));
                map.moveCamera(CameraUpdateFactory.newLatLng(position));
                map.moveCamera(CameraUpdateFactory.zoomTo(15));
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        Toast.makeText(getApplicationContext(), leMed.getAdresse(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callMed(leMed.getTel().replace(" ", ""));
                } else {
                    Toast.makeText(this, "Vous devez donner la permission d'appel à l'application", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
