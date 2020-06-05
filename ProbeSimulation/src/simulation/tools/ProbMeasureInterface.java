package simulation.tools;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import models.Fire;

public interface ProbMeasureInterface {

  /** 
   *  Collect informations from probs at the rate in param
   *  @param rate ENLEVER CE PARAMETRE SI ON CONSIDERE QUE RATE EST L'ATTRIBUT DE ABSTRACTPROB
   *  @return List<Measures>
 * @throws IOException 
   */
  public List<Fire> collectData() throws IOException;

  /** 
   *  Apply measures errors
 * @return 
   */
  public boolean applyErrors();

  /** 
   *  Send measure to the server
 * @throws IOException 
   */
  public void sendMeasures() throws IOException;

}