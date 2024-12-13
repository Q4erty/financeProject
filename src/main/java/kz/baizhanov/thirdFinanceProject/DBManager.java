package kz.baizhanov.thirdFinanceProject;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private static Connection connection;
    static{
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/thirdFinanceProject", "postgres","nurtas05");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addTransaction(Transaction transaction){
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO journal_entry(date, name, debit, credit) VALUES(?, ?, ?, ?)");
            stmt.setString(1,transaction.getDate().toUpperCase());
            stmt.setString(2,transaction.getName().toUpperCase());
            stmt.setInt(3,transaction.getDebit());
            stmt.setInt(4,transaction.getCredit());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM journal_entry");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()){
                Transaction transaction = Transaction.builder()
                        .id(resultSet.getLong("id"))
                        .date(resultSet.getString("date"))
                        .name(resultSet.getString("name"))
                        .debit(resultSet.getInt("debit"))
                        .credit(resultSet.getInt("credit"))
                        .build();
                transactions.add(transaction);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
        return transactions;

    }

    public static Transaction getTransactionById(Long id){
        Transaction transaction = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM journal_entry WHERE id = ?");
            stmt.setLong(1,id);
            ResultSet resultSet = stmt.executeQuery();
            if(resultSet.next()){
                transaction = Transaction.builder()
                        .id(resultSet.getLong("id"))
                        .date(resultSet.getString("date"))
                        .name(resultSet.getString("name"))
                        .debit(resultSet.getInt("debit"))
                        .credit(resultSet.getInt("credit"))
                        .build();
            }
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return transaction;
    }

    public static void updateTransaction(Transaction transaction){
        try {
            PreparedStatement stmt = connection.prepareStatement
                    ("UPDATE journal_entry SET date = ?, name = ?, debit = ?, credit = ? WHERE id = ?");
            stmt.setString(1, transaction.getDate().toUpperCase());
            stmt.setString(2, transaction.getName().toUpperCase());
            stmt.setInt(3, transaction.getDebit());
            stmt.setInt(4, transaction.getCredit());
            stmt.setLong(5, transaction.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTransaction(Long id){
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM journal_entry WHERE id = ?");
            stmt.setLong(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<TrialBalance> getTrialBalance() {
        ArrayList<TrialBalance> trialBalance = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement
                    ("SELECT name, SUM(debit) AS total_debit, SUM(credit) AS total_credit FROM journal_entry GROUP BY name");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                TrialBalance result = TrialBalance.builder()
                        .name(resultSet.getString("name"))
                        .totalDebit(resultSet.getInt("total_debit"))
                        .totalCredit(resultSet.getInt("total_credit"))
                        .build();
                trialBalance.add(result);
            }
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trialBalance;
    }

}
