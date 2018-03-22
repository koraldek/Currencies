package pl.krasnowski.Currencies.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * Holds data from NBP API or error message if methods could not parse JSON or GET method send error Field code contains error code with description
 * 
 * @author Krasnowski
 */
@JsonView(Views.Public.class)
@Data
public class RateWrapper {
	private String no, tradingDate, effectiveDate, table, currency;

	private String code = "";

	private List<Rate> rates = new ArrayList<>();

	public void addRates(List<Rate> newRates) {
		rates.addAll(newRates);
	}
}
