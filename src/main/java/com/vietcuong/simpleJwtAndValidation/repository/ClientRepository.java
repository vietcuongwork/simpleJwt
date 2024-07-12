package com.vietcuong.simpleJwtAndValidation.repository;

import com.vietcuong.simpleJwtAndValidation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
