package org.catalystone.bookurshow.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.catalystone.bookurshow.dao.IMovieRepository;
import org.catalystone.bookurshow.dao.IMovieScheduleRepository;
import org.catalystone.bookurshow.dao.IMovieTheatreRepository;
import org.catalystone.bookurshow.domain.Movie;
import org.catalystone.bookurshow.domain.MovieSchedule;
import org.catalystone.bookurshow.domain.MovieTheatre;
import org.catalystone.bookurshow.model.APIException;
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

	public MovieTheatreModel addMovieTheatre(MovieTheatreModel movieTheatreModel) {
		MovieTheatre movieTheatre = movieTheatreModel.getDomain();
		movieTheatre = movieTheatreRepository.save(movieTheatre);
		return MovieTheatreModel.getInstance(movieTheatre);
	}

	public List<MovieTheatreModel> listMovieTheatres() {
		List<MovieTheatre> movieTheatres = movieTheatreRepository.findAll();
		return movieTheatres
				.stream()
				.map(movieTheatre -> MovieTheatreModel
						.getInstance(movieTheatre))
				.collect(Collectors.toList());
	}

	public MovieModel addMovie(MovieModel movieModel) {
		Movie movie = movieModel.getDomain();
		movie = movieRepository.save(movie);
		return MovieModel.getInstance(movie);
	}

	public List<MovieModel> listMovies() {
		List<Movie> movies = movieRepository.findAll();
		return movies.stream().map(movie -> MovieModel.getInstance(movie))
				.collect(Collectors.toList());
	}

	public MovieScheduleModel addMovieSchedule(
			MovieScheduleModel movieScheduleModel) throws APIException {
		MovieSchedule movieSchedule = movieScheduleModel.getDomain();
		Movie movie = findMovieById(movieScheduleModel.getMovie());

		if (movie == null) {
			throw new APIException("MS-01", "Please select valid movie.");
		}

		movieSchedule.setMovie(movie);

		MovieTheatre movieTheatre = findMovieTheatreById(movieScheduleModel
				.getMovieTheatre());
		if (movieTheatre == null) {
			throw new APIException("MS-02",
					"Please select valid movie theatre.");
		}
		movieSchedule.setMovieTheatre(movieTheatre);

		movieSchedule = movieScheduleRepository.save(movieSchedule);
		return MovieScheduleModel.getInstance(movieSchedule);
	}
	
	public List<MovieScheduleModel> listMovieSchedules(LocalDate date) {
		List<MovieSchedule> movieSchedules = movieScheduleRepository.findByDate(date);
		return movieSchedules.stream().map(movieSchedule-> MovieScheduleModel.getInstance(movieSchedule))
				.collect(Collectors.toList());
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
		Optional<MovieTheatre> movieTheatre = movieTheatreRepository
				.findById(movieTheatreId);

		if (movieTheatre.isPresent()) {
			return movieTheatre.get();
		} else {
			return null;
		}
	}
}
