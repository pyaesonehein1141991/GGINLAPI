package org.tat.gginl.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tat.gginl.api.common.SecurityUser;
import org.tat.gginl.api.common.emumdata.Role;
import org.tat.gginl.api.domains.services.UserService;

@SpringBootApplication
@EnableScheduling
public class GginlApiApplication implements CommandLineRunner {
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
//		SpringApplication.run(GginlApiApplication.class, args);

		SpringApplication application = new SpringApplication(GginlApiApplication.class);
		Properties properties = new Properties();
		properties.setProperty("spring.main.banner-mode", "log");
		properties.setProperty("logging.file", "C:/APILOG.log");
		properties.setProperty("logging.pattern.console", "");
		application.setDefaultProperties(properties);
		application.run(args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		SecurityUser admin = new SecurityUser();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

//	    userService.signup(admin);

		SecurityUser client = new SecurityUser();
		client.setUsername("client");
		client.setPassword("client");
		client.setEmail("client@email.com");
		client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

//	    userService.signup(client);
	}
}
