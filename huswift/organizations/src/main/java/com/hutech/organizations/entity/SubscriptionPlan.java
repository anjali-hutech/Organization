package com.hutech.organizations.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionPlan {

	private String planType;
	private String planStartDate;
	private String planExpiryDate;
	private String paymentMode;
}
