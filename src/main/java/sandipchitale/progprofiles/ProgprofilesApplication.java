package sandipchitale.progprofiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.util.Arrays;

@SpringBootApplication
public class ProgprofilesApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplicationBuilder()
//				.listeners((ApplicationListener<ApplicationStartingEvent>) event -> {
////					event.getSpringApplication().setAdditionalProfiles("ping");
//					if (event.getSpringApplication().getAdditionalProfiles().contains("ping")) {
//						// Programmatically add the "pong" profile if ping is active
//						event.getSpringApplication().setAdditionalProfiles("pong");
//					}
//				})
				.initializers(	(ConfigurableApplicationContext applicationContext) -> {
					ConfigurableEnvironment environment = applicationContext.getEnvironment();
					if (environment.acceptsProfiles(Profiles.of("ping"))) {
						// Programmatically add the "pong" profile if ping is active
						environment.addActiveProfile("pong");
					}
				})
				.sources(ProgprofilesApplication.class)
				.build();
		springApplication.run(args);
	}

	@Bean
	public CommandLineRunner clr (Environment environment) {
	    return (args) -> {
			String[] activeProfiles = environment.getActiveProfiles();
			System.out.println("Active Profiles = " + Arrays.asList(activeProfiles));
			// Check if this activates application-pong.properties
			System.out.println("pingpong=" + environment.getProperty("pingpong"));
		};
	}
}
