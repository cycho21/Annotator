package kr.ac.uos.ai.uimaTest.nodeAgent;

public abstract class UimaPlusMessage {
	private final UimaPlusMessageType					type;
	
	public UimaPlusMessage(UimaPlusMessageType messageType) {
		this.type = messageType;
	}

	public UimaPlusMessageType getType() {
		return type;
	}
	
	
}
