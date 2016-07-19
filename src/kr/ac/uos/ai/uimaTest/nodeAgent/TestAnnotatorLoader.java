package kr.ac.uos.ai.uimaTest.nodeAgent;

public class TestAnnotatorLoader extends AnnotatorLoader {
	public TestAnnotatorLoader() {
		System.out.println("OK!");
	}
	@Override
	public String getName() {
		return this.getClass().getCanonicalName();
	}

}
