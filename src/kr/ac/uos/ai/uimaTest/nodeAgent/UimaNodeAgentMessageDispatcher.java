package kr.ac.uos.ai.uimaTest.nodeAgent;

import javax.jms.Message;

public class UimaNodeAgentMessageDispatcher {
	private UimaNodeAgent				agent;
	public UimaNodeAgentMessageDispatcher(UimaNodeAgent agent) {
		this.agent = agent;
	}
	public void dispatch(Message message) {
		UimaPlusAgentMessageFactory factory = UimaPlusAgentMessageFactory.getInstance();
		UimaPlusMessage uimaMessage = factory.castUimaPlusAgentMessage(message);
		UimaPlusMessageType type = uimaMessage.getType();
		switch (type) {
		case AnnotatorMessage :
			AnnotatorMessage annoMessage = (AnnotatorMessage)uimaMessage;
			agent.annotatorMessageRecieved(annoMessage);
		case ExcuteMessage :
		case ProfileReportMessage :
		case StateConfirmationMessage :
		case TaskResultMessage :
		default:
			break;
		}
		
	}

}
