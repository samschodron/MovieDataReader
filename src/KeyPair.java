/**
 * Class to construct an object with generic parameters
 * and accessor methods along with it
 * @param <KeyType> generic object, the key that is used to find the hashCode
 * @param <ValueType> generic object
 */
public class KeyPair<KeyType, ValueType> {
  private KeyType key;
  private ValueType value;

  public KeyPair(KeyType k, ValueType v) {
    key = k;
    value = v;
  }

  public KeyType getKey() { return key; }

  public ValueType getValue() {
    return value;
  }
}