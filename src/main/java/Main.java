import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
public class Main {
    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        while(true){
            runMenu();
            String name = scanner.nextLine();
            while (name.length() < 3 || (name.charAt(0) != '1' && name.charAt(0) != '2')){
                if(!name.isEmpty()) if(name.charAt(0) == 'q') return;
                System.out.println("invalid!");
                name = scanner.nextLine();
            }
            char choice = name.charAt(0);
            if (choice == 'q') return;

            name = name.substring(2);
            Movie m = new Movie(new ArrayList<>(),"",0);
            Actors a = new Actors("", false);
            while (true){
                try {
                    if (Objects.equals(name, "q")) return;

                    if(choice == '1'){
                        System.out.println("...");
                        String movieData = m.getMovieData(name);
                        showMovieDetail(movieData, m);
                        break;
                    }else if(choice == '2'){
                        System.out.println("...");
                        String actorData = a.getActorData(name);
                        showActorDetail(name, actorData);
                        break;
                    }
                }catch (Exception ex){
                    System.out.println("Not Found!, Please Enter a valid name : (name)");
                    System.out.println("Error : " + ex.getMessage());
                    name = scanner.nextLine();
                }
            }
        }
    }
    public static void runMenu() {
        System.out.println("\nHello and Welcome.");
        System.out.println("Write Your favourite 1-movie(series) Or 2-celebrity name and you'll get the details about it: ");
        System.out.println("(1-name) (2-name)");
        System.out.println("(press q to quit.)");
    }

    public static void showMovieDetail(String data, Movie movie){
        System.out.println("\n");
        System.out.println("-----------------------------------------------------------");
        System.out.println("\tTitle : " + movie.getTitle(data));
        System.out.println("\tImdbVotes : " + movie.getImdbVotesViaApi(data));
        System.out.println("\tImdb Rating : " + movie.getRatingViaApi(data));
        System.out.println("\tYear : " + movie.getYear(data));
        System.out.println("\tGenre : " + movie.getGenre(data));
        System.out.println("\tCountry : " + movie.getCountry(data));
        System.out.println("\tRated : " + movie.getRated(data));
        movie.getActorListViaApi(data);
        System.out.println("-----------------------------------------------------------");
        System.out.println("\nChoose an Actor or press enter to continue...");

        Scanner scanner = new Scanner(System.in);
        String next = scanner.nextLine();
//         if the input contains a number , show the actors detail, else go to the main menu.
        if(next.matches("\\d+")){
            while (!(Integer.parseInt(next) - 1 < movie.actorsList.size() && Integer.parseInt(next) > 0)){
                System.out.print("Out of list, Enter another number");
                next = scanner.nextLine();
            }
            showActorDetail(movie.actorsList.get(Integer.parseInt(next) - 1), "");
        }
    }

    public static void showActorDetail(String actorName, String data){
        Actors actor = new Actors("", false);
        String actorData = data;
        if(Objects.equals(data, "")) {
            System.out.println("Reading from the web");
            actorData = actor.getActorData(actorName);
        }
        boolean isAlive = actor.isAlive(actorData);

        System.out.println("\n-----------------------------------------------------------");
        System.out.println("\tName : " + actor.getName(actorData));
        System.out.printf("\tWorth : %.0f\n", actor.getNetWorthViaApi(actorData));
        System.out.println("\tNationality : " + actor.getNationality(actorData));
        System.out.println("\tBirthday : " + actor.getBirthday(actorData));
        if(isAlive) System.out.println("\tis Alive : Yes");
        else System.out.println("\tData of death: " + actor.getDateOfDeathViaApi(actorData));
        System.out.println("-----------------------------------------------------------");
        System.out.println("Press Enter to continue...");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}