package com.vietcuong.simpleJwt.repository;

import com.vietcuong.simpleJwt.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
