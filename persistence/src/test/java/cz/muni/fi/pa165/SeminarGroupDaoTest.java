package cz.muni.fi.pa165;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.SeminarGroup;

/**
 *
 * @author Martin Wörgötter
 */
@ContextConfiguration(classes = PersistenceApplicationContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class SeminarGroupDaoTest extends AbstractTestNGSpringContextTests {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SeminarGroupDao seminarGroupDao;

	private SeminarGroup basicSeminarGroup, advancedSeminarGroup;

	@BeforeMethod
	public void setup() {
		basicSeminarGroup = new SeminarGroup();
		basicSeminarGroup.setName("Basic");
		advancedSeminarGroup = new SeminarGroup();
		advancedSeminarGroup.setName("Advanced");
	}

	@Test
	public void addGroup() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		assertThat(seminarGroupDao.findAllGroups())
				.containsOnly(basicSeminarGroup);
	}

	@Test
	public void addNullGroup() {
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void addGroupWithNullName() {
		SeminarGroup seminarGroupWithNullName = new SeminarGroup();
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(seminarGroupWithNullName);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void addGroupWithExistingId() {
		basicSeminarGroup.setId(1L);
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void removeGroup() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		seminarGroupDao.addGroup(advancedSeminarGroup);

		seminarGroupDao.removeGroup(basicSeminarGroup);
		assertThat(seminarGroupDao.findAllGroups())
				.containsOnly(advancedSeminarGroup);
	}

	@Test
	public void removeNullGroup() {
		assertThatThrownBy(() -> {
			seminarGroupDao.removeGroup(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void removeNonExistingGroup() {
		basicSeminarGroup.setId(1L);
		assertThatThrownBy(() -> {
			seminarGroupDao.removeGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void removeGroupWithNullId() {
		assertThatThrownBy(() -> {
			seminarGroupDao.removeGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void removeGroupWithNullName() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		basicSeminarGroup.setName(null);
		assertThatThrownBy(() -> {
			seminarGroupDao.removeGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void editGroup() {
		SeminarGroup seminarAncientGreek = new SeminarGroup();
		seminarAncientGreek.setName("Ancient Greece");

		SeminarGroup seminarAncientRome = new SeminarGroup();
		seminarAncientRome.setName("Ancient Rome");

		seminarGroupDao.addGroup(seminarAncientGreek);
		seminarGroupDao.addGroup(seminarAncientRome);

		seminarAncientGreek.setName("Ancient Egypt");
		seminarGroupDao.editGroup(seminarAncientGreek);

		assertThat(seminarGroupDao.findGroup(seminarAncientGreek.getId()))
				.isEqualToComparingFieldByField(seminarAncientGreek);
		assertThat(seminarGroupDao.findGroup(seminarAncientRome.getId()))
				.isEqualToComparingFieldByField(seminarAncientRome);
	}

	@Test
	public void editNullGroup() {
		assertThatThrownBy(() -> {
			seminarGroupDao.editGroup(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void editGroupWithNullId() {
		assertThatThrownBy(() -> {
			seminarGroupDao.editGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void editGroupWithNullName() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		basicSeminarGroup.setName(null);
		assertThatThrownBy(() -> {
			seminarGroupDao.editGroup(basicSeminarGroup);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void findGroupTest() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		assertThat(seminarGroupDao.findGroup(basicSeminarGroup.getId()))
				.isEqualTo(basicSeminarGroup);
	}
	
	@Test
	public void findGroupByNullId() {
		assertThat(seminarGroupDao.findGroup(null)).isNull();
	}

	@Test
	public void findAllGroups() {
		assertThat(seminarGroupDao.findAllGroups()).isEmpty();

		seminarGroupDao.addGroup(basicSeminarGroup);
		seminarGroupDao.addGroup(advancedSeminarGroup);

		assertThat(seminarGroupDao.findAllGroups())
				.usingFieldByFieldElementComparator()
				.containsOnly(basicSeminarGroup, advancedSeminarGroup);
	}
}
