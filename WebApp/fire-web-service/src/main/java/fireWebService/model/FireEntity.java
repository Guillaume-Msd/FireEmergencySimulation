package fireWebService.model;



import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fire_entity")
public class FireEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String type;
	
	@Column
	private String intensity;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<CoordEntity> location = new HashSet<CoordEntity>();
	
	
	public FireEntity() {

	}
	
	public FireEntity(String type, String intensity) {
		this.type = type;
		this.intensity = intensity;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIntensity() {
		return intensity;
	}

	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}

	public Set<CoordEntity> getLocation() {
		return location;
	}

	public void setLocation(Set<CoordEntity> location) {
		this.location = location;
	}
	
	public void addCoord(int x, int y) {
		this.location.add(new CoordEntity(x, y));
	}

	
	@Override
	public String toString() {
		return "Type : " + this.type + "\n Intensity : " + this.intensity;
	}

	
	
}
