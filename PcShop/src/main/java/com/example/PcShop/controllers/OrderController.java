package com.example.PcShop.controllers;

import com.example.PcShop.entities.Component;
import com.example.PcShop.entities.User;
import com.example.PcShop.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.Authentication;

import com.example.PcShop.entities.Order;
import com.example.PcShop.entities.OrderList;
import com.example.PcShop.services.ComponentService;
import com.example.PcShop.services.OrderService;
import com.example.PcShop.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Controller
public class OrderController {
    @Autowired
    private ComponentService componentService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    public double calculateTotalCost(List<OrderList> orderLists) {
        double totalCost = 0.0;
        if (orderLists == null){
//            System.out.println("YYYYYY");
            return totalCost;
        }
        for (OrderList orderListItem : orderLists) {
            totalCost += orderListItem.getAmount() * orderListItem.getComponent().getComponentPrice();
        }
//        System.out.println(totalCost);
        return totalCost;
    }




    public Order findOrderCurrentUser(CustomUserDetailsService.CustomUserDetails customUserDetails){
        User user = userService.findById(customUserDetails.getUser().getId());
        List<Order> userOrders = user.getOrders();
        Order order = null;
        for (Order orderFindInList : userOrders) {
            if (!orderFindInList.isStatusCompleted()) {
                order = orderFindInList;
                break;
            }
        }
        return order;
    }

    @GetMapping("/{category}_store")
    public String getAllComponents(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                                   @PathVariable("category") String category,
                                   Model model) {
        model.addAttribute("orderCostUnderBin", "Корзина");
        model.addAttribute("orderListAmount", 0);

        if (customUserDetails != null){
            System.out.println("dddddd");
            User user = userService.findById(customUserDetails.getUser().getId());
            Order order = findOrderCurrentUser(customUserDetails);
            if (user.getAccountBan()){
                model.addAttribute("orderError", "Ваш аккаунт заблокирован, сделать заказ невозможно");
                List<Component> components = componentService.findByCategory(category);
                model.addAttribute("components", components);
                return category + "_store";
            }

            if (order == null) {
                System.out.println("new");
                order = new Order();
                List<OrderList> orderItems = new ArrayList<>();
                order.setOrderList(orderItems);
                order.setUser(user);
                user.getOrders().add(order);
                orderService.saveOrder(order);
            }
            model.addAttribute("orderListAmount", order.getOrderList().size());
            if (order.getTotalCost() != 0){
                System.out.println(order.getTotalCost());
                model.addAttribute("orderCostUnderBin", order.getTotalCost() + " \u20BD");
            }
        }
        else {
            model.addAttribute("orderError", "Войдите в аккаунт, чтобы сделать заказ");
        }
        List<Component> components = componentService.findByCategory(category);
        model.addAttribute("components", components);
        return category + "_store";
    }

    @PostMapping("/addToCart")
    public String addToCart(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                            @RequestParam("productId") int productId,
                            @RequestParam("quantity") Integer quantity,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("orderCostUnderBin", "Корзина");
        model.addAttribute("orderListAmount", 0);
        String referer = request.getHeader("referer");
//        System.out.println(category);
        if (customUserDetails != null){
            User user = userService.findById(customUserDetails.getUser().getId());
            Order order = findOrderCurrentUser(customUserDetails);
            if (user.getAccountBan()){
                model.addAttribute("orderError", "Ваш аккаунт заблокирован, добавить товар в корзину невозможно");
//                List<Component> components = componentService.findByCategory(category);
//                model.addAttribute("components", components);
                return "redirect:" + referer;
            }
            boolean productExistsInOrder = false;
            for (OrderList orderListItem : order.getOrderList()) {
                if (orderListItem.getComponent().getId() == productId) {
                    orderListItem.setAmount(orderListItem.getAmount() + quantity);
                    orderService.saveOrderList(orderListItem);
                    productExistsInOrder = true;
                    break;
                }
            }
            if (!productExistsInOrder) {
                OrderList orderList = new OrderList();
                orderList.setAmount(quantity);
                orderList.setComponent(componentService.findById(productId));
                orderList.setOrder(order);
                order.addOrderList(orderList);
                user.getOrders().add(order);
                orderService.saveOrderList(orderList);
            }
            if (order.getOrderList() != null){
                order.setTotalCost(calculateTotalCost(order.getOrderList()));
                orderService.saveOrder(order);
                model.addAttribute("orderListAmount", order.getOrderList().size());
            }


        }
        else {
            model.addAttribute("orderError", "Войдите в аккаунт, прежде чем делать покупки");
        }
//        model.addAttribute("components", componentService.findByCategory(category));
        return "redirect:" + referer;
    }

    @GetMapping("/storage")
    public String storagee(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                           Model model){
        if (customUserDetails != null){
            User user = userService.findById(customUserDetails.getUser().getId());
            if (user.getAccountBan()){
                model.addAttribute("enterError", "Ваш аккаунт заблокирован, оформить заказ невозможно");
                model.addAttribute("currentUser", user);
                return "storage";
            }
            Order order = findOrderCurrentUser(customUserDetails);
            order.setTotalCost(calculateTotalCost(order.getOrderList()));
            orderService.saveOrder(order);
//            User user = userService.findById(customUserDetails.getUser().getId());
//            if (user.getAccountBan()){
//                model.addAttribute("enterError", "Ваш аккаунт заблокирован, оформить заказ невозможно");
//                return "storage";
//            }
            if (order != null){
                model.addAttribute("orderId", order.getId());
                model.addAttribute("storage", order.getOrderList());
                model.addAttribute("totalCost", order.getTotalCost());
            }
        }
        else{
            model.addAttribute("enterError", "Войдите в аккаунт, чтобы совершать покупки");
        }
        return "storage";
    }

    @PostMapping("deleteStorageItem")
    public String deleteStorageItem(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                                    @RequestParam("deleteStorageItemId") int deleteStorageItemId,
                                    Model model){
        orderService.deleteOrderList(deleteStorageItemId);
        return "redirect:/storage";
    }

    @PostMapping("updateAmount")
    public String updateAmount(@RequestParam("storItem") int storItem,
                               @RequestParam("quantity") Integer quantity){
        OrderList orderList = orderService.findById(storItem);
        orderList.setAmount(quantity);
        orderService.saveOrderList(orderList);
        return "redirect:/storage";
    }

    @PostMapping("/payOrder")
    public String payOrder(@AuthenticationPrincipal CustomUserDetailsService.CustomUserDetails customUserDetails,
                           @RequestParam("address") String address,
                           @RequestParam("deliveryDate") LocalDate deliveryDate,
                           Model model) {
        Order order = findOrderCurrentUser(customUserDetails);

        order.setAddress(address);
        order.setDeliveryDate(deliveryDate);
        order.setStatusCompleted(true);
        orderService.saveOrder(order);
        model.addAttribute("enterError", "Покупка оплачена, ожидайте доставку!");
        return "storage";
    }



}
