package ch.letsboot.jugstalk.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class HeadersLoggingFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        Collections.list(httpRequest.getHeaderNames())
                .forEach(headerName -> {
                    log.info("Header Name: [{}], Header Value: [{}]",
                            headerName, httpRequest.getHeader(headerName));
                });
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
