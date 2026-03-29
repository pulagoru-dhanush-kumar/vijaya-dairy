package com.uday.vijayadairy.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RazorpayUtil {

	public static String generateSignature(String orderId, String paymentId, String keySecret) throws Exception {
	    String payload = orderId + "|" + paymentId;
	    Mac mac = Mac.getInstance("HmacSHA256");
	    SecretKeySpec secretKey = new SecretKeySpec(keySecret.getBytes(), "HmacSHA256");
	    mac.init(secretKey);
	    byte[] hash = mac.doFinal(payload.getBytes());
	    
	    // Convert to hex
	    StringBuilder hexString = new StringBuilder();
	    for (byte b : hash) {
	        String hex = Integer.toHexString(0xff & b);
	        if (hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
}