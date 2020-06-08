package model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * @author francoise.perrin
 *
 */
public class Coord implements Serializable {
	
	/**
	 * 
	 */
	public int x, y;
	
	/**
	 * @param x
	 * @param y
	 */
	public Coord(@JsonProperty("x")int x, @JsonProperty("y")int y) {
		this.x = x; 
		this.y = y;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[x=" + x + ", y=" + y + "]";
	}
	
	/**
	 * @param x
	 * @param y
	 * @return true si les coordonn�es sont valides (dans un plateau de 8*8)
	 */
	public static boolean coordonnees_valides(int x, int y){
		return ( (x<=7) && (x>=0) && (y<=7) && (y>=0) );
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	public boolean isInRange(Coord center, int r) {
        return (Math.abs(center.x - this.x) < r && Math.abs(center.y - this.y) < r);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coord other = (Coord) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
}
