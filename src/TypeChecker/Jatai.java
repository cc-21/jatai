package TypeChecker;

import Exceptions.TypeError;
import Terms.*;
import Types.*;

import java.util.HashMap;

public class Jatai implements Visitor {

    public void storeInContext(String aName, Type aType) {
        typingContext.put(aName, aType);
    }

    public void removeFromContext(String aName, Type aType) {
        typingContext.remove(aName, aType);
    }

    /**
     * ?
     * Γ ⊢ t1 : T1 ... Γ ⊢ tn : Tn
     * <p>
     * --------------------------------------
     * </p>
     * Γ ⊢ {l1=t1, ..., ln=tn} : {l1:T1, ..., ln:Tn}
     */
    @Override
    public Type inferRecordType(Terms.Record aTerm) {
        HashMap<String, Term> records = aTerm.getRecords();
        HashMap<String, Type> recordTypes = new HashMap<>();
        for (String k : records.keySet()) {
            Type t = records.get(k).inferType(this);
            if (t == null) return null;
            recordTypes.put(k, t);
        }
        return new TRecord(recordTypes);
    }

    @Override
    public boolean checkRecordType(Type t, Terms.Record aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * ?
     * Γ ⊢ t1 ⇒ 𝐴 and Γ, 𝑥 : 𝐴 ⊢ t2 ⇒ 𝐵
     * <p>
     * --------------------------------
     * <p>
     * Γ ⊢ let 𝑥 = t1 in t2 ⇒ 𝐵
     */
    @Override
    public Type inferLetType(Let aTerm) {
        Type assignmentType = aTerm.getAssignment().inferType(this);
        storeInContext(aTerm.getName(), assignmentType);
        Type result = assignmentType != null ? aTerm.getExpression().inferType(this) : null;
        removeFromContext(aTerm.getName(), assignmentType);
        return result;
    }

    /**
     * Γ ⊢ t1 ⇒ 𝐴 and Γ, 𝑥 : 𝐴 ⊢ t2 ⇐ 𝐵
     * <p>
     * --------------------------------
     * <p>
     * Γ ⊢ let 𝑥 = t1 in t2 ⇐ 𝐵
     */
    @Override
    public boolean checkLetType(Type t, Let aTerm) {
        Type assignmentType = aTerm.getAssignment().inferType(this);
        storeInContext(aTerm.getName(), assignmentType);
        boolean result = aTerm.getExpression().checkType(this, t);
        removeFromContext(aTerm.getName(), assignmentType);
        return result;
    }

    /**
     * Γ ⊢ t1 ⇒ τ1 → τ2 and Γ ⊢ t2 ⇐ τ1
     * <p>
     * ----------------------------------
     * <p>
     * Γ ⊢ t1 t2 ⇒ τ2
     */
    @Override
    public Type inferApplicationType(Application aTerm) {
        Type appType = aTerm.getTerm1().inferType(this);
        if (appType instanceof TFunction) {
            TFunction funcType = (TFunction) appType;
            return aTerm.getTerm2().checkType(this, funcType.getArgType()) ? funcType.getReturnType() : null;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkApplicationType(Type t, Application aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * ?
     * Γ ⊢ (λ x . t): τ1 → τ2 ⇐ τ1 → τ2
     * <p>
     * ---------------------------------
     * </p>
     * Γ ⊢ (λ x . t): τ1 → τ2 ⇒ τ1 → τ2
     */
    @Override
    public Type inferAbstractionType(Abstraction aTerm) {
        Annotation argAnn = aTerm.getArgAnnotation();
        Annotation returnAnn = aTerm.getReturnAnnotation();
        TFunction funcType = new TFunction(argAnn.getType(), returnAnn.getType());
        return aTerm.checkType(this, funcType) ? funcType : null;
    }

    /**
     * Γ, x : τ1 ⊢ t ⇐ τ2
     * <p>
     * ---------------------
     * </p>
     * Γ ⊢ λ x . t ⇐ τ1 → τ2
     */
    @Override
    public boolean checkAbstractionType(Type t, Abstraction aTerm) {
        if (t instanceof TFunction) {
            TFunction funcType = (TFunction) t;
            Annotation argAnn = aTerm.getArgAnnotation();
            Annotation returnAnn = aTerm.getReturnAnnotation();
            storeInContext(argAnn.getTerm().toString(), argAnn.getType());
            storeInContext(returnAnn.getTerm().toString(), returnAnn.getType());
            // assert x : τ1 because it's provided in annotations
            if (!aTerm.getArg().checkType(this, funcType.getArgType())) {
                throw new TypeError("The annotation of the lambda's argument is wrong.");
            }
            boolean result = aTerm.getExpression().checkType(this, funcType.getReturnType());

            removeFromContext(argAnn.getTerm().toString(), argAnn.getType());
            removeFromContext(returnAnn.getTerm().toString(), returnAnn.getType());
            return result;
        }
        return false;
    }

    /**
     * Γ ⊢ t ⇐ τ
     * <p>
     * -------------
     * </p>
     * Γ ⊢ t : τ ⇒ τ
     */
    @Override
    public Type inferAnnotationType(Annotation aTerm) {
        Type t = aTerm.getType();
        if (aTerm.getTerm().checkType(this, t)) {
            return t;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkAnnotationType(Type t, Annotation aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * (x : τ) ∈ Γ
     * <p>
     * -----------
     * <p>
     * Γ ⊢ x ⇒ τ
     */
    @Override
    public Type inferVariableType(Variable aTerm) {
        return typingContext.get(aTerm.toString());
    }

    @Override
    public boolean checkVariableType(Type t, Variable aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * Γ ⊢ () ⇐ unit
     */
    @Override
    public Type inferEmptyType(Empty aTerm) {
        return new TUnit();
    }

    /**
     * Γ ⊢ () ⇒ unit
     */
    @Override
    public boolean checkEmptyType(Type t, Empty aTerm) {
        return new TUnit().hasSubtype(t);
    }

    /**
     * Γ ⊢ [any integer number] ⇒ TInt
     */
    @Override
    public Type inferIntType(Int aTerm) {
        return new TInt();
    }

    @Override
    public boolean checkIntType(Type t, Int aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * Γ ⊢ true ⇒ TBool and
     * Γ ⊢ false ⇒ TBool
     */
    @Override
    public Type inferBooleanType(Bool aTerm) {
        return new TBool();
    }

    @Override
    public boolean checkBooleanType(Type t, Bool aTerm) {
        return checkInfer(t, aTerm);
    }

    /**
     * Γ ⊢ t ⇒ τ
     * <p>
     * ----------
     * <p>
     * Γ ⊢ t ⇐ τ
     */
    private boolean checkInfer(Type t, Term aTerm) {
        Type result = aTerm.inferType(this);
        return result != null && t.hasSubtype(result);
    }
}
