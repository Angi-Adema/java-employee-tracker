package labs.pm.app;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import labs.pm.data.Rating;
import labs.pm.data.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import labs.pm.data.Drink;
import labs.pm.data.Food;
import labs.pm.data.ProductManager;
public class Shop {
    public static void main(String[] args) {

        ProductManager pm = new ProductManager(Locale.US);

        pm.reviewProduct(101, Rating.FOUR_STAR, "Nice hot cup of tea");
        pm.reviewProduct(101, Rating.TWO_STAR, "Rather weak tea");
        pm.reviewProduct(101, Rating.FOUR_STAR, "Fine tea");
        pm.reviewProduct(101, Rating.FOUR_STAR, "Good tea");
        pm.reviewProduct(101, Rating.FIVE_STAR, "Perfect tea");
        pm.reviewProduct(101, Rating.THREE_STAR, "Just add some lemon");
        //pm.printProductReport(101);

        pm.createProduct(102, "Coffee", BigDecimal.valueOf(2.99), Rating.NOT_RATED);
        pm.reviewProduct(102, Rating.THREE_STAR, "Coffee was ok");
        pm.reviewProduct(102, Rating.ONE_STAR, "Where is the milk?");
        pm.reviewProduct(102, Rating.FIVE_STAR, "It's perfect with ten spoons of sugar!");
        //pm.printProductReport(102);

        pm.createProduct(103, "Cake", BigDecimal.valueOf(3.99), Rating.NOT_RATED);
        pm.reviewProduct(103, Rating.FIVE_STAR, "Very nice cake");
        pm.reviewProduct(103, Rating.FOUR_STAR, "It good, but I've expected more chocolate");
        pm.reviewProduct(103, Rating.FIVE_STAR, "This cake is perfect!");
        //pm.printProductReport(103);

        pm.createProduct(104, "Cookie", BigDecimal.valueOf(2.99), Rating.NOT_RATED);
        pm.reviewProduct(104, Rating.THREE_STAR, "Just another cookie");
        pm.reviewProduct(104, Rating.THREE_STAR, "Ok");
        //pm.printProductReport(104);

        pm.createProduct(105, "Hot Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED);
        pm.reviewProduct(105, Rating.FOUR_STAR, "Tasty!");
        pm.reviewProduct(105, Rating.FOUR_STAR, "No bad at all");
        //pm.printProductReport(105);

        pm.createProduct(106, "Chocolate", BigDecimal.valueOf(2.50), Rating.NOT_RATED, LocalDate.now().plusDays(3));
        pm.reviewProduct(106, Rating.TWO_STAR, "Too sweet");
        pm.reviewProduct(106, Rating.THREE_STAR, "Better then cookie");
        pm.reviewProduct(106, Rating.TWO_STAR, "Too bitter");
        pm.reviewProduct(106, Rating.ONE_STAR, "I don't get it!");
        //pm.printProductReport(106);

        pm.printProducts((p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal());

    }

}
