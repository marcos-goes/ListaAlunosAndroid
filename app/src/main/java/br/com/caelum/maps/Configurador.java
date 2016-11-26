package br.com.caelum.maps;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

public class Configurador implements GoogleApiClient.ConnectionCallbacks {

    private AtualizadorLocalizacao atualizador;

    public Configurador(AtualizadorLocalizacao atualizadorLocalizacao){
        this.atualizador = atualizadorLocalizacao;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest request = LocationRequest.create();
        request.setInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setSmallestDisplacement(5);
        atualizador.inicia(request);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
