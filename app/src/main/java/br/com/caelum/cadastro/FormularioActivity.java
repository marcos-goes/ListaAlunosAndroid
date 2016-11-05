package br.com.caelum.cadastro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private AlunoDao alunoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);
        alunoDao = new AlunoDao(this);
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

                if(helper.validaNome()){
                    Aluno aluno = helper.pegaAlunoDoFormulario();

                    Toast.makeText(this, aluno.getNome() + " adicionado.", Toast.LENGTH_SHORT).show();

                    alunoDao.insere(aluno);
                } else {

                }



                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
