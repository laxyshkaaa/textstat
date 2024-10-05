import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
            int totalCharacters = text.length();
            int totalCharactersNoSpaces = text.replace(" ", "").length();
            int totalWords = countWords(text);
            int totalLines = lines.size();
            int totalParagraphs = countParagraphs(text);
            int totalPages = countPages(text);

            // Форматирование вывода
            String statsFormat = "Статистика по тексту:\n" +
                    "1. Всего символов (включая пробелы): %d\n" +
                    "2. Всего символов (не включая пробелы): %d\n" +
                    "3. Всего слов: %d\n" +
                    "4. Всего строк: %d\n" +
                    "5. Всего абзацев: %d\n" +
                    "6. Всего страниц: %d\n";

            String stats = String.format(statsFormat, totalCharacters, totalCharactersNoSpaces, totalWords, totalLines, totalParagraphs, totalPages);
            // Вывод в консоль
            System.out.println(stats);
            // Запись в текстовый файл
            writeToFile(outputFile, stats);
            // Запись в svc-файл (в этом случае просто используем тот же формат)
            writeToFile(svcOutputFile, stats);
        } catch (NoSuchFileException e) {
            System.err.println("Ошибка: Файл не найден - " + e.getFile());
        } catch (MalformedInputException e) {
            System.err.println("Ошибка кодировки при чтении файла: " + inputFile);
        } catch (SecurityException e) {
            System.err.println("Ошибка безопасности: доступ к файлу запрещен.");
        } catch (OutOfMemoryError e) {
            System.err.println("Ошибка: недостаточно памяти для обработки файла.");
        } catch (FileNotFoundException e) {
            System.err.println("Ошибка: файл не может быть найден - " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Ошибка: неподдерживаемая кодировка при работе с файлом.");
        } catch (IOException e) {
            System.err.println("Общая ошибка ввода-вывода: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Непредвиденная ошибка: " + e.getMessage());
        }


    }
    // Метод для подсчета слов
    public static int countWords(String text) {
        String[] words = text.trim().split("\\s+");
        return words.length;
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
