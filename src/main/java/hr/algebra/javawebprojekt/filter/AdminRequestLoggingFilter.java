package hr.algebra.javawebprojekt.filter;

import hr.algebra.javawebprojekt.domain.RequestHistory;
import hr.algebra.javawebprojekt.repository.StoreRepository;
import jakarta.servlet.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.time.LocalDateTime;

public class AdminRequestLoggingFilter implements Filter {
    private StoreRepository storeRepository;

    @Override
    public void init(FilterConfig filterConfig) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        storeRepository = context.getBean(StoreRepository.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        RequestHistory requestHistory = new RequestHistory(
                null, username, LocalDateTime.now().toString(), request.toString()
        );

        storeRepository.addRequestHistory(requestHistory);

        chain.doFilter(request, response);
    }
}

