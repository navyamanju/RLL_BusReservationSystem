package com.busManagement.serviceImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.busManagement.Dao.*;
import com.busManagement.entity.Admin;
import com.busManagement.entity.BookingDetails;
import com.busManagement.entity.BusDetails;
import com.busManagement.entity.Passenger;
import com.busManagement.entity.User;
import com.busManagement.exception.AdminAlreadyExistException;
import com.busManagement.exception.AdminDoesnotExistException;
import com.busManagement.exception.BookingDoesNotFoundException;
import com.busManagement.exception.BusDetailsNotFoundException;
import com.busManagement.exception.NullAdminException;
import com.busManagement.exception.NullBusDetailsException;
import com.busManagement.exception.NullUserException;
import com.busManagement.exception.UserDoesnotExistException;
import com.busManagement.service.AdminService;
import com.busManagement.utils.AdminAuth;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDao adminDao;

	@Autowired
	BusDetailsDao busDao;
	
	@Autowired
	PassengerDao passengerDao;
	
	@Autowired
	BookingDetailsDao bookingDao;
	
	@Autowired
	UserDao userDao;

	//adding admin to the database
	@Override
	public Admin addAdmin(Admin admin) {
		if (admin == null)
			throw new NullAdminException("Please Enter Data");
		Integer adminId = (int) ((Math.random() * 900) + 100); //
		
		admin.setAdminId(adminId);
		Optional<Admin> checkAdmin = adminDao.findById(admin.getAdminId());
		if (checkAdmin.isPresent()) {
			throw new AdminAlreadyExistException("admin already exist exception");
		} else {
			adminDao.save(admin);
			System.out.println(adminId);
			return admin;
		}
	}
	
	
//for getting admin by ID
	@Override
	public Admin getAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("Please Enter Data");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin does not exist ");
		}
		return admin.get();
	}
	
	//FOR DELETING ADMIN
	@Override
	public void deleteAdmin(Integer adminId) {
		if (adminId == null)
			throw new NullAdminException("Please Enter Data");
		Optional<Admin> admin = adminDao.findById(adminId);
		if (!admin.isPresent()) {
			throw new AdminDoesnotExistException("admin Doesnot Exist Exception");
		}
		adminDao.deleteById(adminId);
	}
    
	//admin Login 
	@Override
	public Admin adminLogin(AdminAuth auth) {
		if (auth == null) {
			throw new NullAdminException("Please Enter Data");
		}
		Optional<Admin> admin = adminDao.findById(auth.getAdminId());
		if (admin.isPresent()) {
			
			///////Check
			if (admin.get().getAdminId() == auth.getAdminId() && admin.get().getPassword().equals(auth.getPassword())) {
				return admin.get();
			} else {
				throw new AdminDoesnotExistException("Invalid Login ID or Password");
			}
			
		} else
			throw new AdminDoesnotExistException("Please Enter Data");
	}
     
	
	//For getting all the bus details
	@Override
	public List<BusDetails> getAllBusDetails() {
		return busDao.findAll();
	}
    
	//For adding bus details
	@Override
	public BusDetails addBusDetails(BusDetails details) {
		if (details == null) {
			throw new NullBusDetailsException("Please Enter Data");
		}
		Integer busNumber = (int) ((Math.random() * 9000) + 1000);
		details.setBusNumber(busNumber);
		busDao.save(details);
		return details;
	}

	//Deleting Bus By ID
	@Override
	public void deleteBus(Integer busNumber) {
		if (busNumber == null)
			throw new NullBusDetailsException("Please Enter Data");
		Optional<BusDetails> details = busDao.findById(busNumber);
		if (!details.isPresent()) {
			throw new BusDetailsNotFoundException("Bus Details Not Found");
		}
		busDao.deleteById(busNumber);
	}
    
	//Updating the bus details By ID
	@Override
	public BusDetails updateBus(BusDetails details) {
		if (details == null)
			throw new NullBusDetailsException("Please Enter Data");
		Optional<BusDetails> busDetails = busDao.findById(details.getBusNumber());
		if (!busDetails.isPresent()) {
			throw new BusDetailsNotFoundException("Bus with busNumber: " + details.getBusNumber() + " not exists..");
		}
		busDao.save(details);
		return details;
	}
	
	
	//Getting all the passengers
	public List<Passenger> getAllPassengers(){
		return passengerDao.findAll();
	}
	
	//Getting list of passengers by ID
	public List<Passenger> getPassengersByBooking(Integer id){
		if (id == null) throw new BookingDoesNotFoundException("Please Enter Data");
		Optional<BookingDetails> details = bookingDao.findById(id);
		if (!details.isPresent())
			throw new BookingDoesNotFoundException("Booking Not Found");
		return details.get().getPassengers();
	}
	
	//Getting all the Users
		public  List<User> getAllUsers(){
			return userDao.findAll();
		}


		public void deleteUser(Integer id) {
	        if (id == null)
	            throw new IllegalArgumentException("Please Enter Data");

	        Optional<User> user = userDao.findById(id);
	        if (!user.isPresent()) {
	            throw new UserDoesnotExistException("User Does not Exist");
	        }

	        userDao.deleteById(id);
	    }


		public List<BookingDetails> getBookingByUserId(Integer userId) {
			Optional<User> user = userDao.findById(userId);
			if (!user.isPresent()) {
				throw new UserDoesnotExistException("Oops! user id not found");
			}
			return user.get().getBookingDetails();
		}


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
		public BusDetails getBusDetails(Integer busNumber) {
		    if (busNumber == null) {
		        throw new NullBusDetailsException("Please Enter Data");
		    }
		    Optional<BusDetails> details = busDao.findById(busNumber);
		    if (!details.isPresent()) {
		        throw new BusDetailsNotFoundException("Oops! No Bus Service Found");
		    }
		    return details.get();
		}

		
		


}
