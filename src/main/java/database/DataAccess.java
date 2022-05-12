package database;

public interface DataAccess {
    boolean equals(Object o);
    void save();
    void delete();
    int hashCode();
}
