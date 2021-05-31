package com.foodmanagement.business.model.menu;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.UUID;

/**
 * Abstract class used for defining item from menu.
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
@AllArgsConstructor
@Data
@NoArgsConstructor
public abstract class MenuItem implements Serializable {

    static final long serialVersionUID = 5312433198603641526L;

    /** Unique identifies of item from menu. */
    UUID id;

    /** Name of item from menu. */
    String name;

    /** Price of item from menu. */
    double price;

    /**
     * Computes the price of item from menu.
     *
     * @return The computed price.
     */
    public abstract double computePrice();
    protected abstract boolean partWellFormed();

    public boolean wellFormed() {
        return id != null && !name.isBlank() && price >= 0.0 && this.partWellFormed();
    }
}
