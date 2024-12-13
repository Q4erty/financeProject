package kz.baizhanov.thirdFinanceProject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/finance")
public class JournalController {

    @GetMapping(value = "/add-transaction")
    public String openAddTransation() {
        return "add-transaction";
    }

    @PostMapping(value = "/add-transaction")
    public String addTransaction(@RequestParam("date") String date,
                                 @RequestParam("name") String name,
                                 @RequestParam("debit") int debit,
                                 @RequestParam("credit") int credit){
        Transaction transaction = Transaction.builder()
                .date(date)
                .name(name)
                .debit(debit)
                .credit(credit)
                .build();
        DBManager.addTransaction(transaction);
        return "redirect:/finance";
    }

    @GetMapping
    public String openJournalEntry(Model model){
        ArrayList<Transaction> list = DBManager.getAllTransactions();
        model.addAttribute("list", list);
        return "journal-entry";
    }

    @GetMapping(value = "/about/{id}")
    public String openAbout(Model model, @PathVariable Long id){
        Transaction transaction = DBManager.getTransactionById(id);
        model.addAttribute("transaction", transaction);
        return "about";
    }

    @PostMapping(value = "/update-transaction")
    public String updateTransaction(@RequestParam("id") Long id,
                                    @RequestParam("date") String date,
                                    @RequestParam("name") String name,
                                    @RequestParam("debit") int debit,
                                    @RequestParam("credit") int credit){
        Transaction transaction = Transaction.builder()
                .id(id)
                .date(date)
                .name(name)
                .debit(debit)
                .credit(credit)
                .build();
        DBManager.updateTransaction(transaction);
        return "redirect:/finance";
    }

    @PostMapping(value = "/delete/{id}")
    public String deleteTransaction(@PathVariable Long id){
        DBManager.deleteTransaction(id);
        return "redirect:/finance";
    }

    @GetMapping(value = "/trial-balance")
    public String openTrialBalance(Model model) {
        ArrayList<TrialBalance> trialBalance = DBManager.getTrialBalance();
        model.addAttribute("trialBalance", trialBalance);
        return "trial-balance";
    }

}
