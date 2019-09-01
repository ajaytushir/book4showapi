package org.catalystone.bookurshow.controller;

import java.time.LocalDate;
import java.util.List;

import org.catalystone.bookurshow.model.APIException;
import org.catalystone.bookurshow.model.MovieScheduleModel;
import org.catalystone.bookurshow.service.MovieTheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("movieSchdeule")
@RestController
public class MovieScheduleController {
	
	@Autowired
	private MovieTheatreService movieTheatreService;
	
	@PostMapping("/add")
	public MovieScheduleModel add(@RequestBody MovieScheduleModel movieScheduleModel) throws APIException {
		return movieTheatreService.addMovieSchedule(movieScheduleModel);
	}
	
	@GetMapping("/findByDate")
	public List<MovieScheduleModel> list(@RequestParam(name="date",required=true) LocalDate date) {
		return movieTheatreService.listMovieSchedules(date);
	}
}
