package kz.baizhanov.thirdFinanceProject;

import java.util.ArrayList;

public class DBManager {

    private static ArrayList<Transaction> list = new ArrayList<>();

    public static void addElement(Transaction transaction){
        list.add(transaction);
    }

    public static ArrayList<Transaction> getAllTransactions(){
        return list;
    }

    public static Transaction getTransactionById(Long id){
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getId().equals(id)){
                index = i;
            }
        }
        return list.get(index);
    }
}
