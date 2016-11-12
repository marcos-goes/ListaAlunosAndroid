package br.com.caelum.cadastro;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private FormularioHelper helper;
    private String localArquivoFoto;
    private static final int CODE_CAMERA_INTENT = 7656;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra(ALUNO_SELECIONADO);

        if(aluno != null)
            helper.colocaAlunoNoFormulario(aluno);

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

        FloatingActionButton botaoFoto = helper.getBotaoFoto();
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                Uri uriLocalFoto = Uri.fromFile(new File(localArquivoFoto));
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uriLocalFoto);
                startActivityForResult(intentCamera, CODE_CAMERA_INTENT);
            }
        });
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
                    AlunoDao alunoDao = new AlunoDao(this);
                    Aluno aluno = helper.pegaAlunoDoFormulario();

                    if(aluno.getId() == null){
                        alunoDao.insere(aluno);
                        Toast.makeText(this, aluno.getNome() + " adicionado(a).", Toast.LENGTH_SHORT).show();
                    } else {
                        alunoDao.altera(aluno);
                        Toast.makeText(this, aluno.getNome() + " alterado(a).", Toast.LENGTH_SHORT).show();
                    }
                    alunoDao.close();
                } else {
                    helper.mostraErro();
                }

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if(requestCode == CODE_CAMERA_INTENT){
//            if(resultCode == Activity.RESULT_OK){
//                helper.carregaImagem(this.localArquivoFoto);
//            } else {
//                this.localArquivoFoto = null;
//            }
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_CAMERA_INTENT){
            if(resultCode == Activity.RESULT_OK){
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }
}
