package isasim.gui;

import isasim.main.Processor;
import isasim.physical.Memory;
import javafx.scene.paint.Stop;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class MainWindow extends JFrame {
    public Processor processor ;
    public JButton Start_B = new JButton("Start") ;
    public JButton Stop_B = new JButton("Stop") ;
    public JButton Register_B = new JButton("Show register") ;
    public JButton RAM_B = new JButton("Show RAM") ;
    public JButton ROM_B = new JButton("Show ROM") ;
    public JPanel ControlPanel = new JPanel() ;
    public JPanel PipelinePanel = new JPanel() ;
    public MemoryTableWindow RamWindow  ;
    public MemoryTableWindow RomWindow ;
    public RegisterTableWindow RegisterWindow ;
    private JTable PipeTable  ;
    private DefaultTableModel dtm ;
    /**
     * Initialize the JPanel holding all control-elements.
     */
    private void InitializeControlPanel(){

        ControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,12,12));
        ControlPanel.add(Start_B);
        ControlPanel.add(Stop_B);
        ControlPanel.add(Register_B) ;
        ControlPanel.add(RAM_B) ;
        ControlPanel.add(ROM_B) ;
        this.add(ControlPanel,BorderLayout.NORTH);
    }
    private void InitPipelineDrawing(){
        PipelinePanel.setLayout(new GridLayout(1,0));
        String[] columnNames = {"Pipeline stage","command"};
        Object[][] data = {
                {"FETCH", ""},
                {"DECODE", ""},
                {"LOAD", ""},
                {"EXECUTE", ""},
                {"WRITE", ""}
             };
        this.PipeTable = new JTable() ;
        this.dtm = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.dtm.setColumnIdentifiers(columnNames);
        this.PipeTable.setModel(dtm);
        for (int c = 0 ; c <= 4 ; c++){
            dtm.addRow(data[c]);
        }
        PipeTable.setRowHeight(61);
        PipeTable.setFont(new Font("Serif", Font.BOLD, 20)) ;
        this.PipelinePanel.add(PipeTable) ;
    }

    public void initListener(){
        Register_B.addActionListener(e -> {
                RegisterWindow = new RegisterTableWindow(this);
        });
        RAM_B.addActionListener(e -> {
            RamWindow = new MemoryTableWindow(this,processor.ram);
        });
        ROM_B.addActionListener(e -> {
            RomWindow  = new MemoryTableWindow(this,processor.rom);
        });

    }

    public MainWindow(){
        super("MainWindow") ;
        processor = new Processor(this) ;
        this.setLayout(new BorderLayout());
        this.add(PipelinePanel,BorderLayout.CENTER) ;
        InitializeControlPanel();
        PipelinePanel.setBackground(Color.white);
        InitPipelineDrawing();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE) ;
        //Finally, set the size of the window, and pop it up
        this.setSize(1200, 400);
        //Set the background-color of the window.
        this.setBackground(Color.white);
        this.setVisible(true);
        initListener() ;
        this.UpdatePipeline();
    }
    int a = 1 ;
    public void UpdatePipeline(){
        System.out.println("Updated table");
        a++ ;
        if (processor != null){
            this.dtm.setValueAt(processor.getFetch().GetStringFormatOfPipelineStage(),0,1);
            this.dtm.setValueAt(processor.getDecode().GetStringFormatOfPipelineStage(),1,1);
            this.dtm.setValueAt(processor.getLoad().GetStringFormatOfPipelineStage(),2,1);
            this.dtm.setValueAt(processor.getExec().GetStringFormatOfPipelineStage(),3,1);
            this.dtm.setValueAt(processor.getMWB().GetStringFormatOfPipelineStage(),4,1);
        }

        if (this.RomWindow != null) this.RomWindow.UpdateTable();
        if (this.RamWindow != null) this.RamWindow.UpdateTable();
        if (this.RegisterWindow != null) this.RegisterWindow.UpdateTable();
        this.PipeTable.setModel(dtm);

    }
}
