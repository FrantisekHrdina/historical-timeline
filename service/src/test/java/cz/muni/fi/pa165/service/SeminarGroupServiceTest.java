package cz.muni.fi.pa165.service;

import org.hamcrest.core.IsEqual;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;

import static org.assertj.core.api.Assertions.*;

public class SeminarGroupServiceTest {

	@Mock
	private SeminarGroupDao seminarGroupDao;
	
	@Autowired
    @InjectMocks
    SeminarGroupService seminarGroupService;

	private SeminarGroup basicGroup;
	
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
}
