import java.util.*;

class PlagiarismDetector {

    int N = 5; // n-gram size

    HashMap<String, Set<String>> ngramIndex = new HashMap<>();
    HashMap<String, List<String>> documentNgrams = new HashMap<>();

    // Generate n-grams
    public List<String> generateNgrams(String text) {

        String[] words = text.toLowerCase().split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {

            StringBuilder gram = new StringBuilder();

            for (int j = 0; j < N; j++) {
                gram.append(words[i + j]).append(" ");
            }

            ngrams.add(gram.toString().trim());
        }

        return ngrams;
    }

    // Add document to database
    public void addDocument(String docId, String text) {

        List<String> ngrams = generateNgrams(text);
        documentNgrams.put(docId, ngrams);

        for (String gram : ngrams) {

            ngramIndex.putIfAbsent(gram, new HashSet<>());
            ngramIndex.get(gram).add(docId);
        }

        System.out.println("Document stored with " + ngrams.size() + " n-grams");
    }

    // Analyze document similarity
    public void analyzeDocument(String docId, String text) {

        List<String> ngrams = generateNgrams(text);

        HashMap<String, Integer> matchCount = new HashMap<>();

        for (String gram : ngrams) {

            if (ngramIndex.containsKey(gram)) {

                for (String doc : ngramIndex.get(gram)) {

                    matchCount.put(doc,
                            matchCount.getOrDefault(doc, 0) + 1);
                }
            }
        }

        System.out.println("Extracted " + ngrams.size() + " n-grams");

        for (String doc : matchCount.keySet()) {

            int matches = matchCount.get(doc);

            double similarity =
                    (matches * 100.0) / ngrams.size();

            System.out.println("Found " + matches +
                    " matching n-grams with " + doc);

            System.out.println("Similarity: " +
                    String.format("%.2f", similarity) + "%");

            if (similarity > 60)
                System.out.println("PLAGIARISM DETECTED\n");
            else if (similarity > 10)
                System.out.println("Suspicious\n");
        }
    }
}

public class Plagiarism {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        PlagiarismDetector detector =
                new PlagiarismDetector();

        System.out.println("Plagiarism Detection System Started");
        System.out.println("Type 'close' to exit\n");

        while (true) {

            System.out.print("Enter document ID: ");
            String docId = sc.nextLine();

            if (docId.equalsIgnoreCase("close"))
                break;

            System.out.println("Enter document text:");
            String text = sc.nextLine();

            if (!detector.documentNgrams.containsKey(docId)) {

                detector.addDocument(docId, text);
            } else {

                detector.analyzeDocument(docId, text);
            }
        }

        sc.close();
        System.out.println("System Closed");
    }
}
