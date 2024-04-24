package tfg.daniel.riskreal.riskrealApp.model;

import java.util.HashMap;

public class UserSelection {
	
	private HashMap<Integer,String> answers;
	private HashMap<Integer,Integer> answersValues;
	
    public UserSelection() {
        this.answers = new HashMap<>();
        this.answersValues = new HashMap<>();
    }


	public void setAnswer(Integer id, String value) {
		this.answers.put(id, value);
	}
	
	public HashMap<Integer, Integer> getAnswersValues() {
		return answersValues;
	}
	public void setAnswersValues(HashMap<Integer, Integer> answersValues) {
		this.answersValues = answersValues;
	}
	
	public void setAnswerValue(Integer id, Integer value) {
		this.answersValues.put(id, value);
	}

	public HashMap<Integer,String> getAnswers() {
		return answers;
	}
		
}
