package cz.muni.fi.pa165.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

import cz.muni.fi.pa165.entities.SeminarGroup;

/**
 *
 * @author František Hrdina
 *
 */
@Repository
public class SeminarGroupDaoImpl implements SeminarGroupDao {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Add group g to the database.
	 *
	 * @param g
	 */
	@Override
	public void addGroup(SeminarGroup g) {
		if (g == null) {
			throw new IllegalArgumentException("Given group is null");
		}
		if (g.getName() == null) {
			throw new IllegalArgumentException("Given group has null name");
		}
		if (g.getId() != null) {
			throw new IllegalArgumentException("Given group has not null id");
		}
		em.persist(g);
	}

	/**
	 * Delete group g from the database.
	 *
	 * @param g
	 */
	@Override
	public void removeGroup(SeminarGroup g) {
		if (g == null) {
			throw new IllegalArgumentException("Given group is null");
		}
		if (g.getId() == null) {
			throw new IllegalArgumentException("Given group has null id");
		}
		if (g.getName() == null) {
			throw new IllegalArgumentException("Given group has null name");
		}
		em.remove(g);
	}

	/**
	 * Update group g.
	 *
	 * @param g
	 */
	@Override
	public void editGroup(SeminarGroup g) {
		if (g == null) {
			throw new IllegalArgumentException("Given group is null");
		}
		if (g.getId() == null) {
			throw new IllegalArgumentException("Given group has null id");
		}
		if (g.getName() == null) {
			throw new IllegalArgumentException("Given group has null name");
		}
		em.merge(g);
	}

	/**
	 * Find group by id and return an object.
	 *
	 * @param id
	 *            of the group
	 * @return
	 */
	@Override
	public SeminarGroup findGroup(Long id) {
		try {
			return em
					.createQuery(
							"SELECT g from SeminarGroup g WHERE g.id = :id",
							SeminarGroup.class)
					.setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Find all groups and return them.
	 *
	 * @return
	 */
	@Override
	public List<SeminarGroup> findAllGroups() {
		return em
				.createQuery("select g from SeminarGroup g", SeminarGroup.class)
				.getResultList();
	}
}
