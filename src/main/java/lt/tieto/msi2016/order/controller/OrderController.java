package lt.tieto.msi2016.order.controller;

import lt.tieto.msi2016.order.model.Order;
import lt.tieto.msi2016.order.model.OrderResults;
import lt.tieto.msi2016.order.repository.model.OrderDb;
import lt.tieto.msi2016.order.service.OrderResultsService;
import lt.tieto.msi2016.order.service.OrderService;
import lt.tieto.msi2016.roles.Roles;
import lt.tieto.msi2016.utils.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderResultsService orderResultsService;

    @Secured(Roles.ADMIN)
    @RequestMapping(method = RequestMethod.GET, path = "/api/order")
    public List<Order> all() throws IOException {return orderService.all();}

    @RequestMapping(method = RequestMethod.POST, path = "/api/order")
    public Order createOrder(@RequestBody Order order) throws IOException {
        return orderService.createOrder(order);
    }

    @Secured(Roles.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, path = "/api/order/accept/{id}")
    public Order acceptOrder(@PathVariable Long id) throws IOException {
        return orderService.updateStatus(id, OrderDb.Status.Accepted);
    }

    @Secured(Roles.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, path = "/api/order/decline/{id}")
    public Order declineOrder(@PathVariable Long id) throws IOException {
        return orderService.updateStatus(id, OrderDb.Status.Declined);
    }

    @Secured(Roles.OPERATOR)
    @RequestMapping(method = RequestMethod.PUT, path = "/api/order/publish/{id}")
    public Order publishOrder(@PathVariable Long id) throws IOException {
        return orderService.updateStatus(id, OrderDb.Status.Completed);
    }

    @Secured(Roles.OPERATOR)
    @RequestMapping(method = RequestMethod.PUT, path = "/api/order/redo/{id}")
    public Order redoOrder(@PathVariable Long id) throws IOException {
        return orderService.updateStatus(id, OrderDb.Status.Accepted);
    }


    @Secured(Roles.CUSTOMER)
    @RequestMapping(method = RequestMethod.GET, path = "/api/customer/orders")
    public List<Order> getAllUserOrders() throws IOException {
        return orderService.getAllUserOrders();
    }

    @Secured(Roles.CUSTOMER)
    @RequestMapping(method = RequestMethod.GET, path = "/api/customer/orders/{id}")
    public List<OrderResults> getOrderResultsByOrderId(@PathVariable Long id) throws IOException {
        return orderResultsService.getOrderResultsByOrderId(id);
    }

}
