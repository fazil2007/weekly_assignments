import java.util.*;

class DNSEntry {
    String ipAddress;
    long expiryTime;

    DNSEntry(String ipAddress, int ttlSeconds) {
        this.ipAddress = ipAddress;
        this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
    }

    boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}

class DNSCache {

    private int capacity;
    private LinkedHashMap<String, DNSEntry> cache;

    private int hits = 0;
    private int misses = 0;

    DNSCache(int capacity) {
        this.capacity = capacity;

        cache = new LinkedHashMap<String, DNSEntry>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                return size() > capacity;
            }
        };
    }

    // Simulate upstream DNS query
    private String queryUpstream(String domain) {
        Random r = new Random();
        return "172.217.14." + (100 + r.nextInt(100));
    }

    public String resolve(String domain) {

        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            return "Cache HIT → " + entry.ipAddress;
        }

        if (entry != null && entry.isExpired()) {
            cache.remove(domain);
            System.out.println("Cache EXPIRED");
        }

        misses++;

        String ip = queryUpstream(domain);
        cache.put(domain, new DNSEntry(ip, 10));

        return "Cache MISS → Query upstream → " + ip;
    }

    public void getStats() {
        int total = hits + misses;

        if (total == 0) {
            System.out.println("No queries yet.");
            return;
        }

        double hitRate = (hits * 100.0) / total;

        System.out.println("Hit Rate: " + hitRate + "%");
        System.out.println("Hits: " + hits);
        System.out.println("Misses: " + misses);
    }
}

public class DNS_Cache {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DNSCache cache = new DNSCache(5);

        System.out.println("DNS Cache System Started");
        System.out.println("Type 'close' to stop\n");

        while (true) {

            System.out.print("Enter domain: ");
            String domain = sc.nextLine();

            if (domain.equalsIgnoreCase("close"))
                break;

            if (domain.equalsIgnoreCase("stats")) {
                cache.getStats();
                continue;
            }

            String result = cache.resolve(domain);
            System.out.println(result);
        }

        sc.close();
        System.out.println("DNS Cache System Closed");
    }
}
