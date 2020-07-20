
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class ParsingExportData {

    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        //parser = fr.getCSVParser();
        
        bigExporters(parser, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser,String country){
        for(CSVRecord record:parser){
            String myCountry = record.get("Country");
            if(myCountry.contains(country)){
                String exports = record.get("Exports");
                String value = record.get("Value(dollars)");
                String info = myCountry + ": " + exports + ": " + value;
                return info;
            }
        }
        return "NOT FOUND";
    
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for(CSVRecord record:parser){
            String export = record.get("Exports");
            if(export.contains(exportItem1) && export.contains(exportItem2))
            {
                String country = record.get("Country");
                System.out.println(country);
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem){
        int nr=0;
        for(CSVRecord record:parser){
            String export = record.get("Exports");
            if(export.contains(exportItem)){
                nr=nr+1;
            }
        }
        return nr;
        
    }
    
    public void bigExporters(CSVParser parser, String amount){
    
        for(CSVRecord record : parser) {
			String value = record.get("Value (dollars)");
			String country = record.get("Country");

			if(value.length() > amount.length()) {
				System.out.println(country + ": " + value);
			}
		}
    }
    
}


