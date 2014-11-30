package cz.fit.sin.model.fuzzy;

import com.fuzzylite.Engine;
import com.fuzzylite.Op;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
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
    public final static int MAX = 200;
    public final static String ZERO = "ZERO";
    public final static String S = "S";
    public final static String M = "M";
    public final static String L = "L";
    public final static String VL = "VL";
    public final static String YES = "Y";
    public final static String NO = "N";

    public final static String QUEUE_NUM = "QueueNum";
    public final static String FRONT_NUM = "FrontNum";
    public final static String RED_TIME = "RedTime";
    public final static String OUT_URGENCY = "OutputUrgency";
    public final static String IN_URGENCY = "InputUrgency";
    public final static String OUT_EXTEND = "OutputExtend";
    public final static String IN_EXTEND = "InputExtend";
    public final static String DECISION = "SwitchPhase";

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
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+L + " then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+L +" and "+RED_TIME+" is "+VL +" then "+OUT_URGENCY+" is "+M,
        "if "+QUEUE_NUM+" is "+S+" and "+FRONT_NUM+" is "+VL+" and "+RED_TIME+" is "+S + " then "+OUT_URGENCY+" is "+S,
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

    /**
     * Meaning is "Should I take new phase?"
     */
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
        "if "+IN_EXTEND+" is "+VL+" and "+IN_URGENCY+" is "+VL+  " then "+DECISION+" is "+NO,
    };

    public Engine nextPhaseEngine;
    public Engine greenPhaseEngine;
    public Engine decisionEngine;
    public Engine urgencyEngine;

    public InputVariable queueNum = new InputVariable();
    public InputVariable frontNum = new InputVariable();
    public InputVariable redTime = new InputVariable();

    public OutputVariable outUrgency = new OutputVariable();
    public InputVariable inUrgency = new InputVariable();

    public OutputVariable outExtend = new OutputVariable();
    public InputVariable inExtend = new InputVariable();

    public OutputVariable finalDecision = new OutputVariable();

    /**
     *        /|        |\
     *       / |        | \
     *      /  |        |  \
     *     /___|        |___\
     *    a    b        b    a
     *
     *    a <  b        a >  b
     */
    class Sawtooth extends Ramp {

        public Sawtooth(String name, double start, double end) {
            super(name, start, end);
        }

        @Override
        public double membership(double x) {
            if (Double.isNaN(x)) {
                return Double.NaN;
            }

            if (Op.isLt(start, end) && (Op.isLE(x, start) || Op.isGE(x, end))) {
                return 0.0;
            }

            if (Op.isGt(start, end) && (Op.isLE(x, end) || Op.isGE(x, start))) {
                return 0.0;
            }

            return super.membership(x);
        }
    }

    public IntersectionFuzzyEngine() {
        nextPhaseEngine = new Engine("next-phase-engine");
        greenPhaseEngine = new Engine("green-phase-engine");
        decisionEngine = new Engine("finalDecision-engine");
        urgencyEngine =  new Engine("urgency-engine");

        queueNum.setName(QUEUE_NUM);
        queueNum.setRange(0.000, MAX);
        queueNum.addTerm(new Sawtooth(ZERO, 1, 0));
        queueNum.addTerm(new Sawtooth(S, 15, 0));
        queueNum.addTerm(new Triangle(M, 0, 30));
        queueNum.addTerm(new Triangle (L, 15, 45));
        queueNum.addTerm(new Ramp(VL, 30, 45));

        frontNum.setName(FRONT_NUM);
        frontNum.setRange(0.000, MAX);
        frontNum.addTerm(new Ramp(S, 15, 0));
        frontNum.addTerm(new Triangle (M, 0, 30));
        frontNum.addTerm(new Triangle(L, 15, 45));
        frontNum.addTerm(new Ramp(VL, 30, 45));

        redTime.setName(RED_TIME);
        redTime.setRange(0.000, MAX);
        redTime.addTerm(new Ramp(S, 20, 0));
        redTime.addTerm(new Triangle (M, 0, 40));
        redTime.addTerm(new Triangle(L, 20, 60));
        redTime.addTerm(new Ramp(VL, 40, 60));

        outUrgency.setName(OUT_URGENCY);
        outUrgency.setRange(0.000, MAX);
        outUrgency.setDefaultValue(0);
        outUrgency.addTerm(new Ramp(ZERO, 20, 0));
        outUrgency.addTerm(new Triangle(S, 0, 40));
        outUrgency.addTerm(new Triangle(M, 20, 60));
        outUrgency.addTerm(new Triangle(L, 40, 80));
        outUrgency.addTerm(new Ramp(VL, 60, 80));


        inUrgency.setName(IN_URGENCY);
        inUrgency.setRange(0.000, MAX);
        inUrgency.addTerm(new Ramp(ZERO, 20, 0));
        inUrgency.addTerm(new Triangle(S, 0, 40));
        inUrgency.addTerm(new Triangle(M, 20, 60));
        inUrgency.addTerm(new Triangle(L, 40, 80));
        inUrgency.addTerm(new Ramp(VL, 60, 80));

        outExtend.setName(OUT_EXTEND);
        outExtend.setRange(0.000, MAX);
        outExtend.setDefaultValue(0);
        outExtend.addTerm(new Ramp(ZERO, 20, 0));
        outExtend.addTerm(new Triangle(S, 0, 40));
        outExtend.addTerm(new Triangle(M, 20, 60));
        outExtend.addTerm(new Triangle(L, 40, 80));
        outExtend.addTerm(new Ramp(VL, 60, 80));

        inExtend.setName(IN_EXTEND);
        inExtend.setRange(0.000, MAX);
        inExtend.addTerm(new Ramp(ZERO, 20, 0));
        inExtend.addTerm(new Triangle(S, 0, 40));
        inExtend.addTerm(new Triangle(M, 20, 60));
        inExtend.addTerm(new Triangle(L, 40, 80));
        inExtend.addTerm(new Ramp(VL, 60, 80));

        // Meaning is "Should I take new phase?"
        //  |\      /|
        //  | \    / |
        //  |  \  /  |
        //  |___\/___|
        // -1   0.   1
        //  N        Y
        finalDecision.setName(DECISION);
        finalDecision.setDefaultValue(-1);
        finalDecision.setRange(-1, 1);
        finalDecision.addTerm(new Sawtooth(YES, 0, 1));
        finalDecision.addTerm(new Sawtooth(NO, -1, 0));

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

        // No Conjunction or Disjunction is needed
        greenPhaseEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid", MAX);
        nextPhaseEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid", MAX);
        decisionEngine.configure("AlgebraicProduct", "AlgebraicSum", "AlgebraicProduct", "AlgebraicSum", "Centroid", MAX);
    }

    public IntersectionPhase nextPhase(Intersection intersection, List<IntersectionPhase> phases, IntersectionPhase greenPhase){
        int greenPhaseQueueNum = greenPhase.getQueueNum(intersection);
        int greenPhaseFrontNum = greenPhase.getFrontNum(intersection);

        queueNum.setInputValue(greenPhaseQueueNum);
        frontNum.setInputValue(greenPhaseFrontNum);

        greenPhaseEngine.process();
        double extendPhase = outExtend.defuzzify();

        System.out.println(greenPhase.toString()+" (extend "+extendPhase+") |> "+"queueNum="+greenPhaseQueueNum + ", "+"frontNum="+greenPhaseFrontNum);

        double maxUrgency = -1.0;
        IntersectionPhase maxUrgentPhase = null;
        for(IntersectionPhase phase : phases){
            if(phase == greenPhase)
                continue;

            int phaseQueueNum = phase.getQueueNum(intersection);
            int phaseFrontNum = phase.getFrontNum(intersection);
            int phaseRedTime = phase.getRedTime(intersection);

            queueNum.setInputValue(phaseQueueNum);
            frontNum.setInputValue(phaseFrontNum);
            redTime.setInputValue(phaseRedTime);

            nextPhaseEngine.process();
            double urgency = outUrgency.defuzzify();

            System.out.println(phase.toString()+" (urgency "+urgency+") |> "+"queueNum="+phaseQueueNum+", "+"frontNum="+phaseFrontNum+", "+"redTime="+phaseRedTime);

            if(Op.isGt(urgency, maxUrgency)){
                maxUrgency = urgency;
                maxUrgentPhase = phase;
            }
        }

        inExtend.setInputValue(extendPhase);
        inUrgency.setInputValue(maxUrgency);

        decisionEngine.process();

        double decision = finalDecision.defuzzify();
        System.out.println("final decision: " + decision + "\n");
        if(Double.isNaN(decision))
            System.err.print("====> decision is NaN");

        // should I take new phase?
        return decision < 0 ? greenPhase : maxUrgentPhase;
    }

    protected static void addRules(Engine engine, String[] rules) {
        RuleBlock ruleBlock;
        ruleBlock = new RuleBlock();
        for(String text : rules)
            ruleBlock.addRule(Rule.parse(text, engine));
        engine.addRuleBlock(ruleBlock);
    }
}
