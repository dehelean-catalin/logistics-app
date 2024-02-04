package app.logisctics.dao.converter;

import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class OrderConverter {

    public static Order createDtoToModel(Long deliveryDate, Destination destination){
        return Order.builder()
                .destination(destination)
                .orderStatus(OrderStatus.NEW)
                .deliveryDate(deliveryDate)
                .lastUpdated(System.currentTimeMillis())
                .build();
    }

    public static List<OrderDto> modelListToDtoList(List<Order> orders){
        return orders.stream()
                .map(OrderConverter::modelToDto)
                .toList();
    }

    public static OrderDto modelToDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .destinationId(order.getDestination().getId())
                .orderStatus(order.getOrderStatus())
                .deliveryDate(order.getDeliveryDate())
                .lastUpdated(order.getLastUpdated())
                .build();
    }
}
