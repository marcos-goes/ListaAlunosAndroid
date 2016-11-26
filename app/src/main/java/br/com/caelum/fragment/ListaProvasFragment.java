package br.com.caelum.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.caelum.cadastro.ProvasActivity;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Prova;

public class ListaProvasFragment extends Fragment {

    private ListView listViewProvas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        Prova prova1 = new Prova("20/11/2016", "Matemática");
        prova1.setTopicos(Arrays.asList("Álgebra Linear", "Cálculo", "Estatística"));

        Prova prova2 = new Prova("25/11/2016", "Português");
        prova2.setTopicos(Arrays.asList("Complemento Nominal", "Orações Subordinadas", "Análise Sintática"));

        List<Prova> provas = Arrays.asList(prova1, prova2);

        View layoutProvas = inflater.inflate(R.layout.fragment_lista_provas, container, false);
        this.listViewProvas = (ListView) layoutProvas.findViewById(R.id.lista_provas_listview);

        this.listViewProvas.setAdapter(new ArrayAdapter<Prova>(getActivity(), android.R.layout.simple_list_item_1, provas));

        this.listViewProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Prova provaSelecionada = (Prova) adapterView.getItemAtPosition(pos);
                //Toast.makeText(getActivity(), "Prova selecionada: " + provaSelecionada, Toast.LENGTH_LONG).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(provaSelecionada);
            }
        });

        return layoutProvas;
    }


}
