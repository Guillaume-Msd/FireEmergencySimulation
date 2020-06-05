package models;




public class CoordEntity {
	private Integer id;
	private int x;
	private int y;
	
	
	public Integer IdCoord() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
	
	
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
