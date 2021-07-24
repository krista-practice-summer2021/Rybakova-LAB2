import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Numbers {

    private File fileIn;
    private File fileOut;

    private List<Integer> inputInts; //исходный файл
    private List<Integer> listSquares; //хранение квадратных чисел
    private List<Integer> evenList; //чётные
    private Map<String, Integer> mapInput;

    public Numbers() {
        inputInts = new ArrayList<> ();
        listSquares = new ArrayList<>();
        mapInput = new HashMap<>();
        evenList = new ArrayList<>();
    }

    public static void main(String[] args) {
        Numbers Numberoperations = new Numbers();

        String pathIn = "src/main/resources/input.txt";
        String pathOut = "src/main/resources/output.txt";

        Numberoperations.setFileOut(new File(pathOut).getAbsoluteFile());
        Numberoperations.setFileIn(new File(pathIn).getAbsoluteFile());
        Numberoperations.readFileNums();

        Numberoperations.filterList(Numberoperations.inputInts);
        Numberoperations.squareList(Numberoperations.evenList);
        Collections.sort(Numberoperations.listSquares);

        Numberoperations.fileOutput(Numberoperations.listSquares);
    }


    public void setFileIn(File toSetIn){
        this.fileIn = toSetIn;
    }
    public void setFileOut(File toSetOut){
        this.fileOut = toSetOut;
    }
    public void fileOutput(List<Integer> ints){
        try(FileWriter writer = new FileWriter(fileOut)) {
            for(int i=0;i<ints.size();i++){
                writer.write(ints.get(i)+" ");
                System.out.print(ints.get(i)+" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFileNums() {
        try (Scanner in = new Scanner(fileIn)) {
            while (in.hasNext()) {
                inputInts.add(in.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public void filterList(List<Integer> ints) {
        ints.stream().filter(number -> number % 2 == 0).forEach(number -> evenList.add(number));
    }
    public void squareList(List<Integer> ints){
        ints.forEach(number -> listSquares.add(number*number));
    }

}