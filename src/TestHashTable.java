import java.util.NoSuchElementException;

/**
 * main test class to test all the methods in the HashMapTable Class
 */
public class TestHashTable {
  public static void main(String[] args) {
    System.out.println("Put method test: " + test1());
    System.out.println("Get method test: " + test2());
    System.out.println("ContainsKey method test: " + test3());
    System.out.println("Grow private method test: " + test4());
    System.out.println("Remove method test: " + test5());
    System.out.println("Clear method test: " + test6());
  }

  /**
   * testing the put method of the HashTableMap class along with the grow helper method
   * testing adding duplicate keys and collisions all the while checking the size increment
   * @return true or false depending on if all tests passed
   */
  public static boolean test1() {
    HashTableMap hashMap1 = new HashTableMap();
    // adding first key-value pair and check size increment
    int keyType1 = 13;
    String valueType1 = "Hello";
    if(!(hashMap1.put(keyType1, valueType1) && hashMap1.size() == 1)) // checked
      return false;
    // adding duplicate key and check size
    String valueTypeDupe = "Bye";
    if(hashMap1.put(keyType1, valueTypeDupe) || hashMap1.size() != 1) // checked
      return false;
    // adding a collision and check size increment
    int keyType2 = 23;
    String valueType2 = "Bleh";
    if (!(hashMap1.put(keyType2, valueType2) && hashMap1.size() == 2)) // checked
      return false;
    return true;
  }

  /**
   * testing the get method of the HashTableMap class
   * testing with single element
   * testing after collision
   * testing in between 2 elements
   * and testing with a nonexistent key
   * @return true or false depending on if all tests passed
   */
  public static boolean test2() {
    HashTableMap hashMap1 = new HashTableMap();
    // testing basic get method
    int keyType1 = 10;
    String valueType1 = "Hello";
    hashMap1.put(keyType1, valueType1);
    if (!hashMap1.get(keyType1).equals(valueType1)) // checked
      return false;
    // testing get method after a collision
    int keyType2 = 20;
    String valueType2 = "Yeah";
    hashMap1.put(keyType2,valueType2);
    if (!hashMap1.get(keyType1).equals(valueType1)) // checked
      return false;
    // testing get method for an element between two elements
    int keyType3 = 30;
    String valueType3 = "No";
    hashMap1.put(keyType3, valueType3);
    if (!hashMap1.get(keyType2).equals(valueType2)) // checked
      return false;
    // testing the get method on a nonexistent key-value pair
    // an exception should be thrown when calling the method
    int keyType4 = 40;
    try { // checked
      hashMap1.get(keyType4);
    } catch (NoSuchElementException e) {
      return true;
    }
    return false;
  }

  /**
   * a test to the containsKey method in the HashTableMap class
   * testing containsKey on an index of the hash table with only one element
   * testing containsKey on an index after a collision
   * testing containsKey on an index after two collision to check the value in between
   * @return true or false depending on if all tests passed
   */
  public static boolean test3() { // all checked
    HashTableMap hashMap1 = new HashTableMap();
    int keyType1 = 1;
    int keyType2 = 2;
    int keyType3 = 3;
    String keyType4 = "Booyah";
    int keyType5 = 12;
    String keyType6 = "yahBoo";
    String valueType1 = "nu";
    String valueType2 = "nuuh";
    String valueType3 = "nuuhuh";
    int valueType4 = 99;
    String valueType5 = "nuhuhuhh";
    int valueType6 = 420;
    //testing the containsKey with no collision
    hashMap1.put(keyType1, valueType1);
    hashMap1.put(keyType2, valueType2);
    hashMap1.put(keyType3, valueType3);
    hashMap1.put(keyType4, valueType4);
    if (!hashMap1.containsKey(1))
      return false;
    if (!hashMap1.containsKey(2))
      return false;
    if (!hashMap1.containsKey(3))
      return false;
    if (!hashMap1.containsKey("Booyah"))
      return false;
    // testing containsKey after a collision
    hashMap1.put(keyType5, valueType5);
    if (!hashMap1.containsKey(2))
      return false;
    hashMap1.put(keyType6, valueType6);
    if (!hashMap1.containsKey("Booyah"))
      return false;
    return true;
  }

  /**
   * testing the private grow method by adding enough to go pass the default
   * capacity of 10 and then some
   * @return true or false depending on if all tests passed
   */
  public static boolean test4() {
    HashTableMap hashMap1 = new HashTableMap();
    int keyType3 = 10;
    int keyType4 = 11;
    int keyType5 = 12;
    int keyType6 = 14;
    int keyType7 = 15;
    int keyType8 = 16;
    int keyType9 = 17;
    int keyType10 = 18;
    int keyType11 = 19;
    int keyType12 = 20;
    int keyType13 = 21;
    int keyType14 = 22;
    int keyType15 = 24;
    String valueType3 = "ha";
    String valueType4 = "hah";
    String valueType5 = "haha";
    String valueType6 = "hahah";
    String valueType7 = "hahaha";
    String valueType8 = "hahahah";
    String valueType9 = "hahahaha";
    String valueType10 = "hahahahah";
    String valueType11 = "hahahahaha";
    String valueType12 = "hahahahahah";
    String valueType13 = "hahahahahaha";
    String valueType14 = "hahahahahahah";
    String valueType15 = "hahahahahahaha";
    hashMap1.put(keyType3, valueType3);
    hashMap1.put(keyType4, valueType4);
    hashMap1.put(keyType5, valueType5);
    hashMap1.put(keyType6, valueType6);
    hashMap1.put(keyType7, valueType7);
    hashMap1.put(keyType8, valueType8);
    hashMap1.put(keyType9, valueType9);
    hashMap1.put(keyType10, valueType10);
    hashMap1.put(keyType11, valueType11);
    // testing further calls to put to test if the hash table has been expanded
    if (!(hashMap1.put(keyType12, valueType12) && hashMap1.size() == 10)) // checked
      return false;
    if (!(hashMap1.put(keyType13, valueType13) && hashMap1.size() == 11)) // checked
      return false;
    if (!(hashMap1.put(keyType14, valueType14) && hashMap1.size() == 12)) // checked
      return false;
    if (!(hashMap1.put(keyType15, valueType15) && hashMap1.size() == 13)) // checked
      return false;
    return true;
  }

  /**
   * testing the remove method of the HashMapTable class
   * testing with a single element
   * testing with one collision
   * testing removal in quick succession on same index
   * testing removal of pair in between two pairs
   * testing removal of nonexistent key
   * @return true or false depending on if all tests passed
   */
  public static boolean test5() {
    HashTableMap hashMap1 = new HashTableMap();
    // removing a lone pair
    int keyType1 = 1;
    String valueType1 = "Mwaha";
    hashMap1.put(keyType1, valueType1);
    if (!(hashMap1.remove(1).equals(valueType1) && hashMap1.size() == 0))
      return false;
    // removing a pair with one collision
    int keyType2 = 11;
    String valueType2 = "Mwahaha";
    hashMap1.put(keyType1, valueType1);
    hashMap1.put(keyType2, valueType2);
    if (!(hashMap1.remove(1).equals(valueType1) && hashMap1.size() == 1))
      return false;
    // removing in quick succession at same index
    hashMap1.put(keyType1, valueType1);
    if (!(hashMap1.remove(1).equals(valueType1) && hashMap1.size() == 1))
      return false;
    if (!(hashMap1.remove(11).equals(valueType2) && hashMap1.size() == 0))
      return false;
    // removing a pair in between two pairs
    int keyType3 = 21;
    String valueType3 = "Mwahahaha";
    hashMap1.put(keyType1, valueType1);
    hashMap1.put(keyType2, valueType2);
    hashMap1.put(keyType3, valueType3);
    if (!(hashMap1.remove(11).equals(valueType2) && hashMap1.size() == 2))
      return false;
    // removing a non existent pair
    if (!(hashMap1.remove(6) == null && hashMap1.size() == 2))
      return false;
    return true;
  }

  /**
   * testing the clear method of the HashMapTable class
   * checks if the size is correct after clearing
   * @return true or false depending on if all tests passed
   */
  public static boolean test6() {
    HashTableMap hashMap1 = new HashTableMap();
    int keyType1 = 1;
    int keyType2 = 2;
    int keyType3 = 12;
    int keyType4 = 3;
    String valueType1 = "he";
    String valueType2 = "heh";
    String valueType3 = "hehe";
    String valueType4 = "heheh";
    hashMap1.put(keyType1, valueType1);
    hashMap1.put(keyType2, valueType2);
    hashMap1.put(keyType3, valueType3);
    hashMap1.put(keyType4, valueType4);
    hashMap1.clear();
    if (hashMap1.size() != 0)
      return false;
    return true;
  }
}