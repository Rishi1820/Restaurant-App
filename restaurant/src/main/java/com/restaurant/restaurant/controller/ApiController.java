package com.restaurant.restaurant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.restaurant.dto.CreateOrderRequest;
import com.restaurant.restaurant.dto.PaymentRequest;
import com.restaurant.restaurant.entity.Food;
import com.restaurant.restaurant.entity.OrderDetails;
import com.restaurant.restaurant.entity.TableNo;
import com.restaurant.restaurant.entity.Waiter;
import com.restaurant.restaurant.repository.FoodRepository;
import com.restaurant.restaurant.repository.OrderDetailsRepository;
import com.restaurant.restaurant.repository.TableRepository;
import com.restaurant.restaurant.repository.WaiterRepository;
import com.restaurant.restaurant.service.ApiService;

@CrossOrigin(origins = { "http://10.0.2.2:9090", "http://localhost:3000" })
@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private ApiService apiService;

	@Autowired
	private FoodRepository foodRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private TableRepository tableRepository;

	@Autowired
	private WaiterRepository waiterRepository;

	@PostMapping("/addFood")
	public ResponseEntity<Food> addFood(@RequestBody Food food) {
		Food savedFood = apiService.saveFood(food);
		return new ResponseEntity<>(savedFood, HttpStatus.CREATED);
	}

	@PostMapping("/addTable")
	public ResponseEntity<TableNo> addTable(@RequestBody TableNo table) {
		TableNo savedTable = apiService.saveTable(table);
		return new ResponseEntity<>(savedTable, HttpStatus.CREATED);
	}

	@PostMapping("/addWaiter")
	public ResponseEntity<Waiter> addWaiter(@RequestBody Waiter waiter) {
		Waiter savedWaiter = apiService.saveWaiter(waiter);
		return new ResponseEntity<>(savedWaiter, HttpStatus.CREATED);
	}

	@GetMapping("/getFoodList")
	public List<Food> getFoodList() {
		return foodRepository.findAll();
	}

	@GetMapping("/getTableList")
	public List<TableNo> getTableList() {
		return tableRepository.findAll();
	}

	@GetMapping("/getWaiterList")
	public List<Waiter> getWaiterList() {
		return waiterRepository.findAll();
	}

	@PostMapping("/createOrder")
	public ResponseEntity<OrderDetails> createOrder(@RequestBody CreateOrderRequest request) {
	    OrderDetails order = apiService.createOrder(request);
	    return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PostMapping("/makePayment")
	public ResponseEntity<OrderDetails> makePayment(@RequestBody PaymentRequest request) {
		OrderDetails order = apiService.completeOrder(request);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

}
