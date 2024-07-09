package com.example.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControlaFinanca {

    private final JdbcTemplate jdbcTemplate;

    public ControlaFinanca(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Criar tabela de transações para MySQL
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS transacoes (\n"
                + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                + " descricao VARCHAR(255) NOT NULL,\n"
                + " valor DOUBLE NOT NULL,\n"
                + " receita TINYINT(1) NOT NULL\n"
                + ")";

        jdbcTemplate.execute(sql);
    }

    // Adicionar uma nova transação
    public void adicionarTransacao(String descricao, double valor, boolean receita) {
        String sql = "INSERT INTO transacoes(descricao, valor, receita) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, descricao, valor, receita ? 1 : 0); // MySQL utiliza 1 para true e 0 para false
    }

    // Apagar uma transação pelo id
    public void apagarTransacao(int id) {
        String sql = "DELETE FROM transacoes WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Listar todas as transações
    public List<Transacao> listarTransacoes() {
        String sql = "SELECT id, descricao, valor, receita FROM transacoes";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Transacao(rs.getInt("id"), rs.getString("descricao"),
                        rs.getDouble("valor"), rs.getBoolean("receita")));
    }

    // Calcular o saldo
    public double calcularSaldo() {
        List<Transacao> transacoes = listarTransacoes();
        double saldo = 0;
        for (Transacao transacao : transacoes) {
            if (transacao.isReceita()) {
                saldo += transacao.getValor();
            } else {
                saldo -= transacao.getValor();
            }
        }
        return saldo;
    }

    // Visualizar relatório financeiro
    public void visualizarRelatorio() {
        double totalReceitas = 0;
        double totalDespesas = 0;
        List<Transacao> transacoes = listarTransacoes();

        for (Transacao transacao : transacoes) {
            if (transacao.isReceita()) {
                totalReceitas += transacao.getValor();
            } else {
                totalDespesas += transacao.getValor();
            }
        }

        double saldo = calcularSaldo();

        System.out.println("Relatório Financeiro:");
        System.out.println("Total de Receitas: R$ " + totalReceitas);
        System.out.println("Total de Despesas: R$ " + totalDespesas);
        System.out.println("Saldo Final: R$ " + saldo);
    }
}
