package com.autowaterer.autowatererbackend;

import org.springframework.stereotype.Component;

public class PaypalPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("PAYPAL");
        System.out.println("Amount: " + amount);
    }
}
