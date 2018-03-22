package pl.krasnowski.Currencies.DAO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

/**
 * Basic implementation of RateDAO
 * 
 * @author Korald
 *
 */
@Repository
public class RateDAOImpl implements RateDAO {
	public static final Logger logger = LoggerFactory.getLogger(RateDAOImpl.class);

	@Override
	public RateWrapper findByCode(String code, LocalDate fromDate, LocalDate toDate) {
		String inline = "";
		RateWrapper resultWrapper = new RateWrapper();
		ObjectMapper mapper = new ObjectMapper();
		List<Rate> fetchedRates = new ArrayList<>();
		List<LocalDate> queueQuery = new ArrayList<>();
		int days = (int) ChronoUnit.DAYS.between(fromDate, toDate);

		queueQuery.add(fromDate);
		while (days > RateDAO.MAX_RESULTS_PER_QUERY) {
			fromDate = fromDate.plusDays(RateDAO.MAX_RESULTS_PER_QUERY);
			queueQuery.add(fromDate);
			days = (int) ChronoUnit.DAYS.between(fromDate, toDate);
		}
		queueQuery.add(toDate);

		for (int i = 0; i < queueQuery.size() - 1; i++) {
			try {

				if (fromDate.equals(toDate)) {
					inline = RateDAOImpl.fetchData("rates/c/" + code);

					if (inline.contains(HTTP_RESPONSE_ERROR)) {
						if (inline.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)))
							inline = inline + " - błędne dane wejściowe.";
						else if (inline.contains(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND)))
							inline = inline + " - nie znaleziono wyników dla danego zapytania.";

						resultWrapper.setCode(inline);
						return resultWrapper;
					}
					resultWrapper = mapper.readValue(inline, new TypeReference<RateWrapper>() {
					});
					break;
				} else {
					inline = RateDAOImpl.fetchData("rates/c/" + code + "/" + queueQuery.get(i).toString() + "/" + queueQuery.get(i + 1).toString());

					if (inline.contains(HTTP_RESPONSE_ERROR)) {
						if (inline.contains(String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST)))
							inline = inline + " - błędne dane wejściowe.";
						else if (inline.contains(String.valueOf(HttpURLConnection.HTTP_NOT_FOUND)))
							inline = inline + " - nie znaleziono wyników dla danego zapytania.";

						resultWrapper.setCode(inline);
						return resultWrapper;
					}

					fetchedRates = (List<Rate>) ((RateWrapper) mapper.readValue(inline, new TypeReference<RateWrapper>() {
					})).getRates();
					resultWrapper.addRates(fetchedRates);
				}
			} catch (JsonParseException e) {
				logger.error("JSON parse exception!");
				resultWrapper.setCode("Wystąpił bład podczas parsowania danych wejściowych:\n " + inline);
				e.printStackTrace();
				return resultWrapper;
			} catch (JsonMappingException e) {
				logger.error("JSON mapping exception!");
				resultWrapper.setCode("Wystąpił błąd podczas mapowania na obiekty. Dane wejściowe:\n" + inline);
				e.printStackTrace();
				return resultWrapper;
			} catch (IOException e) {
				logger.error("IO exception!");
				resultWrapper.setCode("Nie można było połączyć się z serwerem NBP. Sprawdź swoje połączenie z internetem i spróbuj ponownie.");
				e.printStackTrace();
				return resultWrapper;
			}
		}
		resultWrapper.setCode(null);
		return resultWrapper;
	}

	/**
	 * Fetching data from NBP API by HTTP protocol
	 * 
	 * @param parameters
	 *            part of URL which specifies request
	 * @return json format as String object
	 * @throws IOException
	 */
	private static String fetchData(String parameters) throws IOException {
		int responsecode;
		String inline;
		StringBuilder sBuilder = new StringBuilder();
		String sUrl = RateDAO.NBP_API_URL + parameters + RateDAO.JSON_FORMAT;

		URL url = new URL(sUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		responsecode = conn.getResponseCode();
		logger.debug("Sending GET to:" + sUrl);
		System.out.println("Sending GET to:" + sUrl);

		if (responsecode != HttpURLConnection.HTTP_OK)
			return HTTP_RESPONSE_ERROR + responsecode;
		else {
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext())
				sBuilder.append(sc.nextLine());
			inline = sBuilder.toString();
			sc.close();
		}

		System.out.println("\nJSON Response in String format:\n" + inline);
		conn.disconnect();
		return inline;
	}

}