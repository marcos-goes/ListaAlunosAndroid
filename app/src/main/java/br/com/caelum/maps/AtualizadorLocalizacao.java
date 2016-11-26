package br.com.caelum.maps;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import br.com.caelum.fragment.MapaFragment;


public class AtualizadorLocalizacao implements LocationListener {

    private GoogleApiClient client;
    private MapaFragment mapaFragment;

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng coordenada = new LatLng(latitude, longitude);
        //mapaFragment.centralizaMapa(coordenada, );
    }

    public AtualizadorLocalizacao(Context context, MapaFragment frag){
        this.mapaFragment = frag;
        Configurador config = new Configurador(this);
        this.client = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(config)
                .build();
        this.client.connect();
    }

    public void inicia(LocationRequest request) throws SecurityException {
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    public void cancela(){
        LocationServices.FusedLocationApi.removeLocationUpdates(client, this);
        this.client.disconnect();
    }
}
