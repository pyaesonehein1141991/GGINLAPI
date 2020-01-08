package org.tat.gginl.api.configuration;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaNameConfiguration {
	
	public PhysicalNamingStrategy physical() {
		return new PhysicalNamingStrategyStandardImpl();
	}

	public ImplicitNamingStrategy implicit() {
		return new ImplicitNamingStrategyJpaCompliantImpl();
	}
}
