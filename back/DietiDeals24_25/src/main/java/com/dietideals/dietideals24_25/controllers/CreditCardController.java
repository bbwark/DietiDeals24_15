package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credit_cards")
public class CreditCardController {

    private CreditCardService creditCardService;

    private Mapper<CreditCardEntity, CreditCardDto> creditCardMapper;

    public CreditCardController(CreditCardService creditCardService,
            Mapper<CreditCardEntity, CreditCardDto> creditCardMapper) {
        this.creditCardService = creditCardService;
        this.creditCardMapper = creditCardMapper;
    }

    @PostMapping
    public ResponseEntity<CreditCardDto> createCreditCard(@RequestBody CreditCardDto creditCard) {
        CreditCardEntity creditCardEntity = creditCardMapper.mapFrom(creditCard);
        CreditCardEntity savedCreditCardEntity = creditCardService.save(creditCardEntity);
        CreditCardDto responseCreditCard = creditCardMapper.mapTo(savedCreditCardEntity);
        return new ResponseEntity<>(responseCreditCard, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{creditCardNumber}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable("creditCardNumber") String creditCardNumber) {
        creditCardService.delete(creditCardNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
