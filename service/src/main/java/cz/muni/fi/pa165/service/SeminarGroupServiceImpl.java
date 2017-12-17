package cz.muni.fi.pa165.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.exception.DAOException;

/**
 * @author Martin Wörgötter
 */
@Service
public class SeminarGroupServiceImpl implements SeminarGroupService {

	@Autowired
	SeminarGroupDao seminarGroupDao;

	@Override
	public void saveGroup(SeminarGroup g) {
		if (g == null) {
			throw new IllegalArgumentException("SeminarGroup must not be null.");
		}
		try {
			seminarGroupDao.addGroup(g);
		} catch (Exception e) {
			throw new DAOException("Error while persisting SeminarGroup.");
		}
	}

	@Override
	public void removeGroup(SeminarGroup g) {
		if (g == null) {
			throw new IllegalArgumentException("SeminarGroup must not be null.");
		}
		try {
			seminarGroupDao.removeGroup(g);
		} catch (Exception e) {
			throw new DAOException("Error while removing SeminarGroup.");
		}
	}

	@Override
	public SeminarGroup findGroup(Long id) {
		try {
			return seminarGroupDao.findGroup(id);
		} catch (Exception e) {
			throw new DAOException("Error while retrieving SeminarGroup with id " + id);
		}
	}

	@Override
	public List<SeminarGroup> findAllGroups() {
		try {
			return seminarGroupDao.findAllGroups();
		} catch (Exception e) {
			throw new DAOException("Error while retrieving all groups");
		}
	}

	@Override
	public void editGroup(SeminarGroup g) {
		try {
			seminarGroupDao.editGroup(g);
		} catch (Exception e) {
			throw new DAOException(String.format("Error when updating group ({0})", g.toString()), e);
		}
	}

}
