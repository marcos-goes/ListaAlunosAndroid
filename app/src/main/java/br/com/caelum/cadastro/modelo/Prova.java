package br.com.caelum.cadastro.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Prova implements Serializable{

    private String data;
    private String materia;
    private String descricao;
    private List<String> topicos = new ArrayList<String>();

    public Prova(String data, String materia){
        this.data = data;
        this.materia = materia;
    }

    @Override
    public String toString(){
        return materia;
    }

    public String getData(){
        return this.data;
    }

    public String getMateria(){
        return this.materia;
    }

    public String getDescricao(){
        return this.descricao;
    }

    public List<String> getTopicos(){
        return this.topicos;
    }

    public void setData(String data){
        this.data = data;
    }

    public void setMateria(String materia){
        this.materia = materia;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public void setTopicos(List<String> topicos){
        this.topicos = topicos;
    }

}
