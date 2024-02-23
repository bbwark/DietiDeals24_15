package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {
    CreditCardEntity createCreditCard(CreditCardEntity creditCardEntity);
    void delete (String creditCardNumber);
}
