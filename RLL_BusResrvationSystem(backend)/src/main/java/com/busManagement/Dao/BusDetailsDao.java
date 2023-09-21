package com.busManagement.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.busManagement.entity.BusDetails;

@Repository
public interface BusDetailsDao extends JpaRepository<BusDetails, Integer> {

	@Query("select f from BusDetails f where f.arrivalBusstop = ?1 and f.departureBusstop = ?2") //JPQL 
	public List<BusDetails> findByRouteDate(String sourceBusstop,String destinationBusstop);
	
}

