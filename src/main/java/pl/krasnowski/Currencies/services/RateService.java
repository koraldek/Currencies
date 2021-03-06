package pl.krasnowski.Currencies.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

/**
 * Manipulate currencies data.
 * 
 * @author Dawid Krasnowski
 *
 */
@Service
public interface RateService {
	double calculateMeanBid(List<Rate> rates);

	double calculateAskStdDeviation(List<Rate> rates);

	RateWrapper getRates(String code, LocalDate fromDate, LocalDate toDate);
}
