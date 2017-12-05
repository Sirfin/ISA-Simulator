package isasim.gui;

import isasim.helper.BitSetHelper;
import isasim.physical.Register;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.LinkedList;


public class RegisterTableWindow extends JFrame {
    private JPanel TablePanel = new JPanel();
    private JTable RegisterTable ;
    private DefaultTableModel dtmRegister ;
    private MainWindow mainWindow ;
    public RegisterTableWindow(MainWindow mainWindow){
        super("RegTableWindow") ;
        this.mainWindow = mainWindow ;
        System.out.println("Got Called");
        this.setLayout(new BorderLayout());
        this.add(TablePanel,BorderLayout.CENTER) ;
        TablePanel.setBackground(Color.white);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;
        SetupTable();
        //Finally, set the size of the window, and pop it up
        this.setSize(1200, 400);
        //Set the background-color of the window.
        this.setBackground(Color.white);
        this.setVisible(true);
    }
    public void SetupTable(){
        TablePanel.setLayout(new GridLayout(1,0));
        String[] columnNames = {"Register address","Value"};

        this.RegisterTable = new JTable() ;
        this.dtmRegister = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.dtmRegister.setColumnIdentifiers(columnNames);
        this.RegisterTable.setModel(dtmRegister);
        java.util.List<Register> registers = mainWindow.processor.Registerbank ;
        for (int c = 0 ; c < registers.size() ; c++){
            Register r = registers.get(c) ;
            dtmRegister.addRow(new Object[]{"0x" + String.format("%05X",r.getAddress().m_Address & 0xFFFFF),"0x" + String.format("%05X",r.load() & 0xFFFFF)});
        }
        JScrollPane scrollPane = new JScrollPane(RegisterTable) ;
        this.TablePanel.add(scrollPane) ;
    }
    public void UpdateTable(){
        java.util.List<Register> registers = mainWindow.processor.Registerbank ;
        System.out.println(registers.size());
        for (int c = 0 ; c < registers.size() ; c++){
            Register r = registers.get(c) ;
            dtmRegister.setValueAt("0x"+String.format("%05X", r.load() & 0xFFFFF),c,1);
        }
    }
}
