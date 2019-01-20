package pl.krasnowski.Currencies.models;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO class for rate of currency for one day.
 * Contains: number, currency's errorCode and buy  bid
 *
 * @author Korald
 */
@Data
@JsonView(Views.Public.class)
@NoArgsConstructor
public class Rate {
    private String no;
    private String effectiveDate;
    private double bid, ask;

    public Rate(double bid, double ask) {
        this.bid = bid;
        this.ask = ask;
    }
}