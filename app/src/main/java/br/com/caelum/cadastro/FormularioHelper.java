package br.com.caelum.cadastro;

import android.support.design.widget.TextInputEditText;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioHelper {

    private TextInputEditText inputNome;
    private TextInputEditText inputTelefone;
    private TextInputEditText inputEndereco;
    private TextInputEditText inputSite;
    private RatingBar ratingNota;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        inputNome = (TextInputEditText) activity.findViewById(R.id.formulario_nome);
        inputTelefone = (TextInputEditText) activity.findViewById(R.id.formulario_telefone);
        inputEndereco = (TextInputEditText) activity.findViewById(R.id.formulario_endereco);
        inputSite = (TextInputEditText) activity.findViewById(R.id.formulario_site);
        ratingNota = (RatingBar) activity.findViewById(R.id.formulario_rating);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(inputNome.getText().toString());
        aluno.setEndereco(inputEndereco.getText().toString());
        aluno.setTelefone(inputTelefone.getText().toString());
        aluno.setSite(inputSite.getText().toString());
        aluno.setNota(Double.valueOf(ratingNota.getProgress()));
        aluno.setEndereco(inputTelefone.getText().toString());
        return aluno;
    }
}
