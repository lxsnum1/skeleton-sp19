/**
 * OffByN
 */
public class OffByN implements CharacterComparator {

    private int num;

    public OffByN(int n) {
        num = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == num || x - y == -1 * num;
    }
}