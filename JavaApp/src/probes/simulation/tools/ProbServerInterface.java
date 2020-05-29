package probes.simulation.tools;

import java.awt.Point;
import java.io.IOException;

public interface ProbServerInterface {

	  /** 
	   *  Send alarms to the server
	 * @return 
	   */
	  public void triggerAlarm();

	  /** 
	   *  Send information to the server
	   */
	  public void sendInformation(Point p);

	  /** 
	   *  Get information to trigger behaviour
	 * @return 
	 * @throws IOException 
	   */
	  public void getInformation() throws IOException;

	}