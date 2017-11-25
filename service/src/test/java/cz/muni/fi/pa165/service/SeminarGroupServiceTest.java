package cz.muni.fi.pa165.service;

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

import cz.muni.fi.pa165.configuration.ServiceConfiguration;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.exception.DAOException;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Martin Wörgötter
 */
@Transactional
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SeminarGroupServiceTest extends AbstractTestNGSpringContextTests {

	@Mock
	private SeminarGroupDao seminarGroupDao;

	@Autowired
	@InjectMocks
	private SeminarGroupService seminarGroupService;

	private SeminarGroup basicGroup;
	private SeminarGroup advancedGroup;

	@BeforeMethod
	public void initMocks() {
		MockitoAnnotations.initMocks(this);	
	}
	
	@BeforeMethod
	public void createSeminarGroups() {		
		basicGroup = new SeminarGroup();
		basicGroup.setName("Basic");

		advancedGroup = new SeminarGroup();
		advancedGroup.setName("Advanced");
	}
	
	@Test
	public void saveGroup() {
		List<SeminarGroup> allSeminarGroups = new ArrayList<>();
		allSeminarGroups.add(basicGroup);
		when(seminarGroupDao.findAllGroups()).thenReturn(allSeminarGroups);
		seminarGroupService.saveGroup(basicGroup);
		assertThat(seminarGroupService.findAllGroups()).containsOnly(basicGroup);
	}
	
	@Test
	public void saveNullGroup() {
		assertThatThrownBy(() -> seminarGroupService.saveGroup(null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void saveFails() {
		doThrow(new RuntimeException()).when(seminarGroupDao).addGroup(any());
		assertThatThrownBy(() -> seminarGroupService.saveGroup(basicGroup))
				.isInstanceOf(DAOException.class);
	}

	@Test
	public void removeGroup() {
		seminarGroupService.saveGroup(basicGroup);
		seminarGroupService.saveGroup(advancedGroup);

		seminarGroupService.removeGroup(basicGroup);
		
		List<SeminarGroup> remainingGroups = new ArrayList<>();
		remainingGroups.add(advancedGroup);
		when(seminarGroupDao.findAllGroups()).thenReturn(remainingGroups);
		
		assertThat(seminarGroupService.findAllGroups())
				.containsOnly(advancedGroup);
	}

	@Test
	public void removeNullGroup() {
		assertThatThrownBy(() -> seminarGroupService.removeGroup(null))
				.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void removeFails() {
		doThrow(new RuntimeException()).when(seminarGroupDao).removeGroup(any());
		assertThatThrownBy(() -> seminarGroupService.removeGroup(basicGroup))
				.isInstanceOf(DAOException.class);
	}

	@Test
	public void findGroup() {
		when(seminarGroupDao.findGroup(basicGroup.getId())).thenReturn(basicGroup);
		assertThat(seminarGroupService.findGroup(basicGroup.getId()))
				.isEqualTo(basicGroup);
	}

	@Test
	public void findGroupByNullId() {
		assertThat(seminarGroupService.findGroup(null)).isNull();
	}
	
	@Test
	public void findFails() {
		doThrow(new RuntimeException()).when(seminarGroupDao).findGroup(any());
		assertThatThrownBy(() -> seminarGroupService.findGroup(1l))
				.isInstanceOf(DAOException.class);
	}

	@Test
	public void findAllGroups() {
		when(seminarGroupDao.findAllGroups()).thenReturn(new ArrayList<SeminarGroup>());
		assertThat(seminarGroupService.findAllGroups()).isEmpty();

		List<SeminarGroup> allGroups = new ArrayList<>();
		allGroups.add(basicGroup);
		allGroups.add(advancedGroup);
		when(seminarGroupDao.findAllGroups()).thenReturn(allGroups);
		assertThat(seminarGroupService.findAllGroups())
				.usingFieldByFieldElementComparator()
				.containsOnly(basicGroup, advancedGroup);
	}
	
	@Test
	public void findAllFails() {
		doThrow(new RuntimeException()).when(seminarGroupDao).findAllGroups();
		assertThatThrownBy(() -> seminarGroupService.findAllGroups())
				.isInstanceOf(DAOException.class);
	}

}
