package cz.brmlab.yodaqa.flow.dashboard;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

/** A stateful question.  This question has been asked, can be referred to
 * and may or may not have been answered already.
 *
 * Important: Multiple threads can look and poke at a Question at once.
 * Be careful, always deal with it only through synchronized methods. */
public class Question {
	protected int id;
	protected String text;
	protected QuestionSummary summary = null;
	protected List<AnswerSource> sources = new ArrayList<>();
	protected List<QuestionAnswer> answers = null;
	protected boolean finished = false;
	/* Generation counts for various fields above, incremented every
	 * time they are modified. */
	protected int gen_sources = 0;

	protected static Gson gson = new Gson();

	public Question(int id, String text) {
		this.id = id;
		this.text = text;
	}

	/** @return the id */
	public synchronized int getId() { return id; }
	/** @return the text */
	public synchronized String getText() { return text; }

	/** @return the summary */
	public synchronized QuestionSummary getSummary() { return summary; }
	/** @param summary the summary to set */
	public synchronized void setSummary(QuestionSummary summary) {
		this.summary = summary;
	}

	/** @return the sources */
	public synchronized List<AnswerSource> getSources() { return sources; }
	public synchronized void addSource(AnswerSource source) {
		this.sources.add(source);
		gen_sources ++;
	}

	/** @return the answer */
	public synchronized List<QuestionAnswer> getAnswers() { return answers; }
	/** @param answer the answer to set */
	public synchronized void setAnswers(List<QuestionAnswer> answers) {
		this.answers = answers;
		setFinished(true);
	}

	/** @return the finished */
	public synchronized boolean isFinished() { return finished; }
	/** @param finished the finished to set */
	protected synchronized void setFinished(boolean finished) {
		this.finished = finished;
	}

	public synchronized String toJson() {
		return gson.toJson(this);
	}
};
