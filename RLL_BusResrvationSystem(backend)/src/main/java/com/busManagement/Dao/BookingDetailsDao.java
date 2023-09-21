package com.busManagement.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busManagement.entity.BookingDetails;

@Repository
public interface BookingDetailsDao extends JpaRepository<BookingDetails, Integer> {

}
