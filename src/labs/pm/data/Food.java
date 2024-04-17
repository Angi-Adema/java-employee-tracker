package labs.pm.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class Food extends Product {

    private LocalDate bestBefore;

    // To initialize the superclass properties we must invoke the superclass constructor using "super".
    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    public LocalDate getBestBefore() {
        return bestBefore;
    }

    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.equals(LocalDate.now())) ? super.getDiscount() : BigDecimal.ZERO; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Product applyRating(Rating newRating) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        // These are attributes of Product as we have to retrieve this info via the getter methods.
        return new Food(getId(), getName(), getPrice(), newRating, bestBefore);
    }
}

