package cz.muni.fi.pa165.facade;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;


import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.service.SeminarGroupService;

/**
 * @author Martin Wörgötter
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SeminarGroupFacadeTest extends AbstractTestNGSpringContextTests {

	@Mock
	private SeminarGroupService seminarGroupService;
	
	@Autowired
	@InjectMocks
	private SeminarGroupFacade seminarGroupFacade;
	
	private SeminarGroupDTO basicGroup;
	private SeminarGroupDTO advancedGroup;
	
	@BeforeMethod
	public void initMocks() {
		MockitoAnnotations.initMocks(this);	
	}
	
	@BeforeMethod
	public void createSeminarGroups() {		
		basicGroup = new SeminarGroupDTO();
		basicGroup.setName("Basic");

		advancedGroup = new SeminarGroupDTO();
		advancedGroup.setName("Advanced");
	}
	
	@Test
	public void saveGroup() {
		Long id = seminarGroupFacade.saveGroup(basicGroup);
		assertThat(seminarGroupFacade.findGroup(id)).isNotNull();
	}
	
	@Test
	public void removeGroup() {
		Long id = seminarGroupFacade.saveGroup(basicGroup);
		seminarGroupFacade.saveGroup(advancedGroup);
		assertThat(seminarGroupFacade.findAllGroups()).hasSize(2);
		seminarGroupFacade.removeGroup(id);
		assertThat(seminarGroupFacade.findAllGroups()).hasSize(1);
	}
	
	@Test
	public void findGroup() {
		Long id = seminarGroupFacade.saveGroup(basicGroup);
		assertThat(seminarGroupFacade.findGroup(id)).isNotNull();
	}
	
	@Test
	public void findAllGroups() {
		Long basicId = seminarGroupFacade.saveGroup(basicGroup);
		Long advancedId = seminarGroupFacade.saveGroup(advancedGroup);
		assertThat(seminarGroupFacade.findGroup(basicId)).isNotNull();
		assertThat(seminarGroupFacade.findGroup(advancedId)).isNotNull();
	}
}