package org.catalystone.bookurshow.model;

import java.time.LocalTime;

import org.catalystone.bookurshow.domain.Movie;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MovieModel {
	private Long id;
	private String name;
	private LocalTime duration;
	
	@JsonIgnore
	public Movie getDomain() {
		Movie movie = new Movie();
		movie.setId(this.getId());
		movie.setName(this.getName());
		movie.setDuration(this.getDuration());
		return movie;
	}
	
	public static MovieModel getInstance(Movie movie) {
		MovieModel movieModel = new MovieModel();
		movieModel.setId(movie.getId());
		movieModel.setName(movie.getName());
		movieModel.setDuration(movie.getDuration());
		return movieModel;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalTime getDuration() {
		return duration;
	}
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "MovieModel [id=" + id + ", name=" + name + ", duration="
				+ duration + "]";
	}
}
