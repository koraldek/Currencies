package pl.krasnowski.Currencies.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Holds data from NBP API or error message if methods could not parse JSON or GET method send error Field errorCode contains error errorCode with description
 * 
 * @author Krasnowski
 */
@JsonView(Views.Public.class)
@Data
@Component
public class RateWrapper {
	private String no, tradingDate, effectiveDate, table, currency;

	@JsonProperty("code")
	private String errorCode;

	private List<Rate> rates = new ArrayList<>();

	public void addRates(List<Rate> newRates) {
		rates.addAll(newRates);
	}
}
