package pl.krasnowski.Currencies.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import pl.krasnowski.Currencies.models.RateWrapper;

@Configuration
@EnableAutoConfiguration
public class Config {
	@Bean
	@SessionScope
	RateWrapper rateWrapper() {
		return new RateWrapper();
	}
}
