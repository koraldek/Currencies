package pl.krasnowski.Currencies.models.forms;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import pl.krasnowski.Currencies.models.Views;
@JsonView(Views.Public.class)
@Data
public class FindCurrencyForm {
	@Size(min = 3, max = 3, message = "Kod waluty powinien składać się z 3 znaków")
	String code;

	@Size(min = 10, max = 10, message = "Podaj poprawną datę")
	String fromDate, toDate;

}
