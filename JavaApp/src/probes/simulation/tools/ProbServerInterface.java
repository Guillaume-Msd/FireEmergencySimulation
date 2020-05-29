package probes.simulation.tools;

import java.awt.Point;
import java.io.IOException;

public interface ProbServerInterface {

	  /** 
	   *  Send alarms to the server
	 * @return 
	 * @throws IOException 
	   */
	  public void triggerAlarm() throws IOException;

	  /** 
	   *  Send information to the server
	 * @throws IOException 
	   */
	  public void sendInformation(Point p) throws IOException;

	  /** 
	   *  Get information to trigger behaviour
	 * @return 
	 * @throws IOException 
	   */
	  public void getInformation() throws IOException;

	}