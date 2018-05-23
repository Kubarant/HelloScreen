package com.hello.screen;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@ConditionalOnProperty(prefix = "app.scheduling", name="enable", havingValue="true", matchIfMissing = true)
public class SchedulingConfiguration {

}
