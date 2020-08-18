package controller;

import inter.Mediator;

import java.lang.management.MonitorInfo;

public class TimeManager {

    private static final String [] MONTHS = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    private int currentMonth;
    private Mediator mediator;

    public TimeManager(Mediator mediator, int currentMonth) {
        this.currentMonth = currentMonth;
        this.mediator = mediator;
    }

    public void lastMonth() {
        System.out.println(currentMonth);
        if (currentMonth-1 == 0) {
            mediator.changeTime(PageManager.getInstance().getYear()-1,MONTHS.length);
            currentMonth = MONTHS.length;
            mediator.getQueeu();
        }else {
            mediator.changeTime(PageManager.getInstance().getYear(), currentMonth-1);
            currentMonth --;
        }
    }
    public void nextMonth() {
        if (currentMonth == MONTHS.length) {
            mediator.changeTime(PageManager.getInstance().getYear()+1,1);
            currentMonth = 1;
            mediator.getQueeu();
        }else {
            mediator.changeTime(PageManager.getInstance().getYear(), currentMonth+1);
            currentMonth ++;
        }
    }


}
