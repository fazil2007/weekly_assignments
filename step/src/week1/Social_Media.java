import java.util.*;

class UsernameChecker {

    private HashMap<String, Integer> users = new HashMap<>();
    private HashMap<String, Integer> attempts = new HashMap<>();

    public void registerUser(String username, int userId) {
        users.put(username, userId);
    }

    public boolean checkAvailability(String username) {

        attempts.put(username, attempts.getOrDefault(username, 0) + 1);

        if (users.containsKey(username))
            return false;

        return true;
    }

    public List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            String suggestion = username + i;
            if (!users.containsKey(suggestion))
                suggestions.add(suggestion);
        }

        String dotVersion = username.replace("_", ".");
        if (!users.containsKey(dotVersion))
            suggestions.add(dotVersion);

        return suggestions;
    }

    public String getMostAttempted() {

        String name = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attempts.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                name = entry.getKey();
            }
        }

        return name + " (" + max + " attempts)";
    }
}

public class Social_Media {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UsernameChecker system = new UsernameChecker();

        system.registerUser("john_doe", 101);
        system.registerUser("admin", 102);
        system.registerUser("alex", 103);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        if (system.checkAvailability(username)) {
            System.out.println("Username available. OK");
        } else {
            System.out.println("Username already taken.");

            List<String> suggestions = system.suggestAlternatives(username);

            System.out.println("Suggestions:");
            for (String s : suggestions)
                System.out.println(s);
        }

        System.out.println("Most attempted username: " + system.getMostAttempted());
    }
}