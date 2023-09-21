package com.busManagement;
import com.busManagement.exception.BusDetailsNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.busManagement.Dao.UserDao;
import com.busManagement.Dao.BookingDetailsDao;
import com.busManagement.Dao.BusDetailsDao;
import com.busManagement.Dao.PassengerDao;
import com.busManagement.entity.User;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.Passenger;



import com.busManagement.exception.NullUserException;
import com.busManagement.exception.UserDoesnotExistException;
import com.busManagement.exception.PassengerNotFoundException;
import com.busManagement.service.UserService;
import com.busManagement.utils.UserAuth;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserDao dao;
    @MockBean
    private BusDetailsDao busdao;
    @MockBean
    private PassengerDao passengerdao;
    @MockBean
    private BookingDetailsDao bookingdao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
  //ADDING USER
  	@Test
  	public void testAddUser() {
  		User user = new User();
  		user.setUserName("user one");
  		user.setPhone(123456789l);
  		user.setEmail("userone@gmail.com");
  		user.setPassword("user12345");
  		when(dao.save(user)).thenReturn(user);
  		assertEquals(user, userService.addUser(user));
  	}
    //USER LOGIN
    @Test
    public void testUserLoginValidCredentials() {
        UserAuth auth = new UserAuth(123, "password");
        User user = new User();
        user.setUserId(auth.getUserId());
        user.setPassword(auth.getPassword());

        when(dao.findById(auth.getUserId())).thenReturn(Optional.of(user));

        User result = userService.userLogin(auth);

        assertNotNull(result);
        assertEquals(auth.getUserId(), result.getUserId());
    }

    @Test
    public void testUserLoginInvalidCredentials() {
        UserAuth auth = new UserAuth(123, "invalidPassword");
        User user = new User();
        user.setUserId(auth.getUserId());
        user.setPassword("correctPassword");

        when(dao.findById(auth.getUserId())).thenReturn(Optional.of(user));

        UserDoesnotExistException exception = assertThrows(UserDoesnotExistException.class, () -> {
            userService.userLogin(auth);
        });

        assertEquals("Invalid login ID or Password", exception.getMessage());
    }

    @Test
    public void testUserLoginNullAuth() {
        NullUserException exception = Assertions.assertThrows(NullUserException.class, () -> {
            userService.userLogin(null);
        });

        assertEquals("Please Enter Data", exception.getMessage());
    }
    //GET USER BY ID
    @Test
    public void testGetUserValidUserId() {
        int userId = 123;
        User user = new User();
        user.setUserId(userId);

        when(dao.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUser(userId);

        assertEquals(userId, result.getUserId());
    }

    @Test
    public void testGetUserInvalidUserId() {
        int userId = 123;

        when(dao.findById(userId)).thenReturn(Optional.empty());

        UserDoesnotExistException exception = assertThrows(UserDoesnotExistException.class, () -> {
            userService.getUser(userId);
        });

        assertEquals("Sorry! User not found", exception.getMessage());
    }

    @Test
    public void testGetUserNullUserId() {
        NullUserException exception = assertThrows(NullUserException.class, () -> {
            userService.getUser(null);
        });

        assertEquals("Please Enter Data", exception.getMessage());
    }
    //UPDATE USER
    @Test
    public void testUpdateUserValidUser() {
        int userId = 123;
        User user = new User();
        user.setUserId(userId);

        when(dao.findById(userId)).thenReturn(Optional.of(user));

        User updatedUser = new User();
        updatedUser.setUserId(userId);
        updatedUser.setUserName("John Doe");

        userService.updateUser(updatedUser);
    }

    @Test
    public void testUpdateUserInvalidUser() {
        int userId = 123;

        when(dao.findById(userId)).thenReturn(Optional.empty());

        UserDoesnotExistException exception = assertThrows(UserDoesnotExistException.class, () -> {
            userService.updateUser(new User());
        });

        assertEquals("Sorry! User not found", exception.getMessage());
    }

    @Test
    public void testUpdateUserNullUser() {
        NullUserException exception = assertThrows(NullUserException.class, () -> {
            userService.updateUser(null);
        });

        assertEquals("Please Enter Data", exception.getMessage());
    }
    //DELETE USER
    @Test
    public void testDeleteUserValidUser() {
        int userId = 123;
        User user = new User();
        user.setUserId(userId);

        when(dao.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        verify(dao).deleteById(userId);
    }

    @Test
    public void testDeleteUserInvalidUser() {
        int userId = 123;

        when(dao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> {
            userService.deleteUser(userId);
        });
    }

    @Test
    public void testDeleteUserNullUserId() {
        assertThrows(NullUserException.class, () -> {
            userService.deleteUser(null);
        });
    }
    //TO FIND BUS WITH ARRIVALSTOP AND DEPARTURESTOP AND DATE
    @Test
    public void testFindByRouteAndDateValidDetails() {
        String arrivalBusstop = "BusStopA";
        String departureBusstop = "BusStopB";
        String date = "2023-08-10";

        BusDetails bus1 = new BusDetails();
        bus1.setBusNumber(1);
        bus1.setArrivalBusstop(arrivalBusstop);
        bus1.setDepartureBusstop(departureBusstop);
        bus1.setDepartureDate(date);

        List<BusDetails> busList = new ArrayList<>();
        busList.add(bus1);

        when(busdao.findByRouteDate(arrivalBusstop.toLowerCase(), departureBusstop.toLowerCase())).thenReturn(busList);

        BusDetails result = userService.findByRouteAndDate(arrivalBusstop, departureBusstop, date);

        assertEquals(bus1, result);
    }

    @Test
    public void testFindByRouteAndDateInvalidDetails() {
        String arrivalBusstop = "BusStopA";
        String departureBusstop = "BusStopB";
        String date = "2023-08-10";

        List<BusDetails> busList = new ArrayList<>();

        when(busdao.findByRouteDate(arrivalBusstop.toLowerCase(), departureBusstop.toLowerCase())).thenReturn(busList);

        Executable executable = () -> userService.findByRouteAndDate(arrivalBusstop, departureBusstop, date);

        assertThrows(BusDetailsNotFoundException.class, executable);
    }
    //GET BOOKING BY USERID
    @Test
    public void testGetBookingByUserId() {
        // Mocking user
        User user = new User();
        user.setUserId(1);
        List<BookingDetails> bookingList = new ArrayList<>();
        BookingDetails booking1 = new BookingDetails();
        booking1.setBookingId(101);
        bookingList.add(booking1);
        user.setBookingDetails(bookingList);

        // Mocking userDao.findById
        when(dao.findById(1)).thenReturn(Optional.of(user));

        // Call the service method
        List<BookingDetails> result = userService.getBookingByUserId(1);

        // Assertions
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(101, result.get(0).getBookingId());

        // Verify that userDao.findById was called
        verify(dao, times(1)).findById(1);
    }

    @Test
    public void testGetBookingByUserId_UserNotFound() {
        // Mocking userDao.findById
        when(dao.findById(1)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(UserDoesnotExistException.class, () -> userService.getBookingByUserId(1));

        // Verify that userDao.findById was called
        verify(dao, times(1)).findById(1);
    }
    //GET BUS BY BUS NUMBER
    @Test
    public void testGetBusByBusNumber() {
        // Mocking bus details
        BusDetails busDetails = new BusDetails();
        busDetails.setBusNumber(101);
        busDetails.setBusVendor("XYZ Bus Service");

        // Mocking busDetailsDao.findById
        when(busdao.findById(101)).thenReturn(Optional.of(busDetails));

        // Call the service method
        BusDetails result = userService.getBusByBusNumber(101);

        // Assertions
        assertNotNull(result);
        assertEquals(101, result.getBusNumber());
        assertEquals("XYZ Bus Service", result.getBusVendor());

        // Verify that busDetailsDao.findById was called
        verify(busdao, times(1)).findById(101);
    }

    @Test
    public void testGetBusByBusNumber_BusNotFound() {
        // Mocking busDetailsDao.findById
        when(busdao.findById(101)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(BusDetailsNotFoundException.class, () -> userService.getBusByBusNumber(101));

        // Verify that busDetailsDao.findById was called
        verify(busdao, times(1)).findById(101);
    }
    //UPDATE PASSENGER
    @Test
    public void testUpdatePassenger() {
        // Mocking existing passenger
        Passenger existingPassenger = new Passenger();
        existingPassenger.setPassengerId(1);
        existingPassenger.setName("John");

        // Mocking passenger data
        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setPassengerId(1);
        updatedPassenger.setName("John Doe");

        // Mocking passengerDao.findById
        when(passengerdao.findById(1)).thenReturn(Optional.of(existingPassenger));

        // Mocking passengerDao.save
        when(passengerdao.save(updatedPassenger)).thenReturn(updatedPassenger);

        // Call the service method
        Passenger result = userService.updatePassenger(updatedPassenger);

        // Assertions
        assertNotNull(result);
        assertEquals("John Doe", result.getName());

        // Verify that passengerDao.findById and passengerDao.save were called
        verify(passengerdao, times(1)).findById(1);
        verify(passengerdao, times(1)).save(updatedPassenger);
    }

    @Test
    public void testUpdatePassenger_PassengerNotFound() {
        // Mocking passengerDao.findById
        when(passengerdao.findById(1)).thenReturn(Optional.empty());

        // Create a passenger with a non-existing ID
        Passenger passenger = new Passenger();
        passenger.setPassengerId(1);

        // Call the service method and expect an exception
        assertThrows(PassengerNotFoundException.class, () -> userService.updatePassenger(passenger));

        // Verify that passengerDao.findById was called
        verify(passengerdao, times(1)).findById(1);
        // Verify that passengerDao.save was not called
        verify(passengerdao, never()).save(any());
    }
    
   
    // /deleteBooking/{bookingId}/{userId}
    // /addBooking/{userId}/{busNumber}
    
    
}
    









