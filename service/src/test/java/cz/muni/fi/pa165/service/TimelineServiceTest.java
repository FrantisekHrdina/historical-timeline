package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dao.TimelineDao;
import cz.muni.fi.pa165.entities.Timeline;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class TimelineServiceTest {

    @Mock
    private TimelineDao timelineDao;

    @Mock
    private TimelineService timelineService;

    private Timeline testTimeline;

    @BeforeMethod
    public void prepareTestTimeline(){
        testTimeline = new Timeline();

        //when(exchangeService.getCurrencyRate(Currency.EUR, Currency.CZK)).thenReturn(BigDecimal.valueOf(27));
    }

    @BeforeMethod
    public void prepareTestSeminarGroup(){

    }

}
