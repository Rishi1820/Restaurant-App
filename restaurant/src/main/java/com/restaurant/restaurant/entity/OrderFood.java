package com.restaurant.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_foods")
public class OrderFood {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore // Ignore serialization of the 'order' field to avoid recursive nesting
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderDetails order;

	@ManyToOne
	@JoinColumn(name = "food_id")
	private Food food;

	private int quantity;

	private double price;

	// Constructors, getters, setters, etc.
}
