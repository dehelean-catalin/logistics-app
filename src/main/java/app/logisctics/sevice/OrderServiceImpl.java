package app.logisctics.sevice;

import app.logisctics.cache.DestinationCache;
import app.logisctics.config.CompanyInfo;
import app.logisctics.dao.converter.OrderConverter;
import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import app.logisctics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static app.logisctics.dao.converter.OrderConverter.modelListToDtoList;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final DestinationCache destinationCache;
    private final CompanyInfo companyInfo;

    @Override
    public List<OrderDto> getAllOrdersByDeliveryDateAndDestination(String date,
                                                                   String destinationName) {
        long deliveryDate = companyInfo.getLocalDateStringAsLong(date);

        return modelListToDtoList(orderRepository.findAllByDeliveryDateAndDestination_NameContainingIgnoreCase(deliveryDate,
                destinationName));
    }

    @Override
    public List<OrderDto> createOrders(List<CreateOrderDto> createOrderDtos) throws BadRequestException {
        Map<Long, Destination> destinationMap = destinationCache.findAll().stream()
                .collect(Collectors.toMap(Destination::getId, Function.identity()));

        validateCreateOrderDto(createOrderDtos, destinationMap.keySet());

        List<Order> ordersToSave = createOrderDtos.stream()
                .map(order ->
                        OrderConverter.createDtoToModel(order.getDeliveryDate(), destinationMap.get(order.getDestinationId()))
                )
                .toList();

        return modelListToDtoList(orderRepository.saveAll(ordersToSave));
    }

    @Override
    public void cancelOrders(List<Long> orderIds) {
        orderRepository.updateStatus(orderIds, null,  OrderStatus.CANCELED);
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
}
