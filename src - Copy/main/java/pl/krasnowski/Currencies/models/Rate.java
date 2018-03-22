package pl.krasnowski.Currencies.models;

import lombok.Data;

@Data
public class Rate {
	private String no;
	private String effectiveDate;
	private double bid, ask;
}
