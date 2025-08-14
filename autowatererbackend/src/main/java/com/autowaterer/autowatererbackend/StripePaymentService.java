package com.autowaterer.autowatererbackend;

import org.springframework.stereotype.Component;

@Component
public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount){
        System.out.println("STRIP");
        System.out.println("Amount: " + amount);
    }
}
