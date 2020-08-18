package dao;

import controller.AccountChecker;
import controller.PageManager;
import controller.UpdateQueue;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PageDao {


    private PreparedStatement loadData;
    private PreparedStatement insertData;
    private PreparedStatement deleteData;
    private PreparedStatement updateData;
    private String tableName = AccountChecker.readObject()[1];

    public PageDao(Connection connection) {

        try {
            loadData = connection.prepareStatement(
                    "SELECT * FROM "+ tableName +" WHERE YEAR (activitate) = ? AND MONTH(activitate) = ?"
            );
            insertData = connection.prepareStatement(
                    "INSERT INTO "+ tableName +" VALUES (null,?,?)"
            );
            updateData = connection.prepareStatement(
                    "UPDATE "+ tableName +" SET colour = ? WHERE id = ?"
            );
            deleteData = connection.prepareStatement(
                    "DELETE FROM "+ tableName +" WHERE id = ?"
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<UpdateQueue> getMonthState(int yearNumber, int monthNumber) {
        List<UpdateQueue> temp = new ArrayList<>();
        try {
            loadData.setInt(1,yearNumber);
            loadData.setInt(2,monthNumber);
            ResultSet rs = loadData.executeQuery();
            while (rs.next()) {
                Date d1 = rs.getDate("activitate");
                LocalDate cal = d1.toLocalDate();

                temp.add(new UpdateQueue(rs.getInt("id"),cal.getDayOfMonth(),rs.getInt("colour")));
            }
            return temp;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new LinkedList<>();
    }

    public boolean insertData(List<UpdateQueue> listi) {
        listi.forEach(e -> {
            try {
                insertData.setDate(1, Date.valueOf(PageManager.getInstance().getYear() + "-" + PageManager.getInstance().getMonth() + "-" + e.getNumber()));
                insertData.setInt(2, e.getColour());
                insertData.executeUpdate() ;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return true;
    }

    public void updateData(List<UpdateQueue> list) {
        list.forEach(e -> {
            try {
                updateData.setInt(1, e.getColour());
                updateData.setInt(2, e.getId());
                updateData.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    public void deleteData(List<UpdateQueue> li) {

        li.forEach(e -> {
            try {
                deleteData.setInt(1, e.getId());
                deleteData.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }


}