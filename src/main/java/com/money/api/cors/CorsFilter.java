package com.money.api.cors;

import com.money.api.config.property.MoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    @Autowired
    private MoneyApiProperty moneyApiProperty;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.setHeader("Access-control-Allow-Origin", moneyApiProperty.getOriginPermitida());
        resp.setHeader("Access-control-Allow-Credentials", "true");

        if ("OPTIONS".equals(req.getMethod())
                && moneyApiProperty.getOriginPermitida().equals(req.getHeader("Origin"))) {

            resp.setHeader("Access-control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
            resp.setHeader("Access-control-Allow-Headers", "Authorization, Content-Type, Accept");
            resp.setHeader("Access-control-Max-Age", "3600");

            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
