package com.uday.vijayadairy.model;



public enum OrderStatus {
    ACCEPTED,        // Order received and confirmed
    PROCESSING,      // Milk/bottles are being packed/prepared
    OUTFORDELIVERY,  // Order dispatched, on the way to customer
    DELIVERED,       // Order successfully delivered
    CANCELLED,       // Customer or system cancelled the order
    RETURNED         // Order returned due to complaint/issue
          
}