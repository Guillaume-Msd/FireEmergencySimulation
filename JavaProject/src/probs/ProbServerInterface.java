package probs;

public interface ProbServerInterface {

  /** 
   *  Send alarms to the server
   */
  public void triggerAlarm();

  /** 
   *  Send information to the server
   */
  public void sendInformation();

  /** 
   *  Get information to trigger behaviour
   */
  public void getInformation();

}