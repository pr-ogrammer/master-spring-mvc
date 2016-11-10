package pl.prutkowski.master.spring.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by programmer on 11/8/16.
 */
@Configuration
@Profile("redis")
@EnableRedisHttpSession
public class RedisConfig {
}
