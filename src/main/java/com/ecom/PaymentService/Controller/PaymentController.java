package com.ecom.PaymentService.Controller;

import com.ecom.PaymentService.Entity.Payment;
import com.ecom.PaymentService.Service.PaymentService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/payment")
    public ResponseEntity<?> getPaymentDetails(@RequestParam(value = "orderId", required = false) Integer orderId, @RequestParam(value = "paymentId", required = false) Integer paymentId, @RequestParam(value = "customerId", required = false) Integer customerId) throws BadRequestException {
        if(paymentId != null){
            Payment paymentByPaymentId = paymentService.getPaymentByPaymentId(paymentId);
            return new ResponseEntity<>(paymentByPaymentId, HttpStatus.OK);
        }else if(orderId != null){
            Payment paymentByOrderId = paymentService.getPaymentByOrderId(orderId);
            return new ResponseEntity<>(paymentByOrderId, HttpStatus.OK);
        }else if(customerId != null){
            List<Payment> paymentsByCustomerId = paymentService.getPaymentsByCustomerId(customerId);
            return new ResponseEntity<>(paymentsByCustomerId, HttpStatus.OK);
        }
        throw new BadRequestException("No request type found");
    }
}
