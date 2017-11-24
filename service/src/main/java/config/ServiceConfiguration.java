package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.service.SeminarGroupService;

@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={SeminarGroupService.class})
public class ServiceConfiguration {

}
