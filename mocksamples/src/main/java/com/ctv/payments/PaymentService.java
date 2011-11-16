package com.ctv.payments;

import com.ctv.exceptions.PaymentException;

public interface PaymentService {

	public void makePayment(String accountNumber, double amount) throws PaymentException;
}
