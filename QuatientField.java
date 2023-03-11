import java.util.ArrayList;
import java.util.Arrays;

public class QuatientField<R extends EuclideanDomain> implements Field {
    public R num; // 分子(numerator)
    public R denom; // 分母(denominator)

    public QuatientField(R num, R denom) {
        EuclideanDomain gcd = EuclideanDomain.gcd(num, denom);
        this.num = (R)num.divide(gcd);
        this.denom = (R)denom.divide(gcd);
    }

    @Override
    public String toString() {
        int bracketLength = 3;

        if (this.denom.isMultiplyIdentity()) {
            return this.num.toString();
        } else {
            String stringNum;
            if (this.num.toString().length() > bracketLength) {
                stringNum = "(" + this.num.toString() + ")";
            } else {
                stringNum = this.num.toString();
            }

            String stringDenom;
            if (this.denom.toString().length() > bracketLength) {
                stringDenom = "(" + this.denom.toString() + ")";
            } else {
                stringDenom = this.denom.toString();
            }

            return stringNum + "/" + stringDenom;
        }
    }

    public boolean equals(QuatientField<R> a) {
        return this.num.multiply(a.denom).equals(this.denom.multiply(a.num));
    }

    @Override
    public QuatientField<R> multiply(Monoid a) {
        Monoid num = this.num.multiply(((QuatientField<R>)a).num);
        Monoid denom = this.denom.multiply(((QuatientField<R>)a).denom);
        return new QuatientField<R>((R)num, (R)denom);
    }

    @Override
    public boolean isMultiplyIdentity() {
        return this.num.equals(this.denom);
    }

    @Override
    public QuatientField<R> multiplyInverse() {
        return new QuatientField<R>(this.denom, this.num);
    }

    @Override
    public QuatientField<R> add(AddGroup a) {
        Monoid num1 = this.num.multiply(((QuatientField<R>)a).denom);
        Monoid num2 = this.denom.multiply(((QuatientField<R>)a).num);
        AddGroup num = ((AddGroup)num1).add((AddGroup)num2);
        Monoid denom = this.denom.multiply(((QuatientField<R>)a).denom);
        return new QuatientField<R>((R)num, (R)denom);
    }

    @Override
    public boolean isAddIdentity() {
        return this.num.isAddIdentity();
    }

    @Override
    public QuatientField<R> addInverse() {
        AddGroup num = this.num.addInverse();
        return new QuatientField<R>((R)num, this.denom);
    }

    @Override
    public QuatientField<R> remainder(EuclideanDomain a) {
        AddGroup num = this.num.add(this.num.addInverse());
        return new QuatientField<R>((R)num, this.denom);
    }
}