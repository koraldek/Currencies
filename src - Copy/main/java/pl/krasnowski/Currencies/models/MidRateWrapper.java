package pl.krasnowski.Currencies.models;

import java.util.List;

import lombok.Data;

@Data
public class MidRateWrapper {
	private String table;
	private String no;
	private String effectiveDate;
	private List<MidRate> midRates;
}
