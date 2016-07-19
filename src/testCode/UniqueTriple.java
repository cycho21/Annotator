package testCode;

public class UniqueTriple {
	private TripleData 				triple;
	private int					count;
	
	public UniqueTriple(TripleData triple) {
		this.triple = triple;
		count = 1;
	}
	
	public void addCount(){
		count++;
	}
	public boolean isDuplicated(TripleData t){
		return this.triple.equals(t);
	}
	@Override
	public String toString() {
		
		return triple.toString()+'\t'+count;
	}

	public boolean hasCEValue() {
		
		return triple.hasCEValue();
	}
}
