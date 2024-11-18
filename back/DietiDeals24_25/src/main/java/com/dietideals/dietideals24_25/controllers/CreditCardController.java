package com.dietideals.dietideals24_25.controllers;

import com.dietideals.dietideals24_25.domain.dto.CreditCardDto;
import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import com.dietideals.dietideals24_25.mappers.Mapper;
import com.dietideals.dietideals24_25.services.CreditCardService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('BUYER')")
    @PostMapping
    public ResponseEntity<CreditCardDto> createCreditCard(@RequestBody CreditCardDto creditCard) {
        try {
            CreditCardEntity creditCardEntity = creditCardMapper.mapFrom(creditCard);
            CreditCardEntity savedCreditCardEntity = creditCardService.save(creditCardEntity);
            CreditCardDto responseCreditCard = creditCardMapper.mapTo(savedCreditCardEntity);
            return new ResponseEntity<>(responseCreditCard, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorizedByCardNumber(#creditCardNumber)")
    @DeleteMapping(path = "/{creditCardNumber}")
    public ResponseEntity<Void> deleteCreditCard(@PathVariable("creditCardNumber") String creditCardNumber) {
        try {
            creditCardService.delete(creditCardNumber);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('BUYER') && @userSecurityService.isUserAuthorized(#id)")
    @GetMapping(path = "/user/{userId}")
    public ResponseEntity<List<CreditCardDto>> getCreditCardsByUserId(@PathVariable("userId") String id) {
        try {
            UUID userId = UUID.fromString(id);
            List<CreditCardEntity> creditCardEntities = creditCardService.findByUserId(userId);
            List<CreditCardDto> result = creditCardEntities.stream()
                    .map(creditCardEntity -> creditCardMapper.mapTo(creditCardEntity))
                    .collect(java.util.stream.Collectors.toList());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
