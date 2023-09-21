package com.busManagement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busManagement.entity.Passenger;

@Repository
public interface PassengerDao extends JpaRepository<Passenger, Integer> {

}
