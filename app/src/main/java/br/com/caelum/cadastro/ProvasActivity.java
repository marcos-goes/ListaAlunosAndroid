package br.com.caelum.cadastro;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.caelum.cadastro.modelo.Prova;
import br.com.caelum.fragment.DetalheProvaFragment;
import br.com.caelum.fragment.ListaProvasFragment;

public class ProvasActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_provas);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(isTablet()){
            transaction.replace(R.id.provas_lista, new ListaProvasFragment());
            transaction.replace(R.id.provas_detalhes, new DetalheProvaFragment());
        } else {
            transaction.replace(R.id.provas_view, new ListaProvasFragment());
        }

        transaction.commit();
    }

    private boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void selecionaProva(Prova prova){
        FragmentManager manager = getSupportFragmentManager();
        DetalheProvaFragment detalhesFragment;

        if(isTablet()){
            detalhesFragment = (DetalheProvaFragment) manager.findFragmentById(R.id.provas_detalhes);
            detalhesFragment.populaCamposComDados(prova);
        } else {
            Bundle argumentos = new Bundle();
            argumentos.putSerializable("prova", prova);

            detalhesFragment = new DetalheProvaFragment();
            detalhesFragment.setArguments(argumentos);

            FragmentTransaction tx = manager.beginTransaction();
            tx.replace(R.id.provas_view, detalhesFragment);
            tx.addToBackStack(null);
            tx.commit();

        }
    }
}
