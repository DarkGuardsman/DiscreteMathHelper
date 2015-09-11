package com.builtbroken.discretemath.gui;

import com.builtbroken.discretemath.Main;
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
        setMinimumSize(new Dimension(500, 300));
        setTitle("Discrete Math Helper v" + Main.VERSION);
        setName("Discrete Math Helper");
        buildGUI();
    }

    protected void buildGUI()
    {
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        UpdatePanel topPanel = new UpdatePanel();
        UpdatePanel centerPanel = new UpdatePanel();
        tablePanel = new UpdatePanel();

        topPanel.setBorder(loweredetched);
        centerPanel.setBorder(loweredetched);
        tablePanel.setBorder(loweredetched);

        buildTop(topPanel);
        buildTable(tablePanel);
        buildCenter(centerPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        add(mainPanel);

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
        UpdatePanel panel2 = new UpdatePanel();
        panel2.setLayout(new GridLayout(1, 6, 0, 0));
        for (EnumTypes type : EnumTypes.values())
        {
            if (type != EnumTypes.VAL)
            {
                JButton button = new JButton(type.symbol);
                button.setActionCommand(type.symbol);
                button.addActionListener(this);
                button.setMaximumSize(new Dimension(100, 100));
                button.setPreferredSize(new Dimension(50, 50));
                button.setMinimumSize(new Dimension(30, 30));
                panel2.add(button);

            }
        }
        panel.setMinimumSize(new Dimension(600, 100));
        panel.add(panel2);
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
        if (e.getSource() == solve_button)
        {
            prop = null;
            try
            {
                prop = new Proposition(statement_field.getText());
            } catch (RuntimeException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Parsing Error", JOptionPane.ERROR_MESSAGE);
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
            int caret = statement_field.getCaretPosition();
            statement_field.setText(statement_field.getText().substring(0, caret) + e.getActionCommand() + statement_field.getText().substring(caret, statement_field.getText().length()));
            statement_field.requestFocus();
            statement_field.setCaretPosition(caret + 1);
        }
    }
}
