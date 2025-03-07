package edu.bstu.xyloteka.xyloteka.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Names {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name_trade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_trade() {
		return name_trade;
	}

	public void setName_trade(String name_trade) {
		this.name_trade = name_trade;
	}
}
