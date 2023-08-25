package hr.algebra.javawebprojekt.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    @Bean
    public FilterRegistrationBean<AdminRequestLoggingFilter> loggingFilter() {
        FilterRegistrationBean<AdminRequestLoggingFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminRequestLoggingFilter());

        registrationBean.addUrlPatterns("/admin/products/delete");
        registrationBean.addUrlPatterns("/admin/categories/delete");

        registrationBean.setOrder(1);

        return registrationBean;
    }
}
