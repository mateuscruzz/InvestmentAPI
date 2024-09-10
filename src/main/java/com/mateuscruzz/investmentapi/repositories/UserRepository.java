package com.mateuscruzz.investmentapi.repositories;

import com.mateuscruzz.investmentapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
