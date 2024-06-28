package com.restaurant.restaurant.entity;

import com.restaurant.restaurant.enums.TableAndWaiterStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Waiter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long waiterId;

	private String waiterName;

	@Enumerated(EnumType.STRING)
	private TableAndWaiterStatus waiterStatus;

}
