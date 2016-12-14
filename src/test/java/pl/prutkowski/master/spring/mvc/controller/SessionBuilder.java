package pl.prutkowski.master.spring.mvc.controller;

import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.mock.web.MockHttpSession;
import pl.prutkowski.master.spring.mvc.controller.profile.UserProfileSession;

import java.util.Arrays;

/**
 * Created by programmer on 12/14/16.
 */
public class SessionBuilder {

    private MockHttpSession session;
    private UserProfileSession userProfileSession;

    public SessionBuilder() {
        session = new MockHttpSession();
        userProfileSession = new UserProfileSession();
        session.setAttribute(ScopedProxyUtils.getTargetBeanName("userProfileSession"), userProfileSession);
    }

    public SessionBuilder userTastes(String... tastes) {
        userProfileSession.setTastes(Arrays.asList(tastes));
        return this;
    }

    public MockHttpSession build() {
        return session;
    }
}
