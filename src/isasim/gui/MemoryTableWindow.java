package isasim.gui;

import isasim.commands.Command;
import isasim.commands.CommandDecoder;
import isasim.commands.icommands.ICommand;
import isasim.commands.jcommands.JCommand;
import isasim.commands.rcommands.MoveCommand;
import isasim.commands.rcommands.RCommand;
import isasim.helper.BitSetHelper;
import isasim.main.Processor;
import isasim.physical.Memory;
import isasim.physical.Register;
import isasim.physical.RegisterAddress;
import isasim.pipeline.Decode;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;

public class MemoryTableWindow extends JFrame {

    public enum Format{
        Hex,
        Binary,
        Command,
    }


    private JPanel TablePanel = new JPanel();
    private JTable MemoryTable;
    private DefaultTableModel dtmMemory;
    private MainWindow mainWindow ;
    private Memory memory ;
    public MemoryTableWindow(MainWindow mainWindow, Memory memory,String name){
        super(name) ;
        this.mainWindow = mainWindow ;
        this.memory = memory ;
        System.out.println("Got Called");
        this.setLayout(new BorderLayout());
        this.add(TablePanel,BorderLayout.CENTER) ;
        TablePanel.setBackground(Color.white);
        //this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;
        SetupTable();
        //Finally, set the size of the window, and pop it up
        this.setSize(600, 800);
        //Set the background-color of the window.
        this.setBackground(Color.white);
        this.setVisible(true);
    }
    public void SetupTable(){
        TablePanel.setLayout(new GridLayout(1,0));
        String[] columnNames = {"Memory address","Value"};

        this.MemoryTable = new JTable() ;
        this.dtmMemory = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.dtmMemory.setColumnIdentifiers(columnNames);
        this.MemoryTable.setModel(dtmMemory);
        ArrayList<Integer> MemoryValues = memory.getMemory() ;
        for (int c = 0 ; c < MemoryValues.size() ; c++){
            int r = MemoryValues.get(c) ;
            dtmMemory.addRow(new Object[]{"0x" + String.format("%05X",c), "0x"+String.format("%05X", r & 0xFFFFF)});
        }
        JScrollPane scrollPane = new JScrollPane(MemoryTable) ;
        this.TablePanel.add(scrollPane) ;
    }

    public void UpdateTable(Format format,Processor main){

        ArrayList<Integer> MemoryValues = memory.getMemory() ;

        for (int c = 0 ; c < MemoryValues.size() ; c++){
            int r = MemoryValues.get(c) ;
            System.out.println(format.toString()) ;
            switch (format){
                case Hex:
                    dtmMemory.setValueAt("0x"+String.format("%05X",r & 0xFFFFF),c,1);
                    break;
                case Binary:
                    dtmMemory.setValueAt(Integer.toBinaryString(r),c,1);
                    break;
                case Command:
                    Command command = CommandDecoder.decodeCommand(r,main) ;
                    if (command != null) {
                        dtmMemory.setValueAt(command.getName() , c, 1);
                    }else{
                        dtmMemory.setValueAt("NOOP", c, 1);
                    }
                    break;

            }

        }
    }
}
