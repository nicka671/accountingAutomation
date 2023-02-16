import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MonthReport {
    HashMap<String, HashMap<Integer, Boolean>> monthReport = new HashMap<>();
    final Scanner scanner = new Scanner(System.in);



    public List<String> readMonthFileContents(String path) {

        try {
            return Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с одним из месячных отчётом. Возможно файл не находится в нужной директории.");
            return Collections.emptyList();
        }
    }


    public HashMap splitMonthReportArray(List<String> readFileContents) {
        String category;
        Integer quantity;
        Integer moneyAmount;
        Integer totalAmount;
        HashMap<Integer, Boolean> amountDetalization;

        monthReport = new HashMap<>();

        for (String line : readFileContents)
        {
            String[] lineContents = line.split(",");
           {

                category = lineContents[0];
               Boolean isExpense = Boolean.parseBoolean(lineContents[1]);
                quantity = Integer.parseInt(lineContents[2]);
                moneyAmount = Integer.parseInt(lineContents[3]);
                totalAmount = quantity * moneyAmount;
                amountDetalization = new HashMap<>();
                amountDetalization.put(totalAmount, isExpense);
                monthReport.put(category, amountDetalization);
            }

        }
        return monthReport;
    }


    public HashMap<String, Integer> mostExpensiveItem(HashMap<String, HashMap<Integer, Boolean>> monthReport)
    {
        Boolean isExpense;
        Integer mostExpensiveThing = 0;
        String mostExpensiveThingName = null;
        HashMap<String, Integer> mostExpensiveItem = null;

        for (String currentCategory : monthReport.keySet()) // тут вытаскиваем первый хешМеп
        {
            for (Integer currentAmount : monthReport.get(currentCategory).keySet())
            {
               isExpense = monthReport.get(currentCategory).get(currentAmount);
                 if(isExpense == true && currentAmount > mostExpensiveThing)
                 {
                     mostExpensiveThingName = currentCategory;
                     mostExpensiveThing = currentAmount;
                     mostExpensiveItem = new HashMap<>();
                     mostExpensiveItem.put(mostExpensiveThingName, mostExpensiveThing);
                 }

            }
        }

        return mostExpensiveItem;
    }

    public HashMap<String, Integer> mostProfitableIncome(HashMap<String, HashMap<Integer, Boolean>> monthReport)
    {
        Boolean isExpense;
        Integer mostProfitableThing = 0;
        String mostProfitableThingName = null;
        HashMap<String, Integer> mostProfitableItem = null;

        for (String currentCategory : monthReport.keySet()) // тут вытаскиваем первый хешМеп
        {
            for (Integer currentAmount : monthReport.get(currentCategory).keySet())
            {
                isExpense = monthReport.get(currentCategory).get(currentAmount);
                if(isExpense == false && currentAmount > mostProfitableThing)
                {
                    mostProfitableThingName = currentCategory;
                    mostProfitableThing = currentAmount;
                    mostProfitableItem = new HashMap<>();
                    mostProfitableItem.put(mostProfitableThingName, mostProfitableThing);
                }

            }
        }

        return mostProfitableItem;
    }

    public void monthReportInfo(HashMap<String, HashMap<Integer, Boolean>> monthReportHashMap, MonthReport monthReport)
    {

        System.out.println("Самый прибыльный товар: " + monthReport.mostProfitableIncome(monthReportHashMap));
        System.out.println("Cамая большая категория расходов: " + monthReport.mostExpensiveItem(monthReportHashMap));


    }

    public Integer countMonthExpenses(HashMap<String, HashMap<Integer, Boolean>> monthReport)
    {
        Integer countMonthExpenses = 0;
        Boolean isExpense;
        for (String currentCategory : monthReport.keySet()) // тут вытаскиваем первый хешМеп
        {
            for (Integer currentAmount : monthReport.get(currentCategory).keySet())
            {
                isExpense = monthReport.get(currentCategory).get(currentAmount);
                if(isExpense == true)
                {
                    countMonthExpenses += currentAmount;
                }
            }

    }
        return countMonthExpenses;
    }

    public Integer countMonthIncomes(HashMap<String, HashMap<Integer, Boolean>> monthReport)
    {
        Integer countMonthIncomes = 0;
        Boolean isExpense;
        for (String currentCategory : monthReport.keySet()) // тут вытаскиваем первый хешМеп
        {
            for (Integer currentAmount : monthReport.get(currentCategory).keySet())
            {
                isExpense = monthReport.get(currentCategory).get(currentAmount);
                if(isExpense == false)
                {
                    countMonthIncomes += currentAmount;
                }
            }

        }
        return countMonthIncomes;
    }

    public Integer countMonthProfit(HashMap<String, HashMap<Integer, Boolean>> monthReport)
    {
        Integer monthProfit;
        monthProfit = countMonthIncomes(monthReport) - countMonthExpenses(monthReport);
        return monthProfit;
    }

}



//    Название месяца;
//    Самый прибыльный товар, то есть товар для которого is_expense == false,
//        а произведение количества (quantity) на сумму (sum_of_one) максимально. Вывести название товара и сумму;
//        Самую большую трату. Вывести название товара и сумму.