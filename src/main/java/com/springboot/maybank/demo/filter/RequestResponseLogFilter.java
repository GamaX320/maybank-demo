// package com.springboot.maybank.demo.filter;
//
// import java.io.IOException;
// import java.io.UnsupportedEncodingException;
//
// import org.springframework.stereotype.Component;
// import org.springframework.web.util.ContentCachingRequestWrapper;
// import org.springframework.web.util.ContentCachingResponseWrapper;
//
// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.extern.slf4j.Slf4j;
//
// @Component
// @Slf4j
// public class RequestResponseLogFilter implements Filter {
//
// @Override
// public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
// throws IOException, ServletException {
//
// HttpServletRequest request = (HttpServletRequest) servletRequest;
// HttpServletResponse response = (HttpServletResponse) servletResponse;
//
// ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
// ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//
// requestWrapper.getInputStream();
//
// filterChain.doFilter(requestWrapper, responseWrapper);
//
// String requestBody = parseString(requestWrapper.getContentAsByteArray(), requestWrapper.getCharacterEncoding());
// log.info("Request [{}]: \n {}", request.getRequestURI(), requestBody);
// String responseBody = parseString(responseWrapper.getContentAsByteArray(), responseWrapper.getCharacterEncoding());
// log.info("Response [{}]: \n {}", request.getRequestURI(), responseBody);
//
// }
//
// private String parseString(byte[] contentAsByteArray, String encoding) {
// try {
// return new String(contentAsByteArray, 0, contentAsByteArray.length, encoding);
// } catch (UnsupportedEncodingException uee) {
// uee.printStackTrace();
// }
// return "";
// }
//
// }
