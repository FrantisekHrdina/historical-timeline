package cz.muni.fi.pa165.facade;

import static org.assertj.core.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.configuration.ServiceConfiguration;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;

/**
 * @author Martin Wörgötter
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SeminarGroupFacadeTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private SeminarGroupFacade seminarGroupFacade;
	
	private SeminarGroupDTO basicGroup;
	private SeminarGroupDTO advancedGroup;
	
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
		assertThat(seminarGroupFacade.findGroup(id)).isEqualTo(basicGroup);
	}
	
	@Test
	public void removeGroup() {
		Long id = seminarGroupFacade.saveGroup(basicGroup);
		seminarGroupFacade.saveGroup(advancedGroup);
		assertThat(seminarGroupFacade.findAllGroups()).containsOnly(basicGroup, advancedGroup);
		seminarGroupFacade.removeGroup(id);
		assertThat(seminarGroupFacade.findAllGroups()).containsOnly(advancedGroup);
	}
	
	@Test
	public void findGroup() {
		Long id = seminarGroupFacade.saveGroup(basicGroup);
		assertThat(seminarGroupFacade.findGroup(id)).isEqualTo(basicGroup);
	}
	
	@Test
	public void findAllGroups() {
		seminarGroupFacade.saveGroup(basicGroup);
		seminarGroupFacade.saveGroup(advancedGroup);
		assertThat(seminarGroupFacade.findAllGroups()).containsOnly(basicGroup, advancedGroup);
	}
}