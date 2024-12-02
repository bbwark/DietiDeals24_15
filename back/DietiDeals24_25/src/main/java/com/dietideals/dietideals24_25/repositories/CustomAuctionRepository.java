package com.dietideals.dietideals24_25.repositories;

import org.springframework.stereotype.Repository;

import com.dietideals.dietideals24_25.domain.entities.AuctionEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomAuctionRepository {

    private final EntityManager entityManager;

    public CustomAuctionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<AuctionEntity> findByItemNameAndCategories(String itemName, List<String> categories, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AuctionEntity> query = cb.createQuery(AuctionEntity.class);
        Root<AuctionEntity> root = query.from(AuctionEntity.class);

        Predicate namePredicate = cb.like(cb.lower(root.get("item").get("name")), "%" + itemName.toLowerCase() + "%");

        Predicate categoryPredicate = cb.conjunction();
        if (categories != null && !categories.isEmpty()) {
            List<Predicate> categoryPredicates = new ArrayList<>();
            for (String category : categories) {
                categoryPredicates.add(cb.equal(root.get("category"), category));
            }
            categoryPredicate = cb.or(categoryPredicates.toArray(new Predicate[0]));
        }

        Predicate finalPredicate = cb.and(namePredicate, categoryPredicate);

        query.where(finalPredicate);
        query.orderBy(cb.asc(root.get("item").get("name")));

        return entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }
}
