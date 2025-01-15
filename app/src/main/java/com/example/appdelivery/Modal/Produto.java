package com.example.appdelivery.Modal;

public class Produto {

    private String nome;
    private String descricao;
    private String preco;
    private int foto;

    public Produto(String nome, String descricao, String preco, int foto) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
