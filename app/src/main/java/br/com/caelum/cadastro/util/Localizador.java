package br.com.caelum.cadastro.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

public class Localizador {

    private Geocoder geocoder;

    public Localizador(Context contexto){

        geocoder = new Geocoder(contexto);

    }

    public LatLng getCoordenada(String endereco){

        LatLng coordenada = null;

        try {
            Address address = geocoder.getFromLocationName(endereco, 1).get(0);
            coordenada = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (IOException e) {
        }

        return coordenada;

    }

}
