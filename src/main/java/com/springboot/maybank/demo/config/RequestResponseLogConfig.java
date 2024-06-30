// package com.springboot.maybank.demo.config;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import com.springboot.maybank.demo.filter.RequestResponseLogFilter;
//
// @Configuration
// public class RequestResponseLogConfig {
//
// @Autowired
// private RequestResponseLogFilter requestResponseLogFilter;
//
// @Bean
// public FilterRegistrationBean<RequestResponseLogFilter> loggingFilter() {
//
// FilterRegistrationBean<RequestResponseLogFilter> bean = new FilterRegistrationBean<>();
// bean.setFilter(requestResponseLogFilter);
// bean.addUrlPatterns("/api/v1/person/*");
//
// return bean;
// }
//
// }
