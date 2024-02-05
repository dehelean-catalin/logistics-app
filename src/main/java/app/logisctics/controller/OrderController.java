package app.logisctics.controller;

import app.logisctics.dao.dto.CreateOrderDto;
import app.logisctics.dao.dto.OrderDto;
import app.logisctics.sevice.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/status")
    public List<OrderDto> getAllOrdersByDeliveryDateAndDestination(@RequestParam String date,
                                       @RequestParam String destination) throws ParseException {
        return orderService.getAllOrdersByDeliveryDateAndDestination(date, destination);
    }

    @PostMapping("/add")
    public List<OrderDto> createOrders(@Valid @RequestBody List<CreateOrderDto> createOrderDtos) throws BadRequestException {
        return orderService.createOrders(createOrderDtos);
    }

    @PutMapping("/cancel")
    public void cancelOrders(@RequestBody List<Long> ids){
        orderService.cancelOrders(ids);
    }
}
