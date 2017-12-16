package cz.muni.fi.pa165;

import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.service.EventService;
import cz.muni.fi.pa165.service.SeminarGroupService;
import cz.muni.fi.pa165.service.TimelineService;
import cz.muni.fi.pa165.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {

    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private EventService eventService;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private UserService userService;

    @Autowired
    private SeminarGroupService seminarGroupService;

    @SuppressWarnings("unused")
    @Override
    public void loadData() throws IOException {

    }
}
