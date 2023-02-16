import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//дописать вытаскивание года и среднегодовое значение, а также минус заголовка

public class YearReport
{
    HashMap <Integer, ArrayList<HashMap<Integer, Boolean>>> yearReport = new HashMap<>();
    final Scanner scanner = new Scanner(System.in);

    public List<String> readYearFileContents() {
        String path = "./y.2022.csv";
        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с годовым отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }
    public Integer getYearTitle(List<String> readFileContents)
    {
        Integer numberOfYear;
        String[] title = readFileContents.get(0).split(" ");
        numberOfYear = Integer.parseInt(title[3]);
        return numberOfYear;

    }

    public HashMap splitYearReportArray(List<String> readFileContents)
    {

        Integer firstLineMonth;
        Integer firstLineAmount;
        Boolean firstLineIsExpense = false;
        ArrayList<HashMap<Integer,Boolean>> mounthAmount;
        HashMap<Integer,Boolean> monthDetalization;


        String[] firstLineContents = readFileContents.get(1).split(",");
        firstLineMonth =  Integer.parseInt(firstLineContents[0]);
        firstLineAmount =  Integer.parseInt(firstLineContents[1]);
        if (firstLineContents[2].equals("true")) {
            firstLineIsExpense = true;
        }

        monthDetalization = new HashMap<>();
        monthDetalization.put(firstLineAmount, firstLineIsExpense);
        mounthAmount = new ArrayList<>();
        mounthAmount.add(monthDetalization);
        yearReport.put(firstLineMonth,mounthAmount);


        for ( int i = 2; i < readFileContents.size(); i++)
        {
            Integer currentMonth;
            Integer previousMonth;
            String[] currentLineContents = readFileContents.get(i).split(",");
            currentMonth = Integer.parseInt(currentLineContents[0]);
            String[] previousLineContents = readFileContents.get(i-1).split(",");
            previousMonth = Integer.parseInt(previousLineContents[0]);

            if (currentMonth.equals(previousMonth))
            {
                Integer amount;
                Boolean isExpense = false;
                String[] lineContents = readFileContents.get(i).split(",");
                amount = Integer.parseInt(lineContents[1]);
                if (lineContents[2].equals("true")) {
                    isExpense = true;
                }
                monthDetalization = new HashMap<>();
                monthDetalization.put(amount, isExpense);
                yearReport.get(currentMonth).add(monthDetalization);

            }

            else if (!(currentMonth.equals(previousMonth)))
            {
                Integer month;
                Integer amount;
                Boolean isExpense = false;
                String[] lineContents = readFileContents.get(i).split(",");
                month = Integer.parseInt(lineContents[0]);
                amount = Integer.parseInt(lineContents[1]);
                if (lineContents[2].equals("true")) {
                    isExpense = true;
                }

                monthDetalization = new HashMap<>();
                monthDetalization.put(amount, isExpense);
                mounthAmount = new ArrayList<>();
                mounthAmount.add(monthDetalization);
                yearReport.put(month,mounthAmount);
            }


        }

        return yearReport;
    }

    public Double countAvgYearIncomes(HashMap<Integer, ArrayList<HashMap<Integer, Boolean>>> yearReport)
    {
        Integer yearIncomes = 0;
        for (Integer month : yearReport.keySet())
        {
            yearReport.get(month); //тут получаем ArrayList<HashMap<Integer, Boolean>>
            for (HashMap<Integer, Boolean> oneLine : yearReport.get(month)) // //тут получаем HashMap<Integer, Boolean>
            {
                for ( Integer money : oneLine.keySet()) // тут получаем Integer из HashMap
                {
                    if(oneLine.get(money) == false)
                    {
                        yearIncomes += money;
                    }
                }
            }

        }


            return (double)yearIncomes / 12;
    }

    public Double countAvgYearExpenses(HashMap<Integer, ArrayList<HashMap<Integer, Boolean>>> yearReport)
    {
        Integer yearExpenses = 0;
        for (Integer month : yearReport.keySet())
        {
            yearReport.get(month); //тут получаем ArrayList<HashMap<Integer, Boolean>>
            for (HashMap<Integer, Boolean> oneLine : yearReport.get(month)) // //тут получаем HashMap<Integer, Boolean>
            {
                for ( Integer money : oneLine.keySet()) // тут получаем Integer из HashMap
                {
                    if(oneLine.get(money) == true)
                    {
                        yearExpenses += money;
                    }
                }
            }

        }


        return (double)yearExpenses / 12;
    }




//    Рассматриваемый год;
//    Прибыль по каждому месяцу. Прибыль — это разность доходов и расходов;
//    Средний расход за все месяцы в году;
//    Средний доход за все месяцы в году.

    HashMap<Integer,Integer> countEachMonthIncomes(HashMap<Integer, ArrayList<HashMap<Integer, Boolean>>> yearReport)
    {
        HashMap<Integer,Integer> countEachMonthIncomes = new HashMap<>();


        for (Integer month : yearReport.keySet()) // тут получаем Month
        {
            Integer monthExpense = 0;
            Integer monthIncomes = 0;
            Integer eachMonthIncomes = 0;
            yearReport.get(month); //тут получаем ArrayList<HashMap<Integer, Boolean>>
            for (HashMap<Integer, Boolean> oneLine : yearReport.get(month)) // //тут получаем HashMap<Integer, Boolean>
            {
                for ( Integer money : oneLine.keySet()) // тут получаем Integer из HashMap
                {
                    if(oneLine.get(money) == true)
                    {
                        monthExpense += money;

                    } else if (oneLine.get(money) == false)
                    {
                        monthIncomes += money;
                    }

                }
                eachMonthIncomes = monthIncomes - monthExpense;
                countEachMonthIncomes.put(month,eachMonthIncomes);

            }

        }

        return countEachMonthIncomes;
    }


  public void yearReportInfo(HashMap<Integer, ArrayList<HashMap<Integer, Boolean>>> yearReportHashMap, YearReport yearReport)
  {

      System.out.println("Прибыль по каждому месяцу: " + yearReport.countEachMonthIncomes(yearReportHashMap));
      System.out.println("Средний расход за все месяцы в году: " + yearReport.countAvgYearExpenses(yearReportHashMap));
      System.out.println("Средний доход за все месяцы в году: " + yearReport.countAvgYearIncomes(yearReportHashMap));

  }

}

