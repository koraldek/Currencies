package pl.krasnowski.Currencies.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Holds data from NBP API or error message if methods could not parse JSON or GET method send error Field code contains currency code or error code with description
 * 
 * @author Krasnowski
 */
@Data
public class RateWrapper {
	private String no;
	private String tradingDate;
	private String effectiveDate;
	private String table;
	private String currency;
	private String code = "";
	private List<Rate> rates = new ArrayList<>();

	public void addRates(List<Rate> newRates) {
		rates.addAll(newRates);
	}
}
