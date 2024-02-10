package app.logisctics.repository;

import app.logisctics.dao.model.Destination;
import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static java.util.Objects.isNull;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByDestinationId(Long id);

    List<Order> findAllByDeliveryDateAndDestination_NameContainingIgnoreCase(Long date,
                                                                 String name);
    List<Order> findAllByDeliveryDate(Long deliveryDate);

    default void archiveOrder(Order order){
        order.setDestination(null);
        order.setOrderStatus(OrderStatus.ARCHIVED);
        order.setLastUpdated(System.currentTimeMillis());
        this.save(order);
    }

    default void updateStatus(List<Long> orderIds, OrderStatus initialStatus,
                              OrderStatus newStatus){

        List<Order> orders = this.findAllById(orderIds).stream()
                .filter(order -> order.getOrderStatus() == initialStatus || isNull(initialStatus))
                .toList();

        for (Order order : orders) {
            if(canUpdateStatus(order.getOrderStatus(), newStatus)){
                order.setOrderStatus(newStatus);
                order.setLastUpdated(System.currentTimeMillis());
            }
        }

        this.saveAll(orders);
    }

    default boolean canUpdateStatus(OrderStatus initialStatus, OrderStatus newStatus){

        return OrderStatus.transitions.get(initialStatus).contains(newStatus);

    }
}
