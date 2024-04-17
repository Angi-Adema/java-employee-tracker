package labs.pm.data;

import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_UP;
import java.time.LocalDate;
import java.util.Objects;
import static labs.pm.data.Rating.*;

/**
 *
 * @author opc
 */
// We made this class abstract because Product is vague in terms of the actual products of food and drink. Making this abstract
// makes is so that we can no longer instantiate Product, we must instantiate Food or Drink directly.
public abstract class Product {

    private final int id;
    private final String name;
    private final BigDecimal price;
    private Rating rating;

    // Removing "public" in front of the constructors limits these constructors to only be visible to members of the same package.
    Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    Product(int id, String name, BigDecimal price) {
        this(id, name, price, Rating.NOT_RATED);

    }

    /**
     * A constant that defines a {@link java.math.BigDecimal BigDecimal} value
     * of the discount rate
     * <br>
     * Discount rate is 10%
     */
    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    /**
     * Calculates discount based on a product price and
     * {@link DISCOUNT_RATE discount rate}
     *
     * @return a {@link java.math.BigDecimal BigDecimal} value of the discount
     */
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    // This method is actually overriding the default method from the Rateable interface. So we add "@Override" to avoid accidental
    // changes to this method.
    @Override
    public Rating getRating() {
        return rating;
    }
    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    // Here we override the toString method in the Object class so that now when printed in the Shop class, it prints the data and not the memory reference.
    @Override
    public String toString() {
        return id + ", " + name + ", " + price + ", " + getDiscount() + ", " + rating.getStars()+" "+getBestBefore();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
//        if (obj != null && getClass() == obj.getClass()) {
// The below condition only looks at if the two are products. The commented out above stmt compares classes which IS different.
        if (obj instanceof Product) {
            final Product other = (Product) obj;
            // Since we are using HashMap, we have to be sure that there are no two products with the same id and name.
            // We only compare ids to be sure we do not have two of the same.
            return this.id == other.id; // && Objects.equals(this.name, other.name);
        }
        return false;
    }

}

