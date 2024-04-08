package ch.letsboot.jugstalk.jugstalk.config;

import jakarta.servlet.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class ListFilterConfig {

    private final static Logger LOG = LoggerFactory.getLogger(ListFilterConfig.class);


    private final Filter springSecurityFilterChain;

    public ListFilterConfig(@Qualifier("springSecurityFilterChain") Filter springSecurityFilterChain) {
        this.springSecurityFilterChain = springSecurityFilterChain;
    }


    @Bean
    public List<SecurityFilterChain> getFilters() {
        FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
        List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
        list.stream()
                .flatMap(chain -> chain.getFilters().stream())
                .forEach(filter -> LOG.info("FILTER: "+filter.getClass()));
        return list;
    }
}
