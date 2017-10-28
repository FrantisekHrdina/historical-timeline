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

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pa165.dao.SeminarGroupDao;
import cz.muni.fi.pa165.entities.Event;
import cz.muni.fi.pa165.entities.SeminarGroup;
import cz.muni.fi.pa165.entities.Timeline;

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
	public void testAddGroup() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		assertThat(seminarGroupDao.findAllGroups()).containsOnly(basicSeminarGroup);
	}
	
	@Test
	public void testAddNullGroup() {
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void testAddGroupWithNullName() {
		SeminarGroup seminarGroupWithNullName = new SeminarGroup();
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(seminarGroupWithNullName);
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void testRemoveGroup() {
		seminarGroupDao.addGroup(basicSeminarGroup);
		seminarGroupDao.addGroup(advancedSeminarGroup);
		
		seminarGroupDao.removeGroup(basicSeminarGroup);
		assertThat(seminarGroupDao.findAllGroups()).containsOnly(advancedSeminarGroup);
	}
	
	@Test
	public void testRemoveNullGroup() {
		assertThatThrownBy(() -> {
			seminarGroupDao.addGroup(null);
		}).isInstanceOf(IllegalArgumentException.class);
	}
	
	@Test
	public void testEditGroup() {
		SeminarGroup seminarGroup = new SeminarGroup();
		seminarGroup.setName("18th/19th century conflicts");
		
		List<Timeline> timelines = new ArrayList<Timeline>();
		Timeline sevenYearsWar = new Timeline();
		sevenYearsWar.setName("7 Years' War");

		Event battleOfKolin = new Event();
		battleOfKolin.setName("Battle of Kolín");
		battleOfKolin.setDate(LocalDate.of(1757, Month.JUNE, 18));
		Event battleOfTorgau = new Event();
		battleOfTorgau.setName("Battle of Torgau");
		battleOfTorgau.setDate(LocalDate.of(1760, Month.NOVEMBER, 3));
		
		timelines.add(sevenYearsWar);
		seminarGroup.setTimelines(timelines);
		
		seminarGroupDao.addGroup(seminarGroup);
		seminarGroup = seminarGroupDao.findGroup(seminarGroup.getId());
		
		seminarGroup.setName("Napoleonic Wars");
		
		Timeline majorBattles = new Timeline();
		majorBattles.setName("Major battles");
		Event battleOfAusterlitz = new Event();
		battleOfAusterlitz.setName("Battle of Austerlitz");
		battleOfAusterlitz.setDate(LocalDate.of(1805, Month.DECEMBER, 2));
		Event battleOfWaterloo = new Event();
		battleOfWaterloo.setName("Battle of Waterloo");
		battleOfWaterloo.setDate(LocalDate.of(1815, Month.JUNE, 18));
		
		timelines.clear();
		timelines.add(majorBattles);
		seminarGroup.setTimelines(timelines);
		
		seminarGroupDao.editGroup(seminarGroup);
		seminarGroup = seminarGroupDao.findGroup(seminarGroup.getId());
		
		assertThat(seminarGroup.getName()).isEqualTo("Napoleonic Wars");
		assertThat(seminarGroup.getTimelines()).isEqualTo(timelines);
	}
}
