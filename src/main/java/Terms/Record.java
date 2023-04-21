package Terms;

import Exceptions.IllegalDeclarationError;
import TypeChecker.Context;
import Types.TRecord;
import Types.Type;

import java.util.HashMap;

/**
 * Does not allow duplicate labels
 */
public class Record implements Term {

    HashMap<String, Term> records;

    public Record(HashMap<String, Term> records) {
        records.forEach((k,v) -> {
            if(k == null || v == null) throw new IllegalDeclarationError("Null name or null term detected in the declaration of Record.");
        });
        this.records = new HashMap<>(records);
    }

    public HashMap<String, Term> getRecords() {
        return new HashMap<>(records);
    }

    @Override
    public String toString() {
        return records.toString();
    }

    /**
     * <todo>verify</todo>
     * Γ ⊢ t1 ⇒ T1 ... Γ ⊢ tn ⇒ Tn
     * <p>
     * -------------------------------------
     * </p>
     * Γ ⊢ {l1=t1, ..., ln=tn} ⇒ {l1:T1, ..., ln:Tn}
     */
    @Override
    public Type inferType(Context aContext) {
        HashMap<String, Type> recordTypes = new HashMap<>();
        for (String k : records.keySet()) {
            Type t = records.get(k).inferType(aContext);
            if (t == null) return null;
            recordTypes.put(k, t);
        }
        return new TRecord(recordTypes);
    }

    /**
     * <todo>verify</todo>
     * Γ ⊢ t1 ⇐ T1 ... Γ ⊢ tm ⇐ Tm  and m<= n
     * <p>
     * -------------------------------------
     * </p>
     * Γ ⊢ {l1=t1, ..., ln=tn} ⇐ {l1:T1, ..., lm:Tm}
     */
    @Override
    public boolean checkType(Type aType, Context aContext) {
        if (aType instanceof TRecord) {
            TRecord rType = (TRecord) aType;
            HashMap<String, Type> recordTypes = rType.getRecordTypes();
            if (recordTypes.size() > records.size()) return false;
            for (String k: recordTypes.keySet()) {
                Term term = records.get(k);
                if(term == null){
                    return false;
                } else {
                    if (!term.checkType(recordTypes.get(k), aContext)) return false;
                }
            }
            return true;
        }
        return false;
    }
}
