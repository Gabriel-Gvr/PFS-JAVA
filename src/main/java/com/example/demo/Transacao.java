package com.example.demo;

public class Transacao {
    private int id;
    private String descricao;
    private double valor;
    private boolean receita; // true para receita, false para despesa

    // Construtor
    public Transacao(int id, String descricao, double valor, boolean receita) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.receita = receita;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public boolean isReceita() {
        return receita;
    }

    // Setters - caso necessário para operações de atualização futuras

    // toString para exibir informações da transação
    @Override
    public String toString() {
        String tipo = receita ? "Receita" : "Despesa";
        return "Transacao [id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", tipo=" + tipo + "]";
    }
}
