package org.catalystone.bookurshow.model;

import org.catalystone.bookurshow.domain.MovieTheatre;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MovieTheatreModel {
	private Long id;
	private String name;
	private String address;
	private Integer seatCount;
	private boolean deleted; 
	
	@JsonIgnore
	public MovieTheatre getDomain() {
		MovieTheatre movieTheatre = new MovieTheatre();
		movieTheatre.setId(this.id);
		movieTheatre.setAddress(address);
		movieTheatre.setDeleted(deleted);
		movieTheatre.setName(name);
		movieTheatre.setSeatCount(seatCount);
		return movieTheatre;
	}
	
	public static MovieTheatreModel getInstance(MovieTheatre movieTheatre) {
		MovieTheatreModel movieTheatreModel = new MovieTheatreModel();
		movieTheatreModel.setId(movieTheatre.getId());
		movieTheatreModel.setAddress(movieTheatre.getAddress());
		//movieTheatreModel.setDeleted(movieTheatre.get);
		movieTheatreModel.setName(movieTheatre.getName());
		movieTheatreModel.setSeatCount(movieTheatre.getSeatCount());
		return movieTheatreModel;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(Integer seatCount) {
		this.seatCount = seatCount;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "MovieTheatreModel [id=" + id + ", name=" + name + ", address="
				+ address + ", seatCount=" + seatCount + ", deleted=" + deleted
				+ "]";
	}
}
