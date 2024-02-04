package app.logisctics.sevice;

import app.logisctics.config.CompanyInfo;
import app.logisctics.dao.converter.OrderConverter;
import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import app.logisctics.repository.DestinationRepository;
import app.logisctics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;

    @Override
    public List<OrderDto> getAllOrders() {

        List<Order> orders = orderRepository.findAll();
        return OrderConverter.modelListToDtoList(orders);
    }

    @Override
    public List<OrderDto> createOrders(List<CreateOrderDto> createOrderDtos) throws BadRequestException {
        Map<Long, Destination> destinationMap = destinationRepository.findAll().stream()
                .collect(Collectors.toMap(Destination::getId, Function.identity()));

        validateCreateOrderDto(createOrderDtos, destinationMap.keySet());

        List<Order> ordersToSave = new ArrayList<>();

        createOrderDtos.forEach((order)->  {
           ordersToSave.add(OrderConverter.createDtoToModel(order.getDeliveryDate(),
                   destinationMap.get(order.getDestinationId())));
        });

        return OrderConverter.modelListToDtoList(orderRepository.saveAll(ordersToSave));
    }

    @Override
    public void cancelOrders(List<Long> orderIds) {
        List<Order> orders = orderRepository.findAllById(orderIds);

        for (Order order: orders){
            if(canCancelOrder(order.getOrderStatus())){
                order.setOrderStatus(OrderStatus.CANCELED);
                order.setLastUpdated(System.currentTimeMillis());
            }
        }

        orderRepository.saveAll(orders);

    }


    private void validateCreateOrderDto(List<CreateOrderDto> createOrderDtos,
                                        Set<Long> destinationIds) throws BadRequestException {
        StringBuilder errors = new StringBuilder();

        for (int i=0; i< createOrderDtos.size();i++){

            CreateOrderDto createOrderDto = createOrderDtos.get(i);

            if(!destinationIds.contains(createOrderDto.getDestinationId())){
                errors.append(String.format("Destination with id: %d is invalid for order: %d\n",
                        createOrderDto.getDestinationId(), i));
            }

            if(createOrderDto.getDeliveryDate() < new CompanyInfo().getCurrentDateAsLong()){
                errors.append(String.format("Delivery date with id: %d is invalid for order: %d",
                        createOrderDto.getDestinationId(), i));
            }

            if(!errors.toString().isBlank()){
                throw new BadRequestException(errors.toString());
            }

        }
    }

    private boolean canCancelOrder(OrderStatus orderStatus){
        return orderStatus == OrderStatus.NEW || orderStatus== OrderStatus.DELIVERING;
    }
}
