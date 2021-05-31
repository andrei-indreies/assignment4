package com.foodmanagement.business.model.criteria;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * A simple object containing possible criteria used for client's search.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder
public class SearchCriteria {

    String keyword;

    double rating;

    int calories;

    int proteins;

    int fats;

    int sodium;

    double price;
}
