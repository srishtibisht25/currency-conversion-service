package com.in28minutes.microservices.currency_conversion_service;

import java.math.BigDecimal;

import org.apache.el.lang.ELArithmetic.BigDecimalDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
													@PathVariable String to,
													@PathVariable BigDecimal quantity)
	{
		return new CurrencyConversion(10001L, 
				from, to, quantity, 
				BigDecimal.ONE, 
				BigDecimal.ONE, 
				"");
	}
	
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
													@PathVariable String to,
													@PathVariable BigDecimal quantity)
	{
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from,to);

		
		return new CurrencyConversion(currencyConversion.getId(), 
				from, to, quantity, 
				currencyConversion.getConversionMultiple(), 
				quantity.multiply(currencyConversion.getConversionMultiple()), 
				currencyConversion.getEnvironment() + " " + "feign");
	}
	
}
