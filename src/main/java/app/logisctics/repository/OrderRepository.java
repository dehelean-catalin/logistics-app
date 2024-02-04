package app.logisctics.repository;

import app.logisctics.dao.model.Order;
import app.logisctics.dao.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findAllByDestinationId(Long id);

    default void archiveOrder(Order order){
        order.setDestination(null);
        order.setOrderStatus(OrderStatus.ARCHIVED);
        order.setLastUpdated(System.currentTimeMillis());
        this.save(order);
    }
}
