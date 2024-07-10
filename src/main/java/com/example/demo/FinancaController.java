package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financas")
public class FinancaController {

    @Autowired
    private ControlaFinanca controlaFinanca;

    @PostMapping("/adicionar")
    public void adicionarTransacao(@RequestParam String descricao, @RequestParam double valor, @RequestParam boolean receita) {
        controlaFinanca.adicionarTransacao(descricao, valor, receita);
    }

    @DeleteMapping("/apagar/{id}")
    public void apagarTransacao(@PathVariable int id) {
        controlaFinanca.apagarTransacao(id);
    }

    @GetMapping("/listar")
    public List<Transacao> listarTransacoes() {
        return controlaFinanca.listarTransacoes();
    }

    @GetMapping("/relatorio")
    public String visualizarRelatorio() {
        double totalReceitas = 0;
        double totalDespesas = 0;
        List<Transacao> transacoes = controlaFinanca.listarTransacoes();

        for (Transacao transacao : transacoes) {
            if (transacao.isReceita()) {
                totalReceitas += transacao.getValor();
            } else {
                totalDespesas += transacao.getValor();
            }
        }

        double saldo = controlaFinanca.calcularSaldo();

        return "Relatório Financeiro:\n" +
                "Total de Receitas: R$ " + totalReceitas + "\n" +
                "Total de Despesas: R$ " + totalDespesas + "\n" +
                "Saldo Final: R$ " + saldo;
    }
}
