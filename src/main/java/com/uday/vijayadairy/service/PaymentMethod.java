package com.uday.vijayadairy.service;

import java.util.Map;

public interface PaymentMethod {
	Map<String, Object> pay(String email);
	
}
