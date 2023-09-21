package com.busManagement.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bus_details", schema = "bus")
@Data @NoArgsConstructor @AllArgsConstructor
public class BusDetails {
	@Id
	private Integer busNumber;

	@NotNull(message = "Departure Busstop cannot be null")
	private String departureBusstop;

	@NotNull(message = "Source Busstop cannot be null")
	private String arrivalBusstop;

	private Integer availableSeats;

	@NotNull(message = "daparture date cannot be null")
	private String departureDate;

	@NotNull(message = "arrival date cannot be null")
	private String arrivalDate;

	@NotNull(message = "Arival Time cannot be null")
	private String arrivalTime;

	@NotNull(message = "Departure Time cannot be null")
	private String departureTime;

	@NotNull(message = "Bus Vendor cannot be null")
	private String busVendor;

	@NotNull(message = "cost cannot be null")
	private Double cost;
	
	public BusDetails(
			@NotNull(message = "Departure Busstop cannot be null") String departureBusstop,
			@NotNull(message = "Source Busstop cannot be null") String arrivalBusstop, Integer availableSeats,
			@NotNull(message = "daparture date cannot be null") String departureDate,
			@NotNull(message = "arrival date cannot b null") String arrivalDate,
			@NotNull(message = "Arival Time cannot be null") String arrivalTime,
			@NotNull(message = "Departure Time cannot be null") String departureTime,
			@NotNull(message = "Bus Vendor cannot be null") String busVendor, Double cost) {
		super();
		this.departureBusstop = departureBusstop;
		this.arrivalBusstop = arrivalBusstop;
		this.availableSeats = availableSeats;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.busVendor = busVendor;
		this.cost = cost;
	}

}