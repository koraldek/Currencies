package pl.krasnowski.Currencies.DAO;

import java.time.LocalDate;
import org.springframework.stereotype.Repository;

import pl.krasnowski.Currencies.models.RateWrapper;

/**
 * Data access object for currencies data from Narodowy Bank Polski API.
 * 
 * @author Korald
 *
 */
@Repository
public interface RateDAO {
	public static final String JSON_FORMAT = "?format=json";
	public static final String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/";
	public static final String HTTP_RESPONSE_ERROR = "HTTP response error code:";
	public static final int MAX_RESULTS_PER_QUERY = 255;

	RateWrapper findByCode(String code, LocalDate fromDate, LocalDate toDate);
}
