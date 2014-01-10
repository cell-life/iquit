package org.celllife.iquit.framework.interfaces.validator;

import java.util.HashMap;
import java.util.Map;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Executes a set of rules on a specified Map of form values.
 */
public class FormValidator {
	
	private static Logger log = LoggerFactory.getLogger(FormValidator.class);
	
	private static final String RULE_BASE_DIR = "rules/";
	private static final String RULE_FILE_EXTENSION = ".drl";
	
	/** Contains a cache of previously compiled rule sets */
	private static Map<String, StatelessKnowledgeSession> ruleSessionCache = new HashMap<String, StatelessKnowledgeSession>();

	/**
	 * Given a specified set of rules, execute them given the specified parameters and see if there are any validation errors
	 * @param validationRuleId String name of the rules file (must be located in the classpath under a rules directory)
	 * @param parameters Map of String form questions and answers
	 * @throws FormValidationException thrown if there is any validation error or error while compiling the rules
	 */
	public static void validate(String validationRuleId, Map<String, String> parameters) throws FormValidationException {
		StatelessKnowledgeSession ksession = getRuleSession(validationRuleId);
		if (ksession != null) {
			// run the rules
			FormValidationContext context = new FormValidationContext(parameters);
			ksession.execute(context);
			// process the results
			if (!context.isSuccess()) {
				if (log.isDebugEnabled()) {
					log.debug("Validation errors found: "+context.getValidationError());
				}
				throw new FormValidationException(context.getValidationError());
			}
			// if we're here, then it's all OK :)
		} else {
			throw new FormValidationException("Could not build rule file. See logs for more details.");
		}
	}
	
	static StatelessKnowledgeSession getRuleSession(String validationRuleFileName) {
		StatelessKnowledgeSession session = ruleSessionCache.get(validationRuleFileName);
		if (session == null) {
			KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
			try {
				kbuilder.add(ResourceFactory.newClassPathResource(RULE_BASE_DIR+validationRuleFileName+RULE_FILE_EXTENSION), ResourceType.DRL);
				if (kbuilder.hasErrors()) {
					log.error("Errors while compiling form validation rules '"+validationRuleFileName+"': " + kbuilder.getErrors().toString());
				} else {
					KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
					kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
					session = kbase.newStatelessKnowledgeSession();
					ruleSessionCache.put(validationRuleFileName, session); // save it for next time
				}
			} catch (Exception e) {
				log.error("Errors while retrieving form validation rules '"+validationRuleFileName+"': "+e.getMessage(), e);
			}
		}
		return session;
	}
}
