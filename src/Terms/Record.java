package Terms;

import TypeChecker.Jatai;
import Types.Type;

import java.util.HashMap;

/**
 * Does not allow duplicate labels
 */
public class Record implements Term {

    HashMap<String, Term> records;

    public Record(HashMap<String, Term> records) {
        this.records = new HashMap<>(records);
    }

    public HashMap<String, Term> getRecords() {
        return new HashMap<>(records);
    }

    @Override
    public Type inferType(Jatai checker) {
        return checker.inferRecordType(this);
    }

    @Override
    public boolean checkType(Jatai checker, Type t) {
        return checker.checkRecordType(t, this);
    }

    @Override
    public String toString() {
        return records.toString();
    }
}
