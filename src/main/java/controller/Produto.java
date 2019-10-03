package controller;

import java.util.ArrayList;
//Classe descrevendo o produto
public class Produto {
    private String nome;
    private int preco;
    private ArrayList<TipoPromocao> promocoes;

    public Produto(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public ArrayList<TipoPromocao> getPromocoes() {
        return promocoes;
    }

    public void setPromocoes(ArrayList<TipoPromocao> promocoes) {
        this.promocoes = promocoes;
    }
}
