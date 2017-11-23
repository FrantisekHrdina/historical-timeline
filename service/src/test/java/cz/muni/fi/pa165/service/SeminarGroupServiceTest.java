package cz.muni.fi.pa165.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.exception.DAOException;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Martin Wörgötter
 */
public class SeminarGroupServiceTest {

	@Mock
	private SeminarGroupDao seminarGroupDao;

	@Autowired
	@InjectMocks
	private SeminarGroupService seminarGroupService;

	private SeminarGroup basicGroup;

	@BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
	
	@BeforeMethod
	public void createSeminarGroups() {
		SeminarGroup basicGroup = new SeminarGroup();
		basicGroup.setName("Basic");
	}

	@Test
	public void saveGroup() {
		when(seminarGroupDao.findGroup(any())).thenReturn(basicGroup);
		seminarGroupService.saveGroup(basicGroup);
		assertThat(seminarGroupDao.findGroup(1l)).isEqualTo(basicGroup);
	}

	@Test
	public void saveFails() {
		doThrow(new Exception()).when(seminarGroupDao).addGroup(any());
		assertThatThrownBy(() -> seminarGroupService.saveGroup(basicGroup)).isInstanceOf(DAOException.class);
	}
	
	@Test
	public void removeFails() {
		doThrow(new Exception()).when(seminarGroupDao).removeGroup(any());
		assertThatThrownBy(() -> seminarGroupService.removeGroup(basicGroup)).isInstanceOf(DAOException.class);
	}
	
	@Test
	public void findFails() {
		doThrow(new Exception()).when(seminarGroupDao).findGroup(any());
		assertThatThrownBy(() -> seminarGroupService.findGroup(1l)).isInstanceOf(DAOException.class);
	}
	
	@Test
	public void findAllFails() {
		doThrow(new Exception()).when(seminarGroupDao).findAllGroups();
		assertThatThrownBy(() -> seminarGroupService.findAllGroups()).isInstanceOf(DAOException.class);
	}
}
