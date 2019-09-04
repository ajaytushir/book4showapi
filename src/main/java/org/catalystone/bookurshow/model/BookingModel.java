package org.catalystone.bookurshow.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.catalystone.bookurshow.domain.Booking;
import org.catalystone.bookurshow.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BookingModel {
	private Long id;
	private Long userId;
	private Long movieScheduleId;
	private String username;
	private String movieName;
	private String movieTheatreName;
	private Integer seatCount;
	private String bookingDate;
	private String created;
	
	@JsonIgnore
	public Booking getDomain() {
		Booking booking = new Booking();
		booking.setId(this.getId());
		booking.setSeatCount(this.getSeatCount());
		booking.setBookingDate(LocalDate.parse(this.getBookingDate()));
		return booking;
	}
	
	public static BookingModel getInstance(Booking booking) {
		BookingModel bookingModel = new BookingModel();
		bookingModel.setId(booking.getId());
		bookingModel.setMovieName(booking.getMovieSchedule().getMovie().getName());
		bookingModel.setMovieScheduleId(booking.getMovieSchedule().getId());
		bookingModel.setMovieTheatreName(booking.getMovieSchedule().getMovieTheatre().getName());
		bookingModel.setSeatCount(booking.getSeatCount());
		bookingModel.setUserId(booking.getUser().getId());
		bookingModel.setUsername(booking.getUser().getEmail());
		bookingModel.setBookingDate(booking.getBookingDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
		bookingModel.setCreated(booking.getCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		return bookingModel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMovieScheduleId() {
		return movieScheduleId;
	}

	public void setMovieScheduleId(Long movieScheduleId) {
		this.movieScheduleId = movieScheduleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getMovieTheatreName() {
		return movieTheatreName;
	}

	public void setMovieTheatreName(String movieTheatreName) {
		this.movieTheatreName = movieTheatreName;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}
}
