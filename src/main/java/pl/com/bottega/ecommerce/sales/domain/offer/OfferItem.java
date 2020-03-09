/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    // product
    private Product product;

    private int quantity;

    private Money totalCost;

    // discount
    private String discountCause;

    private Money discount;

    public OfferItem(String productId, Money productPrice, String productName, Date productSnapshotDate, String productType,
            int quantity) {
        this(productId, productPrice, productName, productSnapshotDate, productType, quantity, null, null, null);
    }

    public OfferItem(String productId, Money productPrice, String productName, Date productSnapshotDate, String productType,
            int quantity, Money discount, String discountCause, String currency) {
        product = new Product(productId, productPrice, productName, productSnapshotDate, productType);

        this.quantity = quantity;
        this.discount = discount;
        this.discountCause = discountCause;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.add(discount.getDenomination());
        }
        this.totalCost = new Money(currency, product.getProductPrice().
                                                    getDenomination().multiply(new BigDecimal(quantity)).
                                                    subtract(discountValue));
    }

    public Product getProduct() {
        return product;
    }

    public Money getDiscount() { return discount; }

    public String getDiscountCause() {
        return discountCause;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getTotalCost() { return totalCost; }

    @Override
    public int hashCode() {
        return Objects.hash(product, discount, discountCause, quantity);
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
        OfferItem other = (OfferItem) obj;
        return Objects.equals(product, other.product)
                && Objects.equals(discount, other.discount)
                && Objects.equals(discountCause, other.discountCause)
                && quantity == other.quantity;
    }

    /**
     *
     * @param other
     * @param delta
     *            acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (product == null) {
            if (other != null) {
                return false;
            }
        } else if (!product.equals(other.product)) {
            return false;
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getDenomination().compareTo(other.totalCost.getDenomination()) > 0) {
            max = totalCost.getDenomination();
            min = other.totalCost.getDenomination();
        } else {
            max = other.totalCost.getDenomination();
            min = totalCost.getDenomination();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}
