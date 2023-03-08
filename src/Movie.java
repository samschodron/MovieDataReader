import java.util.List;

/**
 * Creates a Movie object that stores metadata for movie
 */
public class Movie implements MovieInterface{
  private String title;
  private Integer year;
  private List<String> genres;
  private String dir;
  private String desc;
  private Float vote;

  /**
   * Initializes a Movie object
   * @param title String
   * @param year integer
   * @param genres list of genres
   * @param dir director
   * @param desc description
   * @param vote float number
   */
  public Movie(String title, Integer year, List<String> genres,String dir, String desc, Float vote){
    this.title = title;
    this.year = year;
    this.genres = genres;
    this.dir=dir;
    this.desc = desc;
    this.vote = vote;
  }

  /**
   * @return the tile
   */
  @Override
  public String getTitle() {
    return title;
  }

  /**
   * @return the year of the movie
   */
  @Override
  public Integer getYear() {
    return year;
  }

  /**
   * @return the list of genres
   */
  @Override
  public List<String> getGenres() {
    return genres;
  }

  /**
   * @return the director
   */
  @Override
  public String getDirector() {
    return dir;
  }

  /**
   * @return the description
   */
  @Override
  public String getDescription() {
    return desc;
  }

  /**
   * @return the average vote
   */
  @Override
  public Float getAvgVote() {
    return vote;
  }

  /**
   * compares movie objects by vote to determine order
   * @param otherMovie compare
   * @return integer that references votes
   */
  @Override
  public int compareTo(MovieInterface otherMovie) {
    if(!(otherMovie instanceof Movie)){
      throw new IllegalArgumentException();
    }
    else{
      if(otherMovie.getAvgVote().equals(this.getAvgVote()))
        return 0;
      if(this.getAvgVote()< otherMovie.getAvgVote())
        return 1;

      return -1;
    }
  }


  /**
   * @return toString representation of Movie Object
   */
  @Override
  public String toString(){
    return "Movie Title: "+title+ "\n"+
           "Year released: "+year+"\n"+
           "Genre(s): "+genres.toString()+"\n"+
           "Director: "+ dir+"\n"+
           "Description: "+desc+"\n"+
           "Average Vote: "+ vote+"\n";

  }
}