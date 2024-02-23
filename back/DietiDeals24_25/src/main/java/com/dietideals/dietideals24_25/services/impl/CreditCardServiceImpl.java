package com.dietideals.dietideals24_25.services.impl;

import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.repositories.CreditCardRepository;
import com.dietideals.dietideals24_25.services.CreditCardService;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public CreditCardEntity createCreditCard(CreditCardEntity creditCardEntity) {
        return creditCardRepository.save(creditCardEntity);
    }

    @Override
    public void delete(String creditCardNumber) {
        creditCardRepository.deleteById(creditCardNumber);
    }
}
