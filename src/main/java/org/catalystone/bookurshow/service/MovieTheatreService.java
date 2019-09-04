package org.catalystone.bookurshow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.catalystone.bookurshow.config.security.UserAuthentication;
import org.catalystone.bookurshow.dao.IBookingRepository;
import org.catalystone.bookurshow.dao.IMovieRepository;
import org.catalystone.bookurshow.dao.IMovieScheduleRepository;
import org.catalystone.bookurshow.dao.IMovieTheatreRepository;
import org.catalystone.bookurshow.dao.IUserRepository;
import org.catalystone.bookurshow.domain.Booking;
import org.catalystone.bookurshow.domain.Movie;
import org.catalystone.bookurshow.domain.MovieSchedule;
import org.catalystone.bookurshow.domain.MovieTheatre;
import org.catalystone.bookurshow.domain.User;
import org.catalystone.bookurshow.model.APIException;
import org.catalystone.bookurshow.model.BookingModel;
import org.catalystone.bookurshow.model.MovieModel;
import org.catalystone.bookurshow.model.MovieScheduleModel;
import org.catalystone.bookurshow.model.MovieTheatreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieTheatreService {

	@Autowired
	private IMovieTheatreRepository movieTheatreRepository;

	@Autowired
	private IMovieRepository movieRepository;

	@Autowired
	private IMovieScheduleRepository movieScheduleRepository;

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IBookingRepository bookRepository;

	@Autowired
	private UserAuthentication userAuthentication;

	public MovieTheatreModel addMovieTheatre(MovieTheatreModel movieTheatreModel) {
		MovieTheatre movieTheatre = movieTheatreModel.getDomain();
		movieTheatre = movieTheatreRepository.save(movieTheatre);
		return MovieTheatreModel.getInstance(movieTheatre);
	}

	public List<MovieTheatreModel> listMovieTheatres() {
		List<MovieTheatre> movieTheatres = movieTheatreRepository.findAll();
		return movieTheatres.stream().map(movieTheatre -> MovieTheatreModel.getInstance(movieTheatre))
				.collect(Collectors.toList());
	}

	public MovieModel addMovie(MovieModel movieModel) {
		Movie movie = movieModel.getDomain();
		movie = movieRepository.save(movie);
		return MovieModel.getInstance(movie);
	}

	public List<MovieModel> listMovies() {
		List<Movie> movies = movieRepository.findAll();
		return movies.stream().map(movie -> MovieModel.getInstance(movie)).collect(Collectors.toList());
	}

	public MovieScheduleModel addMovieSchedule(MovieScheduleModel movieScheduleModel) throws APIException {
		MovieSchedule movieSchedule = movieScheduleModel.getDomain();
		Movie movie = findMovieById(movieScheduleModel.getMovie());

		if (movie == null) {
			throw new APIException("MS-01", "Please select valid movie.");
		}

		movieSchedule.setMovie(movie);

		MovieTheatre movieTheatre = findMovieTheatreById(movieScheduleModel.getMovieTheatre());
		if (movieTheatre == null) {
			throw new APIException("MS-02", "Please select valid movie theatre.");
		}
		movieSchedule.setMovieTheatre(movieTheatre);

		movieSchedule = movieScheduleRepository.save(movieSchedule);
		return MovieScheduleModel.getInstance(movieSchedule);
	}

	public List<MovieScheduleModel> listMovieSchedules(LocalDate date) {
		List<MovieSchedule> movieSchedules = movieScheduleRepository.findByDate(date);
		return movieSchedules.stream().map(movieSchedule -> MovieScheduleModel.getInstance(movieSchedule))
				.collect(Collectors.toList());
	}

	public BookingModel addBooking(BookingModel bookingModel) throws APIException {
		Booking booking = bookingModel.getDomain();
		
		if (booking.getSeatCount() >9) {
			throw new APIException("BK-01", "Can not book more than 9 seats.");
		}
		String username = userAuthentication.getLoggedInUser();
		User user = userRepository.findByEmail(username);
		booking.setUser(user);

		MovieSchedule movieSchedule = findMovieScheduleId(bookingModel.getMovieScheduleId());
		if (movieSchedule == null) {
			throw new APIException("BK-02", "Please select valid movie schedule.");
		}
		booking.setMovieSchedule(movieSchedule);
		
		Integer seatBooked = bookRepository.getTotalSeatCount(movieSchedule.getId(), booking.getBookingDate());
		seatBooked = seatBooked==null?0:seatBooked;
		Integer totalSeat = movieSchedule.getMovieTheatre().getSeatCount();	
		if(seatBooked + booking.getSeatCount() > totalSeat) {
			throw new APIException("BK-03", String.format("Only %d seats available for this show.", totalSeat-seatBooked));
		}
		
		booking = bookRepository.save(booking);
		return BookingModel.getInstance(booking);
	}

	public List<BookingModel> listBookings() {
		String username = userAuthentication.getLoggedInUser();
		User user = userRepository.findByEmail(username);

		List<Booking> bookings = bookRepository.findByUserId(user.getId());
		return bookings.stream().map(booking -> BookingModel.getInstance(booking)).collect(Collectors.toList());
	}

	private Movie findMovieById(Long movieId) {
		Optional<Movie> movie = movieRepository.findById(movieId);

		if (movie.isPresent()) {
			return movie.get();
		} else {
			return null;
		}
	}

	private MovieTheatre findMovieTheatreById(Long movieTheatreId) {
		Optional<MovieTheatre> movieTheatre = movieTheatreRepository.findById(movieTheatreId);

		if (movieTheatre.isPresent()) {
			return movieTheatre.get();
		} else {
			return null;
		}
	}

	private MovieSchedule findMovieScheduleId(Long movieScheduleId) {
		Optional<MovieSchedule> movieSchedule = movieScheduleRepository.findById(movieScheduleId);

		if (movieSchedule.isPresent()) {
			return movieSchedule.get();
		} else {
			return null;
		}
	}
}
