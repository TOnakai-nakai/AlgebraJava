import java.util.ArrayList;
import java.util.Arrays;

public class Calc {
    public static void main(String[] args) {
        //整数環
        Integer n1 = new Integer(7);
        Integer n2 = new Integer(2);

        System.out.println("n1 = " + n1);
        System.out.println("n2 = " + n2);
        System.out.println("n1 + n2 = " + n1.add(n2));
        System.out.println("n1 - n2 = " + n1.subtract(n2));
        System.out.println("n1 * n2 = " + n1.multiply(n2));
        System.out.println("n1 / n2 = " + n1.divide(n2));
        System.out.println("n1 % n2 = " + n1.remainder(n2));

        //有理数体
        QuatientField<Integer> r1 = new QuatientField<>(new Integer(5), new Integer(6));
        QuatientField<Integer> r2 = new QuatientField<>(new Integer(3), new Integer(10));

        System.out.println("r1 = " + r1);
        System.out.println("r2 = " + r2);
        System.out.println("r1 + r2 = " + r1.add(r2));
        System.out.println("r1 - r2 = " + r1.subtract(r2));
        System.out.println("r1 * r2 = " + r1.multiply(r2));
        System.out.println("r1 / r2 = " + r1.divide(r2));

        //多項式環
        QuatientField<Integer> r3 = new QuatientField<>(new Integer(0), new Integer(1));
        QuatientField<Integer> r4 = new QuatientField<>(new Integer(-1), new Integer(1));
        QuatientField<Integer> r5 = new QuatientField<>(new Integer(1), new Integer(1));

        Poly<QuatientField<Integer>> p1 = new Poly<>(new ArrayList<>(Arrays.asList(r5, r3, r5)), "x");
        Poly<QuatientField<Integer>> p2 = new Poly<>(new ArrayList<>(Arrays.asList(r5, r4)), "x");

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p1 + p2 = " + p1.add(p2));
        System.out.println("p1 - p2 = " + p1.subtract(p2));
        System.out.println("p1 * p2 = " + p1.multiply(p2));
        System.out.println("p1 / p2 = " + p1.divide(p2));
        System.out.println("p1 % p2 = " + p1.remainder(p2));

        //有理関数
        Poly<QuatientField<Integer>> p3 = new Poly<>(new ArrayList<>(Arrays.asList(r5, r5)), "x");
        Poly<QuatientField<Integer>> p4 = p2.multiply(p3);
        Poly<QuatientField<Integer>> p5 = p1.multiply(p3);

        System.out.println("p3 = " + p3);
        System.out.println("p4 = " + p4);
        System.out.println("p5 = " + p5);

        RatioFunc<Integer> rf0 = new RatioFunc<>(p4, p5);
        RatioFunc<Integer> rf1 = new RatioFunc<>(p2, p5);
        RatioFunc<Integer> rf2 = new RatioFunc<>(p1, p4);

        System.out.println("rf0 = " + rf0);
        System.out.println("rf1 = " + rf1);
        System.out.println("rf2 = " + rf2);

        System.out.println("rf1 + rf2 = " + rf1.add(rf2));
        System.out.println("rf1 - rf2 = " + rf1.subtract(rf2));
        System.out.println("rf1 * rf2 = " + rf1.multiply(rf2));
        System.out.println("rf1 / rf2 = " + rf1.divide(rf2));
    }
}
