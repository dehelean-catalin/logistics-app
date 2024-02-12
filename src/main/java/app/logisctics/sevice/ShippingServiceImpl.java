package app.logisctics.sevice;

import app.logisctics.config.CompanyInfo;
import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import app.logisctics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShippingServiceImpl implements ShippingService{
    private final OrderRepository orderRepository;
    private final CompanyInfo companyInfo;
    private final ShippingManager shippingManager;
    @Override
    public void startNewDay() {
        LocalDate newDay = companyInfo.incrementDateByOne();

        log.info("New day starting: " + newDay);

        List<Order> orders = orderRepository.findAllByDeliveryDate(companyInfo.getCurrentDateAsLong());

        Map<Destination, List<Long>> ordersMap = orders.stream()
                        .filter(order-> order.getOrderStatus() == OrderStatus.NEW)
                        .collect(groupingBy(Order::getDestination, mapping(Order::getId, toList())));

        List<Long> orderIds = ordersMap.values().stream()
                .flatMap(Collection::stream)
                .toList();

        orderRepository.updateStatus(orderIds, null, OrderStatus.DELIVERING);

        String destinationNames = ordersMap.keySet().stream()
                .map(Destination::getName)
                .collect(Collectors.joining(", "));

        for (Map.Entry<Destination, List<Long>> entry : ordersMap.entrySet()) {
            shippingManager.deliveryToDestination(entry.getKey(), entry.getValue());
        }

        log.info("Today we will be delivering to: " + destinationNames);
    }
}
