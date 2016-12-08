package pl.prutkowski.master.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

/**
 * Created by programmer on 12/8/16.
 */
@Configuration
@EnableSpringHttpSession
public class MapSessionConfig {

    @Bean
    public SessionRepository sessionRepository() {
        return new MapSessionRepository();
    }
}
