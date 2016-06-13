import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;


/**
 * Created by m on 09.06.2016.
 */
public class MainWindow extends JFrame {

    private XYSeries origSeries = new XYSeries("Signal");
    private XYSeries spectrSeries = new XYSeries("Spectrum");

    public MainWindow() {

        this.setLayout(new BorderLayout());

        JPanel chartPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        JButton addButton = new JButton("Add signal");
        JButton calcButton = new JButton("Calc spectrum");
        JButton exportButton = new JButton("Export");

        buttonsPanel.setBackground(new Color(255,255,255));

        buttonsPanel.add(addButton);
        buttonsPanel.add(calcButton);
        buttonsPanel.add(exportButton);

        chartPanel.setLayout(new GridLayout(2,0));

        //original signal graph
        XYSeriesCollection origDataset = new XYSeriesCollection(this.origSeries);
        JFreeChart origSignal = ChartFactory.createXYLineChart("Signal", "time", "ampl", origDataset);

        //Spectrum
        XYSeriesCollection spectrDataset = new XYSeriesCollection(this.spectrSeries);
        JFreeChart spectrSignal = ChartFactory.createXYLineChart("Spectrum", "freq", "ampl", spectrDataset);


        calcButton.setEnabled(false);
        exportButton.setEnabled(false);

        chartPanel.add(new ChartPanel(origSignal));
        chartPanel.add(new ChartPanel(spectrSignal));


        this.add(chartPanel, BorderLayout.CENTER);
        this.add(buttonsPanel, BorderLayout.PAGE_END);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(700, 500);
        this.setVisible(true);

        Signal signal = new Signal();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showOpenDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File openFile = chooser.getSelectedFile();
                    signal.readData(openFile);
                    updateSignalGraph(signal.getValues());
                    calcButton.setEnabled(true);
                }
            }
        });

        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signal.calcSpectrum();
                updateSpectrumGraph(signal.getSpectrum());
                exportButton.setEnabled(true);
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int res = chooser.showSaveDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File saveFile = chooser.getSelectedFile();
                    signal.saveData(saveFile);
                }
            }
        });
    }

    public void updateSignalGraph(ArrayList<Double> vals) {
        for (int i = 0; i < vals.size(); i++) {
            this.origSeries.add(i, vals.get(i));
        }
    }
    public void updateSpectrumGraph(ArrayList<Double> vals) {
        for (int i = 0; i < vals.size(); i++) {
            this.spectrSeries.add(i, vals.get(i));
        }
    }
}
