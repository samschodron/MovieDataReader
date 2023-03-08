import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the frontend that instantiates the backend and executes the program
 * while taking user inputs as commands to traverse the selection screens
 */
public class Frontend {
	private ArrayList<String> allRatings;
	Scanner scanIn = new Scanner(System.in);

	/**
	 * Main method that instantiates the backend with a MovieDataReader object
	 * then runs the app program
	 * @param args
	 */
	public static void main(String[] args) {
		MovieDataReader movieReader = new MovieDataReader();
		Frontend frontend = new Frontend();
		Backend backend = new Backend(movieReader);
		frontend.run(backend);
	}

	/**
	 * Runs the program, begins in base mode and selects all the ratings
	 * prompts users for commands, 'g', 'r', 'x' and integers
	 * Integers to scroll through the selected movies list
	 * 'x' to exit the program
	 * 'r' to enter rating selection screen
	 * 'g' to enter genre selection screen
	 * @param backEnd backend object
	 */
	public void run(Backend backEnd) {
		// select all ratings at the first start of the program
		for (int i = 0; i <= 10; i++) {
			backEnd.addAvgRating(i + "");
		}
		// prints introduction to the Movie Mapper App
		System.out.println("Welcome to Movie Mapper!" +
				"\nPress 'g' for genre selection mode, 'r' for rating selection, " +
				"'x' to exit or any integer to scroll through the selected movie(s)" +
				"\nTop 3 selected movie(s) by rating: ");
		// startIndex is used to store what int the user enters in base mode
		int startIndex = 0;
		// prints out the top 3 selected movies by rating
		for (int i = 0; i < backEnd.getThreeMovies(startIndex).size(); i++) {
			System.out.println(backEnd.getThreeMovies(startIndex).get(i).getAvgVote() + " " + backEnd.getThreeMovies(startIndex).get(i).getTitle());
		}
		String command = scanIn.nextLine();
		while (!command.equalsIgnoreCase("x")) {
			// entering g changes modes to genre selection mode, which is called with a separate method
			if (command.equalsIgnoreCase("g")) {
				runGenres(backEnd);
				// after exiting reprints the intro
				System.out.println("Welcome to Movie Mapper!" +
						"\nPress 'g' for genre selection mode, 'r' for rating selection, " +
						"'x' to exit or any integer to scroll through the selected movie(s)" +
						"\nTop 3 selected movie(s) by rating: ");
				for (int i = 0; i < backEnd.getThreeMovies(startIndex).size(); i++) {
					System.out.println(backEnd.getThreeMovies(startIndex).get(i).getAvgVote() + " " + backEnd.getThreeMovies(startIndex).get(i).getTitle());
				}
			}
			// entering r changes modes to rating selection mode, which is called with a separate method
			if (command.equalsIgnoreCase("r")) {
				runRatings(backEnd);
				// after exiting reprints the intro
				System.out.println("Welcome to Movie Mapper!" +
						"\nPress 'g' for genre selection mode, 'r' for rating selection, " +
						"'x' to exit or any integer to scroll through the selected movie(s)" +
						"\nTop 3 selected movie(s) by rating: ");
				for (int i = 0; i < backEnd.getThreeMovies(startIndex).size(); i++) {
					System.out.println(backEnd.getThreeMovies(startIndex).get(i).getAvgVote() + " " + backEnd.getThreeMovies(startIndex).get(i).getTitle());
				}
			}
			try { // turns the string the user enters and checks if it is an int
				// int commands get transferred into the startIndex value to scroll through the selected movies
				startIndex = Integer.parseInt(command);
				if (startIndex >= backEnd.getNumberOfMovies()) {
					System.out.println("Index is greater than the total number of movies, please enter another command: ");
				}
				// prints out the 3 movies from the start index indicated by the user
				else if (startIndex < backEnd.getNumberOfMovies()) {
					for (int i = 0; i < backEnd.getThreeMovies(startIndex).size(); i++) {
						System.out.println(backEnd.getThreeMovies(startIndex).get(i).getAvgVote() + " " + backEnd.getThreeMovies(startIndex).get(i).getTitle());
					}
				}
			} catch (NumberFormatException e) { // in case the command is not a int or x, g, or r its an invalid command
				if (!command.equalsIgnoreCase("r") && !command.equalsIgnoreCase("g"))
					System.out.println("Invalid command, please enter 'g', 'r', 'x', or and integer: ");
			}
			System.out.println(backEnd.getThreeMovies(startIndex));
			command = scanIn.nextLine();
		}
		// returns nothings to stop the program if 'x' is the input
		return;
	}

	/**
	 * After 'g' is inputted as the command from the main menu
	 * this method is called to move to genre selection screen
	 * this mode displays a list of all genres available to filter
	 * from corresponding to an integer, when the integer is selected
	 * the method checks the already selected genres list to see if the genres
	 * been selected already then proceeds to remove or add if the genre has already
	 * been added or not
	 * @param backEnd backend object
	 */
	private void runGenres(Backend backEnd) {
		// introduction to the genre selection mode
		System.out.println("Filter by Genres: ");
		// informs the user about the commands they can use
		System.out.println("Please type in the genres corresponding number to select that genre or 'x' to return to main menu" +
				"\n(if genre is already selected typing in the number will deselect it): ");
		// a first print of what has already been selected
		System.out.println("Selected genres: " + backEnd.getGenres());
		for (int i = 0; i < backEnd.getAllGenres().size(); i++) {
			System.out.println(i + 1 + " " + backEnd.getAllGenres().get(i));
		}
		String genre = scanIn.nextLine();
		// returns to main menu is x is entered as a command
		while (!genre.equalsIgnoreCase("x")) {
			try {
				int genreIndex = Integer.parseInt(genre);
				// checks if the particular genre has been added already or not
				// remove if it is, add if its not
				if (backEnd.getGenres().contains(backEnd.getAllGenres().get(genreIndex - 1)))
					backEnd.removeGenre(backEnd.getAllGenres().get(genreIndex - 1));
				else
					backEnd.addGenre(backEnd.getAllGenres().get(genreIndex - 1));
				// prompt the user to filter again, or exit program
				System.out.println("Please select another genre to filter by or type in 'x' to return to the main menu");
				// notify the user what genre has been selected
				System.out.println("Selected genres: " + backEnd.getGenres());
				// reprints the different genres up for selection
				for (int i = 0; i < backEnd.getAllGenres().size(); i++) {
					System.out.println(i + 1 + " " + backEnd.getAllGenres().get(i));
				}
			} catch (NumberFormatException e) { // catch an error when parsing the command variable into int to
				System.out.println("Invalid command please enter an integer or 'x'");
			}
			genre = scanIn.nextLine();
		}
		return;
	}

	/**
	 * similar to the runGenres class but this time with the
	 * rating selection mode. Method displays all ratings that
	 * can be chosen from and the user only needs to input the integer to
	 * select all the ratings that begin with that integer and is followed
	 * by a decimal. The integers to select from is 0-10 and ratings can include
	 * decimal values
	 * @param backEnd backend object
	 */
	private void runRatings(Backend backEnd) {
		// at the start of the run method all ratings get selected
		// this is to keep track of all the ratings that are available
		// to filter from
		allRatings = new ArrayList<>(backEnd.getAvgRatings());
		System.out.println("Filter by Ratings (all ratings are selected by default) ");
		System.out.println("(if rating is already selected typing in the number will deselect it): ");
		System.out.println("Selected ratings: " + backEnd.getAvgRatings());
		System.out.println("Ratings of all available movies to filter by: ");
		// lists out all the movies ratings
		for (int i = 0; i < allRatings.size(); i++) {
			try { // prints out all the ratings associated with the integer on one line
				System.out.print(allRatings.get(i) + " ");
				if (!allRatings.get(i).substring(0, 1).equalsIgnoreCase(allRatings.get(i + 1).substring(0, 1)))
					System.out.println();
			} catch	(IndexOutOfBoundsException e) { // skips at the end of the list
				System.out.println();
			}
		}
		String ratings = scanIn.nextLine();
		// returns to main menu is x is entered as a command
		while (!ratings.equalsIgnoreCase("x")) {
			try {
				// used to check if the command entered is valid
				int check = Integer.parseInt(ratings);
				// checks the list of selected ratings to see if the selected rating has already been added
				for (int i = 0; i < backEnd.getAvgRatings().size(); i++) {
					if (backEnd.getAvgRatings().get(i).contains(ratings)) {
						backEnd.removeAvgRating(ratings);
					} else {
						backEnd.addAvgRating(ratings);
					}
					// the remove or add is done once on the frontend side
				}
				// re-prompt the user for commands
				System.out.println("Please select another rating to filter by or type in 'x' to return to the main menu");
				System.out.println("Selected ratings: " + backEnd.getAvgRatings());
				System.out.println("Ratings of all available movies to filter by: ");
				// lists out all the movies ratings
				for (int i = 0; i < allRatings.size(); i++) {
					System.out.print(allRatings.get(i) + " ");
					try { // prints out all the ratings associated with the integer on one line
						if (!allRatings.get(i).substring(0, 1).equalsIgnoreCase(allRatings.get(i + 1).substring(0, 1)))
							System.out.println();
					} catch (IndexOutOfBoundsException e) { // skips a line after each step of the rating
						System.out.println();
					}
				}
			} catch (NumberFormatException e) { // catched the earlier exception to test for invalid commands
				System.out.println("Invalid command please enter an integer or 'x'");
			}
			ratings = scanIn.nextLine();
		}
		return;
	}
}