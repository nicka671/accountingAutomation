import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void printMenu()
    {
        System.out.println("МЕНЮ:");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("6 - выйти");
    }

    public static void main(String[] args)
    {
        HashMap<String, HashMap<Integer, Boolean>> firstMonthReportHashMap = new HashMap<>();
        HashMap<String, HashMap<Integer, Boolean>> secondMonthReportHashMap = new HashMap<>();
        HashMap<String, HashMap<Integer, Boolean>> thirdMonthReportHashMap = new HashMap<>();
        MonthReport monthReport = new MonthReport();
        HashMap <Integer, ArrayList<HashMap<Integer, Boolean>>> yearReportHashMap = new HashMap<>();
        YearReport yearReport = new YearReport();
        Scanner scanner = new Scanner(System.in);
        List<String> readFileContents;
        Integer yearNumber = 0;


        printMenu();

        while (true)
        {
            int command = scanner.nextInt();

            if (command == 1)
            {

                System.out.println("Считывание файлов");
                String path1 = "./m.202201.csv";
                String path2 = "./m.202202.csv";
                String path3 = "./m.202203.csv";


                    readFileContents = monthReport.readMonthFileContents(path1);
                    firstMonthReportHashMap = monthReport.splitMonthReportArray(readFileContents);

                    readFileContents = monthReport.readMonthFileContents(path2);
                    secondMonthReportHashMap = monthReport.splitMonthReportArray(readFileContents);

                    readFileContents = monthReport.readMonthFileContents(path3);
                    thirdMonthReportHashMap = monthReport.splitMonthReportArray(readFileContents);

                System.out.println("Готово!");
                printMenu();
            } else if (command == 2)
            {

                readFileContents = yearReport.readYearFileContents();
                yearReportHashMap = yearReport.splitYearReportArray(readFileContents);
                yearNumber = yearReport.getYearTitle(readFileContents);
                System.out.println("Готово!");
                printMenu();
            } else if (command == 3)
            {
                if (thirdMonthReportHashMap.size() != 0 && yearReportHashMap.size() != 0)
                {
                    System.out.println("Сверяем месячные и годовые отчеты");

                HashMap<Integer,Integer> countEachMonthIncomeHashmap = new HashMap<>();
                countEachMonthIncomeHashmap = yearReport.countEachMonthIncomes(yearReportHashMap);

                HashMap<Integer,Integer> countMonthProfit = new HashMap<>();
                countMonthProfit.put(1,monthReport.countMonthIncomes(firstMonthReportHashMap) - monthReport.countMonthExpenses(firstMonthReportHashMap));
                countMonthProfit.put(2,monthReport.countMonthIncomes(secondMonthReportHashMap) - monthReport.countMonthExpenses(secondMonthReportHashMap));
                countMonthProfit.put(3,monthReport.countMonthIncomes(thirdMonthReportHashMap) - monthReport.countMonthExpenses(thirdMonthReportHashMap));

                for (int i = 1; i < 4; i++)
                {
                    Integer differenceBetweenReports = countEachMonthIncomeHashmap.get(i) - countMonthProfit.get(i);

                    if( differenceBetweenReports != 0)
                    {
                        System.out.println("Месяц " + i + " проверен. Все плохо");
                        String smthWentWrong = differenceBetweenReports.toString();
                        if (smthWentWrong.startsWith("-")) {
                            smthWentWrong = smthWentWrong.replace("-", " ");
                            System.out.println("Доход в месяце получился больше годового на" + smthWentWrong);
                        } else {
                            System.out.println("Доход в году получился больше месячного на " + smthWentWrong);
                        }

                        }
                    }
                } else {
                    System.out.println("Информации не хватает. Введите все отчеты за год");
                }
                System.out.println("Готово!");
                printMenu();

// ./m.202201.csv
                // ./y.2022.csv
            } else if (command == 4)
            {
                if (thirdMonthReportHashMap.size() != 0 && firstMonthReportHashMap.size() != 0 && secondMonthReportHashMap.size() != 0)
                {
                    System.out.println("Информация по месяцу " + 1);
                    monthReport.monthReportInfo(firstMonthReportHashMap, monthReport);
                    System.out.println("Информация по месяцу " + 2);
                    monthReport.monthReportInfo(secondMonthReportHashMap, monthReport);
                    System.out.println("Информация по месяцу " + 3);
                    monthReport.monthReportInfo(thirdMonthReportHashMap, monthReport);
                    System.out.println("Готово!");
                    printMenu();
                } else {
                    System.out.println("Кажется, вы не загрузили необходимые отчеты");
                }

            } else if (command == 5)
            {
                if (yearReportHashMap.size() != 0) {
                    System.out.println("Информация об отчете за " + yearNumber + " год");
                    yearReport.yearReportInfo(yearReportHashMap, yearReport);
                    System.out.println("Готово!");
                    printMenu();
                } else {
                    System.out.println("Сначала нужно загрузить годовой отчет");
                }
            } else if (command == 6)
            {
                return;
            }

        }

    }
}
