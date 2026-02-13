package exercise;

import java.util.*;

public class Problem1_UsernameChecker {


    private Map<String, String> usernameMap = new HashMap<>();


    private Map<String, Integer> attemptFrequency = new HashMap<>();

    public Problem1_UsernameChecker() {

        usernameMap.put("john_doe", "user001");
        usernameMap.put("admin", "user002");
        usernameMap.put("jane_smith", "user003");
    }


    public boolean checkAvailability(String username) {
        attemptFrequency.put(username, attemptFrequency.getOrDefault(username, 0) + 1);
        return !usernameMap.containsKey(username);
    }


    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();
        int counter = 1;


        while (suggestions.size() < 3) {
            String suggestion = username + counter;
            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
            counter++;
        }


        String dotVariant = username.replace("_", ".");
        if (!usernameMap.containsKey(dotVariant) && suggestions.size() < 3) {
            suggestions.add(dotVariant);
        }

        return suggestions;
    }


    public String getMostAttempted() {
        return attemptFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }


    public static void main(String[] args) {
        Problem1_UsernameChecker checker = new Problem1_UsernameChecker();

        System.out.println("Checking 'john_doe': " + checker.checkAvailability("john_doe")); // false
        System.out.println("Checking 'jane_smith': " + checker.checkAvailability("jane_smith")); // false
        System.out.println("Checking 'new_user': " + checker.checkAvailability("new_user")); // true

        System.out.println("Suggestions for 'john_doe': " + checker.suggestAlternatives("john_doe"));

        // Simulate multiple attempts
        checker.checkAvailability("admin");
        checker.checkAvailability("admin");
        checker.checkAvailability("new_user");

        System.out.println("Most attempted: " + checker.getMostAttempted());
    }
}

