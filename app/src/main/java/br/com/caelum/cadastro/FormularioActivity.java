package br.com.caelum.cadastro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);
        /*
        Button botaoSalvar = (Button) findViewById(R.id.formulario_botao);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormularioActivity.this, "Clicado botao Salvar...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_formulario_ok:
                //Toast.makeText(this, "Item de menu clicado para inserir Aluno...", Toast.LENGTH_SHORT).show();

                String nomeAluno = helper.pegaAlunoDoFormulario().getNome();
                Toast.makeText(this, nomeAluno + " adicionado.", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
