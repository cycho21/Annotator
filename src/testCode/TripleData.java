package testCode;

public class TripleData {
	private String				subject;
	private String				predicate;
	private String				object;
	
	private float				constraintEnforcement;
	
	public TripleData(String s, String p, String o) {
		this.subject = s;
		this.predicate = p;
		this.object = o;
		this.constraintEnforcement = 0f;
	}
	public void setConstraintEnforcementValue(float value){
		this.constraintEnforcement = value;
	}
	public boolean hasCEValue(){
		if(constraintEnforcement!=0f){
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return subject+'\t'+predicate+'\t'+object+'\t'+constraintEnforcement;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TripleData){
			TripleData other = (TripleData)obj;
			if(this.subject.equals(other.subject)&&this.predicate.equals(other.predicate)&&this.object.equals(other.object)){
				if(this.constraintEnforcement == other.constraintEnforcement){
					return true;
				}
			}
		}
		return false;
	}
}
