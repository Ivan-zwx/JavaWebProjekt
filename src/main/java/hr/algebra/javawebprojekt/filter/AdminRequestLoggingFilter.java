package hr.algebra.javawebprojekt.filter;

import hr.algebra.javawebprojekt.repository.StoreRepository;
import jakarta.servlet.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

public class AdminRequestLoggingFilter implements Filter {
    private StoreRepository storeRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Now you can use storeRepository to perform database operations
        // ...
        chain.doFilter(request, response);
    }
}

