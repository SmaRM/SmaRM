package org.smarthome.knowledge.ace;

/**
<table>
<thead>
<tr><td>Element Name</td><td>Count</td><td>Content</td><td>Default</td><td>Description</td></tr>
</thead>

<tbody>
<tr>
	<td>Program</td>
	<td>1</td>
	<td><em>text</em></td>
	<td></td>
	<td>The AceRules program as an ACE text.</td>
</tr><tr>
	<td>Mode</td>
	<td>0-1</td>
	<td>court | stable | stable_strong</td>
	<td>court</td>
	<td>The mode (i.e. semantics) to be used to calculate the answer: courteous, stable, or stable with strong negation.</td>
</tr><tr>
	<td>UserLexiconURL</td>
	<td>0-1</td>
	<td><em>URL</em></td>
	<td></td>
	<td>If this element is present then the user lexicon is fetched from the specified URL and loaded before parsing the ACE text. Otherwise, no user lexicon is used.</td>
</tr><tr>
	<td>Guess</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>If on, unknown words are guessed. (Unknown proper names are recognized anyway.)</td>
</tr><tr>
	<td>MaxAnswers</td>
	<td>0-1</td>
	<td><em>positive-integer</em></td>
	<td>1</td>
	<td>The maximal number of answers that should be calculated. It has no effect in courteous mode, since there is always exactly one answer.</td>
</tr><tr>
	<td>RulesOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>Rules in internal Prolog notation are returned.</td>
</tr><tr>
	<td>SimpleRulesOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>Rules in a simplified Prolog representation are returned.</td>
</tr><tr>
	<td>AnswersetOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>Answersets are returned (Prolog notation).</td>
</tr><tr>
	<td>AnswertextOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>on</td>
	<td>Answertexts are returned.</td>
</tr><tr>
	<td>TraceOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>Trace is returned in Prolog notation (courteous mode only).</td>
</tr><tr>
	<td>ACETraceOutput</td>
	<td>0-1</td>
	<td>on | off</td>
	<td>off</td>
	<td>Verbalized trace is returned (courteous mode only).</td>
</tr>
</tbody>
</table>
 */
public class AceRulesParam {
	
}
