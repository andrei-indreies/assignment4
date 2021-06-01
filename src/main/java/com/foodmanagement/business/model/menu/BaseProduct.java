package com.foodmanagement.business.model.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

/**
 * Concrete implementation for an item menu.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseProduct extends MenuItem implements Serializable {

    static final long serialVersionUID = -8865083732695195668L;

    /**
     * The title of the menu item.
     */
    String title;

    /**
     * The rating of the menu item.
     */
    double rating;

    /**
     * The calories of the menu item.
     */
    int calories;

    /**
     * The proteins of the menu item.
     */
    int proteins;

    /**
     * The fats of the menu item.
     */
    int fats;

    /**
     * The sodium of the menu item.
     */
    int sodium;

    @Override
    public double computePrice() {
        return this.getPrice();
    }

    @Override
    protected boolean partWellFormed() {
        return (rating >= 1.0 && rating <= 10.0) && proteins >= 0 && calories >= 0 && sodium >= 0 && fats >= 0;
    }

    @Builder(toBuilder = true)
    public BaseProduct(final UUID id, final String name, final double price, final String title,
                       final double rating, final int calories, final int proteins,
                       final int fats, final int sodium) {
        super(id, name, price);
        this.title = title;
        this.rating = rating;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.sodium = sodium;
    }

    @Override
    public String toString() {
        return String.join(",", title, String.valueOf(rating), String.valueOf(calories), String.valueOf(proteins),
                String.valueOf(fats), String.valueOf(sodium), String.valueOf(price));
    }
}
