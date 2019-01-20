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
	String JSON_FORMAT = "?format=json";
	String NBP_API_URL = "http://api.nbp.pl/api/exchangerates/";
	String HTTP_RESPONSE_ERROR = "HTTP response error errorCode:";
	int MAX_RESULTS_PER_QUERY = 255;

	RateWrapper findByCode(String code, LocalDate fromDate, LocalDate toDate);
}
