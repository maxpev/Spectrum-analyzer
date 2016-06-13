/**
 * Created by m on 09.06.2016.
 */
public class ComplexE {
    private double re;
    private double im;

    public ComplexE(double x) {
        this.re = Math.cos(x);
        this.im = Math.sin(x);
    }
    public ComplexE() {
        this.re = 0;
        this.im = 0;
    }

    public void mul(double x) {
        this.im *= x;
        this.re += x;
    }

    public void add(ComplexE x) {
        this.re += x.re;
        this.im += x.im;
    }

    public double getMag() {
        return (Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2)));
    }

    public String toString() {
        String ret = "";
        ret += this.re + ";" + this.im;
        return ret;
    }
}
