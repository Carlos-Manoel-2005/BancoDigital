package application;

import entities.*;
import exception.SaldoInsuficienteException;
import exception.ValorInvalidoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final Banco banco = new Banco();

    public static void exibirMenuPrincipal() {
        String menu = """
        +===================================+
        |         BANCO DIGITAL G-BANK      |
        +===================================+
        | 1. Criar Nova Conta               |
        | 2. Acessar Conta Existente        |
        |                                   |
        | 0. Sair do Sistema                |
        +===================================+
        """;
        System.out.println(menu);
        System.out.print("Digite a opção desejada: ");
    }

    public static void exibirMenuOperacoes() {
        String menu = """
        +===================================+
        |         MENU DA CONTA             |
        +===================================+
        | 1. Depositar                      |
        | 2. Sacar                          |
        | 3. Transferir                     |
        | 4. Exibir Extrato                 |
        |                                   |
        | 9. Voltar ao Menu Principal       |
        +===================================+
        """;
        System.out.println(menu);
        System.out.print("Digite a opção desejada: ");
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        banco.setName("Banco Digital G-Bank");
        banco.setContas(new ArrayList<>());

        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            String input = sc.nextLine();

            if (!input.matches("\\d+")) {
                System.out.println("--- Erro: Opção inválida. Digite um número. ---\n");
                continue;
            }
            opcao = Integer.parseInt(input);

            switch (opcao) {
                case 1 -> criarNovaConta();
                case 2 -> acessarContaExistente();
                case 0 -> System.out.println("\nObrigado por utilizar o " + banco.getName() + "!");
                default -> System.out.println("--- Opção inválida. Tente novamente. ---");
            }
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                sc.nextLine();
            }
        }
        sc.close();
    }

    private static void operarConta(Conta contaAtiva) {
        int opcao = -1;
        while (opcao != 9) {
            exibirMenuOperacoes();
            String input = sc.nextLine();

            if (!input.matches("\\d+")) {
                System.out.println("--- Erro: Opção inválida. Digite um número. ---\n");
                continue;
            }
            opcao = Integer.parseInt(input);

            try {
                switch (opcao) {
                    case 1 -> depositar(contaAtiva);
                    case 2 -> sacar(contaAtiva);
                    case 3 -> transferir(contaAtiva);
                    case 4 -> contaAtiva.imprimirExtratoDaConta();
                    case 9 -> System.out.println("\nRetornando ao menu principal...");
                    default -> System.out.println("--- Opção inválida. Tente novamente. ---");
                }
            } catch (ValorInvalidoException | SaldoInsuficienteException e) {
                System.out.println("--- ERRO NA OPERAÇÃO: " + e.getMessage() + " ---");
            }
            if (opcao != 9) {
                System.out.println("\nPressione Enter para continuar...");
                sc.nextLine();
            }
        }
    }

    private static void criarNovaConta() {
        System.out.println("\n--- Criação de Nova Conta ---");
        System.out.print("Digite o nome completo do titular: ");
        String nomeCliente = sc.nextLine();

        if (nomeCliente.trim().isEmpty()) {
            System.out.println("--- Erro: O nome do titular não pode ser vazio. ---");
            return;
        }

        Cliente novoCliente = new Cliente();
        novoCliente.setName(nomeCliente);

        System.out.print("Qual tipo de conta deseja criar? (1 - Corrente / 2 - Poupança): ");
        String tipoContaInput = sc.nextLine();

        Conta novaConta;
        if (tipoContaInput.equals("1")) {
            novaConta = new ContaCorrente(novoCliente);
        } else if (tipoContaInput.equals("2")) {
            novaConta = new ContaPoupanca(novoCliente);
        } else {
            System.out.println("--- Erro: Tipo de conta inválido. Operação cancelada. ---");
            return;
        }

        banco.getContas().add(novaConta);
        System.out.println("\n--- Conta criada com sucesso! ---");
        System.out.printf("Titular: %s\nAgência: %d\nConta N°: %d\n",
                novaConta.getCliente().getName(), novaConta.getAgencia(), novaConta.getNumeroConta());
    }

    private static void acessarContaExistente() {
        if (banco.getContas().isEmpty()) {
            System.out.println("\n--- Não existem contas cadastradas no banco. Crie uma conta primeiro. ---");
            return;
        }

        System.out.println("\n--- Acesso à Conta ---");
        System.out.println("Por favor, selecione uma conta para operar:");
        List<Conta> contas = banco.getContas();
        for (int i = 0; i < contas.size(); i++) {
            Conta conta = contas.get(i);
            System.out.printf("%d - Titular: %s | Ag: %d | Conta: %d\n",
                    i + 1, conta.getCliente().getName(), conta.getAgencia(), conta.getNumeroConta());
        }
        System.out.println("0 - Voltar");

        System.out.print("Sua escolha: ");
        String input = sc.nextLine();
        if (input.matches("\\d+")) {
            int escolha = Integer.parseInt(input);
            if (escolha == 0) {
                return;
            }
            if (escolha > 0 && escolha <= contas.size()) {
                Conta contaAtiva = contas.get(escolha - 1);
                System.out.printf("\nConta de %s selecionada.\n", contaAtiva.getCliente().getName());
                operarConta(contaAtiva);
                return;
            }
        }
        System.out.println("--- Escolha inválida. Retornando ao menu principal. ---");
    }

    private static void depositar(Conta conta) throws ValorInvalidoException {
        System.out.print("Digite o valor do depósito: ");
        String valorInput = sc.nextLine();
        if (!valorInput.matches("\\d+(\\.\\d+)?")) {
            throw new ValorInvalidoException("Formato de valor inválido.");
        }
        double valor = Double.parseDouble(valorInput);
        conta.depositar(valor);
    }

    private static void sacar(Conta conta) throws ValorInvalidoException, SaldoInsuficienteException {
        System.out.print("Digite o valor do saque: ");
        String valorInput = sc.nextLine();
        if (!valorInput.matches("\\d+(\\.\\d+)?")) {
            throw new ValorInvalidoException("Formato de valor inválido.");
        }
        double valor = Double.parseDouble(valorInput);
        conta.sacar(valor);
    }

    private static void transferir(Conta contaOrigem) throws ValorInvalidoException, SaldoInsuficienteException {
        System.out.print("Digite o NÚMERO da conta de destino: ");
        String numContaDestinoInput = sc.nextLine();
        if (!numContaDestinoInput.matches("\\d+")) {
            throw new ValorInvalidoException("Número da conta de destino inválido.");
        }
        int numContaDestino = Integer.parseInt(numContaDestinoInput);

        Conta contaDestino = null;
        for (Conta c : banco.getContas()) {
            if (c.getNumeroConta() == numContaDestino) {
                contaDestino = c;
                break;
            }
        }

        if (contaDestino == null) {
            throw new ValorInvalidoException("Conta de destino não encontrada.");
        }
        if (contaDestino.getNumeroConta().equals(contaOrigem.getNumeroConta())) {
            throw new ValorInvalidoException("Não é possível transferir para a mesma conta.");
        }

        System.out.print("Digite o valor da transferência: ");
        String valorInput = sc.nextLine();
        if (!valorInput.matches("\\d+(\\.\\d+)?")) {
            throw new ValorInvalidoException("Formato de valor inválido.");
        }
        double valor = Double.parseDouble(valorInput);
        contaOrigem.transferencia(valor, contaDestino);
    }
}