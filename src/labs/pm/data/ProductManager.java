package labs.pm.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;


public class ProductManager {
    private Map<Product, List<Review>> products = new HashMap<>();

    private ResourceFormatter formatter;

    private static Map<String, ResourceFormatter> formatters =
            Map.of("en-GB", new ResourceFormatter(Locale.UK),
                    "en-US", new ResourceFormatter(Locale.US),
                    "fr-FR", new ResourceFormatter(Locale.FRANCE),
                    "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
                    "zh-CN", new ResourceFormatter(Locale.CHINA));

    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }

    public ProductManager(String languageTag) {
        changeLocale(languageTag);
    }

    public void changeLocale(String languageTag) {
        formatter = formatters.getOrDefault(languageTag, formatters.get("en-US"));
    }
    // Returning the string or key of the HashMap available locales.
    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        // Here we add "Product" in front of "product" so that we are using the local variable for product rather than the instance variable.
        Product product = new Food(id, name, price, rating, bestBefore);
        // putIfAbsent checks if the product is already present or not and adds it to the new ArrayList. If we just use a simple
        // put statement, it will replace the array of items each time. We do not want to replace an existing array of reviews with a new one
        // so we use putIfAbsent.
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        return reviewProduct(findProduct(id), rating, comments);
    }

    // This method is designed to create a new Review object and applies a rating based on a Rating value supplied as part
    // of the review. It is declared to return Product rather than be defined as void because Product is immutable. The method
    // in the Rateable interface called applyRating is designed to create a new Product object and set number of stars.
    public Product reviewProduct(Product product, Rating rating, String comments) {

        // Here we locate the review in the HashMap that corresponds to the product and get from it the list of reviews.
        List<Review> reviews = products.get(product);

        // We must remove the current reviews entry as we will recreate it with the new HashMap reviews method.
        // Since map does not have a way of replacing values, it must be removed and then recreated.
        products.remove(product, reviews);

        reviews.add(new Review(rating, comments));

        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating().ordinal();
        }

        product = product.applyRating(Rateable.convert(Math.round((float)sum/reviews.size())));
        products.put(product, reviews);
        return product;
    }

    public Product findProduct(int id) {
        Product result = null;

        for (Product product : products.keySet()) {
            if (product.getId() == id) {
                result = product;
                break;
            }
        }
        return result;
    }

    public void printProductReport(int id) {
        printProductReport(findProduct(id));
    }

    public void printProductReport(Product product) {
        List<Review> reviews = products.get(product);

        Collections.sort(reviews);

        StringBuilder txt = new StringBuilder();

        txt.append(formatter.formatProduct(product));
        txt.append("\n");

        for (Review review : reviews) {
            txt.append(formatter.formatReview(review));
            txt.append("\n");

        }

        if (reviews.isEmpty()) {
            txt.append(formatter.getText("no.reviews"));
            txt.append("\n");
        }
        System.out.println(txt);
    }

    public void printProducts(Comparator<Product> sorter) {
        List<Product> productList = new ArrayList<>(products.keySet());
        productList.sort(sorter);
        StringBuilder txt = new StringBuilder();

        for (Product product : productList) {
            txt.append(formatter.formatProduct(product));
            txt.append('\n');
        }
        System.out.println(txt);
    }

    public static class ResourceFormatter {

        // These declarations were moved into this nested class from the ProductManager outer class.
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("labs.pm.data.resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }
        // We format the product.
        private String formatProduct(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(product.getBestBefore()));
        }
        // Format the review.
        private String formatReview(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments());
        }
        // Get any other text from the resource bundle (third item listed in resources.properties)
        private String getText(String key) {
            return resources.getString(key);
        }
    }

}

