package br.com.caelum.cadastro;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        String[] alunos = {"Marcos", "Josefina", "Maria", "Ressonancio", "Jasmine", "Jurema", "Xurumelas"};

        this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);
        this.listaAlunos.setAdapter(adapter);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                String aluno = (String) adapter.getItemAtPosition(pos);
                Toast.makeText(ListaAlunosActivity.this, aluno, Toast.LENGTH_SHORT).show();
            }
        });

        listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int pos, long id) {
                Toast.makeText(ListaAlunosActivity.this, "Posicao: " + String.valueOf(pos), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.lista_alunos_floating_button);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ListaAlunosActivity.this, "Clicado no Floating...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
    }
}
