package org.catalystone.bookurshow.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequestMapping("movieSchedule")
@RestController
public class MovieScheduleController {
	
	@Autowired
	private MovieTheatreService movieTheatreService;
	
	@PostMapping("/add")
	public MovieScheduleModel add(@RequestBody MovieScheduleModel movieScheduleModel) throws APIException {
		return movieTheatreService.addMovieSchedule(movieScheduleModel);
	}
	
	@GetMapping("/batch")
	public String addBatch(@RequestParam(name="startDate",required=true) String startDate, @RequestParam(name="endDate",required=true) String stopDate) throws APIException {
		LocalDate endDate = LocalDate.parse(stopDate);
		LocalDate scheduleDate = LocalDate.parse(startDate);
		movieTheatreService.addMovieScheduleBatch(scheduleDate, endDate);
		return "Success";
	}
	
	@GetMapping("/findByDate")
	public List<MovieScheduleModel> list(@RequestParam(name="date",required=true) String date) {
		LocalDate given = LocalDate.parse(date);
		return movieTheatreService.listMovieSchedules(given);
	}
	

	private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

	@GetMapping("/listStream")
	public SseEmitter handleSse() {
		SseEmitter emitter = new SseEmitter(120000l);
		emitter.onTimeout(()-> {
			emitter.complete();
        });
		nonBlockingService.execute(() -> {
			movieTheatreService.listSchedules(emitter);
			emitter.complete();
		});
		return emitter;
	}
}
