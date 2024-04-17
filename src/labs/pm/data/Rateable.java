package labs.pm.data;

// This is added will throw an error if a second abstract method is added as this will no longer be a functional interface.
// The "@FunctionalInterface" is just a fail safe to be sure another abstract method is not added.
@FunctionalInterface
public interface Rateable<T> {

    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;
    // public and abstract are added by default to an interface method so we do not have to explicitly add these.
    public abstract T applyRating(Rating rating);

    public default T applyRating(int stars) {
        return applyRating(Rateable.convert(stars));
    }

    public default Rating getRating() {
        return DEFAULT_RATING;
    }

    // convert take the rating number of stars and matches or converts it to the Rating enum object.
    public static Rating convert(int stars) {
        return (stars >= 0 && stars <= 5) ? Rating.values()[stars] : DEFAULT_RATING;
    }
}

