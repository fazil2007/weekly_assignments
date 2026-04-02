import java.util.*;

class AnalyticsSystem {

    HashMap<String, Integer> pageViews = new HashMap<>();
    HashMap<String, Set<String>> uniqueVisitors = new HashMap<>();
    HashMap<String, Integer> trafficSources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {

        // count page views
        pageViews.put(url, pageViews.getOrDefault(url, 0) + 1);

        // track unique visitors
        uniqueVisitors.putIfAbsent(url, new HashSet<>());
        uniqueVisitors.get(url).add(userId);

        // count traffic sources
        trafficSources.put(source, trafficSources.getOrDefault(source, 0) + 1);
    }

    public void getDashboard() {

        System.out.println("\nTop Pages:");

        List<Map.Entry<String, Integer>> pages =
                new ArrayList<>(pageViews.entrySet());

        pages.sort((a, b) -> b.getValue() - a.getValue());

        int limit = Math.min(10, pages.size());

        for (int i = 0; i < limit; i++) {

            String url = pages.get(i).getKey();
            int views = pages.get(i).getValue();
            int unique = uniqueVisitors.get(url).size();

            System.out.println(
                    (i + 1) + ". " + url +
                            " - " + views +
                            " views (" + unique + " unique)"
            );
        }

        System.out.println("\nTraffic Sources:");

        int total = 0;
        for (int count : trafficSources.values())
            total += count;

        for (String source : trafficSources.keySet()) {

            int count = trafficSources.get(source);
            double percent = (count * 100.0) / total;

            System.out.println(
                    source + ": " +
                            String.format("%.2f", percent) + "%"
            );
        }

        System.out.println();
    }
}

public class Website_Traffic {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AnalyticsSystem system = new AnalyticsSystem();

        System.out.println("Real-Time Analytics System Started");
        System.out.println("Type 'dashboard' to view analytics");
        System.out.println("Type 'close' to exit\n");

        while (true) {

            System.out.print("Enter URL: ");
            String url = sc.nextLine();

            if (url.equalsIgnoreCase("close"))
                break;

            if (url.equalsIgnoreCase("dashboard")) {
                system.getDashboard();
                continue;
            }

            System.out.print("Enter userId: ");
            String userId = sc.nextLine();

            System.out.print("Enter source (google/facebook/direct): ");
            String source = sc.nextLine();

            system.processEvent(url, userId, source);
        }

        sc.close();
        System.out.println("System Closed");
    }
}
