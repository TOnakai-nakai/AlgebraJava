public class Integer implements EuclideanDomain {
    private int value;

    public Integer(int n) {
        this.value = n;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Integer) {
            Integer n = (Integer) obj;
            return this.value == n.value;
        } else {
            return false;
        }
    }

    @Override
    public Monoid multiply(Monoid a) {
        return new Integer(this.value * ((Integer) a).value);
    }

    @Override
    public boolean isMultiplyIdentity() {
        return equals(new Integer(1));
    }

    @Override
    public AddGroup add(AddGroup a) {
        return new Integer(this.value + ((Integer) a).value);
    }

    @Override
    public boolean isAddIdentity() {
        return equals(new Integer(0));
    }

    @Override
    public AddGroup addInverse() {
        return new Integer(-this.value);
    }

    @Override
    public EuclideanDomain divide(EuclideanDomain a) {
        return new Integer(this.value / ((Integer) a).value);
    }

    @Override
    public EuclideanDomain remainder(EuclideanDomain a) {
        return new Integer(this.value % ((Integer) a).value);
    }
}
