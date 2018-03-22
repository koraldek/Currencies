package pl.krasnowski.Currencies.services;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.krasnowski.Currencies.DAO.RateDAO;
import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

@Service
public class RateServiceImpl implements RateService {

	@Autowired
	RateDAO rateDAO;

	@Override
	public double calculateMeanBid(List<Rate> rates) {
		int ratesQuantity = rates.size();
		double sumOfBids = 0, result = 0.0;
		DecimalFormat df = new DecimalFormat("#.####", new DecimalFormatSymbols(Locale.getDefault()));
		if (ratesQuantity == 0)
			return 0;
		else {
			for (Rate rate : rates) {
				sumOfBids += rate.getBid();
			}
			result = sumOfBids / (double) ratesQuantity;
			return Double.parseDouble(df.format(result));
		}
	}

	@Override
	public double calculateStdDeviationAsk(List<Rate> rates) {
		double result, divident = 0.0;
		double arithmeticAvg = 0.0;
		double ratesQuantity = rates.size();
		DecimalFormat df = new DecimalFormat("#.####", new DecimalFormatSymbols(Locale.getDefault()));

		if (ratesQuantity == 0)
			return 0;
		else {
			for (Rate rate : rates) {
				arithmeticAvg += rate.getAsk();
			}
			arithmeticAvg = arithmeticAvg / ratesQuantity;
			for (Rate rate : rates) {
				divident += Math.pow(rate.getAsk() - arithmeticAvg, 2);
			}

			result = Math.sqrt(divident / ratesQuantity);
			return Double.parseDouble(df.format(result));
		}
	}

	@Override
	public RateWrapper getRates(String code, LocalDate fromDate, LocalDate toDate) {
		return rateDAO.findByCode(code, fromDate, toDate);
	}
}
