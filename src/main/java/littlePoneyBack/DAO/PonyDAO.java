package littlePoneyBack.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaUpdate;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import littlePoneyBack.model.Pony;

@Repository
@Transactional
 public class PonyDAO {
	@PersistenceContext
	EntityManager em;
	

	public <S extends Pony> S save(S entity) {
		em.persist(entity);
		
		return entity;
	}

	
	public <S extends Pony> Iterable<S> saveAll(Iterable<S> entities) {
		for (S entity : entities) {
			em.persist(entity);
	}
		return entities;
	}

	
	public Optional<Pony> findById(Integer id) {
		Optional<Pony> o = Optional.ofNullable(em.find(Pony.class, id));
		return o;
	}

	
	public boolean existsById(Integer id) {	Optional<Pony> o = Optional.ofNullable(em.find(Pony.class, id));
		return o.isPresent();
	}

	


	@Transactional
	public Iterable<Pony> findAll() {
		TypedQuery<Pony> query = em.createQuery("select distinct p from Pony p", Pony.class);

		List<Pony> ponies = query.getResultList();

		return ponies;
	}


	public Iterable<Pony> findAllById(Iterable<Integer> ids) {

		String listId = "";
		for (int id : ids) {
			listId += id;
		}
		if (listId.length() > 0) {
			listId = "(" + listId + ")";
			TypedQuery<Pony> query = em.createQuery("select distinct p from Pony p where id in" + listId, Pony.class);
			List<Pony> ponies = query.getResultList();

			return ponies;
		} else {
			return new ArrayList<Pony>();
		}

	}


	public long count() {
		TypedQuery<Pony> query = em.createQuery("select distinct p from Pony p", Pony.class);

		int gamesCount = query.getFirstResult();

		return gamesCount;
	}


	public void deleteById(Integer id) {
		em.remove(em.find(Pony.class, id));

	}


	public void delete(Pony entity) {
		em.remove(entity);

	}


	public void deleteAll(Iterable<? extends Pony> entities) {
		for (Pony p : entities) {
			em.remove(p);
		}
	}


	public void deleteAll() {
		Query query = em.createQuery("delete from Pony ");

		query.executeUpdate();
	}

}
