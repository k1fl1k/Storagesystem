# Storage System Console Program in Java (OOP)

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Documentation](#documentation)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)

## Introduction

Welcome to the Storage System Console Program in Java, built using Object-Oriented Programming (OOP). This program serves as a basic storage management system, demonstrating OOP principles to enhance code organization and reusability.

## Features

- **Object-Oriented Design:** The program leverages OOP concepts, including classes, objects, encapsulation, and inheritance, to provide a structured and modular solution.
- **Storage Management:** The system allows users to manage storage units, categorize items, and perform basic operations on stored items.
- **User-friendly Interface:** The console-based interface provides an intuitive way for users to interact with the storage system.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- Java Development Kit (JDK)
- Git
- Command-line interface (CLI) or Terminal

## Getting Started

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/k1fl1k/Storagesystem.git
   ```

2. Navigate to the project directory:

   ```bash
   cd Storagesystem
   ```

3. Compile the Java source files:

   ```bash
   javac Main.java
   ```

4. Run the program:

   ```bash
   java Main
   ```

## Usage

Follow the on-screen instructions to interact with the storage system. Use the provided commands and inputs to manage storage units, categorize items, and perform various operations.

## Documentation

For detailed information about the classes, methods, and overall structure of the program, refer to the [documentation](docs/).

## Examples

Here are some examples of how to use the storage system:

1. **Example 1: Add a Storage Unit**

   ```java
   // Create a new storage unit
   StorageUnit myStorageUnit = new StorageUnit("Kitchen");
   
   // Add the storage unit to the system
   storageSystem.addStorageUnit(myStorageUnit);
   ```

2. **Example 2: Add an Item to a Storage Unit**

   ```java
   // Create a new item
   Item myItem = new Item("Toaster", 1);
   
   // Add the item to a specific storage unit
   storageSystem.addItemToStorageUnit(myItem, "Kitchen");
   ```

## Contributing

If you would like to contribute to the development of this storage system, please follow the guidelines in [CONTRIBUTING.md](CONTRIBUTING.md).

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute the code for both personal and commercial purposes.
