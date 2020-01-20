package com.clane.test.util;

import com.clane.test.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestUtil {

    static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
    }

    public static Long getLoggedInUser() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return (Long)getRequest().getAttribute("userId");
        }
        throw new AppException("user_not_logged_in", HttpStatus.UNAUTHORIZED);
    }

    public static void setLoggedInUser(Long userId) {
        getRequest().setAttribute("userId", userId);
    }
}