package com.restaurant.restaurant.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PaymentRequest {

	private Long orderId;

//	private double discountAmount;

}
