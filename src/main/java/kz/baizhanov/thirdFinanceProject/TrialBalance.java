package kz.baizhanov.thirdFinanceProject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrialBalance {
    private String name;
    private int totalDebit;
    private int totalCredit;
}
