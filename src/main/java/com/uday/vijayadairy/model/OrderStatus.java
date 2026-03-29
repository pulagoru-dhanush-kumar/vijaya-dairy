package com.uday.vijayadairy.model;



public enum OrderStatus {
	    PENDING,        // created but not paid
	    SUCCESS,        // payment success
	    FAILED,         // payment failed
	    ACCEPTED,       
	    PROCESSING,
	    OUTFORDELIVERY,
	    DELIVERED,
	    CANCELLED,
	    RETURNED
          
}