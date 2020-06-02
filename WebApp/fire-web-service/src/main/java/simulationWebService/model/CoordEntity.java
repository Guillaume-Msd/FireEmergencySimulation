package simulationWebService.model;


import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author francoise.perrin
 *
 */
@Entity
@Table(name = "coord_entity")
public class CoordEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	public Integer IdCoord() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column 
	private int x;
	
	
	@Column
	private int y;
	
	
	
	public CoordEntity() {

	}
	
	/**
	 * @param x
	 * @param y
	 */
	public CoordEntity(int x, int y) {
		this.x = x; 
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
