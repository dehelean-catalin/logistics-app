package app.logisctics.sevice;

import app.logisctics.config.CompanyInfo;
import app.logisctics.dao.model.Destination;
import app.logisctics.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static app.logisctics.dao.model.OrderStatus.DELIVERED;
import static app.logisctics.dao.model.OrderStatus.DELIVERING;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingManager {
    private final OrderRepository orderRepository;
    private final CompanyInfo companyInfo;
    @SneakyThrows
    @Async("executor")
    public void deliveryToDestination(Destination destination, List<Long> orderIds){

        log.info(String.format("Started delivering %d orders to %s on thread %s for %d km.",
                orderIds.size(),
                destination.getName(),
                Thread.currentThread().getName(),
                destination.getDistance()));


        Thread.sleep(destination.getDistance() * 1000L);

        int numOfDeliveredOrders = orderRepository.updateStatus(orderIds, DELIVERING, DELIVERED);
        log.info(String.format("%d deliveries completed for %s ", numOfDeliveredOrders, destination.getName()));

        long profit =
                companyInfo.updateProfit(((long) numOfDeliveredOrders * destination.getDistance()));

        log.info("Company profit : " + profit);

    }
}
