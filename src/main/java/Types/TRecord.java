package Types;

import Exceptions.IllegalDeclarationError;

import java.util.HashMap;
import java.util.Set;

// record type cannot have duplicate labels

public class TRecord implements Type {

    HashMap<String, Type> recordTypes;

    public TRecord(HashMap<String, Type> types) {
        types.forEach((k,v) -> {
            if(k == null || v == null) throw new IllegalDeclarationError("Null name or null type detected in the declaration of record types.");
        });
        recordTypes = new HashMap<>(types);
    }

    public HashMap<String, Type> getRecordTypes() {
        return new HashMap<>(recordTypes);
    }

    // subtyping for records
    @Override
    public boolean hasSubtype(Type aType) {
        if (aType instanceof TRecord) {
            HashMap<String, Type> subRecord = ((TRecord) aType).recordTypes;
            // S-RCD
            for (String k : recordTypes.keySet()) {
                if (subRecord.containsKey(k)) {
                    if (!recordTypes.get(k).hasSubtype(subRecord.get(k))) return false;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean typeEquals(Type aType) {
        if (aType instanceof TRecord) {
            HashMap<String, Type> target = ((TRecord) aType).recordTypes;
            Set<String> keys = recordTypes.keySet();
            keys.equals(target.keySet());
            for (String k : keys) {
                if (!recordTypes.get(k).typeEquals(target.get(k))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return recordTypes.toString();
    }
}
