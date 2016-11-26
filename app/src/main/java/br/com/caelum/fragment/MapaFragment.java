package br.com.caelum.fragment;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.util.Localizador;


public class MapaFragment extends SupportMapFragment {

    private static final float ZOOM_GOOGLE = 15;

    @Override
    public void onResume() {
        super.onResume();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Localizador localizador = new Localizador(getContext());
                //LatLng coordenada = localizador.getCoordenada("Avenida Bolonha 89 Jaguaré");

                //centralizaMapa(coordenada, googleMap);
                //Log.i("MAPA", "Coordenada mais importante do universo: " + coordenada);

                LatLng coordenada = null;
                AlunoDao dao = new AlunoDao(getContext());
                for(Aluno aluno : dao.getLista()){
                    coordenada = localizador.getCoordenada(aluno.getEndereco());
                    MarkerOptions marcador = new MarkerOptions()
                            .title(aluno.getNome())
                            .position(coordenada);
                    googleMap.addMarker(marcador);
                }

                if(coordenada != null){
                    centralizaMapa(coordenada, googleMap);
                }



            }
        });
    }

    public void centralizaMapa(LatLng coordenada, GoogleMap googleMap){
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(coordenada, ZOOM_GOOGLE);
        googleMap.moveCamera(cameraUpdate);
    }
}
