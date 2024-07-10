package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FinancaWebController {

    @Autowired
    private ControlaFinanca controlaFinanca;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/adicionar")
    public String adicionar() {
        return "adicionar";
    }

    @PostMapping("/adicionar")
    public String adicionarTransacao(@RequestParam String descricao, @RequestParam double valor, @RequestParam boolean receita, Model model) {
        controlaFinanca.adicionarTransacao(descricao, valor, receita);
        model.addAttribute("mensagem", "Transação adicionada com sucesso!");
        return "adicionar";
    }

    @GetMapping("/listar")
    public String listarTransacoes(Model model) {
        model.addAttribute("transacoes", controlaFinanca.listarTransacoes());
        return "listar";
    }

    @GetMapping("/relatorio")
    public String visualizarRelatorio(Model model) {
        double totalReceitas = 0;
        double totalDespesas = 0;
        for (Transacao transacao : controlaFinanca.listarTransacoes()) {
            if (transacao.isReceita()) {
                totalReceitas += transacao.getValor();
            } else {
                totalDespesas += transacao.getValor();
            }
        }
        double saldo = controlaFinanca.calcularSaldo();
        model.addAttribute("totalReceitas", totalReceitas);
        model.addAttribute("totalDespesas", totalDespesas);
        model.addAttribute("saldo", saldo);
        return "relatorio";
    }
}
