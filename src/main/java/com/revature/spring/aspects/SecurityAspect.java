package com.revature.spring.aspects;


import com.revature.spring.dtos.responses.Principal;
import com.revature.spring.services.TokenService;
import com.revature.spring.services.UserService;
import com.revature.spring.util.exceptions.AuthenticationException;
import com.revature.spring.util.exceptions.ForbiddenException;
import com.revature.spring.util.security.Secured;
import com.revature.spring.util.security.SecurityContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class SecurityAspect {

    private UserService userService;
    private TokenService tokenService;
    private SecurityContext securityContext;

    @Autowired
    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Before("@annotation(com.revature.spring.util.security.Secured)")
    public void secureEndpoint(JoinPoint jp) {
        MethodSignature sig = (MethodSignature) jp.getSignature();
        System.out.println(sig + " WE START HERE");
        Method method = sig.getMethod();
        System.out.println(method);
        Secured myAnnotation = method.getAnnotation(Secured.class);
        System.out.println(myAnnotation.allowedRoles());
        System.out.println(myAnnotation);

        Principal requester = tokenService.extractRequesterDetails(getCurrentRequest().getHeader("Authorization"));
        if (requester != null) {
            for (String myRole : myAnnotation.allowedRoles()) {
                System.out.println(requester);
                System.out.println("myRole: " +myRole);
                System.out.println("requester.getRole()): " +requester.getRole());

                //Can't put in same println statement for some reason
                System.out.println("myRole == requester.getRole()) ");
                System.out.println(myRole == requester.getRole());


                if (Objects.equals(myRole, requester.getRole())) {
                    System.out.println("WE WANT TO BE HERE");
                    securityContext.setRequester(requester);
                    return;
                }
            }
            throw new ForbiddenException();
            }
        else throw new AuthenticationException("Please log in before making Requests");
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }
}
