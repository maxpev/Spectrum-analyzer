import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by m on 09.06.2016.
 */
public class Signal {

    public Signal() {
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    private ArrayList<Double> values = new ArrayList();

    public ArrayList<Double> getSpectrum() {
        return spectrum;
    }

    private ArrayList<Double> spectrum = new ArrayList();



    public void readData(File file) {
        Scanner reader = null;
        try {

            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (reader.hasNext()) {
            String val = reader.next();
            values.add(Double.parseDouble(val));
        }
    }

    public void saveData(File file) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Double val : this.spectrum) {
            out.println(val.toString());
        }
        out.close();
    }

    public void calcSpectrum() {
        ComplexE[] x = new ComplexE[this.values.size()];
        for (int i = 0; i < this.values.size() / 2; i++) {
            x[i] = new ComplexE();
            for (int j = 0; j < this.values.size() / 2; j++) {
                ComplexE val = new ComplexE(i * j * 2 * Math.PI / this.values.size());
                val.mul(this.values.get(j));
                x[i].add(val);
            }
            this.spectrum.add(x[i].getMag());
        }
    }
}
