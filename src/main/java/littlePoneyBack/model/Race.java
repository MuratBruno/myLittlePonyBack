package littlePoneyBack.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Race {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String location;
	private Date date;
	
	@OneToMany
	private List<Pony> ponies;

	public Race() {
		super();
	}

	public Race(int id, String location, Date date, List<Pony> ponies) {
		super();
		this.id = id;
		this.location = location;
		this.date = date;
		this.ponies = ponies;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Pony> getPonies() {
		return ponies;
	}

	public void setPonies(List<Pony> ponies) {
		this.ponies = ponies;
	}

	@Override
	public String toString() {
		return "Race [id=" + id + ", location=" + location + ", date=" + date + "]";
	}

}
