package com.revature.spring.util.security;


import com.revature.spring.dtos.responses.Principal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class SecurityContext {
    private Principal requester;

    public SecurityContext(Principal requester) {
        this.requester = requester;
    }

    public void setRequester(Principal requester) {
        this.requester = requester;
    }

    @Override
    public String toString() {
        return "SecurityContext{" +
                "requester=" + requester +
                '}';
    }
}
