package controller;

import db.DBConnection;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookReportController {
    public int getMonthlyIssueBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book issue` WHERE IssueDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getAnnuallyIssueBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book issue` WHERE IssueDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getDayIssueBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book issue` WHERE IssueDate ='"+date+"' ").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getMonthlyReturnBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book Return` WHERE ReturnDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getAnnuallyReturnBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book Return` WHERE ReturnDate LIKE '%"+date+"%'").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public int getDayReturnBooks(String date) throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance().getConnection().
                prepareStatement("select BookCount from `book Return` WHERE ReturnDate ='"+date+"' ").executeQuery();
        int count = 0;
        while (rst.next()) {

            count += rst.getInt(1);
        }
        return count;
    }
    public ArrayList<BookReturn> getAllBookReturns() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Book Return`");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookReturn> bookReturns = new ArrayList<>();
        while (rst.next()) {
            bookReturns.add(new BookReturn(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));
        }
        return bookReturns;
    }
    public ArrayList<BookIssue> getAllBookIssues() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Book Issue`");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookIssue> bookIssues = new ArrayList<>();
        while (rst.next()) {
            bookIssues.add(new BookIssue(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5)
            ));
        }
        return bookIssues;
    }
    public ArrayList<Book> getIssueDetail(String id ) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("  select b.name from `Book Issue Detail` bi JOIN `Book` b on bi.BookId=b.bookId where bi.IssueId='"+id+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<Book> books = new ArrayList<>();
        while (rst.next()) {
            books.add(new Book(
                    rst.getString(1)
            ));
        }
        return books;
    }
    public Reader getReader(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Reader WHERE ReaderId=?");
        stm.setObject(1, id);

        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Reader(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getString(5)
            );

        } else {
            return null;
        }
    }
    public ArrayList<BookReturnDetail> getReturnDetail(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("  select b.name,brd.report,brd.FinePrice from `Book Return Detail` brd JOIN `Book` b on brd.BookId=b.bookId where brd.ReturnId='"+id+"'");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookReturnDetail> books = new ArrayList<>();
        while (rst.next()) {
            books.add(new BookReturnDetail(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return books;
    }



    public static ArrayList<BookReturn> searchBookReturns(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Book Return` where ReaderId like'%"+id+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookReturn> bookReturns = new ArrayList<>();
        while (rst.next()) {
            bookReturns.add(new BookReturn(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));
        }
        return bookReturns;
    }
    public static ArrayList<BookIssue> searchBookIssues(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Book Issue`where ReaderId like'%"+id+"%'");
        ResultSet rst = stm.executeQuery();
        ArrayList<BookIssue> bookIssues = new ArrayList<>();
        while (rst.next()) {
            bookIssues.add(new BookIssue(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getInt(5)
            ));
        }
        return bookIssues;
    }
}
