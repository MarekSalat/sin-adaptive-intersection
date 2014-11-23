package cz.fit.sin.utils;

/**
* User: Marek Salát
* Date: 23. 11. 2014
* Time: 12:51
*/
public class Pair<L,R> {
    public final L first;
    public final R second;

    public Pair(L left, R right) {
        this.first = left;
        this.second = right;
    }

    public static <L,R> Pair<L,R> of(L left, R right){
        return new Pair<L,R>(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
        if (second != null ? !second.equals(pair.second) : pair.second != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }
}
