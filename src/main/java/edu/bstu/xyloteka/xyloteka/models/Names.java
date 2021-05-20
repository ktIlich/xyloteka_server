package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Names {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String name_lat;
	private String name_trade;
	private String name_alt;

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

	public String getName_lat() {
		return name_lat;
	}

	public void setName_lat(String name_lat) {
		this.name_lat = name_lat;
	}

	public String getName_trade() {
		return name_trade;
	}

	public void setName_trade(String name_trade) {
		this.name_trade = name_trade;
	}

	public String getName_alt() {
		return name_alt;
	}

	public void setName_alt(String name_alt) {
		this.name_alt = name_alt;
	}
}
