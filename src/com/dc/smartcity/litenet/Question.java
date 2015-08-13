package com.dc.smartcity.litenet;

public class Question {
	private String question_id;
	private String answer;
	private String type;
	public String getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(String questionId) {
		question_id = questionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
