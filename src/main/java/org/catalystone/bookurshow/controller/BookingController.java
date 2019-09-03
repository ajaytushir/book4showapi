package org.catalystone.bookurshow.controller;

import java.util.List;

import org.catalystone.bookurshow.model.APIException;
import org.catalystone.bookurshow.model.BookingModel;
import org.catalystone.bookurshow.service.MovieTheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	private MovieTheatreService movieTheatreService;
	
	@PostMapping("/add")
	public BookingModel add(@RequestBody BookingModel bookingModel) throws APIException {
		return movieTheatreService.addBooking(bookingModel);
	}
	
	@GetMapping("/list")
	public List<BookingModel> list() {
		return movieTheatreService.listBookings();
	}
}
