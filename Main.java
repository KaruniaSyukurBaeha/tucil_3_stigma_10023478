import java.util.*;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continueProgram = true;

            while (continueProgram) {
                // Meminta pengguna untuk memilih file kamus
                System.out.print("Enter dictionary file name (e.g., dict.dat): ");
                String dictionaryFileName = scanner.nextLine();

                // Meminta pengguna untuk memasukkan kata awal
                System.out.print("Enter starting word: ");
                String startWord = scanner.nextLine().toLowerCase();

                // Meminta pengguna untuk memasukkan kata akhir
                System.out.print("Enter ending word: ");
                String endWord = scanner.nextLine().toLowerCase();

                // Meminta pengguna untuk memilih algoritma pencarian
                System.out.println("Select search algorithm: ");
                System.out.println("1. Uniform Cost Search (UCS)");
                System.out.println("2. Greedy Best First Search (Greedy)");
                System.out.println("3. A* Search (AStar)");
                System.out.print("Enter algorithm number: ");
                int algorithmChoice = scanner.nextInt();

                // Inisialisasi WordFileReader dan membaca kamus kata dari file yang dipilih
                WordFileReader fileReader = new WordFileReader(dictionaryFileName);
                List<String> dictionary = fileReader.readDictionary();

                // Jika file kamus tidak ditemukan atau kosong, hentikan eksekusi
                if (dictionary.isEmpty()) {
                    System.out.println("Dictionary file not found or empty.");
                    return;
                }

                // Inisialisasi WordLadder dengan kamus kata yang telah dibaca
                WordLadder wordLadder = new WordLadder(dictionary);

                // Menyimpan waktu awal eksekusi program
                long startTime = System.currentTimeMillis();

                // Mencari tangga kata menggunakan algoritma yang dipilih
                List<String> result = null;
                switch (algorithmChoice) {
                    case 1:
                        result = wordLadder.findWordLadder(startWord, endWord, "UCS");
                        break;
                    case 2:
                        result = wordLadder.findWordLadder(startWord, endWord, "Greedy");
                        break;
                    case 3:
                        result = wordLadder.findWordLadder(startWord, endWord, "AStar");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                // Menyimpan waktu akhir eksekusi program
                long endTime = System.currentTimeMillis();

                // Menampilkan hasil pencarian tangga kata
                if (result != null) {
                    System.out.println("Result: " + result);                    
                    System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");
                } else {
                    System.out.println("No path found.");
                }

                // Meminta pengguna untuk memilih apakah ingin melanjutkan atau mengakhiri program
                System.out.print("Do you want to continue? (yes/no): ");
                String choice = scanner.next();
                continueProgram = choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y");
                scanner.nextLine(); // Membuang karakter baru dari buffer
            }
        } // Scanner akan ditutup secara otomatis setelah keluar dari blok try
    }
}
