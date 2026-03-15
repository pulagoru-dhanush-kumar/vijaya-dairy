package com.uday.vijayadairy.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


public enum Categories {
	MILK("milk") ,
	CURD("curd"),
	SWEETS("sweets"),
	ICE_CREAMS("ice_creams"),
	PANNER("panner"),
	GHEE("ghee"),
	PICKEL("pickel");
public final String value;
	Categories(String string) {
		this .value=string.toLowerCase();
	}
	
	@JsonCreator
	public static Categories getValue(String input)
	{
		for(Categories i:Categories.values())
		{
			if(i.value.equalsIgnoreCase(input))
			{
				return i;
			}
		}
		
		 throw new IllegalArgumentException("invalid categroy:"+input+"  allowed values :"+Categories.values());
	}	
}
