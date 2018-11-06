package littlePoneyBack.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pony {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private int weight;
	private int age;
	private String color;

	//pas de many to one
	

	public Pony() {
		super();
	}

	public Pony(int id, String name, int weight, int age, String color) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.age = age;
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Pony [id=" + id + ", name=" + name + ", weight=" + weight + ", age=" + age + ", color=" + color + "]";
	}

}
