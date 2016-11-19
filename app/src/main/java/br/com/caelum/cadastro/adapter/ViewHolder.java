package br.com.caelum.cadastro.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.caelum.cadastro.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolder {

    @BindView(R.id.item_foto)
    public ImageView foto;

    @BindView(R.id.item_nome)
    public TextView nome;

    @BindView(R.id.item_telefone)
    public TextView telefone;

    public ViewHolder(View view){
        ButterKnife.bind(this,view);
    }
}
