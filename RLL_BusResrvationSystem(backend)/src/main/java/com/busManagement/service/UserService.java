package com.busManagement.service;

import java.util.List;

import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.Passenger;
import com.busManagement.entity.User;
import com.busManagement.utils.UserAuth;

public interface UserService {
	public User addUser(User user);

	public void updateUser(User user);

	public User getUser(Integer userId);

	public void deleteUser(Integer userId);

	public User userLogin(UserAuth auth);

	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer busNumber);

	public void deleteBooking(Integer bookingId, Integer userId);

	public List<BookingDetails> getBookingByUserId(Integer userId);

	public BusDetails findByRouteAndDate(String arrivalBusstop, String departureBusstop, String date);
	
	public BusDetails getBusByBusNumber(Integer busNumber);
	
	public Passenger updatePassenger(Passenger passenger);

	public Passenger getPassengerById(Integer passengerId);

	public User getUserDetailsForUpdate(Integer userId);

}
