package com.restaurant.restaurant.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.restaurant.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details", uniqueConstraints = { @UniqueConstraint(columnNames = "invoice"), })
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    private String invoice;

    private LocalDateTime orderDateTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private TableNo table;

    @ManyToOne
    @JoinColumn(name = "waiter_id")
    private Waiter waiter;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderFood> orderFoods = new ArrayList<>();

    private double totalAmount;
    
    private double amount;
    
    private double discountAmount;

    // Utility method to add an order food
    public void addOrderFood(Food food, int quantity) {
        OrderFood orderFood = new OrderFood();
        orderFood.setOrder(this);
        orderFood.setFood(food);
        orderFood.setQuantity(quantity);
        orderFood.setPrice(food.getPrice() * quantity);
        this.orderFoods.add(orderFood);
    }
}

