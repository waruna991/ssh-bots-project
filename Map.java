import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map {
	
	public static ArrayList<String> countries = new ArrayList<String>();
	
    public static void mapCountries(ArrayList<String> countries) {
        String s;
        String address = "https://maps.googleapis.com/maps/api/staticmap?center=Sri%20Lanka&zoom=1&size=1000x700&maptype=roadmap&format=jpg";
        Process p;
        String country;
        
        for(int i=0; i < countries.size(); i++){
        	//System.out.println(countries[i]);
        	 country = countries.get(i).replaceAll(" ", "%20");
        	//System.out.println(countries[i]);
        	address = address + "&markers=color:red|label:aa|" + country;
        }
        
        System.out.println(address);
        
        try {
            p = Runtime.getRuntime().exec("firefox "+address);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                //System.out.println("line: " + s);
            p.waitFor();
            //System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
        	
        }
    }
    
    public static double Ip2Num(String Ip){
    	String [] ip = Ip.split("\\.");
    	Double num = Double.parseDouble(ip[0]) *256*256*256 + 
    			Double.parseDouble(ip[1]) *256*256 +
    			Double.parseDouble(ip[2]) *256 +
    			Double.parseDouble(ip[3]);
    	
    	return num;
    }
    
    public static String GetCountry(Double ip){
    	//ip to country data from http://software77.net/geo-ip/
    	String fileName = "IpToCountryc.csv";
    	try {
			FileReader fileRd = new FileReader(fileName);
			BufferedReader bufferRd = new BufferedReader(fileRd);
			String line = bufferRd.readLine();
			
			while( (line = bufferRd.readLine()) != null) {
				//System.out.println(line);
				String [] s = line.split(",");
				s[0]=s[0].replaceAll("\"", "");
				s[1]=s[1].replaceAll("\"", "");
				Double fromIp = Double.parseDouble(s[0]);
				Double toIp = Double.parseDouble(s[1]);
				if(fromIp<=ip && ip<=toIp){
					s[6]=s[6].replaceAll("\"", "");
					return s[6];
				}
			}
			
			fileRd.close();
			bufferRd.close();
			
		} catch (FileNotFoundException x) {
			System.out.println("Make sure " + fileName + " is also here!");
			System.exit(-1);
			
		} catch (IOException x) {
			System.out.println(x);
			System.exit(-1);
			
		}
    	
    	return null;
    }
    
    public static void showMap(){
    	Map.mapCountries(countries);
    }
    
    public static void addIp(String ip){
    	Double ipNum = Map.Ip2Num(ip);
    	String country = Map.GetCountry(ipNum);
    	Map.countries.add(country);
    }
    
    public static void main(String [] args){
		
    	
    	String fileName = "blocked.txt";
    	try {
			FileReader fileRd = new FileReader(fileName);
			BufferedReader bufferRd = new BufferedReader(fileRd);
			String line = bufferRd.readLine();
			
			while( (line = bufferRd.readLine()) != null) {
				//System.out.println(line);
				Map.addIp(line);
			}
			
			fileRd.close();
			bufferRd.close();
			
		} catch (FileNotFoundException x) {
			System.out.println("Make sure " + fileName + " is also here!");
			System.exit(-1);
			
		} catch (IOException x) {
			System.out.println(x);
			System.exit(-1);
		}
		
		Map.showMap();
		
		//System.out.println(Map.Ip2Num("1.2.3.4"));
		
		//ystem.out.println(Map.GetCountry(Map.Ip2Num("193.104.41.55")));
    }
}