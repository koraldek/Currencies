package pl.krasnowski.Currencies.DAO;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.DSAGenParameterSpec;
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

import pl.krasnowski.Currencies.models.MidRateWrapper;
import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

@Repository
public class RateDAOImpl implements RateDAO {
	public static final Logger logger = LoggerFactory.getLogger(RateDAOImpl.class);

	@Override
	public List<MidRateWrapper> findByCurrencyName(String countryName, LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MidRateWrapper> findByCurrencyName(String countryName, int topCount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RateWrapper findByCode(String code, LocalDate fromDate, LocalDate toDate) {
		String inline;
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

				// if (queueQuery.size() == 2) {
				if (days == 0 && queueQuery.size() == 2) {
					inline = RateDAOImpl.fetchData("rates/c/" + code);

					if (inline.contains(HTTP_RESPONSE_ERROR)) {
						resultWrapper.setCode(inline);
						return resultWrapper;
					}
					resultWrapper = mapper.readValue(inline, new TypeReference<RateWrapper>() {
					});
					break;
				} else {
					inline = RateDAOImpl.fetchData("rates/c/" + code + "/" + queueQuery.get(i).toString() + "/" + queueQuery.get(i + 1).toString());

					if (inline.contains(HTTP_RESPONSE_ERROR)) {
						resultWrapper.setCode(inline);
						return resultWrapper;
					}

					fetchedRates = (List<Rate>) ((RateWrapper) mapper.readValue(inline, new TypeReference<RateWrapper>() {
					})).getRates();
					resultWrapper.addRates(fetchedRates);
				}
			} catch (JsonParseException e) {
				logger.error("JSON parse exception!");
				e.printStackTrace();
			} catch (JsonMappingException e) {
				logger.error("JSON mapping exception!");
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("IO exception!");
				e.printStackTrace();
			}
		}
		return resultWrapper;
	}

	// @Override
	// public List<Rate> findByCode(String code, int topCount) throws InterruptedException {
	// // wez ostatnie notowanie, pobierz effectiveDate i od tej day odlicz topCount-1 wynikow datami!
	// int ratesCount = 0;
	// List<Rate> resultList = new ArrayList<>();
	// LocalDate toDate, fromDate = null;
	// while (ratesCount <= topCount) {
	//
	// if (resultList.isEmpty()) {
	// fromDate = LocalDate.now().minusDays(topCount);
	// toDate = LocalDate.parse(findByCode(code, LocalDate.now(), LocalDate.now()).get(0).getEffectiveDate());
	// ratesCount++;
	// } else {
	// toDate = LocalDate.parse(fromDate.toString());
	// fromDate = fromDate.minusDays(topCount - ratesCount);
	// }
	// System.out.println("Rates count:" + ratesCount + ", Date ranges for next fetch:" + fromDate + " / " + toDate);
	// Thread.sleep(1000);
	// List<Rate> gatheredRates = findByCode(code, fromDate, toDate);
	// resultList.addAll(gatheredRates);
	// ratesCount += gatheredRates.size();
	// }
	// Collections.reverse(resultList);// rates were gathered in inverted order
	// return resultList;
	// }

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