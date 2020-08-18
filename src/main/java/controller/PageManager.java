package controller;

import dao.PageDao;
import gui.MainFrame;
import inter.Mediator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class PageManager implements Mediator {

    private PageDao pageDao;
    private MainFrame mainFrame;
    private List<UpdateQueue> queue;
    private List<UpdateQueue> toInsert;
    private List<UpdateQueue> toDelete;
    private List<UpdateQueue> toUpdate;
    private int month;
    private int year;
    private TimeManager timeManager;

    @Override
    public void update() {
        if (!toUpdate.isEmpty()) {
            pageDao.updateData(toUpdate);
            toUpdate.clear();
        }
        if (!toInsert.isEmpty()) {
            pageDao.insertData(toInsert);
            toInsert.clear();
        }
        if (!toDelete.isEmpty()) {
            pageDao.deleteData(toDelete);
        }
    }

    @Override
    public void refresh() {
        toDelete.clear();
        toUpdate.clear();
        toInsert.clear();
        mainFrame.getSave().setEnabled(false);
    }

    @Override
    public void getQueeu() {
        this.queue = pageDao.getMonthState(year,month);
    }

    @Override
    public void changeTime(int year, int month) {
        this.month = month;
        this.year = year;

        mainFrame.getYear().setText(String.valueOf(year));
        mainFrame.getMonth().setText(String.valueOf(YearMonth.of(year,month).getMonth()).toUpperCase());

        mainFrame.refresh(YearMonth.of(this.year,this.month).lengthOfMonth());
    }

    @Override
    public void stateChange(int number, int colour) {
        UpdateQueue tempObj = new UpdateQueue(number, colour);


        if( colour == 2) {
           if (toDelete.contains(tempObj)) {

               return;
           }else{
               if (toUpdate.contains(tempObj)) {
                   toUpdate.remove(tempObj);

               }else if(toInsert.contains(tempObj)) {

                   toInsert.remove(tempObj);

               }else if(queue.contains(tempObj)) {
                   int id = queue.indexOf(tempObj);
                   tempObj.setId(queue.get(id).getId());
                   toDelete.add(tempObj);

               }
           }
       }else if(colour == 1 || colour == 3) {
            if (toDelete.contains(tempObj)) {
                toDelete.remove(tempObj);

            }
            if(toUpdate.contains(tempObj)) {
                toUpdate.get(toUpdate.indexOf(tempObj)).setColour(colour);

            }else if(toInsert.contains(tempObj)) {
                toInsert.get(toInsert.indexOf(tempObj)).setColour(colour);

            }else if(queue.contains(tempObj) && queue.get(queue.indexOf(tempObj)).getColour() != colour) {
                queue.get(queue.indexOf(tempObj)).setColour(tempObj.getColour());
                toUpdate.add(queue.get(queue.indexOf(tempObj)));
            }else if(!queue.contains(tempObj)) {
                toInsert.add(tempObj);
            }

        }
        mainFrame.getSave().setEnabled(true);
    }

    private static final class SingletonHolder{
        private static final PageManager INSTANCE = new PageManager();
    }

    private PageManager() {
        this.month = LocalDate.now().getMonthValue();
        this.year = LocalDate.now().getYear();
        this.pageDao = new PageDao(ConnectionManager.getInstance().getConnection());

        this.timeManager = new TimeManager(this, month);
        this.queue = pageDao.getMonthState(year,month);
        this.toInsert = new ArrayList<>();
        this.toDelete = new ArrayList<>();
        this.toUpdate = new ArrayList<>();
    }

    public static PageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }



    public void SetMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public TimeManager getTimeManager() {
        return timeManager;
    }

    public List<UpdateQueue> getQueue() {
        return queue;
    }
}
