package org.catalystone.bookurshow.controller;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.catalystone.bookurshow.model.APIException;
import org.catalystone.bookurshow.model.BookingModel;
import org.catalystone.bookurshow.service.MovieTheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;

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
	@Transactional(readOnly = true)
	public ResponseEntity<StreamingResponseBody> list() {
		// Stream<BookingModel> output = movieTheatreService.listBookings();
		StreamingResponseBody stream = out -> {
			// output.forEach(booking -> {
			Stream.generate(() -> LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)).limit(100)
					.forEach(output -> {

						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							// out.write(objectMapper.writeValueAsString(booking).getBytes());
							out.write(output.getBytes());
						} catch (JsonProcessingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		};
		return new ResponseEntity<StreamingResponseBody>(stream, HttpStatus.OK);
	}

	private ExecutorService nonBlockingService = Executors.newCachedThreadPool();

	@GetMapping("/listStream")
	public SseEmitter handleSse() {
		SseEmitter emitter = new SseEmitter();
		nonBlockingService.execute(() -> {
			movieTheatreService.listBookings(emitter);
			emitter.complete();
		});
		return emitter;
	}
}
