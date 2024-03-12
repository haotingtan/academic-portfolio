# P03 Exceptional Shopping Cart

## Overview

The "Exceptional Shopping Cart" assignment extends the functionality of a basic shopping cart (from P01) by incorporating features to enhance its robustness through exception handling. It introduces the capability to load and save the cart summary from/to a file, along with the development of unit tests to verify the implementation's correctness.

### Learning Objectives

- **Error Handling**: Learn to improve program robustness to survive unusual circumstances and handle erroneous input without crashing.
- **Exception Management**: Gain practical experience with both checked and unchecked exceptions, including how to throw, catch, and manage them.
- **Testing Skills**: Develop testing strategies, especially for scenarios that involve exceptions, to ensure program correctness under various conditions.

### Key Features

- **Exception Handling**: Methods within the shopping cart are enhanced to throw exceptions for erroneous conditions such as invalid input or misuse, improving the program's reliability and user feedback.
- **File Operations**: Ability to save the current state of the shopping cart to a file and load it back, facilitating data persistence across sessions.
- **Unit Testing**: Comprehensive tests to verify each functionality, especially focusing on exception handling and file operations, ensuring that the shopping cart behaves as expected under all scenarios.

### Technical Implementation

- **Updated Methods**: The existing methods `lookupProductById()` and `lookupProductByName()` are updated to throw exceptions for invalid inputs, enhancing input validation.
- **New Methods**: Implements new methods `addItemToMarketCatalog()`, `saveCartSummary()`, `parseCartSummaryLine()`, and `loadCartSummary()` to manage product catalog updates and cart summary file operations.
- **Exceptional Handling**: Methods are designed to throw specific exceptions like `IllegalArgumentException`, `NoSuchElementException`, `IOException`, `DataFormatException`, and handle them appropriately to maintain the application's integrity.
- **Testing**: The `ExceptionalShoppingCartTester` class implements various test methods to assert the correct behavior of the shopping cart, especially testing exception handling mechanisms and file I/O operations.

### Project Requirements

- Individual work is emphasized to foster a deep understanding of exception handling and file operations.
- The project restricts import statements to only those necessary for file operations and exception handling, ensuring focus on the Java standard library's capabilities.
- The assignment requires the implementation of additional private static helper methods and public static methods as specified, without adding any new public methods outside the provided specifications.

