
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);      //Define localidade padrão US

        Scanner sc = new Scanner(System.in);        // Leitor de input
        Cofrinho cofrinho = new Cofrinho();     // Instancia o repositório de moedas, todas operações são realizadas através deste objeto

        while (true) {
            // Loop Menu interface
            System.out.println("\n===== COFRINHO UNINTER =====");
            System.out.println("1 - Adicionar moeda");
            System.out.println("2 - Remover moeda");
            System.out.println("3 - Listar moedas");
            System.out.println("4 - Calcular total em Real");
            System.out.println("0 - Encerrar");
            System.out.print("Escolha uma opção: ");

            int opcao;

            try {
                opcao = sc.nextInt();
            }

            catch (InputMismatchException e) {
                // Trata input não numérica limpando o buffer e retornando ao menu.
                System.out.println("Entrada inválida. Digite um número.");
                sc.nextLine();
                continue;
            }

            if (opcao == 0) {
                // Break Loop e encerra o programa
                System.out.println("Encerrando cofrinho...");
                break;
            }

            switch (opcao) {

                case 1:
                    // Adicionar moedas
                    adicionarMoeda(sc, cofrinho);
                    break;

                case 2:
                    // Remover moedas
                    removerMoeda(sc, cofrinho);
                    break;

                case 3:
                    // Listar moedas
                    cofrinho.listar();
                    break;

                case 4:
                    // Calcula Total em Real
                    BigDecimal total = cofrinho.totalConvertido();
                    System.out.println(String.format(Locale.US,"Total convertido para Real: R$ %.2f", total));
                    break;

                default:
                    // Retorna ao Menu
                    System.out.println("Opção inválida!");
            }
        }

        sc.close();     //Close Scanner
    }

    public static void adicionarMoeda(Scanner sc, Cofrinho cofrinho) {

        System.out.println("\n1 - Real\n2 - Dólar\n3 - Euro");
        System.out.print("Escolha o tipo de moeda: ");

        int tipo;
        try {
            tipo = sc.nextInt();        // Lê tipo com sc.nextInt() dentro de try/catch para evitar InputMismatchException
        } catch (InputMismatchException e) {
            System.out.println("Tipo inválido!");
            sc.nextLine();
            return;
        }

        System.out.print("Digite o valor: ");
        BigDecimal valor;

        try {
            valor = sc.nextBigDecimal(); // Trata imprecisão de ponto flutuante com BigDecimal em caso de erro
        } catch (Exception e) {
            System.out.println("Valor inválido!");
            sc.nextLine();
            return;
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            // Impede valor <= 0
            System.out.println("Valor deve ser maior que zero.");
            return;
        }

        Moeda moeda = switch (tipo) {       // Cria objeto Moeda
            case 1 -> new Real(valor);
            case 2 -> new Dolar(valor);
            case 3 -> new Euro(valor);
            default -> {
                System.out.println("Tipo de moeda inválido!");
                yield null;
            }
        };

        if (moeda != null)      // Verifica se condição for true chama adicionar moeda
            cofrinho.adicionar(moeda);
    }

    public static void removerMoeda(Scanner sc, Cofrinho cofrinho) {

        System.out.println("\nQual moeda deseja Remover?");
        System.out.println("1 - Real\n2 - Dólar\n3 - Euro");
        System.out.print("Escolha o tipo: ");

        int tipo;
        try {
            tipo = sc.nextInt();
        } catch (InputMismatchException e) {        // Lê tipo com tratamento InputMismatchException
            System.out.println("Entrada inválida!");
            sc.nextLine();
            return;
        }

        System.out.print("Digite o valor exato da moeda a remover: ");
        BigDecimal valor;

        try {
            valor = sc.nextBigDecimal();        // Trata imprecisão de ponto flutuante com BigDecimal em caso de erro
        } catch (Exception e) {
            System.out.println("Valor inválido!");
            sc.nextLine();
            return;
        }

        Moeda moeda = switch (tipo) {       // Instancia Moeda correspondente
            case 1 -> new Real(valor);
            case 2 -> new Dolar(valor);
            case 3 -> new Euro(valor);
            default -> {
                System.out.println("Tipo inválido!");
                yield null;
            }
        };

        if (moeda != null)      // Verifica se condição for true chama remover moeda
            cofrinho.remover(moeda);
    }
}
