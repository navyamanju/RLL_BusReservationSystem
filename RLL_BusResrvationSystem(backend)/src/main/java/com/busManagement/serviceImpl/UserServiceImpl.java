package com.busManagement.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busManagement.Dao.*;
import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.Passenger;
import com.busManagement.entity.User;
import com.busManagement.exception.BusDetailsNotFoundException;
import com.busManagement.exception.NullBusDetailsException;
import com.busManagement.exception.NullUserException;
import com.busManagement.exception.PassengerNotFoundException;
import com.busManagement.exception.UserAlreadyExistException;
import com.busManagement.exception.UserDoesnotExistException;
import com.busManagement.service.UserService;
import com.busManagement.utils.UserAuth;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;

	@Autowired
	BusDetailsDao busDao;

	@Autowired
	BookingDetailsDao bookingDao;
	
	@Autowired
	PassengerDao passengerDao;

	@Override
	public User addUser(User user) {

		if (user == null)
			throw new NullUserException("Please Enter Data");
		Integer userId = (int) ((Math.random() * 900) + 100);
		user.setUserId(userId);
		Optional<User> checkUser = userDao.findById(user.getUserId());
		if (checkUser.isPresent())
			throw new UserAlreadyExistException("user already exists");

		userDao.save(user);
		System.out.println("user Added Succesfully");
		return user;

	}

	
	@Override
	public void updateUser(User user) {
		if (user == null)
			throw new NullUserException("Please Enter Data");
		Optional<User> checkUser = userDao.findById(user.getUserId());
		if (checkUser.isPresent())
			userDao.save(user);
		else
			throw new UserDoesnotExistException("Sorry! User not found");

	}

	
	@Override
	public User getUser(Integer userId) {
		if (userId == null)
			throw new NullUserException("Please Enter Data");
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent())
			throw new UserDoesnotExistException("Sorry! User not found");
		return user.get();
	}

	
	@Override
	public void deleteUser(Integer userId) {
		if (userId == null)
			throw new NullUserException("Please Enter Data");
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent())
			throw new UserDoesnotExistException("Sorry! User not found");
		userDao.deleteById(userId);
	}

	
	@Override
	public User userLogin(UserAuth auth) {
		if (auth == null) {
			throw new NullUserException("Please Enter Data");
		}
		Optional<User> user = userDao.findById(auth.getUserId());
		if (user.isPresent()) {
			if (user.get().getUserId() == auth.getUserId() && user.get().getPassword().equals(auth.getPassword())) {
				return user.get();
			} else {
				throw new UserDoesnotExistException("Invalid login ID or Password");
			}
			
		} else {
			throw new UserDoesnotExistException("Sorry! User not found");
		}
	}

	
	@Override
	public BookingDetails addBooking(BookingDetails booking, Integer userId, Integer busNumber) {
		Optional<User> user = userDao.findById(userId);
		Optional<BusDetails> bus = busDao.findById(busNumber);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("Sorry! user id not found");
		}
		if (!bus.isPresent()) {
			throw new BusDetailsNotFoundException("Oops!! bus details not found");
		}
		Integer bookingId = (int) ((Math.random() * 9000) + 1000);
		booking.setBookingId(bookingId);
		booking.setOwnerId(userId);
		booking.setBusNumber(busNumber);
		booking.setBookingDate(LocalDate.now().toString());
		booking.setBookingTime(LocalTime.now().toString().substring(0, 5));
		booking.setTotalCost(bus.get().getCost() * booking.getPassengers().size());
		List<BookingDetails> bookingList = user.get().getBookingDetails();
		bookingList.add(booking);
		user.get().setBookingDetails(bookingList);
		updateUser(user.get());
		return bookingDao.getById(bookingId);
	}
     
	
	//Deleting Booking
	@Override
	public void deleteBooking(Integer bookingId, Integer userId) {
		Optional<User> u = userDao.findById(userId);
		Optional<BookingDetails> bd = bookingDao.findById(bookingId);
		if (!bd.isPresent()) {
			throw new UserDoesnotExistException("Sorry! booking not found");
		}
		if (!u.isPresent()) {
			throw new UserDoesnotExistException("Oops! user id not found");
		}
		User user = u.get();
		List<BookingDetails> bookingList = user.getBookingDetails();
		BookingDetails deleteBooking = null;
		for (BookingDetails b : bookingList) {
			if (b.getBookingId() == bookingId) {
				System.out.println("Sorry! booking id found");
				deleteBooking = b;
			}
		}
		bookingList.remove(deleteBooking);
		user.setBookingDetails(bookingList);
		bookingDao.deleteById(bookingId);
		updateUser(user);
	}

	
	//List all the booking details made by the user
	@Override
	public List<BookingDetails> getBookingByUserId(Integer userId) {
		Optional<User> user = userDao.findById(userId);
		if (!user.isPresent()) {
			throw new UserDoesnotExistException("Oops! user id not found");
		}
		return user.get().getBookingDetails();
	}

	  //Searching bus details //checking the findByRouteAndDate
	@Override
	public BusDetails findByRouteAndDate(String arrivalBusStop, String departureBusStop, String date) {
		List<BusDetails> list = busDao.findByRouteDate(arrivalBusStop.toLowerCase(),
				departureBusStop.toLowerCase());
		for (BusDetails f : list) {
			if (f.getDepartureDate().equals(date)) {
				return f;
			}
		}
		throw new BusDetailsNotFoundException("Sorry! details not found");
	}
    
	
	@Override
	public BusDetails getBusByBusNumber(Integer busNumber) {
		if (busNumber == null) {
			throw new NullBusDetailsException("Please Enter Data");
		}
		Optional<BusDetails> details = busDao.findById(busNumber);
		if (!details.isPresent()) {
			throw new BusDetailsNotFoundException("Oops! No Bus Service Found");
		}
		return details.get();
	}
	
	@Override
	public Passenger updatePassenger(Passenger passenger) {
		if (passenger == null) {
			throw new PassengerNotFoundException("Please Enter Data");
		}
		
		Optional<Passenger> oldPassenger = passengerDao.findById(passenger.getPassengerId()); 
		if (!oldPassenger.isPresent()) {
			throw new PassengerNotFoundException("Sorry! No Passenger Is Present With This Id Number");
		}
		passengerDao.save(passenger);
		return passenger;
	}
	
	@Override
	  public Passenger getPassengerById(Integer passengerId) {
	    Optional<Passenger> passenger = passengerDao.findById(passengerId);
	    if (!passenger.isPresent()) {
	      throw new PassengerNotFoundException("Passenger not found");
	    }
	    return passenger.get();
	  }
	
	@Override
	public User getUserDetailsForUpdate(Integer userId) {
	    Optional<User> user = userDao.findById(userId);
	    if (!user.isPresent()) {
	        throw new UserDoesnotExistException("Sorry! User not found");
	    }
	    return user.get();
	}

}
