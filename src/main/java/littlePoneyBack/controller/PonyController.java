package littlePoneyBack.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import littlePoneyBack.DAO.PonyDAO;
import littlePoneyBack.exception.PonyException;
import littlePoneyBack.model.Pony;

@RestController("/pony")
public class PonyController {
	@Autowired
	PonyDAO ponyDAO;

	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Pony> getPonies() throws PonyException {
		LinkedList<Pony> lp= new LinkedList<>();
		Iterable<Pony> o = ponyDAO.findAll();
		o.forEach(v -> {lp.push(v);});
		return lp;
	}
	
	@GetMapping(value = "/search/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Pony getPony(@RequestParam("id") int id) throws PonyException {
		Optional<Pony> o = ponyDAO.findById(id);
		if (!o.isPresent()) {
			throw new PonyException();

		} else {
			return (Pony) o.get();
		}
	}

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

	@PostMapping

	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Pony update(@RequestBody @Valid Pony pony, BindingResult bindingResult) throws PonyException {
		if (bindingResult.hasErrors()) {
			// gérer ses erreurs
			System.out.println(bindingResult.getErrorCount());
		}
		Optional<Pony> getPony = ponyDAO.findById(pony.getId());
		Pony ponyFromBD;
		if (getPony.isPresent()) {
			ponyFromBD = ponyDAO.findById(pony.getId()).get();
		} else {
			throw new PonyException();
		}

		ponyFromBD.setAge(pony.getAge());
		ponyFromBD.setColor(pony.getColor());
		ponyFromBD.setName(pony.getName());
		ponyFromBD.setWeight(pony.getWeight());
		ponyDAO.save(ponyFromBD);
		return ponyFromBD;
	}

	@RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String delete(@RequestBody @Valid int id, BindingResult bindingResult) throws PonyException {
		Optional<Pony> obsderveAvantDestruction = ponyDAO.findById(id);
		
		if(obsderveAvantDestruction.isPresent()) {
			ponyDAO.deleteById(id);
			Optional<Pony> obsderveAprestDestruction = ponyDAO.findById(id);
			if(!obsderveAprestDestruction.isPresent()) {
				return "Element Supprimé";
			}else {
				//TODO : Retravailler l'exception
				throw new PonyException();
			}
			
		}else {
			throw new PonyException();
		}
		
	}


}
