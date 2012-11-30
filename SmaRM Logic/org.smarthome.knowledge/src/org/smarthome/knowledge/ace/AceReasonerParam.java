package org.smarthome.knowledge.ace;


public class AceReasonerParam {

	/**
	 * The mode of the reasoner. Using check_consistency, the reasoner checks whether the axioms are consistent. 
	 * In the mode prove, the reasoner tries to prove the theorems on the basis of the axioms. In the mode 
	 * answer_query finally, the reasoner tries to answer the query stated by Theorems on the basis of the axioms. 
	 * Note that RACE uses internal auxiliary axioms for its operation.	 *
	 */
	public enum Mode {
		CHECK_CONSISTENCY, PROVE, ANSWER_QUERY;

		public String param() {
			return name().toLowerCase();
		}
	}

}
