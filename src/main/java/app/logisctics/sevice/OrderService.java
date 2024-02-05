package app.logisctics.sevice;

import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import org.apache.coyote.BadRequestException;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrdersByDeliveryDateAndDestination(String deliveryDate,
                                                            String destination) throws ParseException;

    List<OrderDto> createOrders(List<CreateOrderDto> createOrderDtos) throws BadRequestException;

    void cancelOrders(List<Long> orderIds);
}
