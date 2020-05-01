package it.polito.tdp.poweroutages.model;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class PowerOutage {
	
	private int customersAffected;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventFinish;
	private Nerc nerc;
	private long ore;
	
	public PowerOutage(int customersAffected, LocalDateTime dateEventBegan, LocalDateTime dateEventFinish, Nerc nerc) {
		super();
		this.customersAffected = customersAffected;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinish = dateEventFinish;
		this.nerc = nerc;
		this.ore = dateEventBegan.until(dateEventFinish, ChronoUnit.HOURS);
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}

	public void setDateEventBegan(LocalDateTime dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}

	public LocalDateTime getDateEventFinish() {
		return dateEventFinish;
	}

	public void setDateEventFinish(LocalDateTime dateEventFinish) {
		this.dateEventFinish = dateEventFinish;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public long getOre() {
		return ore;
	}

	public void setOre(long ore) {
		this.ore = ore;
	}

}
