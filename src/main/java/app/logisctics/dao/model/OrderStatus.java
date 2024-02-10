package app.logisctics.dao.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum OrderStatus {
    NEW,
    DELIVERING,
    DELIVERED,
    CANCELED,
    ARCHIVED,
    ;

    public static final Map<OrderStatus, List<OrderStatus>> transitions = new HashMap<>();

    static {
        transitions.put(NEW, List.of(DELIVERING, CANCELED,ARCHIVED));
        transitions.put(DELIVERING, List.of(DELIVERED, CANCELED, ARCHIVED));
        transitions.put(DELIVERED, List.of(ARCHIVED));
        transitions.put(CANCELED, List.of(NEW, ARCHIVED));
        transitions.put(ARCHIVED, Collections.emptyList());
    }

}
