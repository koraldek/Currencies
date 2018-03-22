package pl.krasnowski.Currencies.models.forms;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class FindCurrencyForm {
	@Size(min = 3, max = 3, message = "Kod waluty powinien składać się z 3 znaków")
	String code;

	@Size(min = 10, max = 10, message = "Podaj poprawną datę")
	String fromDate, toDate;

}
