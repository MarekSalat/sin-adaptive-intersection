package cz.fit.sin.model.fuzzy;

import com.fuzzylite.Engine;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Constant;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;
import cz.fit.sin.model.IntersectionPhase;
import cz.fit.sin.model.intersection.Intersection;

import java.util.List;

/**
 * User: Marek Salát
 * Date: 20. 11. 2014
 * Time: 13:02
 */
public class IntersectionFuzzyEngine {
    public final static int MAX = 192;
    public final static String ZERO = "ZERO";
    public final static String S = "SML";
    public final static String M = "MDM";
    public final static String L = "LRG";
    public final static String VL = "LRG";
    public final static String YES = "YES";
    public final static String NO = "NO";

    public final static String QUEUE_NUM = "QueueNum";
    public final static String FRONT_NUM = "FrontNum";
    public final static String RED_TIME = "RedTime";
    public final static String OUT_URGENCY = "OutputUrgency";
    public final static String IN_URGENCY = "InputUrgency";
    public final static String OUT_EXTEND = "OutputExtend";
    public final static String IN_EXTEND = "InputExtend";
    public final static String DECISION = "SwitchPhase";

    public static String []urgencyPhaseRules = {
        "if "+QUEUE_NUM+" is "+ZERO+" then "+OUT_URGENCY+" is "+ZERO,

        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" then "+OUT_URGENCY+" is "+S,

        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" then "+OUT_URGENCY+" is "+S,

        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" then "+OUT_URGENCY+" is "+S,

        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" then "+OUT_URGENCY+" is "+M,
    };
    public static String []nextPhaseRules = {
        "if "+QUEUE_NUM+" is "+ZERO+" then "+OUT_URGENCY+" is "+ZERO,

        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+S,

        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+M,

        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+L,

        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+M + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+VL,
    };

    public static String []greenPhaseRules = {
        "if "+QUEUE_NUM+" is "+ZERO+" then "+OUT_EXTEND+" is "+ZERO,

        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+S +" then "+OUT_EXTEND+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+M +" then "+OUT_EXTEND+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" then "+OUT_EXTEND+" is "+S,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" then "+OUT_EXTEND+" is "+S,

        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+S +" then "+OUT_EXTEND+" is "+L,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+M +" then "+OUT_EXTEND+" is "+M,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+L +" then "+OUT_EXTEND+" is "+S,
        "if "+QUEUE_NUM+" is "+M+" and "+FRONT_NUM+" is "+VL+" then "+OUT_EXTEND+" is "+S,

        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+S +" then "+OUT_EXTEND+" is "+VL,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+M +" then "+OUT_EXTEND+" is "+L,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+L +" then "+OUT_EXTEND+" is "+M,
        "if "+QUEUE_NUM+" is "+L+" and "+FRONT_NUM+" is "+VL+" then "+OUT_EXTEND+" is "+S,

        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+S +" then "+OUT_EXTEND+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+M +" then "+OUT_EXTEND+" is "+VL,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+L +" then "+OUT_EXTEND+" is "+L,
        "if "+QUEUE_NUM+" is "+VL+" and "+FRONT_NUM+" is "+VL+" then "+OUT_EXTEND+" is "+M,
    };

    public static String []decisionPhaseRules = {
        "if "+IN_EXTEND+" is "+ZERO+" and "+IN_URGENCY+" is "+ZERO+" then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+ZERO+" and "+IN_URGENCY+" is "+S+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+ZERO+" and "+IN_URGENCY+" is "+M+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+ZERO+" and "+IN_URGENCY+" is "+L+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+ZERO+" and "+IN_URGENCY+" is "+VL+  " then "+DECISION+" is "+YES,

        "if "+IN_EXTEND+" is "+S+" and "+IN_URGENCY+" is "+ZERO+" then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+S+" and "+IN_URGENCY+" is "+S+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+S+" and "+IN_URGENCY+" is "+M+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+S+" and "+IN_URGENCY+" is "+L+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+S+" and "+IN_URGENCY+" is "+VL+  " then "+DECISION+" is "+YES,

        "if "+IN_EXTEND+" is "+M+" and "+IN_URGENCY+" is "+ZERO+" then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+M+" and "+IN_URGENCY+" is "+S+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+M+" and "+IN_URGENCY+" is "+M+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+M+" and "+IN_URGENCY+" is "+L+   " then "+DECISION+" is "+YES,
        "if "+IN_EXTEND+" is "+M+" and "+IN_URGENCY+" is "+VL+  " then "+DECISION+" is "+YES,

        "if "+IN_EXTEND+" is "+L+" and "+IN_URGENCY+" is "+ZERO+" then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+L+" and "+IN_URGENCY+" is "+S+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+L+" and "+IN_URGENCY+" is "+M+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+L+" and "+IN_URGENCY+" is "+L+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+L+" and "+IN_URGENCY+" is "+VL+  " then "+DECISION+" is "+YES,

        "if "+IN_EXTEND+" is "+VL+" and "+IN_URGENCY+" is "+ZERO+" then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+VL+" and "+IN_URGENCY+" is "+S+   " then "+DECISION+" is "+NO,
        "if "+IN_EXTEND+" is "+VL+" and "+IN_URGENCY+" is "+M+   " then "+DECISION+" is "+NO,
    };

    public Engine nextPhaseEngine;
    public Engine greenPhaseEngine;
    public Engine decisionEngine;

    public InputVariable queueNum = new InputVariable();
    public InputVariable frontNum = new InputVariable();
    public InputVariable redTime = new InputVariable();

    public OutputVariable outUrgency = new OutputVariable();
    public InputVariable inUrgency = new InputVariable();

    public OutputVariable outExtend = new OutputVariable();
    public InputVariable inExtend = new InputVariable();

    public OutputVariable finalDecision = new OutputVariable();

    public IntersectionFuzzyEngine() {
        nextPhaseEngine = new Engine("next-phase-engine");
        greenPhaseEngine = new Engine("green-phase-engine");
        decisionEngine = new Engine("finalDecision-engine");

        queueNum.setName(QUEUE_NUM);
        queueNum.setRange(0.000, MAX);
        queueNum.addTerm(new Constant(ZERO, 0));
        queueNum.addTerm(new Ramp(S, 7, 0));
        queueNum.addTerm(new Triangle(M, 0, 14));
        queueNum.addTerm(new Triangle (L, 7, 21));
        queueNum.addTerm(new Ramp(VL, 14, 21));

        frontNum.setName(FRONT_NUM);
        frontNum.setRange(0.000, MAX);
        frontNum.addTerm(new Constant(ZERO, 0));
        frontNum.addTerm(new Ramp(S, 7, 0));
        frontNum.addTerm(new Triangle (M, 0, 14));
        frontNum.addTerm(new Triangle (L, 7, 21));
        frontNum.addTerm(new Ramp(VL, 14, 21));

        redTime.setName(RED_TIME);
        redTime.setRange(0.000, MAX);
        redTime.addTerm(new Constant(ZERO, 0));
        redTime.addTerm(new Ramp(S, 2, 0));
        redTime.addTerm(new Triangle (M, 0, 4));
        redTime.addTerm(new Triangle (L, 2, 6));
        redTime.addTerm(new Ramp(VL, 4, 6));

        outUrgency.setName(OUT_URGENCY);
        outUrgency.setRange(0.000, MAX);
        outUrgency.setDefaultValue(0);
        outUrgency.addTerm(new Constant(ZERO, 0));
        outUrgency.addTerm(new Constant(S, 2));
        outUrgency.addTerm(new Constant(M, 4));
        outUrgency.addTerm(new Constant(L, 6));
        outUrgency.addTerm(new Constant(VL, 8));

        inUrgency.setName(IN_URGENCY);
        inUrgency.setRange(0.000, MAX);
        inUrgency.addTerm(new Ramp(ZERO, 2, 0));
        inUrgency.addTerm(new Triangle(S, 0, 4));
        inUrgency.addTerm(new Triangle(M, 2, 6));
        inUrgency.addTerm(new Triangle(L, 4, 8));
        inUrgency.addTerm(new Ramp(VL, 6, 8));

        outExtend.setName(OUT_EXTEND);
        outExtend.setRange(0.000, MAX);
        outExtend.setDefaultValue(0);
        outExtend.addTerm(new Ramp(ZERO, 2, 0));
        outExtend.addTerm(new Triangle (S, 0, 4));
        outExtend.addTerm(new Triangle (M, 2, 6));
        outExtend.addTerm(new Triangle (L, 4, 8));
        outExtend.addTerm(new Ramp(VL, 6, 8));

        inExtend.setName(IN_EXTEND);
        inExtend.setRange(0.000, MAX);
        inExtend.addTerm(new Ramp(ZERO, 2, 0));
        inExtend.addTerm(new Triangle(S, 0, 4));
        inExtend.addTerm(new Triangle(M, 2, 6));
        inExtend.addTerm(new Triangle(L, 4, 8));
        inExtend.addTerm(new Ramp(VL, 6, 8));

        finalDecision.setName(DECISION);
        finalDecision.setDefaultValue(-1);
        finalDecision.setRange(-1, 1);
        finalDecision.addTerm(new Constant(YES, 1));
        finalDecision.addTerm(new Constant(NO, -1));

        // Green phase configuration
        greenPhaseEngine.addInputVariable(queueNum);
        greenPhaseEngine.addInputVariable(frontNum);
        greenPhaseEngine.addOutputVariable(outExtend);
        addRules(greenPhaseEngine, greenPhaseRules);

        // Next phase configuration
        nextPhaseEngine.addInputVariable(queueNum);
        nextPhaseEngine.addInputVariable(frontNum);
        nextPhaseEngine.addInputVariable(redTime);
        nextPhaseEngine.addOutputVariable(outUrgency);
        addRules(nextPhaseEngine, nextPhaseRules);

        // Decision/switch module configuration
        decisionEngine.addInputVariable(inExtend);
        decisionEngine.addInputVariable(inUrgency);
        decisionEngine.addOutputVariable(finalDecision);
        addRules(decisionEngine, decisionPhaseRules);

        //No Conjunction or Disjunction is needed
        greenPhaseEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid");
        nextPhaseEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid");
        decisionEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid");
    }

    public IntersectionPhase nextPhase(Intersection intersection, List<IntersectionPhase> phases, IntersectionPhase greenPhase){
        queueNum.setInputValue(greenPhase.getQueueNum(intersection));
        frontNum.setInputValue(greenPhase.getFrontNum(intersection));

        greenPhaseEngine.process();
        double greenPhaseValue = outExtend.defuzzify();

        double maxUrgency = 0;
        IntersectionPhase maxUrgentPhase = null;
        for(IntersectionPhase phase : phases){
            if(phase == greenPhase)
                continue;

            queueNum.setInputValue(phase.getQueueNum(intersection));
            frontNum.setInputValue(phase.getFrontNum(intersection));
            redTime.setInputValue(phase.getRedTime(intersection));

            nextPhaseEngine.process();
            double urgency = outUrgency.defuzzify();

            if(urgency >= maxUrgency){
                maxUrgency = urgency;
                maxUrgentPhase = phase;
            }
        }

        inExtend.setInputValue(greenPhaseValue);
        inUrgency.setInputValue(maxUrgency);

        decisionEngine.process();
        return finalDecision.defuzzify() > 0 ? greenPhase : maxUrgentPhase;
    }

    protected static void addRules(Engine engine, String[] rules) {
        RuleBlock ruleBlock;
        ruleBlock = new RuleBlock();
        for(String text : rules)
            ruleBlock.addRule(Rule.parse(text, engine));
        engine.addRuleBlock(ruleBlock);
    }
}
