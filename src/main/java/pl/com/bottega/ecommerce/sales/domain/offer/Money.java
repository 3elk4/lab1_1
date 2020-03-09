package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    private String currency;
    private BigDecimal denomination;

    public Money(String currency, BigDecimal denomination){
        this.currency = currency;
        this.denomination = denomination;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getDenomination(){
        return denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, denomination);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Money other = (Money) obj;
        return Objects.equals(currency, other.currency)
                && Objects.equals(denomination, other.denomination);
    }
}
