# EDA Java Samples

This repository contains code samples for presentation purposes. It demonstrates simple Java classes with corresponding unit tests using JUnit 5.

## Project Structure

```
eda-java-samples/
├── pom.xml                                    # Maven configuration
├── src/
│   ├── main/java/com/example/
│   │   └── Example.java                       # Example class with sample methods
│   └── test/java/com/example/
│       └── ExampleTest.java                   # JUnit 5 tests for Example class
└── README.md                                  # This file
```

## Prerequisites

- Java 11 or later
- Maven 3.6.0 or later

## Setup Instructions

1. **Install Java 11 or later**: Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)

2. **Install Maven**: Download from [Apache Maven](https://maven.apache.org/download.cgi)
   - Extract to a directory (e.g., `C:\apache-maven-3.9.4`)
   - Add the `bin` directory to your system PATH
   - Verify installation: `mvn --version`

## Running the Project

### Compile the project
```bash
mvn compile
```

### Run tests
```bash
mvn test
```

### Clean and test
```bash
mvn clean test
```

### Generate test report
```bash
mvn surefire-report:report
```

## Example Class Features

The `Example` class demonstrates:

- **Constructor and basic properties**
- **String manipulation** (greeting method)
- **Mathematical operations** (addition, factorial)
- **Algorithm implementation** (palindrome checking)
- **Collection processing** (filtering even numbers)
- **Error handling** (exception throwing for invalid input)

## Test Coverage

The `ExampleTest` class showcases:

- **JUnit 5 annotations** (`@Test`, `@BeforeEach`, `@DisplayName`, `@Nested`)
- **Assertion methods** (`assertEquals`, `assertTrue`, `assertFalse`, `assertThrows`)
- **Test organization** with nested test classes
- **Edge case testing** (null values, empty collections, negative numbers)
- **Exception testing** with proper error message validation

## Usage in Presentations

This repository serves as a reference for:

- Java project structure with Maven
- Unit testing best practices with JUnit 5
- Test-driven development examples
- Clean code principles
- Error handling patterns

## IDE Recommendations

- **IntelliJ IDEA**: Excellent Maven and JUnit integration
- **Eclipse**: Good Maven support with built-in test runners
- **VS Code**: Use with Java Extension Pack for Maven and testing support