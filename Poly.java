import java.util.ArrayList;
import java.util.Arrays;

public class Poly<K extends Field> extends ArrayList<K> implements EuclideanDomain{
    public String var;

    public Poly(String var) {
        this.var = var;
    }

    public Poly(ArrayList<K> coef, String var) {
        this.addAll(coef);
        this.var = var;
    }

    @Override
    public String toString() {
        String string = "";
        String preString = string;

        for (int i = 0; i < this.deg() + 1; i++) {
            if (!string.equals(preString)) { //一周前と変わっていれば"+"を付ける
                string += " + ";
                preString = string;
            }

            if (i == 0 && !this.get(i).isAddIdentity()) {
                string += this.get(i);
            }else if (this.get(i).isMultiplyIdentity()) {
                string += this.var + "^" + i;
            } else if (!this.get(i).isAddIdentity()) {
                string += this.get(i).toString() + " " + this.var + "^" + i;
            }
        }

        return string;
    }

    public int deg() {
        if (this.get(this.size()-1).isAddIdentity()) {
            if (this.size() == 1) {
                return 0;
            } else {
                this.remove(this.size()-1);
                return this.deg();
            }
        } else {
            return this.size() - 1;
        }
    }

    public K lc() { //Leading Coefficient 先頭項係数
        return this.get(this.deg());
    }

    public Poly<K> monic() {
        for (int i = 0; i <= this.deg(); i++) {
            this.set(i, (K)this.get(i).divide(this.lc()));
        }
        return this;
    }
    
    public boolean equals(Poly<K> a) {
        if (this.deg() == a.deg()) {
            return this.equals(a) && this.var.equals(((Poly<K>)a).var);
        } else if (this.deg() > a.deg()) {
            return a.equals(this);
        } else if (a.get(a.deg()).isAddIdentity()) {
            a.remove(a.deg());
            return this.equals(a);
        } else {
            return false;
        }
    }

    @Override
    public Poly<K> multiply(Monoid a) {
        Poly<K> result = new Poly<K>(this.var);
        for (int i = 0; i <= this.deg() + ((Poly<K>)a).deg(); i++) {
            int k = Math.max(0, i -((Poly<K>)a).deg());
            K coefDegI = (K)this.get(k).multiply(((Poly<K>)a).get(i-k));
            
            for (int j = Math.max(0, i - ((Poly)a).deg()) + 1; j <= Math.min(i, this.deg()); j++) {
                coefDegI = (K)coefDegI.add((AddGroup)this.get(j).multiply(((Poly<K>)a).get(i-j)));
            }

            result.add(coefDegI);
        }
        return result;
    }

    @Override
    public boolean isMultiplyIdentity() {
        boolean result = true;
        if (!this.get(0).isMultiplyIdentity()) {
            result = false;
        }
        for (int i = 1; i <+ this.deg(); i++) {
            if (!this.get(i).isAddIdentity()) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Poly<K> add(AddGroup a) {
        Poly<K> result = new Poly<>(this.var);

        for (int i = 0; i <= Math.max(this.deg(), ((Poly<K>)a).deg()); i++) {
            if (i < this.deg() + 1 && i < ((Poly<K>)a).deg() + 1) {
                result.add((K)this.get(i).add(((Poly<K>)a).get(i)));
            } else if (i >= this.deg() + 1) {
                result.add(((Poly<K>)a).get(i));
            } else if (i >= ((Poly<K>)a).deg() + 1) {
                result.add(this.get(i));
            }
        }
        return result;
    }

    @Override
    public boolean isAddIdentity() {
        boolean result = true;
        for (int i = 0; i < this.size(); i++) {
            result = result && this.get(i).isAddIdentity();
        }
        return result;
    }

    @Override
    public Poly<K> addInverse() {
        Poly<K> result = new Poly<>(this.var);
        for (int i = 0; i < this.deg() + 1; i++) {
            result.add((K)this.get(i).addInverse());
        }
        return result;
    }

    private ArrayList<Poly<K>> divisionAlgorithm(Poly<K> a) {
        ArrayList<Poly<K>> result = new ArrayList<>();
        Poly<K> quatient = (Poly<K>)this.subtract(this);
        Poly<K> remainder = (Poly<K>) this.clone();

        while (!remainder.isAddIdentity() && remainder.deg() >= a.deg()) {
            quatient.set(remainder.deg()-a.deg(), (K)remainder.lc().divide(a.lc()));
            for (int i = remainder.deg()-a.deg(); i <= remainder.deg(); i++) {
                AddGroup minuend = remainder.get(i);
                AddGroup subtrahend = (AddGroup)a.get(i-remainder.deg()+a.deg()).multiply(quatient.get(remainder.deg()-a.deg()));
                remainder.set(i, (K)minuend.subtract(subtrahend));
            }
        }

        result.add(quatient);
        result.add(remainder);
        return result;
    }

    @Override
    public Poly<K> divide(EuclideanDomain a) {
        return this.divisionAlgorithm((Poly<K>)a).get(0);
    }

    @Override
    public Poly<K> remainder(EuclideanDomain a) {
        return this.divisionAlgorithm((Poly<K>)a).get(1);
    }
}
