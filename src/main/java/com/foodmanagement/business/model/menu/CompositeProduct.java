package com.foodmanagement.business.model.menu;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Concrete implementation for an item menu.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@EqualsAndHashCode(callSuper = true)
public class CompositeProduct extends MenuItem
        implements Serializable {

    List<MenuItem> menuItems;

    static final long serialVersionUID = -6013113430861035744L;

    @Override
    public double computePrice() {
        return menuItems.stream()
                .mapToDouble(MenuItem::computePrice)
                .sum();
    }

    @Override
    public boolean partWellFormed() {
        return !menuItems.isEmpty() && menuItems.stream().allMatch(MenuItem::wellFormed);
    }

    @Builder(toBuilder = true)
    public CompositeProduct(final UUID id, final String name,
                            final double price, final List<MenuItem> menuItems) {
        super(id, name, price);
        this.menuItems = menuItems;
    }

}
