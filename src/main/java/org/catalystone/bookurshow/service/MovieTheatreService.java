package org.catalystone.bookurshow.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private UserAuthentication userAuthentication;

	private ObjectMapper objectMapper = new ObjectMapper();

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

	public MovieTheatreModel updateMovieTheatre(MovieTheatreModel movieTheatreModel) throws APIException {
		MovieTheatre movieTheatre = findMovieTheatreById(movieTheatreModel.getId());
		
		movieTheatre.setAddress(movieTheatreModel.getAddress());
		movieTheatre.setName(movieTheatreModel.getName());
		movieTheatre.setSeatCount(movieTheatreModel.getSeatCount());
		
		movieTheatre = movieTheatreRepository.save(movieTheatre);
		return MovieTheatreModel.getInstance(movieTheatre);
	}

	public MovieTheatreModel deleteMovieTheatre(Long movieTheatreId) throws APIException {

		MovieTheatre movieTheatre = findMovieTheatreById(movieTheatreId);
		
		List<MovieSchedule> movieSchedules = movieScheduleRepository.findTheatreScheduleByDate(LocalDate.now(), movieTheatreId);
		if(movieSchedules==null || movieSchedules.size()>0) {
			throw new APIException("MT-02", "This theatre has already scheduled some shows in future.");
		}
		movieTheatre.setDeleted(true);
		movieTheatre = movieTheatreRepository.save(movieTheatre);
		return MovieTheatreModel.getInstance(movieTheatre);
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
		
		if (movieTheatre.isDeleted()) {
			throw new APIException("MS-02", "Movie theatre is no longer exist.");
		}
		movieSchedule.setMovieTheatre(movieTheatre);

		movieSchedule = movieScheduleRepository.save(movieSchedule);
		return MovieScheduleModel.getInstance(movieSchedule);
	}

	public void addMovieScheduleBatch(LocalDate scheduleDateStart, LocalDate endDate) throws APIException {

		List<MovieTheatre> movieTheatres = movieTheatreRepository.findAll();
		List<Movie> movies = movieRepository.findAll();
		Function<List<Movie>, Movie> getRandomMovie = (List<Movie> movieList) -> {
			Random r = new Random();
			Integer index = r.nextInt(movieList.size());
			// System.out.println("Index generated : " + index);
			return movieList.get(index);
		};

		for (MovieTheatre movieTheatre : movieTheatres) {
			List<MovieSchedule> movieScheduleList = new ArrayList<MovieSchedule>(1480);
			LocalDate scheduleDate = scheduleDateStart;
			while (!scheduleDate.isEqual(endDate)) {
				for (MovieSchedule.TimeSlot timeSlot : MovieSchedule.TimeSlot.values()) {
					MovieSchedule schedule = new MovieSchedule();
					schedule.setFrom(scheduleDate);
					schedule.setTo(scheduleDate);
					schedule.setMovie(getRandomMovie.apply(movies));
					schedule.setMovieTheatre(movieTheatre);
					schedule.setPrice(BigDecimal.TEN);
					schedule.setTimeSlot(timeSlot);
					movieScheduleList.add(schedule);
				}
				scheduleDate = scheduleDate.plusDays(1);
			}
			movieScheduleRepository.saveAll(movieScheduleList);
		}
	}

	public List<MovieScheduleModel> listMovieSchedules(LocalDate date) {
		List<MovieSchedule> movieSchedules = movieScheduleRepository.findByDate(date);
		return movieSchedules.stream().map(movieSchedule -> MovieScheduleModel.getInstance(movieSchedule))
				.collect(Collectors.toList());
	}

	public BookingModel addBooking(BookingModel bookingModel) throws APIException {
		Booking booking = bookingModel.getDomain();

		if (booking.getSeatCount() > 9) {
			throw new APIException("BK-01", "Can not book more than 9 seats.");
		}
		
		if (booking.getBookingDate().isBefore(LocalDate.now())) {
			throw new APIException("BK-04", "Can not book for past.");
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
		seatBooked = seatBooked == null ? 0 : seatBooked;
		Integer totalSeat = movieSchedule.getMovieTheatre().getSeatCount();
		if (seatBooked + booking.getSeatCount() > totalSeat) {
			throw new APIException("BK-03",
					String.format("Only %d seats available for this show.", totalSeat - seatBooked));
		}

		booking = bookRepository.save(booking);
		return BookingModel.getInstance(booking);
	}

	public List<BookingModel> listBookingsByUser() {
		String username = userAuthentication.getLoggedInUser();
		User user = userRepository.findByEmail(username);

		List<Booking> bookings = bookRepository.findByUserId(user.getId());
		return bookings.stream().map(booking -> BookingModel.getInstance(booking)).collect(Collectors.toList());
	}

	@Transactional
	public void listSchedules(SseEmitter emitter) {

		try (Stream<MovieSchedule> stream = movieScheduleRepository.streamList().parallel()) {
			stream.map(movieSchedule -> {
				MovieScheduleModel temp = MovieScheduleModel.getInstance(movieSchedule);
				em.detach(movieSchedule);
				return temp;
			}).forEach(movieSchedule -> {

				try {
					emitter.send(objectMapper.writeValueAsString(movieSchedule));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			// e.printStackTrace();
			// throw new RuntimeException("Exception occurred while exporting results", e);
		}
	}

	@Transactional
	public void listBookings(SseEmitter emitter) {
		Stream<BookingModel> stream = bookRepository.streamList().map(booking -> BookingModel.getInstance(booking));
		stream.forEach(booking -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				emitter.send(objectMapper.writeValueAsString(booking));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private Movie findMovieById(Long movieId) {
		Optional<Movie> movie = movieRepository.findById(movieId);

		if (movie.isPresent()) {
			return movie.get();
		} else {
			return null;
		}
	}

	private MovieTheatre findMovieTheatreById(Long movieTheatreId) throws APIException {
		Optional<MovieTheatre> movieTheatre = movieTheatreRepository.findById(movieTheatreId);

		if (movieTheatre.isPresent()) {
			return movieTheatre.get();
		} else {
			throw new APIException("MT-01", "Please select valid movie theatre.");
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
