package io.github.opendeveloper.social.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import io.github.opendeveloper.social.google.config.GoogleIdentifierConfiguration;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import io.github.opendeveloper.social.google.api.impl.GoogleTokenVerifierTemplate;

import static org.junit.Assert.assertNotNull;


/**
 * Created by sgupta on 1/18/17.
 */
public class GoogleIdentifierConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @After
    public void tearDown() {
        if (this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void defaultGoogleIdTokenVerifier() {
        load(EmptyConfiguration.class, "spring.social.google.clients=client1,client2,client3");
        GoogleIdTokenVerifier googleIdTokenVerifier = this.context.getBean(GoogleIdTokenVerifier.class);
        assertNotNull(googleIdTokenVerifier);
        GoogleTokenVerifierTemplate googleTokenVerifierTemplate = this.context.getBean(GoogleTokenVerifierTemplate.class);
        assertNotNull(googleTokenVerifierTemplate);
    }

    @Configuration
    static class EmptyConfiguration {
    }

    private void load(Class<?> config, String... environment) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.register(config);
        applicationContext.register(GoogleIdentifierConfiguration.class);
        applicationContext.refresh();
        this.context = applicationContext;
    }
}
