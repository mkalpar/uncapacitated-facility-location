import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


public class Network {
   
  
    private int numOfClients;
    private int numOfFacilities;
    public static int[][] arrayOfCij;
    
    public Network(String[] graphContent) {
    	
    	numOfFacilities = graphContent.length;
    	for (int i =0 ; i < numOfFacilities ; i++) {
    		String[] line = graphContent[i].split(" ");		
    		numOfClients = line.length;
    	}

    	arrayOfCij =  new int[numOfFacilities][numOfClients];
    	for (int i =0 ; i < numOfFacilities ; i++) {
    		String[] line = graphContent[i].split(" ");		
    		for (int j = 0 ; j < line.length ; j++) {
    			
    			arrayOfCij[i][j] = Integer.parseInt(line[j]);
    		}
    			
    	}
    	
    }
    
    public int getNumOfFacilities() {
        return numOfFacilities;
    }
    
  
    
    public int getNumOfClients() {
        return numOfClients;
    }
    public int[][] getCijArray() {
    	return arrayOfCij;
    }
      
    
    public static void main(String[] args) {
        String[] graphContent = new String[]{"4 5 2", "1 0 0", "1 10 2", 
                "1 2 5", "1 2 5 ", "1 3 3", "2 3 4", "4 2 2", "1 3 5"};
        
        Network network = new Network(graphContent);
        System.out.println(network.numOfFacilities);

    }
    
    
}
