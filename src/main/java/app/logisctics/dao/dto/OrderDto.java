package app.logisctics.dao.dto;

import app.logisctics.dao.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderDto {
    private Long id;
    private Long deliveryDate;
    private Long lastUpdated;
    private OrderStatus orderStatus;
    private Long destinationId;
}
