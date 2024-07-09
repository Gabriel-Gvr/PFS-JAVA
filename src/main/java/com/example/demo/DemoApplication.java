package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private ControlaFinanca controlaFinanca;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			controlaFinanca.criaTabela(); // Criar a tabela no banco de dados

			Scanner scanner = new Scanner(System.in);

			while (true) {
				System.out.println("1. Adicionar Receita");
				System.out.println("2. Adicionar Despesa");
				System.out.println("3. Listar Transações");
				System.out.println("4. Visualizar Relatório");
				System.out.println("5. Apagar Transação");
				System.out.println("6. Sair");
				System.out.print("Escolha uma opção: ");
				int opcao = scanner.nextInt();
				scanner.nextLine(); // Consumir nova linha

				if (opcao == 1) {
					System.out.print("Descrição da Receita: ");
					String descricao = scanner.nextLine();
					System.out.print("Valor da Receita: ");
					double valor = scanner.nextDouble();
					controlaFinanca.adicionarTransacao(descricao, valor, true);
				} else if (opcao == 2) {
					System.out.print("Descrição da Despesa: ");
					String descricao = scanner.nextLine();
					System.out.print("Valor da Despesa: ");
					double valor = scanner.nextDouble();
					controlaFinanca.adicionarTransacao(descricao, valor, false);
				} else if (opcao == 3) {
					List<Transacao> transacoes = controlaFinanca.listarTransacoes();
					if (transacoes.isEmpty()) {
						System.out.println("Não há transações para listar.");
					} else {
						for (Transacao transacao : transacoes) {
							System.out.println(transacao);
						}
					}
				} else if (opcao == 4) {
					controlaFinanca.visualizarRelatorio();
				} else if (opcao == 5) {
					System.out.print("Digite o ID da transação a ser apagada: ");
					int id = scanner.nextInt();
					controlaFinanca.apagarTransacao(id);
				} else if (opcao == 6) {
					break;
				} else {
					System.out.println("Opção inválida. Tente novamente.");
				}
			}
			scanner.close();
		};
	}


}
