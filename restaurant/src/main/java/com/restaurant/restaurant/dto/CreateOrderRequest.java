package com.restaurant.restaurant.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CreateOrderRequest {

	private Long tableId;
	private Long waiterId;
	private List<FoodOrder> foodOrders;
    private double discountAmount;


	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FoodOrder {
		private Long foodId;
		private int quantity;

	}

}
