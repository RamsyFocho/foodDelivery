package com.newSpringBootProject.FoodShare.repository;

import com.newSpringBootProject.FoodShare.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByName(String name);

    long countByCreatedDateBetween(LocalDate monthAgo, LocalDate now);
}
