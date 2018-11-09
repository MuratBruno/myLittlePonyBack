package littlePoneyBack.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

import littlePoneyBack.DAO.RaceDAO;
import littlePoneyBack.exception.RaceCudException;
import littlePoneyBack.exception.RaceNotFoundException;
import littlePoneyBack.model.Race;


@RestController
@RequestMapping("/api/races")
public class RaceController {
	@Autowired
	RaceDAO raceDAO;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Race> getPonies() throws RaceNotFoundException {
		LinkedList<Race> lp= new LinkedList<>();
		Iterable<Race> o = raceDAO.findAll();
		o.forEach(v -> {lp.push(v);});
		return lp;
	}
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Race getRaceById(@PathVariable("id") int id) throws RaceNotFoundException {
		Optional<Race> o = raceDAO.findById(id);
		if (!o.isPresent()) {
			throw new RaceNotFoundException("Poney introuvable");

		} else {
			return (Race) o.get();
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Race create(@RequestBody @Valid Race Race, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		System.out.println(Race.toString());
		raceDAO.save(Race);
		return Race;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.PUT,value = "/",  consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Race update(@RequestBody @Valid Race Race, BindingResult bindingResult) throws RaceNotFoundException {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		Optional<Race> getRace = raceDAO.findById(Race.getId());
		Race RaceFromBD;
		if (getRace.isPresent()) {
			RaceFromBD = raceDAO.findById(Race.getId()).get();
		} else {
			throw new RaceNotFoundException("Race introuvable");
		}

		RaceFromBD.setLocation(Race.getLocation());
		RaceFromBD.setDate(Race.getDate());
		RaceFromBD.setPonies(Race.getPonies());
		raceDAO.save(RaceFromBD);
		return RaceFromBD;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.DELETE,value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Message delete(@PathVariable("id") int id) throws RaceNotFoundException, RaceCudException {
		Optional<Race> obsderveAvantDestruction = raceDAO.findById(id);
		
		if(obsderveAvantDestruction.isPresent()) {
			raceDAO.deleteById(id);
			Optional<Race> obsderveAprestDestruction = raceDAO.findById(id);
			if(!obsderveAprestDestruction.isPresent()) {
				return new Message("Élément supprimé", false);
			}else {
				//TODO : Retravailler l'exception
				throw new RaceCudException("Echec de l'opération, l'élément n'a pas été supprimé.");
			}
			
		}else {
			throw new RaceNotFoundException("L'élément que vous cherchez à supprimer n'a pas été trouvé.");
		}
		
	}


}