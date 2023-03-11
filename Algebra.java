interface Monoid { // モノイドの定義
    Monoid multiply(Monoid a); // 乗法を定義する
    boolean isMultiplyIdentity(); // 乗法単元だったらtrueを返す
}

interface Group extends Monoid { // 群を定義する
    Group multiplyInverse(); // 乗法逆元を定義する
    default Monoid divide(Group a) { // 除法を乗法の逆演算として定義する
        return multiply(a.multiplyInverse());
    }
}

interface AddGroup { // 加法群を定義する
    AddGroup add(AddGroup a); // 加算を定義する
    boolean isAddIdentity(); // 加法単元だったらtureを返す
    AddGroup addInverse(); // 加法逆元を定義する
    default AddGroup subtract(AddGroup a) { // 減法を加算の逆演算として定義する
        return add(a.addInverse());
    }
}

interface Ring extends Monoid, AddGroup { //環を定義する

}

interface EuclideanDomain extends Ring { //ユークリッド整域を定義する
    EuclideanDomain divide(EuclideanDomain a); //除算
    EuclideanDomain remainder(EuclideanDomain a); //剰余

    //GCDを求める
    public static EuclideanDomain gcd(EuclideanDomain a, EuclideanDomain b) {
        if (b .isAddIdentity()) {
            return a;
        } else {
            return gcd(b, a.remainder(b));
        }
    }
}

interface Field extends EuclideanDomain { //体
    Field multiplyInverse(); //乗法逆元

    @Override
    default EuclideanDomain divide(EuclideanDomain a) { //除算
        return (EuclideanDomain)multiply(((Field)a).multiplyInverse());
    }
}