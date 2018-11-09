package littlePoneyBack.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import littlePoneyBack.model.Race;

@Repository
@Transactional
public class RaceDAO  {
	@PersistenceContext
	EntityManager em;
	
	
	public <S extends Race> S save(S entity) {
		em.persist(entity);
		
		return entity;
	}
	
	public <S extends Race> Iterable<S> saveAll(Iterable<S> entities) {
		for (S entity : entities) {
			em.persist(entity);
		}
		return entities;
	}

	
	public Optional<Race> findById(Integer id) {
		Optional<Race> o = Optional.ofNullable(em.find(Race.class, id));
		return o;
	}

	
	public boolean existsById(Integer id) {
		Optional<Race> o = Optional.ofNullable(em.find(Race.class, id));
		return o.isPresent();
	}

	


	@Transactional
	public Iterable<Race> findAll() {
		TypedQuery<Race> query = em.createQuery("select distinct r from Race r", Race.class);

		List<Race> races = query.getResultList();

		return races;
	}


	public Iterable<Race> findAllById(Iterable<Integer> ids) {

		String listId = "";
		for (int id : ids) {
			listId += id;
		}
		if (listId.length() > 0) {
			listId = "(" + listId + ")";
			TypedQuery<Race> query = em.createQuery("select distinct r from Race r where id in" + listId, Race.class);
			List<Race> races = query.getResultList();

			return races;
		} else {
			return new ArrayList<Race>();
		}

	}


	public long count() {
			TypedQuery<Race> query = em.createQuery("select distinct r from Race r", Race.class);

		int gamesCount = query.getFirstResult();

		return gamesCount;
	}


	public void deleteById(Integer id) {
		em.remove(em.find(Race.class, id));

	}


	public void delete(Race entity) {
		em.remove(entity);

	}


	public void deleteAll(Iterable<? extends Race> entities) {
		for (Race r : entities) {
			em.remove(r);
		}
	}


	public void deleteAll() {
		Query query = em.createQuery("delete from Race ");

		query.executeUpdate();
	}

}

