package simulationWebService.model;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CoordEntity> location = new ArrayList<CoordEntity>();
	
	
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

	public List<CoordEntity> getLocation() {
		return location;
	}

	public void setLocation(List<CoordEntity> location) {
		this.location = location;
	}
	
	public void addCoord(int i, int j) {
		this.location.add(new CoordEntity(i, j));
	}
	
	public void removeCoord(CoordEntity coord) {
		int index = this.location.indexOf(coord);
		this.location.remove(index);
		
		
	}
	
	@Override
	public String toString() {
		return "Type : " + this.type + "\n Intensity : " + this.intensity;
	}



	
	
}
