package com.foodmanagement.business.model.menu;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.UUID;

/**
 * Concrete implementation for an item menu.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Order implements Serializable {

    static final long serialVersionUID = -1482131193835611733L;

    /** Unique identifier of the order. */
    UUID id;

    /** Unique identifier of the client that made the order. */
    UUID clientId;

    /** The order date. */
    LocalDateTime orderDate;

    @Override
    public int hashCode() {
        return Objects.hash(id, clientId, orderDate);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (this == o) return true;
        Order order = (Order) o;
        return id == order.id && clientId == order.clientId && Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public String toString() {
        return String.format(
                "Order (%s) for client: %s at %s",
                id,
                clientId,
                orderDate.toString()
        );
    }

    public boolean wellFormed() {
        return id != null && clientId != null && orderDate != null;
    }

}
