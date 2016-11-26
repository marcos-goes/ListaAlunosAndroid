package br.com.caelum.cadastro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.modelo.Aluno;
import br.com.caelum.cadastro.support.WebClient;
import br.com.caelum.task.EnviaAlunosTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class ListaAlunosActivity extends AppCompatActivity {

    @BindView(R.id.lista_alunos)
    ListView listaAlunos;

    @BindView(R.id.lista_alunos_floating_button)
    FloatingActionButton floatingButton;

    @OnItemClick(R.id.lista_alunos)
    protected void cliqueItemLista(int pos){
        Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(pos);
        Intent intentEditar = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        intentEditar.putExtra(FormularioActivity.ALUNO_SELECIONADO, aluno);
        startActivity(intentEditar);
    }

    @OnItemLongClick(R.id.lista_alunos)
    protected boolean cliqueItemLongoLista(int pos){
        Toast.makeText(ListaAlunosActivity.this, "Posicao: " + String.valueOf(pos), Toast.LENGTH_SHORT).show();
        return false;
    }

    @OnClick(R.id.lista_alunos_floating_button)
    protected void cliqueFloating(View view) {
        //Toast.makeText(ListaAlunosActivity.this, "Clicado no Floating...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        ButterKnife.bind(this);

        Permissao.fazPermissao(this);

        //this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(this.listaAlunos);

        /*
        String[] alunos = {"Marcos", "Josefina", "Maria", "Ressonancio", "Jasmine", "Jurema", "Xurumelas"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);
        this.listaAlunos.setAdapter(adapter);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                Aluno aluno = (Aluno) adapter.getItemAtPosition(pos);
                //Toast.makeText(ListaAlunosActivity.this, aluno.toString(), Toast.LENGTH_SHORT).show();

                Intent intentEditar = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentEditar.putExtra(FormularioActivity.ALUNO_SELECIONADO, aluno);
                startActivity(intentEditar);
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
        */

        //registerReceiver(bateria, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    protected void onResume(){
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position);

        //getMenuInflater().inflate(R.menu.menu_contexto, menu);
        //MenuItem itemDeletar = (MenuItem) findViewById(R.id.menu_contexto_deletar);
        MenuItem itemDeletar = menu.add("Deletar");
        itemDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDao dao = new AlunoDao(ListaAlunosActivity.this);
                dao.excluir(alunoSelecionado);
                dao.close();
                carregaLista();
                return true;
            }
        });

        MenuItem itemLigar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        itemLigar.setIntent(intentLigar);

        MenuItem itemSms = menu.add("SMS");
        Intent intentSms = new Intent(Intent.ACTION_VIEW);
        intentSms.setData(Uri.parse("sms:" + alunoSelecionado.getTelefone()));
        intentSms.putExtra("sms_body", "pedaço da mensagem...");
        itemSms.setIntent(intentSms);

        MenuItem itemMapa = menu.add("Achar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:-23.5918659,-46.6362278?z=15&q="));
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Navegar no Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = alunoSelecionado.getSite();
        if(!site.startsWith("http://")){
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);
        /*
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent ligacao = new Intent(Intent.ACTION_CALL);
                ligacao.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
                startActivity(ligacao);
            }
        });
        */

    }

    public void carregaLista(){
        AlunoDao dao = new AlunoDao(this);
        List<Aluno> lista = dao.getLista();
        dao.close();

        //ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, lista);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, lista);
        listaAlunos.setAdapter(adapter);
    }

    private BroadcastReceiver bateria = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int valor = intent.getIntExtra("level", 0);
            Toast.makeText(context, valor + "%", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        try{
            switch(item.getItemId()){

                case R.id.menu_enviar_notas:
                    /*
                    AlunoDao dao = new AlunoDao(this);
                    List<Aluno> alunos = dao.getLista();
                    dao.close();

                    String json = new AlunoConverter().toJSON(alunos);
                    WebClient client = new WebClient();
                    String jsonResposta = client.post(json);
                    Toast.makeText(this, jsonResposta, Toast.LENGTH_LONG).show();
                    */
                    new EnviaAlunosTask(this).execute();
                    return true;

                case R.id.menu_receber_provas:
                    Intent provas = new Intent(this, ProvasActivity.class);
                    startActivity(provas);
                    return true;

                case R.id.menu_mapa:
                    Intent mapa = new Intent(this, MostraAlunoActivity.class);
                    startActivity(mapa);
                    return true;
            }
        //} catch(IOException ex){
        //    Toast.makeText(this, "Deu um IOException... Sorry!", Toast.LENGTH_LONG).show();
        } catch(Exception ex){
            Toast.makeText(this, "Deu um erro cabuloso... Impossivel de tratar!", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }
}
