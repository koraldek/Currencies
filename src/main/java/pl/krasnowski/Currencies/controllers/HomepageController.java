package pl.krasnowski.Currencies.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.krasnowski.Currencies.models.AjaxResponseBody;
import pl.krasnowski.Currencies.models.RateWrapper;
import pl.krasnowski.Currencies.models.forms.FindCurrencyForm;
import pl.krasnowski.Currencies.services.RateService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
class HomepageController {

	@Autowired
	private RateService rService;

	@GetMapping({"/find","/"})
	public String homepage(FindCurrencyForm formModel) {
		return "index";
	}

	@PostMapping("/find")
	public ResponseEntity<?> findByAJAX(@Valid @RequestBody FindCurrencyForm currForm, BindingResult bindingResult) {
		AjaxResponseBody responseBody = new AjaxResponseBody();
		if (bindingResult.hasErrors())
			return ResponseEntity.badRequest().body(responseBody);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate fromDate = LocalDate.parse(currForm.getFromDate(), formatter);
		LocalDate toDate = LocalDate.parse(currForm.getToDate(), formatter);

		RateWrapper rateWrapper = rService.getRates(currForm.getCode(), fromDate, toDate);

		if (rateWrapper.getErrorCode() != null) {
			responseBody.setCode(rateWrapper.getErrorCode());
		} else {
			responseBody.setMeanBid(rService.calculateMeanBid(rateWrapper.getRates()));
			responseBody.setStdDeviationAsk(rService.calculateAskStdDeviation(rateWrapper.getRates()));
			responseBody.setRates(rateWrapper.getRates());
		}
		System.out.println(responseBody.toString());
		return ResponseEntity.ok(responseBody);
	}

}