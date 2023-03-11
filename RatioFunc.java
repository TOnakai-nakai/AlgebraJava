public class RatioFunc<R extends EuclideanDomain> extends QuatientField<Poly<QuatientField<R>>>{
    
    public RatioFunc(Poly<QuatientField<R>> num, Poly<QuatientField<R>> denom) {
        super(num, denom);
    }
}
