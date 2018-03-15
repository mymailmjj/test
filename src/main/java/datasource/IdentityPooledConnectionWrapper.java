package datasource;

import java.sql.Connection;

public class IdentityPooledConnectionWrapper<T extends Connection> {
    
    private T t;
    
    public IdentityPooledConnectionWrapper(){
        
    }

    public IdentityPooledConnectionWrapper(T t) {
        this.t = t;
    }

    @Override
    public int hashCode() {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IdentityPooledConnectionWrapper other = (IdentityPooledConnectionWrapper) obj;
        if (t == null) {
            if (other.t != null)
                return false;
        } else if (!t.equals(other.t))
            return false;
        return true;
    }

}
