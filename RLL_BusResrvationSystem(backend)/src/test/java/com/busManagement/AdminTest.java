package com.busManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.busManagement.Dao.AdminDao;
import com.busManagement.Dao.BookingDetailsDao;
import com.busManagement.Dao.BusDetailsDao;
import com.busManagement.Dao.UserDao;
import com.busManagement.Dao.PassengerDao;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.Admin;
import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.User;
import com.busManagement.entity.Passenger;


import com.busManagement.utils.AdminAuth;
import com.busManagement.service.AdminService;
import com.busManagement.serviceImpl.AdminServiceImpl;
import com.busManagement.exception.AdminDoesnotExistException;
import com.busManagement.exception.BusDetailsNotFoundException;
import com.busManagement.exception.UserDoesnotExistException;
import com.busManagement.exception.BookingDoesNotFoundException;


@SpringBootTest
public class AdminTest {

	@Autowired
	private AdminService busService;
	
	@MockBean
	private BusDetailsDao repository;
	@Mock
    private AdminDao adminDao;
	@Mock
    private UserDao userDao;
	@Mock
    private BusDetailsDao busDetailsDao;
	@Mock
    private BookingDetailsDao bookingDao;
	@Mock
    private PassengerDao passengerDao;

    @InjectMocks
    private AdminServiceImpl adminService;
    
 
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }
    //ADMIN LOGIN 
    @Test
    public void testAdminLogin_Success() {
        AdminAuth auth = new AdminAuth(1, "password");
        Admin mockAdmin = new Admin(1, "password", "AdminName");

        when(adminDao.findById(auth.getAdminId())).thenReturn(Optional.of(mockAdmin));

        Admin result = adminService.adminLogin(auth);

        assertEquals(mockAdmin, result);
        verify(adminDao, times(1)).findById(auth.getAdminId());
    }


    @Test
    public void testAdminLogin_AdminNotFound() {
        AdminAuth auth = new AdminAuth(1, "password");

        when(adminDao.findById(auth.getAdminId())).thenReturn(Optional.empty());

        assertThrows(AdminDoesnotExistException.class, () -> adminService.adminLogin(auth));
        verify(adminDao, times(1)).findById(auth.getAdminId());
    }

    @Test
    public void testAdminLogin_InvalidPassword() {
        AdminAuth auth = new AdminAuth(1, "wrong_password");
        Admin mockAdmin = new Admin(1, "password", "AdminName");

        when(adminDao.findById(auth.getAdminId())).thenReturn(Optional.of(mockAdmin));

        assertThrows(AdminDoesnotExistException.class, () -> adminService.adminLogin(auth));
        verify(adminDao, times(1)).findById(auth.getAdminId());
    }
    //GET ADMIN BY ID
    @Test
    public void testGetAdmin_Success() {
        int adminId = 1;
        Admin mockAdmin = new Admin(adminId, "password", "AdminName");

        when(adminDao.findById(adminId)).thenReturn(Optional.of(mockAdmin));

        Admin result = adminService.getAdmin(adminId);

        assertEquals(mockAdmin, result);
        verify(adminDao, times(1)).findById(adminId);
    }

    @Test
    public void testGetAdmin_AdminNotFound() {
        int adminId = 1;

        when(adminDao.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(AdminDoesnotExistException.class, () -> adminService.getAdmin(adminId));
        verify(adminDao, times(1)).findById(adminId);
    }
    


    
	//Test to get all the bus details
	@Test
	public void testGetBusDetails() {
		when(repository.findAll()).thenReturn(Stream
				.of(new BusDetails("delhi", "jaipur", 25, "16-04-2022", "16-04-2022", 
						"23:00", "17:00", "Pass one", 7899.00)).collect(Collectors.toList()));
		assertEquals(1, busService.getAllBusDetails().size());
	}
	
	//Test to display all the bus details
	@Test
	public void testDisplayAllBusDetails() {
		when(repository.findAll()).thenReturn(Stream
				.of(new BusDetails("Banglore", "mumbai", 25, "17-05-2022", "17-05-2022", "23:00", 
						"17:00", "prasanna", 7899.00), 
						new BusDetails("bangalore", "hyderabad", 48, "12-05-2022", "13-05-2020", 
								"05:20", "23:05", "Pass one", 12899.00)).collect(Collectors.toList()));
		assertEquals(2, busService.getAllBusDetails().size());
	}

    //ADDING BUS DETAILS
	@Test
	public void testAddBusDetails() {
		
		BusDetails busObj = new BusDetails("pune", "delhi", 56, "18-05-2022", "18-05-2022", 
				"05:00", "23:55", "Pass one", 9899.55);
		when(repository.save(busObj)).thenReturn(busObj);
		assertEquals(busObj, busService.addBusDetails(busObj));
	}
	

    @Test
    public void testDeleteBus_NonExistentBus_ThrowsBusDetailsNotFoundException() {
        Integer nonExistentBusNumber = 999; // Modify this with a non-existent bus number
        
        when(busDetailsDao.findById(nonExistentBusNumber)).thenReturn(Optional.empty());

        assertThrows(BusDetailsNotFoundException.class, () -> adminService.deleteBus(nonExistentBusNumber));
    }
    //DELETE USER
    @Test
    public void testDeleteUser_ValidUserId_DeletesUser() {
        Integer validUserId = 123; // Modify this with a valid user ID
        
        User user = new User();
        user.setUserId(validUserId);
        
        when(userDao.findById(validUserId)).thenReturn(Optional.of(user));

        adminService.deleteUser(validUserId);

        verify(userDao, times(1)).deleteById(validUserId);
    }


    @Test
    public void testDeleteUser_NonExistentUser_ThrowsUserDoesnotExistException() {
        Integer nonExistentUserId = 999; // Modify this with a non-existent user ID
        
        when(userDao.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> adminService.deleteUser(nonExistentUserId));
    }
    //GET ALL USERS
    @Test
    public void testViewAllUsers_ReturnsListOfUsers() {
        User user1 = new User(1, "user1", "password1", 1234567890L, "user1@example.com");
        User user2 = new User(2, "user2", "password2", 9876543210L, "user2@example.com");
        
        List<User> userList = Arrays.asList(user1, user2);
        
        when(userDao.findAll()).thenReturn(userList);

        List<User> result = adminService.getAllUsers();

        assertEquals(2, result.size()); // Check if the returned list contains 2 users
        assertEquals("user1", result.get(0).getUserName());
        assertEquals("user2", result.get(1).getUserName());
    }
    //UPDATE USER
    @Test
    public void testUpdateUser_ValidUser_UpdatesUser() {
        User existingUser = new User(1, "existingUser", "existingPassword", 1234567890L, "existing@example.com");
        
        when(userDao.findById(existingUser.getUserId())).thenReturn(Optional.of(existingUser));
        when(userDao.save(existingUser)).thenReturn(existingUser);

        adminService.updateUser(existingUser);

        verify(userDao, times(1)).save(existingUser);
    }
    
    
    @Test
    public void testUpdateUser_NonExistentUser_ThrowsException() {
        User nonExistentUser = new User(2, "nonExistentUser", "newPassword", 9876543210L, "new@example.com");

        when(userDao.findById(nonExistentUser.getUserId())).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> {
            adminService.updateUser(nonExistentUser);
        });
    }
    

    @Test
    public void testUpdateBus_NonExistentBus_ThrowsException() {
        int nonExistentBusNumber = 456;

        when(busDetailsDao.findById(nonExistentBusNumber)).thenReturn(Optional.empty());

        BusDetails nonExistentBus = new BusDetails(nonExistentBusNumber, "Source", "Destination", 50,
                "2023-08-10", "2023-08-11", "10:00 AM", "12:00 PM", "BusVendor", 100.0);

        assertThrows(BusDetailsNotFoundException.class, () -> {
            adminService.updateBus(nonExistentBus);
        });
    }
    //GET BOOKING BY USERID
    @Test
    public void testGetBookingByUserId_UserExists_ReturnsBookingDetailsList() {
        User existingUser = new User(1, "user1", "password", 1234567890L, "user1@example.com");
        List<BookingDetails> bookingList = new ArrayList<>();
        bookingList.add(new BookingDetails());
        existingUser.setBookingDetails(bookingList);

        when(userDao.findById(existingUser.getUserId())).thenReturn(Optional.of(existingUser));

        List<BookingDetails> result = adminService.getBookingByUserId(existingUser.getUserId());

        assertEquals(bookingList, result);
    }

    @Test
    public void testGetBookingByUserId_UserDoesNotExist_ThrowsException() {
        int nonExistentUserId = 123;

        when(userDao.findById(nonExistentUserId)).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> {
            adminService.getBookingByUserId(nonExistentUserId);
        });
    }
    

    @Test
    public void testDeleteBooking_BookingDoesNotExist_ThrowsException() {
        int userId = 1;
        int bookingId = 123;

        when(bookingDao.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> {
            adminService.deleteBooking(bookingId, userId);
        });

        verify(bookingDao, times(1)).findById(bookingId);
    }

    @Test
    public void testDeleteBooking_UserDoesNotExist_ThrowsException() {
        int userId = 1;
        int bookingId = 123;

        BookingDetails booking = new BookingDetails();
        booking.setBookingId(bookingId);

        when(bookingDao.findById(bookingId)).thenReturn(Optional.of(booking));
        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserDoesnotExistException.class, () -> {
            adminService.deleteBooking(bookingId, userId);
        });

        verify(bookingDao, times(1)).findById(bookingId);
        verify(userDao, times(1)).findById(userId);
    }
    //GET PASSENGER BY BOOKING ID
    /*@Test
    public void testGetPassengersByBooking_BookingExists_ReturnsPassengers() {
        int bookingId = 123;

        BookingDetails booking = new BookingDetails();
        booking.setBookingId(bookingId);

        Passenger passenger1 = new Passenger();
        passenger1.setPassengerId(1);
        passenger1.setName("John");
        passenger1.setAge(25);
        passenger1.setLuggage(20.0);

        Passenger passenger2 = new Passenger();
        passenger2.setPassengerId(2);
        passenger2.setName("Jane");
        passenger2.setAge(30);
        passenger2.setLuggage(15.0);

        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger1);
        passengers.add(passenger2);

        when(bookingDao.findById(bookingId)).thenReturn(Optional.of(booking));
        when(passengerDao.findByBooking(booking)).thenReturn(passengers);

        List<Passenger> result = adminService.getPassengersByBooking(bookingId);

        assertEquals(passengers, result);

        verify(bookingDao, times(1)).findById(bookingId);
        verify(passengerDao, times(1)).findByBooking(booking);
    }

    @Test
    public void testGetPassengersByBooking_BookingDoesNotExist_ThrowsException() {
        int bookingId = 123;

        when(bookingDao.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(BookingDoesNotFoundException.class, () -> {
            adminService.getPassengersByBooking(bookingId);
        });

        verify(bookingDao, times(1)).findById(bookingId);
        verifyNoInteractions(passengerDao);
    }*/

    //GET ALL PASSENGERS
    

    @Test
    public void testGetPassengersByBooking_ValidBookingId_ReturnsPassengers() {
        // Arrange
        Integer bookingId = 1;
        BookingDetails bookingDetails = new BookingDetails();
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());
        passengers.add(new Passenger());
        bookingDetails.setPassengers(passengers);
        
        when(bookingDao.findById(bookingId)).thenReturn(Optional.of(bookingDetails));

        // Act
        List<Passenger> result = adminService.getPassengersByBooking(bookingId);

        // Assert
        assertEquals(passengers.size(), result.size());
    }

   
	
}
