package app.logisctics.repository;

import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByDestinationId(Long id);

    List<Order> findAllByDeliveryDateAndDestination(Long date, Destination destination);
    List<Order> findAllByDeliveryDate(Long deliveryDate);

    default void archiveOrder(Order order){
        order.setDestination(null);
        order.setOrderStatus(OrderStatus.ARCHIVED);
        order.setLastUpdated(System.currentTimeMillis());
        this.save(order);
    }
}
