package pl.krasnowski.Currencies.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krasnowski.Currencies.DAO.RateDAO;
import pl.krasnowski.Currencies.models.Rate;
import pl.krasnowski.Currencies.models.RateWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class RateServiceImpl implements RateService {

    @Autowired
    private RateDAO rateDAO;

    @Override
    public double calculateMeanBid(List<Rate> rates) {
        double ratesQuantity = rates.size();
        double result, sumOfBids = 0;

        if (ratesQuantity == 0)
            return 0;
        else {
            for (Rate rate : rates) {
                log.trace("Adding {} to sumOfBids:{}", rate.getBid(), sumOfBids);
                sumOfBids += rate.getBid();
            }
            result = sumOfBids / ratesQuantity;
            log.debug("Calculated value of mean bid:{}. SumOfBids:{}, using {} records.", result, sumOfBids, ratesQuantity);
            return BigDecimal.valueOf(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
        }
    }

    @Override
    public double calculateAskStdDeviation(List<Rate> rates) {
        double result, divident = 0.0;
        double arithmeticAvg = 0.0;
        double ratesQuantity = rates.size();

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

            result = Math.sqrt(divident / (ratesQuantity - 1));
            return BigDecimal.valueOf(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
        }
    }

    @Override
    public RateWrapper getRates(String code, LocalDate fromDate, LocalDate toDate) {
        return rateDAO.findByCode(code, fromDate, toDate);
    }
}
