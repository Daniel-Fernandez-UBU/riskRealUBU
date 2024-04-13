package tfg.daniel.riskreal.riskrealApp.model;

import java.util.HashMap;

public class UserSelection {
	
	
    public UserSelection() {
        this.answers = new HashMap<>();
    }
	
	private HashMap<Integer,String> answers;

	public HashMap<Integer,String> getAnswers() {
		return answers;
	}
	public void setAnswer(Integer id, String value) {
		this.answers.put(id, value);
	}
	
	public String getAnswer(int id) {
		return this.answers.get(id);
	}
		
}
