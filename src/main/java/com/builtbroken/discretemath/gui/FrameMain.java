package com.builtbroken.discretemath.gui;

import com.builtbroken.discretemath.propositions.Proposition;
import com.builtbroken.discretemath.propositions.types.EnumTypes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

/**
 * Created by Dark on 9/10/2015.
 */
public class FrameMain extends JFrame implements ActionListener
{
    public long tick = 0;
    JButton solve_button;
    JTextField statement_field;
    UpdatePanel tablePanel;
    AbstractTableModel dataModel;
    Proposition prop;

    public FrameMain()
    {
        this.setSize(800, 600);
        buildGUI();
    }

    protected void buildGUI()
    {
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        UpdatePanel topPanel = new UpdatePanel();
        UpdatePanel centerPanel = new UpdatePanel();
        tablePanel = new UpdatePanel();

        topPanel.setBorder(loweredetched);
        centerPanel.setBorder(loweredetched);
        tablePanel.setBorder(loweredetched);

        buildTop(topPanel);
        buildTable(tablePanel);
        buildCenter(centerPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(tablePanel, BorderLayout.SOUTH);

        //exit icon handler
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    public void buildTop(UpdatePanel panel)
    {
        panel.setLayout(new GridLayout(1, 2, 0, 0));
        statement_field = new JTextField();
        panel.add(statement_field);
        solve_button = new JButton("Solve");
        solve_button.addActionListener(this);
        panel.add(solve_button);
    }

    public void buildTable(UpdatePanel panel)
    {
        panel.setLayout(new BorderLayout());
        dataModel = new AbstractTableModel()
        {
            @Override
            public int getColumnCount()
            {
                return prop != null ? prop.characters.size() + prop.propositions.size() : 8;
            }

            @Override
            public String getColumnName(int column)
            {
                if (prop != null && column >= 0)
                {
                    if (column < prop.characters.size())
                    {
                        return prop.characters.get(column).toString();
                    }
                    else if (column - prop.characters.size() < prop.propositions.size())
                    {
                        return prop.propositions.get(column - prop.characters.size()).toString();
                    }
                }
                return "---";
            }

            @Override
            public int getRowCount()
            {
                return prop != null ? (int) Math.pow(prop.characters.size(), 2) : 10;
            }

            @Override
            public Object getValueAt(int row, int column)
            {
                if (prop != null && column >= 0 && column < getColumnCount() && row < getRowCount())
                {
                    List<Map<Character, Boolean>> lines = prop.getLines();
                    Map<Character, Boolean> map = lines.get(row);
                    if (column < prop.characters.size())
                    {
                        return map.get(prop.characters.get(column));
                    }
                    else if (column - prop.characters.size() < prop.propositions.size())
                    {
                        return prop.propositions.get(column - prop.characters.size()).getTruthValue(map);
                    }
                }
                return ".";
            }
        };
        JTable table = new JTable(dataModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane tableScroll = new JScrollPane(table);
        Dimension tablePreferred = tableScroll.getPreferredSize();
        tableScroll.setPreferredSize(new Dimension(tablePreferred.width, tablePreferred.height / 3));

        panel.add(tableScroll, BorderLayout.SOUTH);
    }

    public void buildCenter(UpdatePanel panel)
    {
        panel.setLayout(new GridLayout(1, 6, 0, 0));
        for (EnumTypes type : EnumTypes.values())
        {
            if (type != EnumTypes.VAL)
            {
                JButton button = new JButton(type.symbol);
                button.setActionCommand(type.symbol);
                button.addActionListener(this);
                panel.add(button);
            }
        }

        panel.setMaximumSize(new Dimension(1000, 100));
        panel.setPreferredSize(new Dimension(600, 100));
        panel.setMinimumSize(new Dimension(100, 100));
    }

    /**
     * Called each tick by the host of this GUI
     */
    public void update()
    {
        tick++;
        if (tick >= Long.MAX_VALUE)
        {
            tick = 0;
        }

        for (Component component : getComponents())
        {
            if (component instanceof IGUIUpdate)
            {
                ((IGUIUpdate) component).update();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Click: " + e);
        if (e.getSource() == solve_button)
        {
            prop = null;
            try
            {
                prop = new Proposition(statement_field.getText());
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            update();
            dataModel.fireTableStructureChanged();
            dataModel.fireTableDataChanged();
        }
        else if (EnumTypes.isSymbol(e.getActionCommand().charAt(0)))
        {
            statement_field.setText(statement_field.getText() + e.getActionCommand());
        }
    }
}
