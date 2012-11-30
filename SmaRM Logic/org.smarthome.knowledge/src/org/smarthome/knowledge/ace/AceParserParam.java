package org.smarthome.knowledge.ace;

/**
 * 
 * This class contains all parameters definitions for the APE web service.
 * 
 * Note: all flags are off by default.
 * 
 */
public class AceParserParam {

	/**
	 * <table>
	 * <thead>
	 * <tr>
	 * <td>Parameter</td>
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * </thead>
	 * 
	 * <tbody>
	 * <tr>
	 * <td>text</td>
	 * <td><em>string</em></td>
	 * <td>ACE text</td>
	 * </tr>
	 * <tr>
	 * <td>file</td>
	 * <td><em>URL</em></td>
	 * <td>URL of an ACE text</td>
	 * </tr>
	 * <tr>
	 * <td>ulextext</td>
	 * <td><em>string</em></td>
	 * <td>User lexicon</td>
	 * </tr>
	 * <tr>
	 * <td>ulexfile</td>
	 * <td><em>URL</em></td>
	 * <td>URL of a user lexicon</td>
	 * </tr>
	 * <tr>
	 * <td>uri</td>
	 * <td><em>URI</em></td>
	 * <td>Ontology URI (only used in the OWL outputs).</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public enum Input {
		TEXT, FILE, ULEXTEXT, ULEXFILE, URI;

		public String param() {
			return name().toLowerCase();
		}
	}

	/**
	 * <table>
	 * <thead>
	 * <tr>
	 * <td>Parameter</td>
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * </thead>
	 * 
	 * <tbody>
	 * <tr>
	 * <td>ulexreload</td>
	 * <td>on | off</td>
	 * <td>Reload the user lexicon. This has effect only if <em>ulexfile</em> is specified.</td>
	 * </tr>
	 * <tr>
	 * <td>noclex</td>
	 * <td>on | off</td>
	 * <td>Ignore the lexicon entries that are built into into the webservice (i.e. rely only on the user lexicon and/or
	 * guessing).</td>
	 * </tr>
	 * <tr>
	 * <td>guess</td>
	 * <td>on | off</td>
	 * <td>Guess the word-class (common noun, verb, adjective, adverb) of unknown words. I.e. a text containing unknown
	 * words is not rejected, instead, the parser tries to figure out the word-class of such words automatically. Note
	 * that the parser does not try to lemmatize such words, e.g. unknown plural nouns would be stored in the DRS in the
	 * plural form, not in their lemma (i.e. singular) form.</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public enum OnOffFlag {
		ULEXRELOAD, NOCLEX, GUESS;

		public String param() {
			return name().toLowerCase();
		}
	}

	/**
	 * <table>
	 * <thead>
	 * <tr>
	 * <td>Parameter</td>
	 * <td>Value</td>
	 * <td>Description</td>
	 * </tr>
	 * </thead>
	 * 
	 * <tbody>
	 * <tr>
	 * <td>cdrs</td>
	 * <td>on | off</td>
	 * <td>Output the DRS as a Prolog term.</td>
	 * </tr>
	 * <tr>
	 * <td>cdrsxml</td>
	 * <td>on | off</td>
	 * <td>Output the DRS in XML.</td>
	 * </tr>
	 * <tr>
	 * <td>cdrspp</td>
	 * <td>on | off</td>
	 * <td>Output the DRS in pretty-printed form in plain text.</td>
	 * </tr>
	 * <tr>
	 * <td>cdrshtml</td>
	 * <td>on | off</td>
	 * <td>Output the DRS in pretty-printed form in HTML.</td>
	 * </tr>
	 * <tr>
	 * <td>cparaphrase</td>
	 * <td>on | off</td>
	 * <td>Output a paraphrase that is a ``best-effort'' combination of paraphrase1 and paraphrase2.</td>
	 * </tr>
	 * <tr>
	 * <td>cparaphrase1</td>
	 * <td>on | off</td>
	 * <td>Output a paraphrase that uses full sentences instead of relative clauses.</td>
	 * </tr>
	 * <tr>
	 * <td>cparaphrase2</td>
	 * <td>on | off</td>
	 * <td>Output a paraphrase that uses relative clauses instead of full sentences. This paraphrase can currently
	 * handle <em>if-then</em> sentences that do not contain any modifiers, <em>of</em>-constructions, ditransitive
	 * verbs and noun phrase coordination. Note: experimental.</td>
	 * </tr>
	 * <tr>
	 * <td>ctokens</td>
	 * <td>on | off</td>
	 * <td>Output tokens as a Prolog list of lists of atoms.</td>
	 * </tr>
	 * <tr>
	 * <td>csentences</td>
	 * <td>on | off</td>
	 * <td>Output sentences as a Prolog list of atoms.</td>
	 * </tr>
	 * <tr>
	 * <td>csyntax</td>
	 * <td>on | off</td>
	 * <td>Output simplified syntax trees as a Prolog list.</td>
	 * </tr>
	 * <tr>
	 * <td>csyntaxpp</td>
	 * <td>on | off</td>
	 * <td>Output simplified syntax trees in pretty-printed form.</td>
	 * </tr>
	 * <tr>
	 * <td>csyntaxd</td>
	 * <td>on | off</td>
	 * <td>Output plain syntax trees as a Prolog list (for debugging).</td>
	 * </tr>
	 * <tr>
	 * <td>csyntaxdpp</td>
	 * <td>on | off</td>
	 * <td>Output plain syntax trees in pretty-printed form (for debugging).</td>
	 * </tr>
	 * <tr>
	 * <td>cowlfss</td>
	 * <td>on | off</td>
	 * <td>Output OWL/SWRL in the functional representation as a Prolog term.</td>
	 * </tr>
	 * <tr>
	 * <td>cowlfsspp</td>
	 * <td>on | off</td>
	 * <td>Output OWL/SWRL in the functional representation pretty-printed.</td>
	 * </tr>
	 * <tr>
	 * <td>cowlrdf</td>
	 * <td>on | off</td>
	 * <td>Output OWL/SWRL in the RDF/XML representation. DEPRECATED</td>
	 * </tr>
	 * <tr>
	 * <td>cowlxml</td>
	 * <td>on | off</td>
	 * <td>Output OWL/SWRL in the XML representation.</td>
	 * </tr>
	 * <tr>
	 * <td>cfol</td>
	 * <td>on | off</td>
	 * <td>Output the standard first-order logic (FOL) representation of the DRS (as a Prolog term).</td>
	 * </tr>
	 * <tr>
	 * <td>cpnf</td>
	 * <td>on | off</td>
	 * <td>Output the standard FOL representation (Prenex Normal Form) of the DRS (as a Prolog term).</td>
	 * </tr>
	 * <tr>
	 * <td>ctptp</td>
	 * <td>on | off</td>
	 * <td>Output the <a href="http://www.tptp.org/">TPTP</a> representation of the DRS. This format is directly usable
	 * as the input format for most FOL theorem provers.</td>
	 * </tr>
	 * </tr>
	 * <tr>
	 * <td>solo</td>
	 * <td>drs | drsxml | drspp | drshtml | paraphrase | paraphrase1 | paraphrase2 | tokens | sentences | syntax |
	 * syntaxpp | syntaxd | syntaxdpp | owlfss | owlfsspp | owlrdf | owlxml | fol | pnf | tptp</td>
	 * <td>Output just one output component. For <em>drspp</em>, <em>paraphrase</em>, <em>paraphrase1</em>,
	 * <em>paraphrase2</em>, <em>syntaxpp</em>, <em>syntaxdpp</em>, <em>owlfsspp</em>, the output is in plain text; for
	 * <em>drs</em>, <em>tokens</em>, <em>sentences</em>, <em>syntax</em>, <em>syntaxd</em>, <em>owlfss</em>,
	 * <em>fol</em>, <em>pnf</em>, the output is in Prolog term notation, for <em>drsxml</em>, <em>drshtml</em>,
	 * <em>owlrdf</em>, <em>owlxml</em>, the output is in XML. For <em>tptp</em>, the output is in TPTP.</td>
	 * </tr>
	 * </tbody>
	 * </table>
	 */
	public enum OutputFormat {
		DRS, DRSXML, DRSPP, DRSHTML, PARAPHRASE, PARAPHRASE1, PARAPHRASE2, TOKENS, SENTENCES, SYNTAX, SYNTAXPP, SYNTAXD, SYNTAXDPP, OWLFSS, OWLFSSPP, OWLXML, FOL, PNF, TPTP;

		public String soloParam() {
			return name().toLowerCase();
		}

		public String multiParam() {
			return "c" + soloParam();
		}
	}
}
