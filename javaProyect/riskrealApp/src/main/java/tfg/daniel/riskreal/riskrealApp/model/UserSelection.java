package tfg.daniel.riskreal.riskrealApp.model;

import java.util.HashMap;

public class UserSelection {
	
	private HashMap<Integer,String> answers;
	private String username;
 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<Integer,String> getAnswer() {
		return answers;
	}
	public void setAnswer(Integer id, String value) {
		this.answers.put(id, value);
	}
	
	public String getAnswerText(int id) {
		return this.answers.get(id);
	}
	
}
