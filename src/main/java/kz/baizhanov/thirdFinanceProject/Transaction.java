package kz.baizhanov.thirdFinanceProject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Transaction {
    private Long id;
    private String date;
    private String name;
    private int debit;
    private int credit;
}