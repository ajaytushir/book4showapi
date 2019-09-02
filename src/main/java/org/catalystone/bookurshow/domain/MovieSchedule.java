package org.catalystone.bookurshow.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = MovieSchedule.TABLE_NAME)
public class MovieSchedule {
	public static final String TABLE_NAME = "MOVIE_SCHEDULE";

	public static enum TimeSlot {
		MORNING, AFTERNOON, EVENING, NIGHT
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MOVIE_THEATRE_ID")
	private MovieTheatre movieTheatre;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MOVIE_ID")
	private Movie movie;

	private TimeSlot timeSlot;
	@Column(name="FROM_DATE")
	private LocalDate from;
	@Column(name="TO_DATE")
	private LocalDate to;
	private BigDecimal price;
	private boolean deleted;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MovieTheatre getMovieTheatre() {
		return movieTheatre;
	}
	public void setMovieTheatre(MovieTheatre movieTheatre) {
		this.movieTheatre = movieTheatre;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public TimeSlot getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(TimeSlot timeSlot) {
		this.timeSlot = timeSlot;
	}
	public LocalDate getFrom() {
		return from;
	}
	public void setFrom(LocalDate from) {
		this.from = from;
	}
	public LocalDate getTo() {
		return to;
	}
	public void setTo(LocalDate to) {
		this.to = to;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
