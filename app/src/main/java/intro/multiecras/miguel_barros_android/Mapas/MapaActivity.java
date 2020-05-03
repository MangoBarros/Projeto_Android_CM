package intro.multiecras.miguel_barros_android.Mapas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import intro.multiecras.miguel_barros_android.API.GetData;
import intro.multiecras.miguel_barros_android.API.Nota;
import intro.multiecras.miguel_barros_android.API.RetrofitClientInstance;
import intro.multiecras.miguel_barros_android.Offline.MainActivity;
import intro.multiecras.miguel_barros_android.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static intro.multiecras.miguel_barros_android.Account.LoginActivity.SHARED_PREFS;


public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private GoogleMap mMap;
    private List<Nota> lista;
    private HashMap<Marker, Nota> marcadores = new HashMap<Marker, Nota>();
    private FusedLocationProviderClient fusedLocationClient;

    LatLng home;
    float zoom=17;

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapLongClick(mMap);
        setInfoWindowClickToPanorama(mMap);


        mMap.setOnMarkerClickListener(this);
        enableMyLocation();

        getAllNotas();

        fusedLocationClient.getLastLocation().addOnSuccessListener( new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location !=null){
                    home = new LatLng(location.getLatitude(),location.getLongitude());
                    //Marker marker = mMap.addMarker(new MarkerOptions().position(home).title("Your Position").icon(BitmapDescriptorFactory.defaultMarker(200)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoom));
                    //marker.showInfoWindow();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.createNota:
                fusedLocationClient.getLastLocation().addOnSuccessListener( new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location !=null){
                            makeNota(new LatLng(location.getLatitude(),location.getLongitude()));
                        }
                    }
                });



                Toast.makeText(this,"Nota criada",Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setMapLongClick(final GoogleMap map){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                makeNota(latLng);
            }
        });
    }

    public void makeNota(LatLng latLng) {
        Intent intent = new Intent(getApplicationContext(), MakeNota.class);
        intent.putExtra("coordenates", String.valueOf(latLng.latitude)+","+String.valueOf(latLng.longitude));
        startActivity(intent);
    }

    private void getAllNotas() {
        GetData service = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Call<List<Nota>> call = service.getAllNotas(sharedPreferences.getString("token", ""));
        call.enqueue(new Callback<List<Nota>>() {
            @Override
            public void onResponse(Call<List<Nota>> call, Response<List<Nota>> response) {
                if(response.body() != null){
                    lista = response.body();
                    fillNotasInMap(lista);
                }else {
                    Toast.makeText(getApplicationContext(),"Não Existem Notas De momento", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Nota>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Não Existem Notas De momento", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fillNotasInMap(List<Nota> lista){
        marcadores.clear();
        for (Nota n : lista) {

            String str = n.getCoordenates();
            String[] arrOfStr = str.split(",");
            Double latitude = Double.valueOf(arrOfStr[0]);
            Double longitude= Double.valueOf(arrOfStr[1]);

            LatLng latLng = new LatLng(latitude,longitude);
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            Integer id = sharedPreferences.getInt("id", -1);
            if(id == n.getUserId()){
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(n.getTitulo())
                        .icon(BitmapDescriptorFactory.defaultMarker(100)));
                marcadores.put(marker, n);
            }else {
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(n.getTitulo()));
                marcadores.put(marker, n);
            }

        }
    }

    private void setInfoWindowClickToPanorama(GoogleMap map) {
        map.setOnInfoWindowClickListener(
                new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        if(marker.getTitle() == "Your Position"){
                            Toast.makeText(getApplicationContext(), "Este Marcador apenas indica a sua posição", Toast.LENGTH_SHORT).show();
                        }
                        Intent i = new Intent(getApplicationContext(),SeeNota.class);
                        i.putExtra("id",marcadores.get(marker).getId());
                        startActivity(i);

                    }
                });
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // Check if location permissions are granted and if so enable the
        // location data layer.
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0]
                        == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                    break;
                }
        }
    }
    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }
}
