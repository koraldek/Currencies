package pl.krasnowski.Currencies.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@JsonView(Views.class)
@Data
public class AjaxResponseBody {
	String code;

	double meanBid, stdDeviationAsk;

	List<Rate> rates;
}
