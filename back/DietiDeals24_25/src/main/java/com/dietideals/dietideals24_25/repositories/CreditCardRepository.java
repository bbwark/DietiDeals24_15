package com.dietideals.dietideals24_25.repositories;

import com.dietideals.dietideals24_25.domain.entities.CreditCardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CreditCardRepository extends CrudRepository<CreditCardEntity, String> {

    @Query("SELECT c FROM CreditCardEntity c WHERE c.owner.id = :userId")
    List<CreditCardEntity> findByUserId(@Param("userId") UUID userId);

    Optional<CreditCardEntity> findByCreditCardNumber(String creditCardNumber);
}
