package tfg.daniel.riskreal.riskrealApp.model;

import java.util.HashMap;

/**
 * Class UserSelection.
 * 
 * Class that store the session information of the user with the current answers selected in the quiz.
 * 
 * @author Daniel Fernández Barrientos
 * @version 1.0
 * 
 */
public class UserSelection {
	
	/** Class attribute answers. */
	private HashMap<Integer,String> answers;
	
	/** Class attribute answersValues. */
	private HashMap<Integer,Integer> answersValues;
	
	/**
	 * Class constructor.
	 */
    public UserSelection() {
        this.answers = new HashMap<>();
        this.answersValues = new HashMap<>();
    }

	/**
	 * Getter answers.
	 * @return
	 */
	public HashMap<Integer,String> getAnswers() {
		return answers;
	}
	
    /**
     * Setter answer.
     * @param id
     * @param value
     */
	public void setAnswer(Integer id, String value) {
		this.answers.put(id, value);
	}
	
	/**
	 * Getter answersValues. 
	 * @return
	 */
	public HashMap<Integer, Integer> getAnswersValues() {
		return answersValues;
	}
	
	/**
	 * Setter answersValues.
	 * @param id
	 * @param value
	 */

	public void setAnswerValue(Integer id, Integer value) {
		this.answersValues.put(id, value);
	}

}
