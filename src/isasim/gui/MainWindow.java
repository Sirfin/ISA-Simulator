package isasim.gui;

import isasim.main.Processor;
import isasim.physical.Memory;
import isasim.physical.RomLoader;
import javafx.scene.paint.Stop;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    public Processor processor ;
    public JButton Register_B = new JButton("Show register") ;
    public JButton RAM_B = new JButton("Show RAM") ;
    public JButton ROM_B = new JButton("Show ROM") ;
    public JButton START_B = new JButton("Start") ;
    public JButton STOP_B = new JButton("Stop") ;
    public JButton RESET_B = new JButton("Reset") ;
    public JPanel ControlPanel = new JPanel() ;
    public JPanel PipelinePanel = new JPanel() ;
    public JLabel frequency_label = new JLabel("") ;
    public JLabel running_label = new JLabel("Stopped") ;
    public MemoryTableWindow RamWindow  ;
    public MemoryTableWindow RomWindow ;
    public RegisterTableWindow RegisterWindow ;
    public JSlider frequency_slider ;
    public JFileChooser Rom_Chooser = new JFileChooser() ;
    public JButton file_Chooser = new JButton("Load File") ;
    private JTable PipeTable  ;
    private DefaultTableModel dtm ;
    /**
     * Initialize the JPanel holding all control-elements.
     */
    private void InitializeControlPanel(){

        ControlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT,12,12));
        ControlPanel.add(Register_B) ;
        ControlPanel.add(RAM_B) ;
        ControlPanel.add(ROM_B) ;
        ControlPanel.add(START_B) ;
        ControlPanel.add(STOP_B) ;
        ControlPanel.add(RESET_B);
        ControlPanel.add(frequency_label) ;
        ControlPanel.add(running_label);
        ControlPanel.add(file_Chooser) ;
        frequency_slider = new JSlider(JSlider.HORIZONTAL,0,3000,3000) ;
        frequency_slider.setPaintTicks(true);
        frequency_slider.setMajorTickSpacing( 10 );
        frequency_slider.setMinorTickSpacing( 2 );
        frequency_slider.setPaintTrack( false );
        frequency_slider.addChangeListener( new ChangeListener() {
            @Override public void stateChanged( ChangeEvent e ) {
                processor.setFrequency(((JSlider) e.getSource()).getValue());
                frequency_label.setText("Frequency: " +String.format("%.5f",1f/ ((((JSlider) e.getSource()).getValue())/1000f)) + " hz");
            }
        } );

        ControlPanel.add(frequency_slider) ;
        frequency_label.setText("Frequency: " +String.format("%.5f",(1f/ (((frequency_slider.getValue())/1000f)))) + " hz");
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
            RamWindow = new MemoryTableWindow(this,processor.ram,"RamWindow");
            this.UpdatePipeline();
        });
        ROM_B.addActionListener(e -> {
            RomWindow  = new MemoryTableWindow(this,processor.rom,"RomWindow");
            this.UpdatePipeline();
        });
        START_B.addActionListener(e -> {
            processor.Continue();
        });
        STOP_B.addActionListener(e -> {
            processor.Halt();
        });
        RESET_B.addActionListener(e -> {
            processor.Reset();
        });
        file_Chooser.addActionListener(e -> {
            int returnVal = Rom_Chooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = Rom_Chooser.getSelectedFile();
                RomLoader.FileInRom(file.getAbsolutePath(),processor,0);
                this.UpdatePipeline();

            } else {
                System.out.println("Open command cancelled by user." );
            }
        });

    }

    public MainWindow() {
        super("MainWindow");
        processor = new Processor(this);
        this.setLayout(new BorderLayout());
        this.add(PipelinePanel, BorderLayout.CENTER);
        InitializeControlPanel();
        PipelinePanel.setBackground(Color.white);
        InitPipelineDrawing();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Finally, set the size of the window, and pop it up
        this.setSize(1200, 400);
        //Set the background-color of the window.
        this.setBackground(Color.white);
        this.setVisible(true);
        initListener();
        this.UpdatePipeline();
    }
    int a = 1 ;
    public void UpdatePipeline(){
        System.out.println("Updated table");
        a++ ;
        if (processor != null){
            this.dtm.setValueAt(processor.getFetch().GetStringFormatOfPipelineStage(),0,1);
            this.dtm.setValueAt(processor.getDecode().GetStringFormatOfPipelineStage(),1,1);
            this.dtm.setValueAt(processor.getExec().GetStringFormatOfPipelineStage(),3,1);
            this.dtm.setValueAt(processor.getMWB().GetStringFormatOfPipelineStage(),4,1);
        }

        if (this.RomWindow != null) this.RomWindow.UpdateTable(MemoryTableWindow.Format.Command,processor);
        if (this.RamWindow != null) this.RamWindow.UpdateTable(MemoryTableWindow.Format.Decimal,processor);
        if (this.RegisterWindow != null) this.RegisterWindow.UpdateTable();
        this.PipeTable.setModel(dtm);
        this.running_label.setText(processor.Running()?"Halted":"Running");
    }
}
