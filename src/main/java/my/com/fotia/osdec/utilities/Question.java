package my.com.fotia.osdec.utilities;

import java.util.List;

import my.com.fotia.osdec.assesment.model.Assesment;
import my.com.fotia.osdec.assesment.model.AssesmentAnswer;

public class Question implements Comparable{
	
	private String id;
	private String question;
	private List<AssesmentAnswer> assesmentAnswer;
	private Assesment assesment;
	private int order;
	
	public Question(String id, String question, List<AssesmentAnswer> assesmentAnswer, Assesment assesment, int order) {
        this.id = id;
        this.question = question;
        this.assesmentAnswer = assesmentAnswer;
        this.assesment = assesment;
        this.order = order;
    }
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public List<AssesmentAnswer> getAssesmentAnswer() {
		return assesmentAnswer;
	}

	public void setAssesmentAnswer(List<AssesmentAnswer> assesmentAnswer) {
		this.assesmentAnswer = assesmentAnswer;
	}

	public Assesment getAssesment() {
		return assesment;
	}

	public void setAssesment(Assesment assesment) {
		this.assesment = assesment;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	/*public int compareTo(Question comparestu) {
		int compareage=((Question)comparestu).getOrder();
         For Ascending order
        return this.order-compareage;

         For Descending order do like this 
        //return compareage-this.order;
	}*/

	@Override
	public int compareTo(Object o) {
		int compareage=((Question)o).getOrder();
		return this.order-compareage;
	}

}
