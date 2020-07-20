
/**
 * Write a description of ParsingWeatherData here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class ParsingWeatherData {

    public CSVRecord coldestHourInFile(CSVParser parser){
    
        CSVRecord coldestSoFar = null;
        for(CSVRecord currentRow : parser){
            if(coldestSoFar == null){
                coldestSoFar = currentRow;
            }
            else {
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                double lowestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
                if(currentTemp<lowestTemp){
                    coldestSoFar = currentRow;
                }
            }
        
        }
        
        return coldestSoFar;
    
    }
    
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        System.out.println("coldest temp was " + coldest.get("TemperatureF") + " at " + coldest.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature(){
        
                File fileName = null;
		CSVRecord coldestSoFar = null;

		DirectoryResource dr = new DirectoryResource();
		for (File file : dr.selectedFiles()) {
			FileResource fr = new FileResource(file);
			CSVParser parser = fr.getCSVParser();
			CSVRecord currentRow = coldestHourInFile(parser);
			
			if(coldestSoFar == null) {
				coldestSoFar = currentRow;
				fileName = file;
			} 
			else {
				double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
				double lowestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
				if(currentTemp < lowestTemp && currentTemp > -50) {
					coldestSoFar = currentRow;
					fileName = file;
				}
			}
		}
		return fileName.getAbsolutePath();
    
    }
    
    public void testFileWithColdestTemperature(){
        
                String fileWithColdestTemp = fileWithColdestTemperature();
		File file = new File(fileWithColdestTemp);
		String fileName = file.getName();

		System.out.println("Coldest day was in file " + fileName);

		
		FileResource fr = new FileResource(file);
		CSVParser parser = fr.getCSVParser();
		CSVRecord lowestTemp = coldestHourInFile(parser);

		System.out.println("Coldest Temperature is: " + lowestTemp.get("TemperatureF"));

		System.out.println("All the Temperatures on the coldest day were");
		CSVParser parser2 = fr.getCSVParser();
		for(CSVRecord record : parser2) {
			double temp = Double.parseDouble(record.get("TemperatureF"));
			System.out.println(temp);
		}
    
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumidity = null;
		int currentHumidity;
		int lowestHumid;
		for(CSVRecord currentRow : parser) {
			if(lowestHumidity == null) {
				lowestHumidity = currentRow;
			} 

			else {
				if(!currentRow.get("Humidity").equals("N/A") && !lowestHumidity.get("Humidity").equals("N/A")) {
					currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
					lowestHumid = Integer.parseInt(lowestHumidity.get("Humidity"));
					
					if(currentHumidity < lowestHumid) {
						lowestHumidity = currentRow;
					}
				}
			}
		}
		return lowestHumidity;
    
    }
    
    public void  testLowestHumidityInFile(){
    
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidity = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + lowestHumidity.get("Humidity") + " at " + lowestHumidity.get("DateUTC"));
    }
    
    public CSVRecord  lowestHumidityInManyFiles(){
    
        CSVRecord lowestHumidity = null;
        int currentHumidity;
	int lowestHumid;
	
	DirectoryResource dr = new DirectoryResource();
	for(File f : dr.selectedFiles()){
	   FileResource fr = new FileResource(f);
	   CSVParser parser = fr.getCSVParser();
	   CSVRecord currentRow = lowestHumidityInFile(parser);
	   
	   if(lowestHumidity == null) {
		lowestHumidity = currentRow;
	   }
	   
	   else{
	           if(!currentRow.get("Humidity").equals("N/A") && !lowestHumidity.get("Humidity").equals("N/A")) {
			currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
			lowestHumid = Integer.parseInt(lowestHumidity.get("Humidity"));
					
			if(currentHumidity < lowestHumid) {
			    lowestHumidity = currentRow;
			}
			else{
			    if(currentRow.get("Humidity") != "N/A" && lowestHumidity.get("Humidity") != "N/A") {
						currentHumidity = Integer.parseInt(currentRow.get("Humidity"));
						lowestHumid = Integer.parseInt(lowestHumidity.get("Humidity"));
						
						if(currentHumidity < lowestHumid) {
							lowestHumidity = currentRow;
						}
			 
			 }
                   }
	   
	   }
	
    
       } 
      
     }
     return lowestHumidity;
   }

    public void  testLowestHumidityInManyFiles(){
    
        //FileResource fr = new FileResource();
        //CSVParser parser = fr.getCSVParser();
        CSVRecord lowestHumidity = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowestHumidity.get("Humidity") + " at " + lowestHumidity.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
		double nr = 0.0;
		double sum = 0.0;

		for(CSVRecord record : parser) {
			double temp = Double.parseDouble(record.get("TemperatureF"));
			sum = sum + temp;
			nr++;
		}

		double average = sum / nr;
		
		return average;
	}

	public void testAverageTemperatureInFile() {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		
		double avg = averageTemperatureInFile(parser);

		System.out.println("Average temperature in file is " + avg);
	}

	public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
		double nr = 0.0;
		double sum = 0.0;

		for(CSVRecord record : parser) {
			double temp = Double.parseDouble(record.get("TemperatureF"));
			int humidity = Integer.parseInt(record.get("Humidity"));
			
			if(humidity >= value) {
				sum = sum + temp;
				nr++;
			}
		}

		double average = sum / nr;
		return average;
	}

	public void testAverageTemperatureWithHighHumidityInFile() {
		FileResource fr = new FileResource();
		CSVParser parser = fr.getCSVParser();
		
		double avg = averageTemperatureWithHighHumidityInFile(parser, 80);

		if(!Double.isNaN(avg)) {
			System.out.println("Average Temp when high Humidity is " + avg);
		} else {
			System.out.println("No temperatures with that humidity");
		}
	}
    
    

}

