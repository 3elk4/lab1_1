package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

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
}
