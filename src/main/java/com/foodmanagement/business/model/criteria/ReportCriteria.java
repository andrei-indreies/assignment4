package com.foodmanagement.business.model.criteria;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/**
 * A simple object containing possible criteria used for administrator report.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder
public class ReportCriteria {

    // data for 'time internal of the orders' report; data can be null
    int startHour;

    int endHour;

    // data for 'the products ordered more than a specified number of times so far' report;
    // data can be null
    int numberOfTimes;

    // data for 'the clients that have ordered more than a specified number of times and the value
    // of the order was higher than a specified amount' report;
    // data can be null
    int clientOrdersNumber;

    double orderAmount;

    // data for 'the products ordered within a specified day with the number of times they have
    // been ordered' report; data can be null
    LocalDateTime orderDate;
}
