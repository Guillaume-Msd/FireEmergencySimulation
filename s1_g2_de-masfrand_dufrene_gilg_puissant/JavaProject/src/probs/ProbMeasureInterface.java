package probs;

public interface ProbMeasureInterface {

  /** 
   *  Collect informations from probs at the rate in param
   *  @param rate ENLEVER CE PARAMETRE SI ON CONSIDERE QUE RATE EST L'ATTRIBUT DE ABSTRACTPROB
   *  @return List<Measures>
   */
  public void collectData();

  /** 
   *  Apply measures errors
   */
  public void applyErrors();

  /** 
   *  Send measure to the server
   */
  public void sendMeasures();

}