package cz.muni.fi.pa165.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.service.SeminarGroupService;

/**
 * @author Martin Wörgötter
 */
@Transactional
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SeminarGroupFacadeTest extends AbstractTestNGSpringContextTests {

	@Mock
	private SeminarGroupService seminarGroupService;
	
	@Autowired
	@InjectMocks
	private SeminarGroupFacade seminarGroupFacade;
	
	@BeforeMethod
	public void initMocks() {
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void saveGroup() {
		seminarGroupFacade.saveGroup(any());
		verify(seminarGroupService, times(1)).saveGroup(any());
	}
	
	@Test
	public void removeGroup() {
		seminarGroupFacade.removeGroup(any());
		verify(seminarGroupService, times(1)).removeGroup(any());
	}
	
	@Test
	public void findGroup() {
		seminarGroupFacade.findGroup(any());
		verify(seminarGroupService, times(1)).findGroup(any());
	}
	
	@Test
	public void findAllGroups() {
		seminarGroupFacade.findAllGroups();
		verify(seminarGroupService, times(1)).findAllGroups();
	}
}
