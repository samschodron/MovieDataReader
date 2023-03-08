import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType>{
  private int size;
  private int capacity;
  private LinkedList<KeyPair<KeyType, ValueType>>[] hashTable;

  public HashTableMap() {
    capacity = 10;
    size = 0;
    hashTable = (LinkedList<KeyPair<KeyType, ValueType>>[]) new LinkedList[capacity];

  }

  public HashTableMap(int c) {
    capacity = c;
    size = 0;
    hashTable = (LinkedList<KeyPair<KeyType, ValueType>>[]) new LinkedList[capacity];
  }

  /**
   * Method that is used to hash an element into the hash table
   * @param key the generic object used as the key for the hash function
   * @param value generic object that determines whats inside the pair
   * @return true or false if the call to method added the key-value pair
   */
  @Override
  public boolean put(KeyType key, ValueType value) {
    // check if key passed is null
    if (key == null)
      return false;
    KeyPair keyPair = new KeyPair(key, value);
    int index = Math.abs(keyPair.getKey().hashCode()) % capacity;
    // instantiate the linkedlist at the specified index
    if (hashTable[index] == null) {
      hashTable[index] = new LinkedList<>();
      hashTable[index].push(keyPair);
      // increment size after each successful addition
      size++;
      // check the load factor after each call to add
      if (((double) size) / ((double) capacity) >= 0.85)
        this.grow();
      return true;
    }
    // first check if the key is already in the hash table
    // return false if it is, otherwise push it onto the stack and return true
    else if (hashTable[index] != null) {
      for (int i = 0; i < hashTable[index].size(); i++) {
        if ((hashTable[index].get(i)).getKey().equals(keyPair.getKey()))
          return false;
      }
      hashTable[index].push(keyPair);
      // increment size after each successful addition
      size++;
      // check the load factor after each call to add
      if (((double) size) / ((double) capacity) >= 0.85)
        this.grow();
      return true;
    }
    return false;
  }

  /**
   * an accessor method to get the value with the specific key pair
   * @param key the key used to locate the pair
   * @return the ValueType of the key-value pair
   * @throws NoSuchElementException when key is null or no such key exists
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    ValueType result = null;
    int index = Math.abs(key.hashCode()) % capacity;
    // checking to see if the linkedlist at the index exists
    if (hashTable[index] == null)
      throw new NoSuchElementException("Value not found");
    // loop through the linkedlist at the index
    for (int i = 0; i < hashTable[index].size(); i++) {
      if (((hashTable[index].get(i))).getKey().equals(key))
        result =  hashTable[index].get(i).getValue();
    }
    // no key found
    if (result == null)
      throw new NoSuchElementException("Value not found");
    return result;
  }

  /**
   * returns the number of elements currently in the hashtable
   * @return size of hash table
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * checks a specific index for a key
   * @param key used to find the index at where the pair would be stored
   * @return true or false if the hashtable contains the specific key or not
   */
  @Override
  public boolean containsKey(KeyType key) {
    int index = Math.abs(key.hashCode()) % capacity;
    // check to see if there is a linkedlist at the index to parse through
    if (hashTable[index] != null && hashTable[index].size() > 0) {
      for (int i = 0; i < hashTable[index].size(); i++) {
        if (hashTable[index].get(i).getKey() == key)
          return true;
      }
    }
    return false;
  }

  @Override
  public ValueType remove(KeyType key) {
    ValueType result = null;
    int index = Math.abs(key.hashCode()) % capacity;
    // check to see if there is a linkedlist at the index to parse through
    if (hashTable[index] != null && hashTable[index].size() > 0) {
      // parse through the linkedlist to find the specific key to remove
      for (int i = 0; i < hashTable[index].size(); i++) {
        if (hashTable[index].get(i).getKey() == key) {
          result = hashTable[index].get(i).getValue();
          hashTable[index].remove(i);
          // decrement size after each successful removal
          size--;
          // end loop right after a successful removal
          break;
        }
      }
    }
    return result;
  }

  /**
   * clear the hashtable and set size to 0
   */
  @Override
  public void clear() {
    size = 0;
    for (int i = 0; i < hashTable.length; i ++) {
      hashTable[i] = null;
    }
  }

  /**
   * private helper method to double the capacity of the hash table
   * after reaching at least 85% load factor
   */
  private void grow() {
    int newLength = 2 * capacity;
    // new hash table to store the rehashed pairs of the old hash table
    LinkedList<KeyPair<KeyType, ValueType>>[] newHashTable = (LinkedList<KeyPair<KeyType, ValueType>>[]) new LinkedList[newLength];
    // loop through the original hash table to rehash all the elements in it
    for (int i = 0; i < hashTable.length; i ++) {
      // loop through each index that has pairs to rehash to the new hash table
      while (hashTable[i] != null && hashTable[i].size() != 0) {
        int hashIndex = (Math.abs(hashTable[i].peek().getKey().hashCode()) % newLength);
        // instantiate the linkedlist at the index of the new hash table
        if (newHashTable[hashIndex] == null) {
          newHashTable[hashIndex] = new LinkedList<>();
          // push the pair of the old hash table onto the new hash table while removing the old
          // pair to continue the loop
          newHashTable[hashIndex].push(hashTable[i].pop());
        }
        else if (newHashTable[hashIndex] != null)
          newHashTable[hashIndex].push(hashTable[i].pop());
      }
    }
    // resize the capacity of the hash table
    capacity = newLength;
    // set the old hash table to the new one
    hashTable = newHashTable;
  }
}
