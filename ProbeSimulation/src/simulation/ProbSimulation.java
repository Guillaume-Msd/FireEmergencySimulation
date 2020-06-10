package simulation;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.*;




public class ProbSimulation {
//PARAMETERS
	private List<AbstractProb> probList = new ArrayList<AbstractProb>(); //liste des sondes
	
	
//CONSTRUCTOR
	public ProbSimulation (){
	}

	
//MAIN
	public static void main(String[] args) throws InterruptedException, IOException {
		final ProbSimulation simulation = new ProbSimulation();
		simulation.initProbs(0);

		new Timer().scheduleAtFixedRate(new TimerTask(){
		    @Override
		    public void run(){
		    	
		    	try {
					simulation.majProbs();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (AbstractProb prob: simulation.probList) {
					if (prob.getRateCount() == 0) {
						try {
							prob.getInformation();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						prob.setRateCount(prob.getRate());
					}
					else {
						prob.setRateCount(prob.getRateCount()-1);
					}
				}
		    }
		}	,0,2000);
			
	}
	
	
//METHODS
	//initialise des probs (a randomiser)
	public void initProbs(int num) throws IOException {
		URL url = new URL("http://localhost:8081/ProbeWebService/removeAll"); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("DELETE"); 
		connection.setDoOutput(false); 
		connection.getInputStream();
		
		/*
		SmokeProb probe1 = new SmokeProb(1, 0.1, new Point(110,110), 10);
		
		this.addProbToMap(probe1);
		this.probList.add(probe1);
		
		ThermicProb probe2 = new ThermicProb( 1, 0.1, new Point(50,200), 20);
		
		this.addProbToMap(probe2);
		this.probList.add(probe2);
		
		ThermicProb probe3 = new ThermicProb( 1, 0.1, new Point(120,30), 20);
		
		this.addProbToMap(probe3);
		this.probList.add(probe3);
		
		SmokeProb probe4 = new SmokeProb(1, 0.1, new Point(50,50), 10);
		
		this.addProbToMap(probe4);
		this.probList.add(probe4);
		
		CO2Prob probe5 = new CO2Prob(1, 0.1, new Point(210,50), 30);
		
		this.addProbToMap(probe5);
		this.probList.add(probe5);
		
		
		CO2Prob probe6 = new CO2Prob(1, 0.1, new Point(60,80), 10);
		
		this.addProbToMap(probe6);
		this.probList.add(probe6);
		
		*/
		
		int i, j;
		Random r = new Random();
		for(i= 0 ; i < 256; i++) {
			for(j = 0; j < 256; j++) {
				if(i%30 == 0 && j%30 == 0) {
					int randint = r.nextInt(3);
					if(randint == 0) {
						SmokeProb probe1 = new SmokeProb(1, 0.1, new Point(i, j), 10);
						this.addProbToMap(probe1);
						this.probList.add(probe1);
					}
					else if(randint == 1) {
						CO2Prob probe5 = new CO2Prob(1, 0.1, new Point(i, j), 10);
						this.addProbToMap(probe5);
						this.probList.add(probe5);
					}
					else {
						ThermicProb probe3 = new ThermicProb( 1, 0.1, new Point(i, j), 10);
						this.addProbToMap(probe3);
						this.probList.add(probe3);
						
					}
				}
			}
		}
	
		for (AbstractProb prob: this.probList) {
			prob.setRateCount(prob.getRate());
		}
	}
	
	//ajoute une probe
	public void addProb(int id, String type, int rate, double error,Point localisation, int range) throws IOException {
		AbstractProb probe = null;
		if (type == "Smoke") {
			probe = new SmokeProb(rate, error, localisation, range);
			probe.setId(id);
			
		}
		else if (type == "CO2") {
			probe = new CO2Prob(rate, error, localisation, range);
			probe.setId(id);
		}
		else  {
			probe = new ThermicProb(rate, error, localisation, range);
			probe.setId(id);
	
		}

		
		this.probList.add(probe);
	}	
	
	public void addProbToMap(AbstractProb probe) throws IOException {
		//envoie la position de la sonde a Simulation service
		URL url = new URL("http://localhost:8081/ProbeWebService/add/" + probe.getType().toString() + "/" + probe.getRange() + "/" + probe.getLocalisation().x + "/" + probe.getLocalisation().y); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
		connection.setRequestMethod("GET"); 
		connection.setDoOutput(false); 
		connection.getInputStream();
		BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		while ((inputLine = in.readLine()) != null) { 
			response.append(inputLine); 
		} 
		in .close();
		probe.setId(Integer.parseInt(response.toString()));
		
		
	}
	

	public void majProbs() throws IOException {
		List<AbstractProb> servProbList = getProbsFromServ();
		boolean trouve = false;
		for (AbstractProb servProb: servProbList) {
			for (AbstractProb prob: this.probList) {
				if (prob.getId() == servProb.getId()) {
					trouve = true;
				}
			}
			if (trouve==false) {
				this.addProb(servProb.getId(), servProb.getType().toString(), servProb.getRate(), servProb.getError(), servProb.getLocalisation(), servProb.getRange());
			}
			trouve = false;
		}
	}
	
	public List<AbstractProb> getProbsFromServ() throws JsonParseException, JsonMappingException, IOException {
		URL obj = new URL("http://localhost:8081/ProbeWebService/getAll");
		HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
		httpURLConnection.setRequestMethod("GET"); 
		httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		BufferedReader in = new BufferedReader( new InputStreamReader(httpURLConnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer(); 
		while ((inputLine = in.readLine()) != null) { 
			response.append(inputLine); 
		} 
		in .close();
		
		List<AbstractProb> probsList = new ArrayList<AbstractProb>();
		
		ObjectMapper mapper = new ObjectMapper();

        AbstractProb[] events= mapper.readValue(response.toString(), AbstractProb[].class);
        int i;
        for (i = 0 ; i < events.length; i++) {
        	AbstractProb probe = events[i];
        	probsList.add(probe);
        	probe.convertCoord();
        	
        }
		return probsList;
		
	}
	
	
}



