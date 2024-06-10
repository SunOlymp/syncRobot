import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();


    public static void main(String[] args) throws InterruptedException {

        int numThreads = 1000;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadNum = i;
            threads[i] = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        countR++;
                    }
                }
                synchronized (sizeToFreq) {
                    int freq = countR;
                    sizeToFreq.put(freq, sizeToFreq.getOrDefault(freq, 0) + 1);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int maxFreq = 0;
        int maxFreqCount = 0;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            int freq = entry.getKey();
            int count = entry.getValue();
            if (count > maxFreqCount) {
                maxFreq = freq;
                maxFreqCount = count;
            }
        }

        System.out.println("Самое частое количество повторений " + maxFreq + " (встретилось " + maxFreqCount + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != maxFreq) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}

