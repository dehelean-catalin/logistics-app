package app.logisctics.sevice;

import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface OrderService {
    public List<OrderDto> getAllOrders();
    public List<OrderDto> createOrders(List<CreateOrderDto> createOrderDtos) throws BadRequestException;

    public void cancelOrders(List<Long> orderIds);
}
