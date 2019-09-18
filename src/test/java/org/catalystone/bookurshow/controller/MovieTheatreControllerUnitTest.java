package org.catalystone.bookurshow.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.catalystone.bookurshow.model.MovieTheatreModel;
import org.catalystone.bookurshow.service.MovieTheatreService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(MovieTheatreController.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieTheatreControllerUnitTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private MovieTheatreService movieTheatreService;

	@Test
	public void test_01_wrong_url() throws Exception {
		mvc.perform(get("/movietheatree/list").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void test_02_list_movie_theatre() throws Exception {
		mvc.perform(get("/movietheatre/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void test_03_add_movie_theatre_annonymous_user() throws Exception {
		mvc.perform(post("/movietheatre/add").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "mock_user", authorities = { "USER" })
	public void test_04_add_movie_theatre_end_user() throws Exception {
		mvc.perform(post("/movietheatre/add").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "mock_admin", authorities = { "ADMIN" })
	public void test_05_add_movie_theatre_with_admin_user() throws Exception {
		String input = new ObjectMapper().writeValueAsString(new MovieTheatreModel());
		mvc.perform(post("/movietheatre/add").contentType(MediaType.APPLICATION_JSON).content(input)).andExpect(status().isOk());
	}

	@Test
	public void test_06_update_movie_theatre_annonymous_user() throws Exception {
		mvc.perform(post("/movietheatre/update").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "mock_user", authorities = { "USER" })
	public void test_07_update_movie_theatre_end_user() throws Exception {
		mvc.perform(post("/movietheatre/update").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "mock_admin", authorities = { "ADMIN" })
	public void test_08_update_movie_theatre_with_admin_user() throws Exception {
		mvc.perform(post("/movietheatre/update").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void test_09_delete_movie_theatre_annonymous_user() throws Exception {
		mvc.perform(get("/movietheatre/delete/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "mock_user", authorities = { "USER" })
	public void test_10_delete_movie_theatre_end_user() throws Exception {
		mvc.perform(get("/movietheatre/delete/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "mock_admin", authorities = { "ADMIN" })
	public void test_11_delete_movie_theatre_with_admin_user() throws Exception {
		mvc.perform(get("/movietheatre/delete/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
