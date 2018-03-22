package pl.krasnowski.Currencies.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.krasnowski.Currencies.DAO.RateDAO;
import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;
import pl.krasnowski.Currencies.models.forms.FindCurrencyForm;
import pl.krasnowski.Currencies.services.RateService;

@Controller
public class HomepageController {

	@Autowired
	RateService rService;

	@Autowired
	RateWrapper rateWrapper;

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		rateWrapper = rService.getRates("USD", LocalDate.now().plusDays(-10), LocalDate.now());
		double x1 = rService.calculateMeanBid(rateWrapper.getRates());
		double x2 = rService.calculateStdDeviationAsk(rateWrapper.getRates());
		// model.put("message", this.message);
		model.put("message", "x1:" + x1 + ", x2:" + x2);
		return "home";
	}

	@GetMapping("/find")
	public String test(FindCurrencyForm formModel) {
		// model.addAttribute("currForm", new FindCurrencyForm());
		return "index";
	}

	@PostMapping("/find")
	public String findCurrency(@Valid FindCurrencyForm currForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors())
			return "index";

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDate fromDate = LocalDate.parse(currForm.getFromDate(), formatter);
		LocalDate toDate = LocalDate.parse(currForm.getToDate(), formatter);
		rateWrapper = rService.getRates(currForm.getCode(), fromDate, toDate);
		if (rateWrapper.getCode().contains(RateDAO.HTTP_RESPONSE_ERROR)) {
			String meanBid = rateWrapper.getCode();
			String stdDeviationAsk = rateWrapper.getCode();
			model.addAttribute("meanBid", meanBid);
			model.addAttribute("stdDeviationAsk", stdDeviationAsk);
		} else {
			double meanBid = rService.calculateMeanBid(rateWrapper.getRates());
			double stdDeviationAsk = rService.calculateStdDeviationAsk(rateWrapper.getRates());
			model.addAttribute("meanBid", meanBid);
			model.addAttribute("stdDeviationAsk", stdDeviationAsk);
		}
		return "index";
	}

}