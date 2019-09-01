package org.catalystone.bookurshow.controller;

import java.util.List;

import org.catalystone.bookurshow.model.MovieModel;
import org.catalystone.bookurshow.service.MovieTheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie")
public class MovieController {
	@Autowired
	private MovieTheatreService movieTheatreService;
	
	@PostMapping("/add")
	public MovieModel add(@RequestBody MovieModel movieModel) {
		return movieTheatreService.addMovie(movieModel);
	}
	
	@GetMapping("/list")
	public List<MovieModel> list() {
		return movieTheatreService.listMovies();
	}
}
