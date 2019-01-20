package pl.krasnowski.Currencies.services;

import pl.krasnowski.Currencies.models.Rate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RateServiceImplTest {

    private List<Rate> firstRates;
    private List<Rate> secondRates;
    private RateService rateService;

    @org.junit.jupiter.api.BeforeEach
    void initData() {
        rateService = new RateServiceImpl();
        firstRates = new ArrayList<Rate>() {{
            add(new Rate(33, 33));
            add(new Rate(22, 22));
            add(new Rate(11, 11));
            add(new Rate(12.41, 12.41));
        }};
        secondRates = new ArrayList<Rate>() {{
            add(new Rate(12.55, 12.55));
            add(new Rate(60.23, 60.23));
            add(new Rate(5, 5));
            add(new Rate(7, 7));
        }};
    }

    @org.junit.jupiter.api.Test
    void calculateMeanBid() {
        assertEquals(19.6025, rateService.calculateMeanBid(firstRates));
        assertEquals(21.195, rateService.calculateMeanBid(secondRates));
    }

    @org.junit.jupiter.api.Test
    void calculateAskStdDeviation() {
        assertEquals(10.1813, rateService.calculateAskStdDeviation(firstRates));
        assertEquals(26.2186, rateService.calculateAskStdDeviation(secondRates));
    }
}