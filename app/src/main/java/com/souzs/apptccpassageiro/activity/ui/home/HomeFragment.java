package com.souzs.apptccpassageiro.activity.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.souzs.apptccpassageiro.R;
import com.souzs.apptccpassageiro.helper.ConfiguracaoFireBase;
import com.souzs.apptccpassageiro.model.Motorista;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng local;

    private DatabaseReference reference;

    private ArrayList<Motorista> markersMotorista = new ArrayList<Motorista>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reference = ConfiguracaoFireBase.getReference();

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        recuMarcadores();
        return root;
    }
    private void metodosMarkes(){
        mMap.clear();
        addMarkes();
        addMarkerUsu();
        markersMotorista.clear();
    }
    private void recuMarcadores(){

        final DatabaseReference motoristaL = reference.child("motorista_logado");
        motoristaL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot ds: dataSnapshot.getChildren()) {
                Motorista m = ds.getValue(Motorista.class);

                markersMotorista.add(m);
            }
            metodosMarkes();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void addMarkerUsu(){
        mMap.addMarker(new MarkerOptions()
                .position(local)
                .title("Menu Local"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(local, 15));

    }
    public void addMarkes(){
        for (int i = 0; i < markersMotorista.size(); i++){
            criaMarkers(markersMotorista.get(i).getLat(), markersMotorista.get(i).getLon(),
                    markersMotorista.get(i).getNomeLinha(), markersMotorista.get(i).getSttLinha());

        }
    }

    protected Marker criaMarkers(String lat, String lon, String titulo, String stt){
        Double latitude = Double.valueOf(lat);
        Double longitude = Double.valueOf(lon);

         return mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .anchor(0.5f, 0.5f)
                    .title(titulo)
                    .snippet(stt)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario)));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        recuLocalizacao();
    }

    public void recuLocalizacao(){
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Double lat = location.getLatitude();
                Double lon = location.getLongitude();
                local = new LatLng(lat, lon);

                addMarkerUsu();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    60000,
                    10,
                    locationListener
            );
        }
    }
}