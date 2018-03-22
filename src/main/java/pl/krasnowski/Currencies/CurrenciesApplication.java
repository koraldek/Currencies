package pl.krasnowski.Currencies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="pl.krasnowski.Currencies")
public class CurrenciesApplication {

	public static void main(String[] args) throws InterruptedException {
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(Config.class);
//		ctx.refresh();
//		RateDAO rateDAO = ctx.getBean(RateDAO.class);
//		RateService ratesService = ctx.getBean(RateService.class);
//		// RateDAO rateDAO = new RateDAOImpl();
//		// RatesService ratesService = new RatesServiceImpl();
//		
//		LocalDate dateFrom = LocalDate.of(2017, 11, 20);
//		LocalDate dateTo = LocalDate.of(2017, 11, 24);
//		
//		// List<Rate> rw = rateDAO.findByCode("USD", LocalDate.now().minusDays(280), LocalDate.now());
//		List<Rate> rates = ratesService.getRates("EUR", dateFrom, dateTo);
//		int i = 0;
//		for (Rate rate : rates) {
//			System.out.println(++i + ":" + rate.toString());
//		}
//		System.out.println("Mean value:" + ratesService.calculateMeanBid(rates) + " ,Standard Deviation:" + ratesService.calculateStdDeviationAsk(rates));
//		System.out.println("koniec programu..");
//		ctx.close();
		SpringApplication.run(CurrenciesApplication.class, args);
	}
}

// A - popularniejsze waluty, bez nazw panstwa z cena srednia
// B - rzadsze waluty, zawiera nazwy panstwa z cena srednia
// C - najpopularniejsze waluty z cena kupna/sprzedazy
