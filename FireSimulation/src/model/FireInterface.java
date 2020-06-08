package model;

public interface FireInterface {

  /** 
   *  Propage le feu
   *  @return Coord
   */
  public Coord aggravate();

  /**
   * attenue le feu
   * @return Coord 
   */
  public Coord attenuate();

}