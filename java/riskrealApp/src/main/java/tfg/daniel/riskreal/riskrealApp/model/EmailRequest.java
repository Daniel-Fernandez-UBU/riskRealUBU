package tfg.daniel.riskreal.riskrealApp.model;

/**
 * Class EmailRequest.
 * 
 * Class with the information that have the emails.
 * 
 * @author Daniel Fernández Barrientos.
 * @version 1.0
 * 
 */
public class EmailRequest {
    
    /** The to. */
    private String to;
    
    /** The subject. */
    private String subject;
    
    /** The body. */
    private String body;

    /**
     * Gets the to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets the to.
     *
     * @param to the new to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the new body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
