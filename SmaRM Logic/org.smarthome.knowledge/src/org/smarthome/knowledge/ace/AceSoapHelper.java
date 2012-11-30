package org.smarthome.knowledge.ace;

import org.smarthome.shared.Constants;

public class AceSoapHelper {
	
	private static final String XML_DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	private static final String ENVELOPE_OPEN = "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">";
	private static final String ENVELOPE_CLOSE = "</env:Envelope>";
	
	private static final String BODY_OPEN = "<env:Body>";
	private static final String BODY_CLOSE= "</env:Body>";

	private static final String REPLY_OPEN = "<ar:Reply>";
	private static final String REPLY_CLOSE = "</ar:Reply>";
	
	private static final String ACERULES_ANSWERTEXT_OPEN = "<ar:Answertext>";
	private static final String ACERULES_ANSWERTEXT_CLOSE = "</ar:Answertext>";
	
	private static final String RACE_USEDAXIOMS_OPEN = "<race:UsedAxioms>";
	private static final String RACE_USEDAXIOMS_CLOSE = "</race:UsedAxioms>";
	
	private static final String RACE_AXIOM_OPEN = "<race:Axiom>";
	private static final String RACE_AXIOM_CLOSE = "</race:Axiom>";
	private static final String RACE_AXIOM_SUBSTITUTION = "Substitution:";
	
	public static String buildAceRulesSoapRequest(String program, int maxAnswers) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(XML_DECLARATION);
		stringBuffer.append(ENVELOPE_OPEN);
		stringBuffer.append(BODY_OPEN);
		stringBuffer.append("<ar:Request xmlns:ar=\"http://attempto.ifi.uzh.ch/acerules\">");
		
		stringBuffer.append("<ar:Program>");
		stringBuffer.append(program);
		stringBuffer.append("</ar:Program>");
			
		stringBuffer.append("<ar:MaxAnswers>");
		stringBuffer.append(String.valueOf(maxAnswers));
		stringBuffer.append("</ar:MaxAnswers>");
		
		stringBuffer.append("<ar:AnswertextOutput>on</ar:AnswertextOutput>");
		
		stringBuffer.append("</ar:Request>");
		stringBuffer.append(BODY_CLOSE);
		stringBuffer.append(ENVELOPE_CLOSE);
		return stringBuffer.toString();
	}
	
	public static String buildRaceSoapRequest(String axioms, String theorems, String mode) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(XML_DECLARATION);
		stringBuffer.append(ENVELOPE_OPEN);
		stringBuffer.append(BODY_OPEN);
		stringBuffer.append("<ar:Request xmlns:race=\"http://attempto.ifi.uzh.ch/race\">");
		
		stringBuffer.append("<race:Axioms>");
		stringBuffer.append(axioms);
		stringBuffer.append("</race:Axioms>");
		
		if (theorems != null) {
			stringBuffer.append("<race:Theorems>");
			stringBuffer.append(theorems);
			stringBuffer.append("</race:Theorems>");
		}
			
		stringBuffer.append("<race:Mode>");
		stringBuffer.append(mode);
		stringBuffer.append("</race:Mode>");
		
		stringBuffer.append("</ar:Request>");
		stringBuffer.append(BODY_CLOSE);
		stringBuffer.append(ENVELOPE_CLOSE);
		return stringBuffer.toString();
	}
	
	public static String getReplyText(String soapResponse) {
		int beginIndex = soapResponse.indexOf(REPLY_OPEN);
		int endIndex = soapResponse.indexOf(REPLY_CLOSE);
		
		String replyText = soapResponse;
		if (beginIndex != -1 && endIndex != -1) {
			beginIndex += REPLY_OPEN.length();
			replyText = soapResponse.substring(beginIndex, endIndex);
		}
		return replyText;
	}
	
	public static String getReplyText(String soapResponse, String opentag, String closetag) {
		StringBuffer stringBuffer = new StringBuffer();
		
		int beginIndex = 0, endIndex = 0;
		while ( ((beginIndex = soapResponse.indexOf(opentag, beginIndex)) > 0) && 
				((endIndex = soapResponse.indexOf(closetag, endIndex)) > 0) ) {
			beginIndex += opentag.length();

			stringBuffer.append(soapResponse.substring(beginIndex, endIndex));	
			stringBuffer.append(Constants.NEWLINE);
			endIndex += closetag.length();
		}
		
		return stringBuffer.toString();
	}
	
	public static String getAceRulesAnswerText(String soapResponse) {
		return getReplyText(soapResponse, ACERULES_ANSWERTEXT_OPEN, ACERULES_ANSWERTEXT_CLOSE);
	}

	public static String getRaceUsedAxioms(String soapResponse) {
		return getReplyText(soapResponse, RACE_USEDAXIOMS_OPEN, RACE_USEDAXIOMS_CLOSE);
	}
	
	public static String getRaceSubstitutions(String usedAxioms) {
		usedAxioms = getReplyText(usedAxioms, RACE_AXIOM_OPEN, RACE_AXIOM_CLOSE);
		
		String[] axioms = usedAxioms.split(Constants.NEWLINE);
		StringBuffer stringBuffer= new StringBuffer();
		for (String axiom : axioms) {
			axiom = axiom.trim();
			if (axiom.startsWith(RACE_AXIOM_SUBSTITUTION)) {
				axiom = axiom.substring(RACE_AXIOM_SUBSTITUTION.length()).trim();
				stringBuffer.append(axiom);
				stringBuffer.append(Constants.NEWLINE);
			}
		}

		return stringBuffer.toString();
	}
}
