import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


class CollectionsHandler {

    private File fileIn;
    //поле класса File (ввод в файл)
    private File fileOut;
    //поле класса File (вывод из файла)

    private List<String> inputLineList;
    //список строк с наименованием переменной
    private Set<String> wordSet;
    //множество строк с переменной
    private Map<String, Integer> wordRepeatCountMap;
    //поиск в строке значения


    public CollectionsHandler() {
        inputLineList = new ArrayList<>();
        //создание нового объекта для массива
        wordSet = new HashSet<>();
        //создание нового объекта для множества
        wordRepeatCountMap = new HashMap<>();
        //создание нового объекта (словарь для хранения элементов)
    }

    public static void main(String[] args) {

        CollectionsHandler wordRepeatCounter = new CollectionsHandler();

        String pathIn = "src/main/resources/input.txt";
        String pathOut = "src/main/resources/output.txt";

        wordRepeatCounter.setFileIn(new File(pathIn).getAbsoluteFile());
        wordRepeatCounter.setFileOut(new File(pathOut).getAbsoluteFile());

        wordRepeatCounter.readFileLines();

        wordRepeatCounter.addWord("Ларчик");
        wordRepeatCounter.addWord("Ларец");
        wordRepeatCounter.addWord("Ларечище");

        wordRepeatCounter.getWordSet().forEach(word -> {
            wordRepeatCounter.setWordRepeatCount(word);
        });

        wordRepeatCounter.writeCountInFileMap();
    }

    public void setFileIn(File fileIn) {
        this.fileIn = fileIn;
    }

    public void setFileOut(File fileOut) {
        this.fileOut = fileOut;
    }

    public void addWord(String element) {
        this.wordSet.add(element);
    }

    public Set<String> getWordSet() {
        return wordSet;
    }

    public void setWordRepeatCount(String word) {
        //метод принимает строку символов
        int count = inputLineList.stream().mapToInt(line -> line.split(word).length - 1).sum();
        // переменной присваивается поток, который ищет по строке все вхождения слова, вычитает 1 так как было бы на 1 больше, и все результаты суммирует
        wordRepeatCountMap.put(word, count);
        //добавление в объект слова
    }

    public List<String> filterLinesWithWord(List<String> lines, String word) {
        //фильтр для поиска слова (принимает список и строку символов)
        return lines.stream().filter(line -> line.contains(word)).collect(Collectors.toList());
        //возвращаем, если фильтр нашёл, совпадение со строкой, и преобразуем в список
    }

    public List<String> sortListByLineLength(List<String> lines) {
        //сортировка списка (принимает список)
        List<String> sortList = new ArrayList<>(lines);
        //создаём новый объект списка
        sortList.sort(Comparator.comparingInt(String::length));
        //сортировка всего списка
        return sortList;
        //возвращает отсортированный список
    }

    public void readFileLines() {
        try (Scanner in = new Scanner(fileIn)) {
            // построчное чтение файла
            while (in.hasNextLine()) {
                //пока следующая порция данных является строкой
                String line = in.nextLine();
                //строка равна прочтённой следующей строке
                inputLineList.add(line);
                //добавление строки
            }
        } catch (FileNotFoundException e) {
            //файл не удалось открыть
            System.out.println(e.fillInStackTrace());
            //ссылка на обработчик исключений

        }
    }

    public void writeCountInFileMap() {
        try (FileWriter writer = new FileWriter(fileOut)) {
            Map<String, Integer> sortedMap = new LinkedHashMap<>(wordRepeatCountMap);
            // построковая запись в файл
            // отсортированных по убыванию частот и соответствующих строк
            sortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> {
                String printStr = "«" + entry.getKey() + "»" + " повторяется " + entry.getValue() + " раз";
                try {
                    writer.write(printStr + "\n\n");
                    List<String> withWordLineList = filterLinesWithWord(inputLineList, entry.getKey());
                    sortListByLineLength(withWordLineList).forEach(sortedLine -> {
                        try {
                            writer.write(sortedLine + "\n");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



