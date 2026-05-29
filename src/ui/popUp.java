package ui;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class popUp {
    private String value;
    public JToolBar run(ArrayList<String> options){
        JToolBar toolBar = new JToolBar();
        toolBar.add(createMoreButton(options));

        return toolBar;
    }

    public JToggleButton createMoreButton(ArrayList<String> options) {
        JToggleButton toggleButton = new JToggleButton("Choose...");
        toggleButton.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                createPopUp(options, toggleButton);
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                System.out.println("close menu");
            }
        });

        return toggleButton;
    }

    public void createPopUp(ArrayList<String> options, JToggleButton toggleButton) {
        JPopupMenu menu = new JPopupMenu();
        for (String option : options) {
            JMenuItem item = new JMenuItem(option);

            item.addActionListener(e -> {
                JMenuItem clickedItem = (JMenuItem) e.getSource();
                String value = clickedItem.getText();

                toggleButton.setText(value);
                toggleButton.setSelected(false);

                this.value = value;
            });

            menu.add(item);
        }

        menu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // je to tu jinak to nebude fungovat
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                toggleButton.setSelected(false);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                toggleButton.setSelected(false);
            }
        });

        menu.show(toggleButton, 0, toggleButton.getHeight());
    }

    public String getValue() {
        return value;
    }
}
