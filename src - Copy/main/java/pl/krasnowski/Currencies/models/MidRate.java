package pl.krasnowski.Currencies.models;

import lombok.Data;

@Data
public class MidRate {
	private String country = "";
	private String currency;
	private String code;
	private double mid;
}