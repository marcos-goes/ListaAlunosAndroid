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

    private static final int VERSAO = 2;
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
                    + " nota REAL, "
                    + " foto TEXT);";
        db.execSQL(sql);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int versaoNova) {
        //String sql = " DROP TABLE IF EXISTS " + TABELA;
        String sql = " ALTER TABLE " + TABELA + " ADD COLUMN foto TEXT;";
        db.execSQL(sql);
        //onCreate(db);
        //db.close();
    }

    public boolean isAlunoByTelefone(String telefone){
        String[] parametros = {telefone};
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT 1 FROM " + TABELA + " WHERE telefone = ?", parametros);

        int total = rawQuery.getCount();
        rawQuery.close();

        return (total > 0);
    }

    private ContentValues toContentValues(Aluno aluno){
        ContentValues values = new ContentValues();

        values.put("nome", aluno.getNome());
        values.put("telefone", aluno.getTelefone());
        values.put("endereco", aluno.getEndereco());
        values.put("site", aluno.getSite());
        values.put("nota", aluno.getNota());
        values.put("foto", aluno.getCaminhoFoto());

        return values;
    }

    public void insere(Aluno aluno){
        getWritableDatabase().insert(TABELA, null, toContentValues(aluno));
    }

    public void altera(Aluno aluno){
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().update(TABELA,toContentValues(aluno),"id=?",args);
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
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("foto")));
            lista.add(aluno);
        }

        cursor.close();
        return lista;
    }

    public void excluir(Aluno aluno){
        String[] args = {aluno.getId().toString()};
        getWritableDatabase().delete(TABELA, "id=?", args);
    }
}
