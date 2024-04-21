package UniqueIdGenerator;

import java.util.Random;

public class UniqueIdGenerator {
    private static final int ID_LENGTH = 10;
    private static final int START_INDEX = 7;

    public static int generateUniqueId() {
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        sb.append(timestamp);

        while (sb.length() < ID_LENGTH) {
            sb.append(random.nextInt(10)); // Append random numbers
        }

        return Integer.parseInt(sb.toString().substring(START_INDEX));
    }
}
