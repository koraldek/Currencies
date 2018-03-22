package pl.krasnowski.Currencies.models;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 *  POJO class for rate of currency for one day.
Contains: number, currency's code and buy  bid
 * @author Korald
 *
 */
@Data
@JsonView(Views.Public.class)
public class Rate {
	private String no;
	private String effectiveDate;
	private double bid, ask;
}
