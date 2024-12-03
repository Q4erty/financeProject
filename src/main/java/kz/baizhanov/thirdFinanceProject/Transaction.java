package kz.baizhanov.thirdFinanceProject;

import lombok.Data;

@Data
public class Transaction {
    private static Long currentId = 0L;
    private Long id;
    private String date;
    private String name;
    private int debit;
    private int credit;

    public Transaction() {
        currentId++;
        id = currentId;
    }

    public Transaction(String date, String name, int debit, int credit) {
        currentId++;
        id = currentId;
        this.date = date;
        this.name = name;
        this.debit = debit;
        this.credit = credit;
    }
}