package cz.muni.fi.pa165.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;

/**
 * @author Martin Wörgötter
 */
@Service
public class SeminarGroupServiceImpl implements SeminarGroupService {

	@Autowired
	SeminarGroupDao seminarGroupDao;

	@Override
	public void saveGroup(SeminarGroup g) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeGroup(SeminarGroup g) {
		throw new UnsupportedOperationException();
	}

	@Override
	public SeminarGroup findGroup(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<SeminarGroup> findAllGroups() {
		throw new UnsupportedOperationException();
	}

}
