package gui;

import controller.PageManager;
import inter.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Day extends JPanel{

    private JPanel panel1;
    private JLabel label;
    private JPanel panel2;
    private MainFrame frame;

    private Mediator mediator;

    public Day(String text, MainFrame frame) {

        label.setText(text);
        this.frame = frame;
        this.mediator = PageManager.getInstance();
        panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == 3) {
                    panel2.setBackground(Color.RED);
                    
                    panel2.revalidate();
                    panel2.repaint();
                    mediator.stateChange(Integer.parseInt(label.getText()), 3);
                } else if(mouseEvent.getButton() == 2) {
                    panel2.setBackground(Color.WHITE);

                    panel2.revalidate();
                    panel2.repaint();
                    mediator.stateChange(Integer.parseInt(label.getText()),2);
                } else if(mouseEvent.getButton() == 1) {
                    panel2.setBackground(Color.GREEN);

                    panel2.revalidate();
                    panel2.repaint();
                    mediator.stateChange(Integer.parseInt(label.getText()), 1);
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
    public JPanel getPanel2() {
        return panel2;
    }
    public JLabel getLabel() {
        return label;
    }
    public void setLabel(String text) {
        label.setText(text);
    }

}
