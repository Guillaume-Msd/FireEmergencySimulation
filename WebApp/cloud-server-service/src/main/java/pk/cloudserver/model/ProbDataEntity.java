package pk.cloudserver.model;

import java.awt.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "probs_data")
public class ProbDataEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected Integer id;
	
	@Column
	protected String type;
	
	@Column
	private float rate;
	
	@Column
	private double error;
	
	@Column
	private Point localisation;
	
	@Column
	protected float range;
	
	@Column 
	private int intensity;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public Point getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Point localisation) {
		this.localisation = localisation;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

}