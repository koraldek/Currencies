package pl.krasnowski.Currencies.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.validation.Valid;

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

@Controller
public class HomepageController {

	@Autowired
	RateService rService;

	@Autowired
	RateWrapper rateWrapper;

	@GetMapping("/find")
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

		rateWrapper = rService.getRates(currForm.getCode(), fromDate, toDate);

		if (rateWrapper.getCode() != null) {
			responseBody.setCode(rateWrapper.getCode());
		} else {
			responseBody.setMeanBid(rService.calculateMeanBid(rateWrapper.getRates()));
			responseBody.setStdDeviationAsk(rService.calculateStdDeviationAsk(rateWrapper.getRates()));
			responseBody.setRates(rateWrapper.getRates());
		}

		return ResponseEntity.ok(responseBody);
	}

}