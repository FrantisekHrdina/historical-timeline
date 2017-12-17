package cz.muni.fi.pa165.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cz.muni.fi.pa165.dto.SeminarGroupCreateDTO;
import cz.muni.fi.pa165.dto.SeminarGroupDTO;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.mapping.BeanMappingService;
import cz.muni.fi.pa165.service.SeminarGroupService;

/**
 * @author Martin Wörgötter
 */
@Service
@Transactional
public class SeminarGroupFacadeImpl implements SeminarGroupFacade {

	@Autowired
	private SeminarGroupService seminarGroupService;

	@Autowired
	private BeanMappingService beanMappingService;

	@Override
	public Long saveGroup(SeminarGroupCreateDTO g) {
		SeminarGroup seminarGroup = beanMappingService.mapTo(g,
				SeminarGroup.class);
		seminarGroupService.saveGroup(seminarGroup);
		return seminarGroup.getId();
	}

	@Override
	public void editGroup(SeminarGroupCreateDTO g) {
		SeminarGroup seminarGroup = beanMappingService.mapTo(g, SeminarGroup.class);
		seminarGroupService.editGroup(seminarGroup);
	}
	
	@Override
	public void removeGroup(Long id) {
		seminarGroupService.removeGroup(seminarGroupService.findGroup(id));
	}

	@Override
	public SeminarGroupDTO findGroup(Long id) {
		SeminarGroup seminarGroup = seminarGroupService.findGroup(id);
		return beanMappingService.mapTo(seminarGroup, SeminarGroupDTO.class);
	}

	@Override
	public List<SeminarGroupDTO> findAllGroups() {
		List<SeminarGroup> seminarGroups = seminarGroupService.findAllGroups();
		return beanMappingService.mapTo(seminarGroups, SeminarGroupDTO.class);
	}

}
