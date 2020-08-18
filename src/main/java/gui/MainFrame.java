package gui;

import controller.PageManager;
import controller.UpdateQueue;
import inter.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public class MainFrame extends JFrame {
    private JPanel panel1;
    private JButton prev;
    private JButton next;
    private JPanel container;
    private JButton save;
    private JLabel year;
    private JLabel month;
    private JButton refreshButton;
    private Day [] days;

    private Mediator mediator;

    public MainFrame() {
        this.setContentPane(panel1);
        this.setSize(950,490);
        GridLayout gridLayout = new GridLayout(3,11);
        container.setLayout(gridLayout);
        PageManager.getInstance().SetMainFrame(this);

        this.mediator = PageManager.getInstance();

        int day = YearMonth.of(LocalDateTime.now().getYear(),LocalDateTime.now().getMonth()).lengthOfMonth();
        this.year.setText(String.valueOf(LocalDateTime.now().getYear()));
        this.month.setText(String.valueOf(LocalDateTime.now().getMonth()));

        buildMatrix(day);

        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh(YearMonth.of(
                        PageManager
                                .getInstance()
                                .getYear(),
                        PageManager
                                .getInstance()
                                .getMonth())
                        .lengthOfMonth());
            }
        });

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()==1) {
                    mediator.update();
                    save.setEnabled(false);
                }
            }

        });
        this.prev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PageManager.getInstance().getTimeManager().lastMonth();
            }
        });
        this.next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PageManager.getInstance().getTimeManager().nextMonth();
            }
        });

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void buildMatrix(int day) {
        days = new Day[day];
        for (int i=  0; i<day;i++) {

            days[i] = new Day(String.valueOf(i+1),this);
            List<UpdateQueue> uplist = PageManager.getInstance().getQueue();
            if(uplist.contains(new UpdateQueue(i+1,0))) {
                Optional<Integer> index = Optional.empty();
                for (UpdateQueue e : uplist) {
                    if (e.getNumber() == i+1) {
                        index = Optional.of(uplist.indexOf(e));
                    }
                }
                if (index.isPresent() && uplist.get(index.get()).getColour() == 1) {
                    days[i].getPanel2().setBackground(Color.GREEN);
                }else if(index.isPresent() && uplist.get(index.get()).getColour() == 3) {
                    days[i].getPanel2().setBackground(Color.RED);
                }

                days[i].getPanel2().revalidate();
                days[i].getPanel2().repaint();
            }
            container.add(days[i].getPanel1());
        }

    }
    public void  refresh(int days) {
        mediator.getQueeu();
        mediator.refresh();
        container.removeAll();
        buildMatrix(days);
        panel1.revalidate();
        panel1.repaint();
    }

    public static void main(String[] args) {
        new MainFrame();
    }

    public JPanel getContainer() {
        return container;
    }

    public JButton getSave() {
        return save;
    }

    public JLabel getYear() {
        return year;
    }

    public JLabel getMonth() {
        return month;
    }
}
