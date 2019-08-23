import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ExchangeRates {

    public static void main(String[] args) {

        String csvURL;
        String pathName;

        Scanner in = new Scanner(System.in);
        System.out.println("Irasykite valiutu kodus (pvz. GBP, USD, JPY):");
        String currency =  in.nextLine().toUpperCase().replace(" ", "");
        System.out.println("Irasykite pradzios data (formatas YYYY-MM-DD):");
        String dateForm = in.nextLine();
        System.out.println("Irasykite pabaigos data (formatas YYYY-MM-DD):");
        String dateTo = in.nextLine();

        List<String> currencyToList = new ArrayList<>(Arrays.asList(currency.split(",")));

        for (int i = 0; i < currencyToList.size(); i++) {

            csvURL = "https://www.lb.lt/lt/currency/exportlist/?csv=1&currency="+currencyToList.get(i)+"&ff=1&class=Lt&type=day&date_from_day="+dateForm+"&date_to_day="+dateTo;
            pathName = "currency"+currencyToList.get(i)+".csv";

            CSVFileManager csvFileManager = new CSVFileManager(csvURL, pathName);
            csvFileManager.downloadCSV();
            csvFileManager.displayCsvInfo();
        }
    }
}
