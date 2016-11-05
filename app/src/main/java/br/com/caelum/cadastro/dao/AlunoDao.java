package br.com.caelum.cadastro.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.cadastro.modelo.Aluno;

public class AlunoDao extends SQLiteOpenHelper{

    private static final int VERSAO = 1;
    private static final String TABELA = "ALUNO";
    private static final String DATABASE = "CadastroAlunos";

    public AlunoDao(Context context){
        super(context, DATABASE, null, VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql =  " CREATE TABLE " + TABELA + " ( "
                    + " id INTEGER PRIMARY KEY, "
                    + " nome TEXT NOT NULL, "
                    + " telefone TEXT, "
                    + " endereco TEXT, "
                    + " site TEXT, "
                    + " nota REAL);";
        db.execSQL(sql);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        String sql = " DROP TABLE IF EXISTS " + TABELA;
        db.execSQL(sql);
        onCreate(db);
        //db.close();
    }

    public void insere(Aluno aluno){
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());

        getWritableDatabase().insert(TABELA, null, values);
    }

    public List<Aluno> getLista(){
        List<Aluno> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABELA + ";", null);

        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            lista.add(aluno);
        }

        cursor.close();
        return lista;
    }
}
