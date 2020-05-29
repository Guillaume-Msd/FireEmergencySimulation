package probes.simulation.tools;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

public interface ProbMeasureInterface {

  /** 
   *  Collect informations from probs at the rate in param
   *  @param rate ENLEVER CE PARAMETRE SI ON CONSIDERE QUE RATE EST L'ATTRIBUT DE ABSTRACTPROB
   *  @return List<Measures>
 * @throws IOException 
   */
  public List<Point> collectData() throws IOException;

  /** 
   *  Apply measures errors
 * @return 
   */
  public boolean applyErrors();

  /** 
   *  Send measure to the server
   */
  public void sendMeasures();

}