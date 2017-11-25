package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.service.SeminarGroupServiceImpl;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={SeminarGroupServiceImpl.class})
public class ServiceConfiguration {

}
