import java.util.Scanner;
import java.text.DecimalFormat;

public class Banco {
    private static final DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar a tela.");
        }
    }

    public static void mostrarMenu(double saldo) {
        System.out.println("\n==================================================================");
        System.out.println("============================= MENU ===============================");
        System.out.printf("%-15s %s%n", "NOME:", "Adrian Patrício da Silva");
        System.out.printf("%-15s %s%n", "TIPO DE CONTA:", "CORRENTE");
        System.out.printf("%-15s %s%n", "SALDO INICIAL:", df.format(saldo));
        System.out.println("==================================================================");
        System.out.println("1 - Ver saldo");
        System.out.println("2 - Depositar");
        System.out.println("3 - Sacar");
        System.out.println("4 - Prestações");
        System.out.println("0 - Sair");
        System.out.println("==================================================================");
        System.out.print("Escolha uma opção: ");
    }

    public static void verSaldo(double saldo) {
        System.out.println("Seu saldo é: " + df.format(saldo));
    }

    public static double depositar(double saldo, Scanner input) {
        System.out.print("Digite o valor que deseja depositar: ");
        double deposito = input.nextDouble();

        saldo += deposito;

        System.out.println("Você fez um depósito de " + df.format(deposito) + ". SALDO ATUALIZADO!");

        return saldo;
    }

    public static double sacar(double saldo, Scanner input) {
        System.out.print("Digite o valor que deseja sacar: ");
        double saque = input.nextDouble();

        if (saque <= saldo) {
            saldo -= saque;
            
            System.out.println("Você fez um saque de " + df.format(saque) + ". SALDO ATUALIZADO!");
        } else {
            System.out.println("O valor que deseja sacar é maior que seu saldo...");
        }

        return saldo;
    }

    public static double prestacao(double saldo, Scanner input) {
        System.out.print("Digite o valor de uma prestação pendente (mensal): ");
        double prestacao = input.nextDouble();

        int mesesPagos = 0;

        for (; saldo >= prestacao; mesesPagos++) {
            saldo -= prestacao;

            System.out.printf("Mês %d: Pagou R$ %.2f | Saldo restante: R$ %.2f%n", mesesPagos + 1, prestacao, saldo);
        }

        System.out.println("--------------------------------------------");
        System.out.println("Foram pagas " + mesesPagos + " prestações.");
        System.out.printf("Saldo final: R$ %.2f%n", saldo);

        return saldo;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option = -1;
        double saldo = 1000.0;

        do {
            clearScreen();
            mostrarMenu(saldo);
            option = input.nextInt();

            switch (option) {
                case 1 -> verSaldo(saldo);
                case 2 -> saldo = depositar(saldo, input);
                case 3 -> saldo = sacar(saldo, input);
                case 4 -> saldo = prestacao(saldo, input);
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida...");
            }

            if (option != 0) {
                System.out.println("\nPressione Enter para continuar...");
                input.nextLine();
                input.nextLine();
            }

        } while (option != 0);

        input.close();
    }
}