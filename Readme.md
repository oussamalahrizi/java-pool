# Java Refresher & Modern Features Guide

This guide covers practical Java concepts and features, inspired by common questions and comparisons with other languages like Python and C++.  
It is formatted for Docsify documentation hosting.

---

## Object Copying: Shallow vs Deep Copy

**Java does _not_ always use deep copy.**  
Java uses references for object variables. By default, when you assign or pass an object, you are passing a reference (pointer) to the object, **not a copy of the object itself**.

**Shallow Copy:**  
- Copies only the reference, not the actual object.
- Both variables point to the same memory location (the same object).
- Default behavior for assignments and method arguments.

**Deep Copy:**  
- Copies all fields of the object recursively, creating a new instance.
- You must implement this manually (e.g. via a copy constructor or custom method).

**Example:**
```java
Dog d1 = new Dog();      // One Dog instance
Dog d2 = d1;             // d2 points to the same Dog as d1 (shallow copy)
```

---

## Passing Objects to Methods and Memory Usage

When passing objects to methods:
- **Only the reference is copied** (a small pointer), not the entire object—even if the object is huge.
- No extra memory is used for the object itself during the call, just the reference.
- If you clone the object or make a deep copy, **then** extra memory is used.

**Example:**
```java
void doSomething(BigObject obj) { /* ... */ }

BigObject bigObj = new BigObject(); // possibly large
doSomething(bigObj); // only the reference is passed
```

---

## Changing the Instance Passed to Methods

You cannot change the caller's variable to refer to a new object from inside a method.  
Java passes object references **by value**, so reassigning the parameter inside the method only changes the local copy.

**Example:**
```java
void morph(Animal a) {
    a = new Cat(); // local copy only
}

Animal myAnimal = new Dog();
morph(myAnimal);
// myAnimal is still a Dog
```

**To change the caller’s variable:**
- Return the new object, and reassign in the caller.

```java
Animal morph(Animal a) {
    return new Cat();
}

myAnimal = morph(myAnimal); // Now myAnimal refers to a Cat
```

**Advanced:**  
Use a holder class to wrap the reference if you want in-place changes (rare in Java).

```java
class AnimalHolder {
    public Animal animal;
}

void morph(AnimalHolder holder) {
    holder.animal = new Cat();
}
```

---

## Returning Multiple Values from a Method

Java does not support native multiple return values (like Python's tuple or C++'s std::pair).  
**Recommended ways:**

### 1. Wrapper/Result Class
```java
public class Result {
    public int value;
    public String message;
    public Animal animal;
}
```
Return an instance of `Result` containing all desired values.

### 2. Built-in Tuple-Like Classes
For two values, use `AbstractMap.SimpleEntry<K, V>`:
```java
import java.util.AbstractMap;
return new AbstractMap.SimpleEntry<>(42, "hello");
```

### 3. List or Array
If all values are of the same type:
```java
return new int[] {1, 2};
```
or
```java
return Arrays.asList("foo", "bar");
```

### 4. Map for Named Values
```java
Map<String, Object> result = new HashMap<>();
result.put("age", 30);
result.put("name", "Bob");
return result;
```

---

## Maps in Java: Comparison & Usage

- **Most common and best:** `HashMap<K, V>`
  - Fast, unsorted, allows null keys/values.
  - Equivalent to Python's `dict` and C++'s `unordered_map`.

**Example:**
```java
Map<String, Integer> map = new HashMap<>();
map.put("apple", 3);
map.get("apple"); // returns 3
```

**Other types:**
- `TreeMap<K, V>`: Sorted keys, O(log n) operations (like C++’s `std::map`)
- `LinkedHashMap<K, V>`: Insertion order maintained

**Best practice:**  
Use the `Map` interface for type, and instantiate with `HashMap` for general use.  
Use `TreeMap` if you need sorted keys.

---

## TreeMap vs std::map (C++): Time Complexity

Both use balanced binary search trees (typically Red-Black tree):

| Operation   | Java TreeMap      | C++ std::map      |
|-------------|-------------------|-------------------|
| get(key)    | O(log n)          | O(log n)          |
| put(key, v) | O(log n)          | O(log n)          |
| remove(key) | O(log n)          | O(log n)          |
| iteration   | O(n), sorted      | O(n), sorted      |

**HashMap** (Java) and `unordered_map` (C++) use hash tables for O(1) average time but do not keep keys sorted.

---

## final and static in Java

### `final`
Declares something as unchangeable:
- **Variable:** Value cannot be reassigned.
- **Method:** Cannot be overridden.
- **Class:** Cannot be subclassed.

**Example:**
```java
final int x = 10;
final class MyClass {}
final void doStuff() {}
```

### `static`
Belongs to the class, not to instances.
- **Static variable:** Shared among all instances.
- **Static method:** Can be called without an object.
- **Static block:** Initialization code run once when the class loads.

**Example:**
```java
class Counter {
    static int count = 0;
    static void increment() { count++; }
}
```

---

## Modern Java Features (Java 8+)

### Lambdas & Functional Interfaces
```java
list.forEach(s -> System.out.println(s));
```

### Streams API
```java
nums.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
```

### Optional
```java
Optional<String> maybeName = Optional.ofNullable(null);
maybeName.ifPresent(System.out::println);
```

### Records (Java 16+)
Immutable data carriers:
```java
public record Point(int x, int y) {}
```

### Text Blocks (Java 15+)
Multi-line strings:
```java
String s = """
           This is a
           multi-line string!
           """;
```

### Switch Expressions (Java 14+)
```java
String result = switch (day) {
    case 1, 2 -> "Mon/Tue";
    case 3    -> "Wed";
    default   -> "Other";
};
```

### Pattern Matching for instanceof (Java 16+)
```java
if (obj instanceof String s) {
    System.out.println(s.toLowerCase());
}
```

---

## Quick Reference Table

| Concept      | Java                        | Python          | C++                |
|--------------|-----------------------------|-----------------|--------------------|
| Map Type     | `HashMap`, `TreeMap`        | `dict`          | `std::map`         |
| Tuple        | Use Entry/List/Custom Class | Native tuples   | `std::pair`        |
| Copying      | Shallow by default          | Reference       | Value/Reference    |
| final        | Unchangeable                | `const`         | `const`            |
| static       | Class-level                 | @staticmethod   | static             |
| Modern Features | Lambdas, Streams, Records | Lambdas, etc.   | Lambdas, auto, etc.|

---

## 📚 Further Reading

- [Java Documentation](https://docs.oracle.com/en/java/)
- [Java 8 Features](https://www.oracle.com/java/technologies/javase/8-whats-new.html)
- [Records in Java](https://docs.oracle.com/en/java/javase/16/language/records.html)
- [Docsify Documentation](https://docsify.js.org/#/)

---

Feel free to extend this guide with more questions, code samples, or advanced topics!
