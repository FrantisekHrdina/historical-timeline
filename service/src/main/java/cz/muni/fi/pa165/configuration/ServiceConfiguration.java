package cz.muni.fi.pa165.configuration;

import cz.muni.fi.pa165.PersistenceApplicationContext;
import cz.muni.fi.pa165.dto.EventDTO;
/*
import cz.muni.fi.pa165.dto.TimelineDTO;
*/
import cz.muni.fi.pa165.dto.UserDTO;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.dto.TimelineDTO;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;
import cz.muni.fi.pa165.entities.User;
import cz.muni.fi.pa165.facade.EventFacadeImpl;
import cz.muni.fi.pa165.facade.SeminarGroupFacadeImpl;
import cz.muni.fi.pa165.facade.TimelineFacadeImpl;
import cz.muni.fi.pa165.facade.UserFacadeImpl;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.mapping.BeanMappingServiceImpl;
import cz.muni.fi.pa165.service.EventServiceImpl;
import cz.muni.fi.pa165.service.SeminarGroupServiceImpl;
import cz.muni.fi.pa165.service.TimelineServiceImpl;
import cz.muni.fi.pa165.service.UserServiceImpl;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author František Hrdina
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses={
        EventFacadeImpl.class, EventServiceImpl.class,
        TimelineFacadeImpl.class, TimelineServiceImpl.class,
        UserFacadeImpl.class, UserServiceImpl.class,
        SeminarGroupFacadeImpl.class, SeminarGroupServiceImpl.class,
        BeanMappingServiceImpl.class
})
public class ServiceConfiguration {
    @Bean
    public Mapper dozer() {
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(Event.class, EventDTO.class).fields("date", "date",
                    FieldsMappingOptions.copyByReference());
            mapping(Timeline.class, TimelineDTO.class);
            mapping(SeminarGroup.class, SeminarGroupDTO.class);
            mapping(User.class, UserDTO.class);
        }
    }


}
