package controller;

import java.sql.Date;
import java.util.List;

public class UpdateQueue {

    private int id;
    private int number;
    private int colour;
    private Date data;

    public UpdateQueue(int number, int colour) {
        this.number = number;
        this.colour = colour;
    }

    public UpdateQueue(int id, int number, int colour) {
        this.id = id;
        this.number = number;
        this.colour = colour;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof UpdateQueue) {
            return this.number == ((UpdateQueue)obj).getNumber();
        }
        return false;
    }

    @Override
    public String toString() {
        return id + " " + number + " " + colour;
    }

    public int getNumber() {
        return number;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data.toString();
    }

    public void setData(Date data) {
        this.data = data;
    }
}
