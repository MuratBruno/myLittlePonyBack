package littlePoneyBack.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import littlePoneyBack.model.User;

@Repository
@Transactional
public class UserDAO  {
	@PersistenceContext
	EntityManager em;
	
	
	public <S extends User> S save(S entity) {
		em.persist(entity);
		
		return entity;
	}
	
	public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
		for (S entity : entities) {
			em.persist(entity);
		}
		return entities;
	}

	
	public Optional<User> findById(Integer id) {
		Optional<User> o = Optional.ofNullable(em.find(User.class, id));
		return o;
	}
	
	public Optional<User> findByLogin(String login) {
		TypedQuery<User> query = em.createQuery("select distinct u from User u where u.login = '" + login+"'", User.class);

		Optional<User> o =  Optional.ofNullable(query.getSingleResult());
		return o;
	}

	
	public boolean existsById(Integer id) {
		Optional<User> o = Optional.ofNullable(em.find(User.class, id));
		return o.isPresent();
	}

	


	@Transactional
	public Iterable<User> findAll() {
		TypedQuery<User> query = em.createQuery("select distinct u from User u", User.class);

		List<User> Users = query.getResultList();

		return Users;
	}


	public Iterable<User> findAllById(Iterable<Integer> ids) {

		String listId = "";
		for (int id : ids) {
			listId += id;
		}
		if (listId.length() > 0) {
			listId = "(" + listId + ")";
			TypedQuery<User> query = em.createQuery("select distinct u from User u where id in" + listId, User.class);
			List<User> Users = query.getResultList();

			return Users;
		} else {
			return new ArrayList<User>();
		}

	}


	public long count() {
			TypedQuery<User> query = em.createQuery("select distinct u from User u", User.class);

		int gamesCount = query.getFirstResult();

		return gamesCount;
	}


	public void deleteById(Integer id) {
		em.remove(em.find(User.class, id));

	}


	public void delete(User entity) {
		em.remove(entity);

	}


	public void deleteAll(Iterable<? extends User> entities) {
		for (User u : entities) {
			em.remove(u);
		}
	}


	public void deleteAll() {
		Query query = em.createQuery("delete from User ");

		query.executeUpdate();
	}

}
