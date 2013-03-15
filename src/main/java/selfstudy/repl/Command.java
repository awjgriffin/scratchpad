package selfstudy.repl;

public interface Command {

	void execute();
	
	enum Type {
		
		STRING(true), LIST(false), NUMBER(true), EXPRESSION(false);
		
		boolean selfEvaluating;

		private Type(boolean selfEvaluating) {
			this.selfEvaluating = selfEvaluating;
		}

		public boolean isSelfEvaluating() {
			return selfEvaluating;
		}
		
	}

}
