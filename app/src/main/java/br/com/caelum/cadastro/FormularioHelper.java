package br.com.caelum.cadastro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import br.com.caelum.cadastro.modelo.Aluno;

public class FormularioHelper {

    private TextInputEditText inputNome;
    private TextInputEditText inputTelefone;
    private TextInputEditText inputEndereco;
    private TextInputEditText inputSite;
    private RatingBar ratingNota;
    private ImageView imageFoto;
    private FloatingActionButton buttonFoto;


    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        inputNome = (TextInputEditText) activity.findViewById(R.id.formulario_nome);
        inputTelefone = (TextInputEditText) activity.findViewById(R.id.formulario_telefone);
        inputEndereco = (TextInputEditText) activity.findViewById(R.id.formulario_endereco);
        inputSite = (TextInputEditText) activity.findViewById(R.id.formulario_site);
        ratingNota = (RatingBar) activity.findViewById(R.id.formulario_rating);
        imageFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        buttonFoto = (FloatingActionButton) activity.findViewById(R.id.formulario_foto_button);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario(){
        aluno.setNome(inputNome.getText().toString());
        aluno.setEndereco(inputEndereco.getText().toString());
        aluno.setTelefone(inputTelefone.getText().toString());
        aluno.setSite(inputSite.getText().toString());
        aluno.setNota((double) ratingNota.getRating());
        aluno.setCaminhoFoto((String) imageFoto.getTag());
        return aluno;
    }

    public void colocaAlunoNoFormulario(Aluno aluno){
        this.aluno = aluno;

        inputNome.setText(aluno.getNome());
        inputTelefone.setText(aluno.getTelefone());
        inputEndereco.setText(aluno.getEndereco());
        inputSite.setText(aluno.getSite());
        ratingNota.setRating(aluno.getNota().longValue());

        if(aluno.getCaminhoFoto() != null && !aluno.getCaminhoFoto().isEmpty()){
            carregaImagem(aluno.getCaminhoFoto());
        }
    }

    public boolean validaNome(){
        return (inputNome != null && !inputNome.getText().toString().isEmpty());
    }

    public FloatingActionButton getBotaoFoto(){
        return buttonFoto;
    }

    public void carregaImagem(String pathImagem){

        //Picasso.with(Context.).load(new File(pathImagem)).into(imageFoto);

        Bitmap imagem = BitmapFactory.decodeFile(pathImagem);
        Bitmap imagemReduzida = Bitmap.createScaledBitmap(imagem, imagem.getWidth(), 200, true);
        imageFoto.setImageBitmap(imagemReduzida);
        imageFoto.setTag(pathImagem);
        imageFoto.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void mostraErro(){
        inputNome.setError("Campo nome não pode ser vazio.");
    }


    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
