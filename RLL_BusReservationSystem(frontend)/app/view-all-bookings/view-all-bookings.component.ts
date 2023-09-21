import { Component, NgZone } from '@angular/core';
import { AdminService } from '../services/admin.service';

@Component({
  selector: 'app-view-all-bookings',
  templateUrl: './view-all-bookings.component.html',
  styleUrls: ['./view-all-bookings.component.css']
})
export class ViewAllBookingsComponent {
  userId: string = '';
  bookings: any[] = [];

  constructor(
    private adminService: AdminService,
    private ngZone: NgZone
  ) {}

  fetchBookings() {
    this.adminService.getBookingsByUser(this.userId).subscribe(
      data => {
        this.bookings = data;
        console.log(data); // Check the data in the console
      },
      error => {
        console.error('Error fetching data:', error);
      }
    );
  }

  deleteBooking(bookingId: number, userId: any) {
    if (confirm('Are you sure you want to cancel this booking?')) {
      this.adminService.deleteBooking(bookingId, userId).subscribe(
        response => {
          // After successful deletion, reload the booking list
          this.fetchBookings();
          // Manually trigger change detection using NgZone
          this.ngZone.run(() => {});
        },
        error => {
          console.error(error);
        }
      );
    }
  }
}
