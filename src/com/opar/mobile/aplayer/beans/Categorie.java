package com.opar.mobile.aplayer.beans;

import java.io.Serializable;
import java.util.List;

public class Categorie implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private List<String> genre;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getGenre() {
		return genre;
	}
	public void setGenre(List<String> genre) {
		this.genre = genre;
	}
	
	
}
