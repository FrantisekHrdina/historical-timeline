package cz.muni.fi.pa165.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import cz.muni.fi.pa165.dao.SeminarGroupDao;

public class SeminarGroupServiceTest {

	@Mock
	private SeminarGroupDao seminarGroupDao;
	
	@Autowired
    @InjectMocks
    SeminarGroupService seminarGroupService;
	
}
