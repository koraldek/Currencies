package pl.krasnowski.Currencies.DAO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.krasnowski.Currencies.models.MidRateWrapper;
import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

@Repository
public interface RateDAO {
	public static final String JSON_FORMAT = "?format=json";
	public static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/";
	public static final String HTTP_RESPONSE_ERROR = "HTTP response error code:";
	public static final int MAX_RESULTS_PER_QUERY = 255;

	List<MidRateWrapper> findByCurrencyName(String countryName, LocalDate fromDate, LocalDate toDate);

	List<MidRateWrapper> findByCurrencyName(String countryName, int topCount);

	RateWrapper findByCode(String code, LocalDate fromDate, LocalDate toDate);

//	List<Rate> findByCode(String code, int topCount) throws InterruptedException;
}
