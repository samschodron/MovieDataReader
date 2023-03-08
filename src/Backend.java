import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.DataFormatException;

public class Backend implements BackendInterface {
  // <Float, Movie>
  private HashTableMap movies; // HashTable containing movies
  private HashTableMap genres; // HashTable containing genres
  private HashTableMap avgRating; // HashTable containing average ratings
  private ArrayList<String> genresList; // ArrayList that contains all genres that are in hash
                                        // table(easier to traverse than the hashTable)
  private ArrayList<String> ratingsList; // ArrayList that contains all ratings that are in hash
                                         // table
  private MovieDataReader movieReader; // ReaderDummy that reads in the movies
  private List<MovieInterface> movieList; // ArrayList that contains all movies in total
  private ArrayList<MovieInterface> masterMovieList; // ArrayList that contains all filtered movies

  /**
   * Backend constructor that creates the genres hashTable avgRating hashTable genresList arrayList
   * ratingsList arrayList movieReader object movieList arrayList and fills the movieList array with
   * the movies loaded in from the movieReader object
   * 
   */
  public Backend(StringReader r) {
    this.genres = new <String, Object>HashTableMap();
    this.avgRating = new <String, Object>HashTableMap();
    this.genresList = new ArrayList<String>();
    this.ratingsList = new ArrayList<String>();
    this.movieReader = new MovieDataReader();
    this.movieList = new ArrayList<MovieInterface>();
    this.masterMovieList = new ArrayList<MovieInterface>();

  }

  public Backend(MovieDataReader movieReader){

    this.genres = new <String, Object>HashTableMap();
    this.avgRating = new <String, Object>HashTableMap();
    this.genresList = new ArrayList<String>();
    this.ratingsList = new ArrayList<String>();
    this.movieReader = new MovieDataReader();
    this.movieList = new ArrayList<MovieInterface>();
    this.masterMovieList = new ArrayList<MovieInterface>();
    this.movieReader = movieReader;
    try {
      this.movieList = movieReader.readDataSet(new FileReader(
    		  "C:\\Users\\samsc\\OneDrive\\Documents\\movies.csv"));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (DataFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    this.movies = new HashTableMap();
    
  }

  @Override
  /**
   * Adds a genre to the hashTable "movies" uses the String genre as the key and passes in the
   * string genre as the value
   * Checks the current ratings filter before adding movies to the list
   */ // Filter
  public void addGenre(String genre) {
    masterMovieList.clear();
    List<Float> constraints = new ArrayList<Float>();
    for (int z = 0; z < ratingsList.size(); z++) {
      Float toBeAdded = Float.valueOf(ratingsList.get(z)).floatValue(); // Creates a list of
                                                                        // sortedRatings
      constraints.add(toBeAdded);
    }
    Collections.sort(constraints);

    if (genres.put(genre, genre)) {
      genresList.add(genre);
    }
    if (!ratingsList.isEmpty()) // If there are no rating constraints
    {
      if (genresList.size() > 1) // Checks if genres are already being filtered
      {

      } else {
        ArrayList<MovieInterface> rightGenres = new ArrayList<MovieInterface>();
        for (int x = 0; x < movieList.size(); x++) {
          if (movieList.get(x).getGenres().contains(genre)) {
            if (genresList.size() > 1) {
              if (movieList.get(x).getGenres().containsAll(genresList)) // If the genres list has
                                                                        // multiple genres it will
                                                                        // make sure before adding
                                                                        // to rightGenres that it
                                                                        // has all the necessary
                                                                        // genres not just one
              {
                rightGenres.add(movieList.get(x));
              }
            } else {
              rightGenres.add(movieList.get(x));
            }
          }
        }

        for (int y = 0; y < rightGenres.size(); y++) {
          if (rightGenres.get(y).getAvgVote() >= constraints.get(0)
              && rightGenres.get(y).getAvgVote() < constraints.get(constraints.size() - 1)) 
          {
            if (ratingsList.size() > 1) {
              masterMovieList.add(rightGenres.get(y));
            }
          }
        }
      }
    } else // If there are no other filters being applied
    {
      for (int x = 0; x < movieList.size(); x++) {
        if (movieList.get(x).getGenres().contains(genre)) {
          masterMovieList.add(movieList.get(x));
        }
      }
    }
  }

  @Override
  /**
   * Adds a averageRating to the hashTable "ratingsList" uses the String rating as the key and
   * passes in the string rating as the value
   * Then adds movies within the rating range to the masterMovieList
   */ // Filter
  // Works as intended
  public void addAvgRating(String rating) {
    masterMovieList.clear(); // Wipes the masterMovieList before refilling
    Float ratingFloat = Float.valueOf(rating).floatValue();

    if (avgRating.put(rating, rating)) {
      ratingsList.add(rating); // If statement so only non duplicates added to the rating list
    }

    ArrayList<MovieInterface> eligableRatings = new ArrayList<MovieInterface>();
    for (int x = 0; x < this.movieList.size(); x++) {
      if (this.movieList.get(x).getAvgVote() >= ratingFloat
          && (this.movieList.get(x).getAvgVote() < ratingFloat + 1)) {
        eligableRatings.add(movieList.get(x));
      }
    }
    if (!genresList.isEmpty()) {
      for (int i = 0; i < genresList.size(); i++) {
        if (eligableRatings.get(i).getGenres().containsAll(genresList))
          masterMovieList.add(eligableRatings.get(i)); // Checks if the movies that fit the rating
                                                       // criteria contain all the genres within the
                                                       // filter
      }
    } else {
      masterMovieList.addAll(eligableRatings);
    }
  }

  @Override
  /**
   * Removes a genre from the hashTable "genres" passing in the String genre as the key to remove
   */
  public void removeGenre(String genre) {
    genres.remove(genre);
    genresList.remove(genre);
    movies.remove(genre);

    for (int x = 0; x < this.getNumberOfMovies(); x++) {
      if (masterMovieList.get(x).getGenres().contains(genre)) {
        masterMovieList.remove(x);
      }
    }
    //masterMovieList.remove(0);
  }



  @Override
  /**
   * Removes an average rating from the hashTable "avgRating" passing in the String rating as the
   * key to remove Removes the rating from the rating list Removes the rating from the movies
   * hashTable
   * 
   */
  public void removeAvgRating(String rating) {
    avgRating.remove(rating);
    ratingsList.remove(rating);
    movies.remove(rating);
    Float ratingFloat = Float.valueOf(rating).floatValue();
    
    for (int x = 0; x < masterMovieList.size(); x++) {
      if (masterMovieList.get(x).getAvgVote() >= ratingFloat
          && masterMovieList.get(x).getAvgVote() < ratingFloat+1) // Removes all movies with a
                                                                // specific rating
      {
        masterMovieList.remove(x);
      }
      
    }
  }

  @Override // Get current genres that are being filtered
  // Works as intended
  public List<String> getGenres() {
    return genresList;
  }

  @Override // Get current avgRatings being filtered
  // Works as intended
  public List<String> getAvgRatings() {
    return ratingsList;
  }


  @Override
  /**
   * Get total number of movies being stored
   */
  public int getNumberOfMovies() {

    return this.masterMovieList.size();
  }

  @Override
  // Returns as a list of genres
  // Grab each element of list
  // Add both to allGenresList
  // Works as intended
  public List<String> getAllGenres() {

    ArrayList<String> allGenres = new ArrayList(); // List that will be returned, no duplicates
    ArrayList<String> foundGenres = new ArrayList(); // List of all total Genres with duplicates

    for (int x = 0; x < movieList.size(); x++) {
      foundGenres.addAll(movieList.get(x).getGenres()); // Use addAll because getGenres returns a
                                                        // list
    }

    // Checks if allGenres list already contains genre being added
    // If not then it is added
    for (int x = 0; x < foundGenres.size(); x++) {
      if (!allGenres.contains(foundGenres.get(x))) {
        allGenres.add(foundGenres.get(x));
      }
    }
    return allGenres;
  }
  
  /**
   * Returns a list of allRatings
   * @return
   */
  public List<String> getAllRating() {
    return this.ratingsList;
  }

  @Override
  /**
   * Return 3 movies from a sorted catalog of movies
   */
  public List<MovieInterface> getThreeMovies(int startingIndex) {
    List<MovieInterface> threeMovies = new ArrayList<MovieInterface>();
    List<Float> sortedRatings = createSortedRatingsList();
    List<MovieInterface> sortedMovies = new ArrayList<MovieInterface>();

    Float[] floatArray = new Float[sortedRatings.size()];
    MovieInterface[] movieArray = new MovieInterface[sortedRatings.size()];

    for (int floats = 0; floats < sortedRatings.size(); floats++) {
      floatArray[floats] = sortedRatings.get(floats);
      movieArray[floats] = null;
    }

    // Creates 2 arrays that will be used to sort the masterMovieList
    for (int movieObjects = 0; movieObjects < sortedRatings.size(); movieObjects++) {
      for (int i = 0; i < sortedRatings.size(); i++) {
        if (masterMovieList.get(movieObjects).getAvgVote() == floatArray[i]) {
          movieArray[i] = masterMovieList.get(movieObjects);
        }
      }
    }

    // Add the sorted ArrayElements to the sortedMovies arrayList
    for (int z = 0; z < sortedRatings.size(); z++) {
      sortedMovies.add(movieArray[z]);
    }

    // Add the movies to the threeMovies list
    for (int x = startingIndex; x < startingIndex + 3; x++) {
      if (x < sortedMovies.size()) {
        threeMovies.add(sortedMovies.get(x));
      }
    }
    return threeMovies;
  }

  /**
   * Creates a list of Floats that is a sorted list of all ratings in the MasterMovieList
   * 
   * @return
   */
  private List<Float> createSortedRatingsList() {
    List<Float> sortedRatings = new ArrayList<Float>();
    for (int x = 0; x < masterMovieList.size(); x++) {
      sortedRatings.add(masterMovieList.get(x).getAvgVote());
    }
    Collections.sort(sortedRatings);
    Collections.reverse(sortedRatings);
    return sortedRatings;
  }

}