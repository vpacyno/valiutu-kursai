import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;


class CSVFileManager {

    private static final String COMMA_DELIMITER = ";";
    private String pathName;
    private String csvURL;
    private List<List<String>> records = new ArrayList<>();

    public CSVFileManager(String csvURL, String pathName) {
        this.csvURL = csvURL;
        this.pathName = pathName;
    }

    public void downloadCSV() {
        try {
            URL url = new URL(csvURL);
            URLConnection uc = url.openConnection();
            uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:68.0) Gecko/20100101 Firefox/68.0");
            uc.connect();

            BufferedInputStream buffer = new BufferedInputStream(uc.getInputStream());
            FileOutputStream fileOutputStream = new FileOutputStream(pathName);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = buffer.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }

            buffer.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<List<String>> csvToArray() {
        try {
            Scanner scanner = new Scanner(new File(pathName));
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return records;
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    public void displayCsvInfo() {

        csvToArray();

        for (int i = 0; i < records.size(); i++) {

            StringJoiner joiner = new StringJoiner("|", "|", "|");

            for (int j = 0; j < records.get(i).size(); j++) {

                joiner.add(records.get(i).get(j).replace("\"", ""));
            }
            System.out.println(joiner);
        }

        String endValue = records.get(1).get(2).replace("\"", "").replace(",", ".");
        String startValue = records.get(records.size()-1).get(2).replace("\"", "").replace(",", ".");
        double currencyChange = Double.valueOf(endValue) - Double.valueOf(startValue);

        NumberFormat formatter = new DecimalFormat("#0.0000");

        System.out.println("Valiutos pokytis: " + formatter.format(currencyChange) + " " + records.get(1).get(1).replace("\"", "") + "\n");
    }
}
