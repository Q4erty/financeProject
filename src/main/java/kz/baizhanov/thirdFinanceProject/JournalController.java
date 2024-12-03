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
        Transaction transaction = new Transaction(date, name, debit, credit);
        DBManager.addElement(transaction);
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
}
