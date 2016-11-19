package br.com.caelum.cadastro.receiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDao;

@TargetApi(23)
public class SMSReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Object[] mensagens = (Object[]) bundle.get("pdus");
        byte[] mensagem = (byte[]) mensagens[0];
        String formato = (String) bundle.get("format");
        SmsMessage sms = SmsMessage.createFromPdu(mensagem, formato);

        AlunoDao alunoDao = new AlunoDao(context);
        if(alunoDao.isAlunoByTelefone(sms.getOriginatingAddress())){
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

            Toast.makeText(context, "Chegou um SMS de aluno.", Toast.LENGTH_SHORT).show();
        }
        alunoDao.close();
    }
}
