import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class TextStatistics {

    public static void main(String[] args) {
        String inputFile = "text.txt";
        String outputFile = "output.txt";
        String svcOutputFile = "output.svc";  // Дополнительный svc-файл

        try {
            String text = new String(Files.readAllBytes(Paths.get(inputFile)));
            List<String> lines = Files.readAllLines(Paths.get(inputFile));

            // Подсчет статистики
            int totalCharacters = countTotalCharacters(text);
            int totalCharactersNoSpaces = countCharactersNoSpaces(text);
            int totalWords = countWords(text);
            int totalLines = countLines(lines);
            int totalParagraphs = countParagraphs(text);
            int totalPages = countPages(text);

            // Формирование вывода в формате StringBuilder для обычного текста
            StringBuilder stats = new StringBuilder();
            stats.append("Статистика по тексту:\n");
            stats.append("1. Всего символов (включая пробелы): ").append(totalCharacters).append("\n");
            stats.append("2. Всего символов (не включая пробелы): ").append(totalCharactersNoSpaces).append("\n");
            stats.append("3. Всего слов: ").append(totalWords).append("\n");
            stats.append("4. Всего строк: ").append(totalLines).append("\n");
            stats.append("5. Всего абзацев: ").append(totalParagraphs).append("\n");
            stats.append("6. Всего страниц : ").append(totalPages).append("\n");

            // Формирование вывода для svc
            StringBuilder svcStats = new StringBuilder();
            svcStats.append("Ститистика по тексту: \n");
            svcStats.append("Всего символов (включая пробелы),").append(totalCharacters).append("\n");
            svcStats.append("Всего символов (не включая пробелы),").append(totalCharactersNoSpaces).append("\n");
            svcStats.append("Всего слов,").append(totalWords).append("\n");
            svcStats.append("Всего строк,").append(totalLines).append("\n");
            svcStats.append("Всего абзацев,").append(totalParagraphs).append("\n");
            svcStats.append("Всего страниц,").append(totalPages).append("\n");

            // Вывод в консоль
            System.out.println(stats.toString());

            // Запись в текстовый файл
            writeToFile(outputFile, stats.toString());

            // Запись в CSV-файл
            writeToFile(svcOutputFile, svcStats.toString());

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    // Метод для подсчета всех символов в тексте
    public static int countTotalCharacters(String text) {
        return text.length();
    }

    // Метод для подсчета всех символов без пробелов
    public static int countCharactersNoSpaces(String text) {
        return text.replace(" ", "").length();
    }

    // Метод для подсчета слов
    public static int countWords(String text) {
        String[] words = text.trim().split("\\s+");
        return words.length;
    }

    // Метод для подсчета строк
    public static int countLines(List<String> lines) {
        return lines.size();
    }

    // Метод для подсчета абзацев
    public static int countParagraphs(String text) {
        String[] paragraphs = text.split("\\n(\\t|\\s{4})");
        return paragraphs.length;
    }

    // Метод для подсчета страниц
    public static int countPages(String text) {
        int pageCount = 1;  // Начинаем с одной страницы по умолчанию
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\u000C') {  // Если находим символ \u000C (form feed)
                pageCount++;
            }
        }
        return pageCount;
    }

    // Метод для записи статистики в файл
    public static void writeToFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}
