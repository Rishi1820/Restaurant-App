package com.restaurant.restaurant.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.restaurant.dto.CreateOrderRequest;
import com.restaurant.restaurant.dto.PaymentRequest;
import com.restaurant.restaurant.entity.Food;
import com.restaurant.restaurant.entity.OrderDetails;
import com.restaurant.restaurant.entity.TableNo;
import com.restaurant.restaurant.entity.Waiter;
import com.restaurant.restaurant.enums.OrderStatus;
import com.restaurant.restaurant.enums.TableAndWaiterStatus;
import com.restaurant.restaurant.repository.FoodRepository;
import com.restaurant.restaurant.repository.OrderDetailsRepository;
import com.restaurant.restaurant.repository.TableRepository;
import com.restaurant.restaurant.repository.WaiterRepository;

import jakarta.transaction.Transactional;

@Service
public class ApiService {

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	public Food saveFood(Food food) {
		return foodRepository.save(food);
	}

	public TableNo saveTable(TableNo table) {
		table.setTableStatus(TableAndWaiterStatus.AVAILABLE);
		return tableRepository.save(table);
	}

	public Waiter saveWaiter(Waiter waiter) {
		waiter.setWaiterStatus(TableAndWaiterStatus.AVAILABLE);
		return waiterRepository.save(waiter);
	}

	@Transactional
	public OrderDetails createOrder(CreateOrderRequest request) {

		TableNo table = tableRepository.findById(request.getTableId())
				.orElseThrow(() -> new RuntimeException("Table not found with ID: " + request.getTableId()));
		Waiter waiter = waiterRepository.findById(request.getWaiterId())
				.orElseThrow(() -> new RuntimeException("Waiter not found with ID: " + request.getWaiterId()));

		OrderDetails order = new OrderDetails();
		order.setOrderDateTime(LocalDateTime.now());
		order.setOrderStatus(OrderStatus.BOOKED);
		order.setInvoice("INV-" + System.currentTimeMillis());
		order.setWaiter(waiter);
		order.setTable(table);

		double totalAmount = 0;

		for (CreateOrderRequest.FoodOrder foodOrder : request.getFoodOrders()) {
			Food food = foodRepository.findById(foodOrder.getFoodId())
					.orElseThrow(() -> new RuntimeException("Food not found with ID: " + foodOrder.getFoodId()));
			order.addOrderFood(food, foodOrder.getQuantity());
			totalAmount += food.getPrice() * foodOrder.getQuantity();
		}

		double discountAmount = request.getDiscountAmount();
		double finalAmount = totalAmount - discountAmount;

		order.setTotalAmount(finalAmount);
		order.setAmount(totalAmount);
		order.setDiscountAmount(discountAmount);

		table.setTableStatus(TableAndWaiterStatus.ENGAGED);
		waiter.setWaiterStatus(TableAndWaiterStatus.ENGAGED);

		tableRepository.save(table);
		waiterRepository.save(waiter);

		return orderDetailsRepository.save(order);
	}

	@Transactional
	public OrderDetails completeOrder(PaymentRequest request) {
		OrderDetails order = orderDetailsRepository.findById(request.getOrderId())
				.orElseThrow(() -> new RuntimeException("Order not found with ID: " + request.getOrderId()));

		if (order.getOrderStatus() != OrderStatus.BOOKED) {
			throw new IllegalStateException("Order is not in a state that can be completed");
		}

		order.setOrderStatus(OrderStatus.COMPLETED);

		order.getTable().setTableStatus(TableAndWaiterStatus.AVAILABLE);
		order.getWaiter().setWaiterStatus(TableAndWaiterStatus.AVAILABLE);

		tableRepository.save(order.getTable());
		waiterRepository.save(order.getWaiter());

		return orderDetailsRepository.save(order);
	}

}
