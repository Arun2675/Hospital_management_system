# Hospital Management System — Java OOP Mini Project

**Student:** ARUN SHARMA (R24SA005)
**Program:** B.Sc. (BSTCs), Semester V — Java Programming
**Assigned Project:** Hospital Management System

## 1. How to Compile and Run

```
javac -d bin $(find src -name "*.java")
java -cp bin com.hospital.main.HospitalApp
```

## 2. Project Structure

```
src/com/hospital/model/    Department.java, Payable.java, Person.java, Patient.java, Doctor.java
src/com/hospital/service/  HospitalService.java
src/com/hospital/main/     HospitalApp.java   (console menu / entry point)
sample_output.txt          Captured end-to-end console run
```

## 3. Feature Traceability Table

| # | Mandatory Feature | File : Class | Where |
|---|---|---|---|
| 1 | 3–4 classes with encapsulation | `Person.java`, `Patient.java`, `Doctor.java` | All fields `private`; public getters/setters throughout |
| 2 | Varied data types, final constants, variable scope | `Person.java` | Fields `String/int/char`; `static final HOSPITAL_NAME`; instance vars vs. local var `category` in `ageCategory()` vs. static `totalPersonsCreated` |
| 3 | Operators + operator precedence | `Patient.java` → `calculateBill(int,double,boolean)` | Comment explains `*` binds before `+` in the total-bill expression |
| 4 | Type conversion / casting | `Patient.java` → `getRoundedBillAmount()` | Explicit `(int) bill` narrowing cast, double→int |
| 5 | Enum | `Department.java` | `CARDIOLOGY, NEUROLOGY, ORTHOPEDICS, PEDIATRICS, GENERAL` with a field + constructor |
| 6 | All control flow + jump statements | `HospitalApp.java`, `HospitalService.java`, `Person.java` | `do-while` menu loop, `switch` menu + department selection, `for`/`while`/enhanced-for, `break`, `continue`, `return` |
| 7 | Arrays of objects | `HospitalService.java` | `private Patient[] patients;` and `private Doctor[] doctors;` |
| 8 | Console I/O with formatted output | `HospitalApp.java` | `Scanner` for input; `System.out.printf` / `String.format` throughout |
| 9 | Constructor overloading | `Person.java`, `Patient.java`, `Doctor.java` | Each has a no-arg constructor and a fully parameterized constructor |
| 10 | Method overloading | `Patient.java` → `calculateBill(int)` / `calculateBill(int,double,boolean)`; `Doctor.java` → `addBonus(double)` / `addBonus(double,boolean)` | Same method name, different signatures |
| 11 | Static fields/methods | `Person.java` (`totalPersonsCreated`, `getTotalPersonsCreated()`), `Patient.java` (`totalPatients`), `Doctor.java` (`totalDoctors`) | Object counters |
| 12 | Explicit `this` (shadowing / chaining) | `Person.java` constructors | `this("Unknown", 0, 'O', "N/A")` chaining; `this.name = name` shadow resolution |
| 13 | String class methods | `Patient.java` → `parseSymptoms()`; `HospitalService.java` → `searchPatientByName()`; `HospitalApp.java` | `trim()`, `split()`, `compareToIgnoreCase()`, `contains()`, `toLowerCase()`, `equalsIgnoreCase()` |
| 14 | Base class + ≥2 subclasses | `Person.java` → `Patient.java`, `Doctor.java` | Inheritance hierarchy `Person → Patient, Doctor` |
| 15 | `super` keyword | `Patient.java`, `Doctor.java` constructors and `toString()` | `super(...)` constructor calls; `super.toString()` method call |
| 16 | Overriding + dynamic binding | `HospitalService.java` → `printRoster(Person[] roster)`; `HospitalApp.java` → `showDynamicBindingDemo()` | `Person` reference array holding `Patient`/`Doctor` objects; `p.displayRole()` resolves to the runtime type |
| 17 | Abstract class + abstract method | `Person.java` | `public abstract class Person`, `public abstract String displayRole();` |
| 18 | Interface, implemented + accessed via interface reference | `Payable.java` implemented by `Patient.java`; used in `HospitalService.totalHospitalRevenue()` and `HospitalApp.showBills()` | `Payable billable = patients[i];` |
| 19 | Override `toString()`/`equals()` | `Person.java`, `Patient.java` | Both overridden; `Patient` also overrides its own `equals()`/`toString()` |
| 20 | `final` method or class, with comment | `Person.java` → `public final String ageCategory()` | Comment explains it encodes fixed hospital policy that subclasses must not alter |
| 21 | ≥2 custom packages with imports | `com.hospital.model`, `com.hospital.service`, `com.hospital.main` | `HospitalService.java` and `HospitalApp.java` import classes from `com.hospital.model` |

## 4. Sample Console Output

See `sample_output.txt` for a full captured run exercising every menu option
(list patients, list doctors, search, register a new patient, dynamic-binding
roster, bill generation, and statistics).

## 5. UML Class Diagram (Bonus)

```
                    Person (abstract)
                 ┌───────────────┴───────────────┐
              Patient                          Doctor
        (implements Payable)

Payable (interface)  ◄╌╌ implements ╌╌  Patient
Department (enum)    used by Patient, Doctor
HospitalService — holds Patient[], Doctor[]; used by HospitalApp (main menu)
```
