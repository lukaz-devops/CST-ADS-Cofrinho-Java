import java.math.BigDecimal;
import java.util.ArrayList;

public class Cofrinho {

    private ArrayList<Moeda> moedas = new ArrayList<>();        // Coleção que armazena todas as moedas

    public void adicionar(Moeda moeda) {        // Insere uma moeda na lista e imprime confirmação
        moedas.add(moeda);
        System.out.println("Moeda adicionada com sucesso!");
    }

    public void remover(Moeda moeda) {      // Remove a moeda por tipo+valor
        if (moedas.remove(moeda)) {
            System.out.println("Moeda removida com sucesso!");
        }
        else {
            System.out.println("Moeda não encontrada!");
        }
    }

    public void listar() {      // Mostra moedas chamando info() em cada uma individualmente
        if (moedas.isEmpty()) {     // Verifica se há itens no cofrinho, true imprime moedas no cofrinho, se não imprime cofrinho vazio
            System.out.println("O cofrinho está vazio.");
            return;
        }

        System.out.println("\n===== Moedas no Cofrinho =====");
        for (Moeda m : moedas) {
            m.info();      // Metodo info() é polimorfico cada subclasse imprime sua própria representação formatada
        }
    }

    public BigDecimal totalConvertido() {       // Soma todas as conversões
        BigDecimal total = BigDecimal.ZERO;

        for (Moeda m : moedas) {
            total = total.add(m.converter());       // m.converter() é polimórfico: Real.converter() retorna o próprio valor; Dolar.converter() e Euro.converter() multiplicam pelo TAXA (BigDecimal)
        }

        return total.setScale(2, BigDecimal.ROUND_HALF_UP);     // retorna total.setScale(2, RoundingMode.HALF_UP) ou (BigDecimal.ROUND_HALF_UP), garantindo 2 casas decimais.
    }
}
