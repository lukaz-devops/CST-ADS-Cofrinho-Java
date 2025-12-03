import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;
import java.util.Objects;

public abstract class Moeda {

    protected BigDecimal valor;     // Guarda o valor da moeda

    public Moeda(BigDecimal valor) {
        this.valor = valor;
    }

    public abstract void info();        // Cada subclasse implementa para exibir tipo+valor formatado
    public abstract BigDecimal converter();     // Cada subclasse converte seu valor para Real

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Moeda)) return false;

        Moeda outra = (Moeda) obj;

        // Retorna True se mesma classe e mesmo valor == 0
        return this.getClass().equals(outra.getClass()) &&
                this.valor.compareTo(outra.valor) == 0;
    }

    @Override
    public int hashCode() {     // Implementado com Objects.hash(this.getClass(), valor) para consistência com equals
        return Objects.hash(this.getClass(), valor);
    }
}

// REAL

class Real extends Moeda {

    public Real(BigDecimal valor) {
        super(valor);
    }

    @Override
    public void info() {
        System.out.println(String.format(Locale.US,
                "Real - R$ %.2f", valor));
    }

    @Override
    public BigDecimal converter() {
        return valor;
    }
}

// DÓLAR

class Dolar extends Moeda {

    private static final BigDecimal TAXA = new BigDecimal("5.60");

    public Dolar(BigDecimal valor) {
        super(valor);
    }

    @Override
    public void info() {
        System.out.println(String.format(Locale.US,
                "Dólar - US$ %.2f", valor));
    }

    @Override
    public BigDecimal converter() {
        return valor.multiply(TAXA).setScale(2, RoundingMode.HALF_UP);
    }
}

// EURO

class Euro extends Moeda {

    private static final BigDecimal TAXA = new BigDecimal("6.20");

    public Euro(BigDecimal valor) {
        super(valor);
    }

    @Override
    public void info() {
        System.out.println(String.format(Locale.US,
                "Euro - € %.2f", valor));
    }

    @Override
    public BigDecimal converter() {
        return valor.multiply(TAXA).setScale(2, RoundingMode.HALF_UP);
    }
}