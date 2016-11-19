package br.com.caelum.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDao;
import br.com.caelum.cadastro.support.WebClient;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private Activity activity;
    private ProgressDialog progress;

    public EnviaAlunosTask(Activity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Object... objects) {

        AlunoDao dao = new AlunoDao(activity);
        String json = new AlunoConverter().toJSON(dao.getLista());
        dao.close();

        WebClient client = new WebClient();
        String jsonResposta;
        try{
            jsonResposta = client.post(json);
        } catch(IOException ex){
            jsonResposta = "IOException: " + ex.getMessage();
        } catch(Exception ex){
            jsonResposta = "Exception: " + ex.getMessage();
        }


        return jsonResposta;
    }

    @Override
    protected void onPostExecute(String result){
        progress.dismiss();
        Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute(){
        progress = ProgressDialog.show(activity, "Aguarde...", "Envio de dados para a web.", true, true);
    }
}
