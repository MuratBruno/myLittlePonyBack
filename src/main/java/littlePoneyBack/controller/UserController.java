package littlePoneyBack.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import littlePoneyBack.DAO.UserDAO;
import littlePoneyBack.DAO.UserDAO;
import littlePoneyBack.exception.UserCudException;
import littlePoneyBack.exception.UserNotFoundException;
import littlePoneyBack.exception.UserCudException;
import littlePoneyBack.exception.UserNotFoundException;
import littlePoneyBack.model.User;
import littlePoneyBack.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserDAO UserDAO;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getPonies() throws UserNotFoundException {
		LinkedList<User> lp= new LinkedList<>();
		Iterable<User> o = UserDAO.findAll();
		o.forEach(v -> {lp.push(v);});
		return lp;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/byLogin/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUserByLogin(@PathVariable("login") String login) throws UserNotFoundException {
		Optional<User> o = UserDAO.findByLogin(login);
		if (!o.isPresent()) {
			throw new UserNotFoundException("Utilisateur introuvable");

		} else {
			return (User) o.get();
		}
	}
	

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUserById(@PathVariable("id") int id) throws UserNotFoundException {
		Optional<User> o = UserDAO.findById(id);
		if (!o.isPresent()) {
			throw new UserNotFoundException("Utilisateur introuvable");

		} else {
			return (User) o.get();
		}
	}
	
	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public User create(@RequestBody @Valid User user, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		System.out.println(user.toString());
		UserDAO.save(user);
		return user;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.PUT,value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public User update(@RequestBody @Valid User user, BindingResult bindingResult) throws UserNotFoundException {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		Optional<User> getUser = UserDAO.findById(user.getId());
		User UserFromBD;
		if (getUser.isPresent()) {
			UserFromBD = UserDAO.findById(user.getId()).get();
		} else {
			throw new UserNotFoundException("Poney introuvable");
		}

		UserFromBD.setLogin(user.getLogin());
		UserFromBD.setPassword(user.getPassword());
		UserDAO.save(UserFromBD);
		return UserFromBD;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.DELETE,value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Message delete(@PathVariable("id") int id) throws UserNotFoundException, UserCudException {
		Optional<User> obsderveAvantDestruction = UserDAO.findById(id);
		
		if(obsderveAvantDestruction.isPresent()) {
			UserDAO.deleteById(id);
			Optional<User> obsderveAprestDestruction = UserDAO.findById(id);
			if(!obsderveAprestDestruction.isPresent()) {
				return new Message("Élément supprimé", false);
			}else {
				//TODO : Retravailler l'exception
				throw new UserCudException("Echec de l'opération, l'élément n'a pas été supprimé.");
			}
			
		}else {
			throw new UserNotFoundException("L'élément que vous cherchez à supprimer n'a pas été trouvé.");
		}
		
	}


}
