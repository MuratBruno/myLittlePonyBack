package littlePoneyBack.controller;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import littlePoneyBack.DAO.UserRepository;
import littlePoneyBack.DAO.UserDAO;
import littlePoneyBack.exception.UserCudException;
import littlePoneyBack.exception.UserNotFoundException;
import littlePoneyBack.exception.UserCudException;
import littlePoneyBack.exception.UserNotFoundException;
import littlePoneyBack.model.LoginRequest;
import littlePoneyBack.model.User;
import littlePoneyBack.model.User;

import com.auth0.jwt.*;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	UserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthentificationResponse(jwt));
    }

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<User> getUsers() throws UserNotFoundException {
		LinkedList<User> lp = new LinkedList<>();
		Iterable<User> o = userDAO.findAll();
		o.forEach(v -> {
			lp.push(v);
		});
		return lp;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/byLogin/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public User getUserByLogin(@PathVariable("login") String login) throws UserNotFoundException {
		Optional<User> o = userDAO.findByLogin(login);
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
		Optional<User> o = userDAO.findById(id);
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
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		System.out.println(user.toString());
		userDAO.save(user);
		return user;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.PUT, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public User update(@RequestBody @Valid User user, BindingResult bindingResult) throws UserNotFoundException {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		Optional<User> getUser = userDAO.findById(user.getUserId());
		User userFromBD;
		if (getUser.isPresent()) {
			userFromBD = userDAO.findById(user.getUserId()).get();
		} else {
			throw new UserNotFoundException("Poney introuvable");
		}

		userFromBD.setUsername(user.getUsername());
		userFromBD.setPassword(user.getPassword());
		userDAO.save(userFromBD);
		return userFromBD;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Message delete(@PathVariable("id") int id) throws UserNotFoundException, UserCudException {
		Optional<User> obsderveAvantDestruction = userDAO.findById(id);

		if (obsderveAvantDestruction.isPresent()) {
			userDAO.deleteById(id);
			Optional<User> obsderveAprestDestruction = userDAO.findById(id);
			if (!obsderveAprestDestruction.isPresent()) {
				return new Message("Élément supprimé", false);
			} else {
				// TODO : Retravailler l'exception
				throw new UserCudException("Echec de l'opération, l'élément n'a pas été supprimé.");
			}

		} else {
			throw new UserNotFoundException("L'élément que vous cherchez à supprimer n'a pas été trouvé.");
		}

	}

}
