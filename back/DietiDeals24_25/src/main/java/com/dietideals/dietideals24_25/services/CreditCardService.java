package com.dietideals.dietideals24_25.services;

import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public interface CreditCardService {
    CreditCardEntity save(CreditCardEntity creditCardEntity);
    void delete (String creditCardNumber);
    List<CreditCardEntity> findByUserId(UUID userId);
    Optional<CreditCardEntity> findByCardNumber(String cardNumber);
}
