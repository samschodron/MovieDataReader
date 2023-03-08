import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

/**
 * Creates a MovieDataReader object that reads a csv file and returns a list of movie objects
 */
public class MovieDataReader implements MovieDataReaderInterface {

  /**
   * Method takes in a csv file and returns a list of movie objects
   * @param inputFileReader file
   * @return list of Movie Objects
   * @throws FileNotFoundException if file error
   * @throws IOException if generic error
   * @throws DataFormatException if csv is improperly formatted
   */
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader) throws FileNotFoundException, IOException, DataFormatException {
    List<MovieInterface> toReturn = new ArrayList<>();
    //Checks if file can be loaded or is null
    if(inputFileReader==null){
      throw new FileNotFoundException("Error with File");
    }
    //Scanner object will read csv file line by line
    Scanner scan = new Scanner(inputFileReader);
    List<String> header = Arrays.asList(scan.nextLine().split(","));
    //number of properties contained in header line
    final int LIMIT = header.size();
    //Creates an array that contains the required properties
    //and determines the locations of them according to the header
    int[] PROPERTY_LOCATION = new int[6];
    if(header.contains("title")){
      PROPERTY_LOCATION[0] = header.indexOf("title");
    }
    else{
      throw new DataFormatException("Missing properties");
    }
    if(header.contains("year")){
      PROPERTY_LOCATION[1] = header.indexOf("year");
    }
    else{
      throw new DataFormatException("Missing properties");
    }
    if(header.contains("genre")){
      PROPERTY_LOCATION[2] = header.indexOf("genre");
    }
    else{
      throw new DataFormatException("Missing properties");
    }
    if(header.contains("director")){
      PROPERTY_LOCATION[3] = header.indexOf("director");
    }
    else{
      throw new DataFormatException("Missing properties");
    }
    if(header.contains("description")){
      PROPERTY_LOCATION[4] = header.indexOf("description");
    }
    else{
      throw new DataFormatException("Missing properties");
    }
    if(header.contains("avg_vote")){
      PROPERTY_LOCATION[5] = header.indexOf("avg_vote");
    }
    else{
      throw new DataFormatException("Missing properties");
    }

    //Splits the csv file line by line
    // and creates movie objects using the stored property locations
    int lineNum = 1;
    while(scan.hasNextLine()){
      lineNum++;
      String nextLine = scan.nextLine();
      //splits first line of csv file based on commas
      //regex makes it so that commas inside of quotes are avoided
      String[] properties = nextLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
      if(properties.length!=LIMIT){
        throw new DataFormatException("Line: "+lineNum+" has invalid number of properties");
      }
      //creates a movie object with the respective properties and adds it to arraylist
      toReturn.add(new Movie(properties[PROPERTY_LOCATION[0]],Integer.valueOf(properties[PROPERTY_LOCATION[1]]),
          reformat(properties[PROPERTY_LOCATION[2]].replace("\"","").split(",")),properties[PROPERTY_LOCATION[3]]
          ,properties[PROPERTY_LOCATION[4]].replace("\"",""),Float.valueOf(properties[PROPERTY_LOCATION[5]])));
    }
    return toReturn;
  }

  /**
   * reformats elements inside of genre array to
   * remove any irregularities
   * @param inp array
   * @return List if Strings
   */
  public List<String> reformat(String[] inp){
    //Reformats each element in Array to remove
    //Spaces at the beginning or end of String
    for(int i = 0; i<inp.length; i++){
      if(inp[i].charAt(0)==' '&&inp[i].charAt(inp[i].length()-1)==' '){
       inp[i] = inp[i].substring(1,inp[i].length()-1);
      }
      else if(inp[i].charAt(0)==' '){
        inp[i] = inp[i].substring(1);
      }
      else if(inp[i].charAt(inp[i].length()-1)==' '){
        inp[i] = inp[i].substring(0,inp[i].length()-1);
      }

    }
    return Arrays.asList(inp);
  }
  }


