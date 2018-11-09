package littlePoneyBack.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import littlePoneyBack.DAO.PonyDAO;
import littlePoneyBack.DAO.RaceDAO;
import littlePoneyBack.exception.PonyCudException;
import littlePoneyBack.exception.PonyNotFoundException;
import littlePoneyBack.model.Pony;
import littlePoneyBack.model.Race;

@RestController
@RequestMapping("/api/ponies")
public class PonyController {
	@Autowired
	PonyDAO ponyDAO;

	@Autowired
	RaceDAO raceDAO;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Pony> getPonies() throws PonyNotFoundException {
		LinkedList<Pony> lp = new LinkedList<>();
		Iterable<Pony> o = ponyDAO.findAll();
		o.forEach(v -> {
			lp.push(v);
		});
		return lp;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Pony getPonyById(@PathVariable("id") int id) throws PonyNotFoundException {
		Optional<Pony> o = ponyDAO.findById(id);
		if (!o.isPresent()) {
			throw new PonyNotFoundException("Poney introuvable");

		} else {
			return (Pony) o.get();
		}
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Pony create(@RequestBody @Valid Pony Pony, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		System.out.println(Pony.toString());
		ponyDAO.save(Pony);
		return Pony;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Pony update(@RequestBody @Valid Pony pony, BindingResult bindingResult) throws PonyNotFoundException {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		Optional<Pony> getPony = ponyDAO.findById(pony.getId());
		Pony ponyFromBD;
		if (getPony.isPresent()) {
			ponyFromBD = ponyDAO.findById(pony.getId()).get();
		} else {
			throw new PonyNotFoundException("Poney introuvable");
		}

		ponyFromBD.setAge(pony.getAge());
		ponyFromBD.setColor(pony.getColor());
		ponyFromBD.setName(pony.getName());
		ponyFromBD.setWeight(pony.getWeight());
		ponyDAO.save(ponyFromBD);
		return ponyFromBD;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@OnDelete(action = OnDeleteAction.CASCADE)
	public Message delete(@PathVariable("id") int id) throws PonyNotFoundException, PonyCudException {
		Optional<Pony> observeAvantDestruction = ponyDAO.findById(id);

		if (observeAvantDestruction.isPresent()) {

			/*ArrayList<Race> lr = (ArrayList<Race>) raceDAO.findAll();
			
	
			lr.forEach((race) -> {
				List<Pony> poneysFiltrees = race.getPonies().stream().filter((pony) -> {
					return pony.getId() != id;
				}).collect(Collectors.toList());
				race.setPonies(poneysFiltrees);
				raceDAO.save(race);
			});*/

			ponyDAO.deleteById(id);

			
			
			
			
			Optional<Pony> observeApresDestruction = ponyDAO.findById(id);
			if (!observeApresDestruction.isPresent()) {
				return new Message("Élément supprimé", false);
			} else {
				throw new PonyCudException("Echec de l'opération, l'élément n'a pas été supprimé.");
			}

		} else {
			throw new PonyNotFoundException("L'élément que vous cherchez à supprimer n'a pas été trouvé.");
		}

	}

}
