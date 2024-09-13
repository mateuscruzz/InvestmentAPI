package com.mateuscruzz.investmentapi.repositories;

import com.mateuscruzz.investmentapi.model.BillingAdress;
import com.mateuscruzz.investmentapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BiilingAdressRepository extends JpaRepository<BillingAdress
        ,UUID> {
}
