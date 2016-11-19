package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.modelo.Aluno;

public class ListaAlunosAdapter extends BaseAdapter{

    private final int CHAVE_TAG_VIEW_HOLDER = 2774;
    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter(Activity activity, List<Aluno> alunos){
        this.activity = activity;
        this.alunos = alunos;
    }


    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int pos) {
        return alunos.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        Aluno alunoDaPosicao = alunos.get(pos);
        return alunoDaPosicao.getId();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;

        if(convertView == null){
            view = activity.getLayoutInflater().inflate(R.layout.item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        Aluno aluno = alunos.get(pos);
        holder.nome.setText(aluno.getNome());
        holder.telefone.setText(aluno.getTelefone());

        Bitmap imagem;
        if(aluno.getCaminhoFoto() != null){
            imagem = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            imagem = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_no_image);
        }
        imagem = Bitmap.createScaledBitmap(imagem, 100, 100, true);
        holder.foto.setImageBitmap(imagem);
        holder.foto.setScaleType(ImageView.ScaleType.FIT_XY);

        if(pos % 2 == 0) {
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaPar));
        } else{
            view.setBackgroundColor(activity.getResources().getColor(R.color.linhaImpar));
        }

        return view;
    }
}
