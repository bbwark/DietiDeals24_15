package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreditCardController {

    private CreditCardService creditCardService;

    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;

    public CreditCardController(CreditCardService creditCardService,
            Mapper<CreditCardEntity, CreditCardDto> creditCardMapper) {
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
    }

    @PostMapping(path = "/credit_cards")
    public CreditCardDto createCreditCard(@RequestBody CreditCardDto creditCard) {
        CreditCardEntity creditCardEntity = creditCardMapper.mapFrom(creditCard);
        CreditCardEntity savedCreditCardEntity = creditCardService.createCreditCard(creditCardEntity);
        return creditCardMapper.mapTo(savedCreditCardEntity);
    }

    @DeleteMapping(path = "/credit_cards/{creditCardNumber}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable("creditCardNumber") String creditCardNumber) {
        creditCardService.delete(creditCardNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
