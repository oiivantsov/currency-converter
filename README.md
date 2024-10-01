# Currency Converter Application

## Overview

The Currency Converter Application is a Java-based desktop application that allows users to convert currencies, manage exchange rates, and track conversion transactions. It features a graphical user interface (GUI) built with JavaFX and uses a MariaDB database for data storage, implemented via JPA (Java Persistence API).

## Features

1. **Currency Conversion**: Convert between different currencies using current exchange rates.
2. **Manage Currencies**: Add, update, or delete currency details such as exchange rates.
3. **Transaction History**: Store and manage conversion transactions for tracking purposes.
4. **User-friendly GUI**: A JavaFX-based user interface for seamless interaction.
5. **Persistence Layer**: Data stored in a MariaDB database using JPA for robust data management.

## Technologies Used

- **Java**: Core programming language used for application logic.
- **JavaFX**: Used to create a graphical user interface.
- **JPA (Jakarta Persistence API)**: To manage persistence with the MariaDB database.
- **MariaDB**: Database system for storing currencies and transactions.
- **Maven**: Build tool used for dependency management and project building.

## Project Structure

The application follows an MVC (Model-View-Controller) pattern to ensure separation of concerns and easy maintainability.

### Packages

- **controller**: Contains the `ConverterController` class which manages interactions between the view and data access layers.
- **dao**: Data access classes (`CurrencyDao`, `TransactionDao`) that interact with the database.
- **entity**: Entity classes (`Currency`, `Transaction`) that map to database tables.
- **datasource**: Contains the `MariaDbJpaConnection` class, which provides a singleton `EntityManager` for database operations.
- **view**: Contains the `ConverterUI` JavaFX application for the user interface.

### Key Components

- **ConverterController**: Manages conversion logic, currency updates, and transactions.
- **CurrencyDao** & **TransactionDao**: Handles database operations like finding, persisting, updating, and deleting `Currency` and `Transaction` entities.
- **Currency**: Represents a currency entity with fields like code, name, and rate.
- **Transaction**: Represents a conversion transaction with source and destination currencies and amounts.
- **MariaDbJpaConnection**: Singleton for managing the `EntityManager` used to connect to the database.
- **ConverterUI**: Provides a user interface for the application, allowing users to perform currency conversions and manage currencies.

## How to Use

1. **Setup the Database**: Ensure MariaDB is installed and configured. Update `persistence.xml` with correct database credentials.
2. **Build the Application**: Use Maven to build the application (`mvn clean install`).
3. **Run the Application**: Execute the `Main` class to start the application. This will open the JavaFX interface for user interaction.

### User Interface Guide

- **Currency Conversion**:
  1. Select the source and target currencies from the dropdown menus.
  2. Enter the amount to convert.
  3. Click "Convert" to see the result.
- **Swap Currencies**: Use the "Swap" button to quickly exchange the source and target currencies.
- **Add/Update/Delete Currencies**:
  1. Open the "Config" menu and select "Add / Update / Delete".
  2. Enter the currency details (code, name, rate) and click "Add / Update" or "Delete".

## Database Configuration

The application uses JPA to interact with a MariaDB database. Update `persistence.xml` to create the necessary tables and configure database connection properties (`database URL`, `username`, `password`).
In `data.sql`, initial data for the `Currency` table is provided to bootstrap the application with commonly used currencies. This file contains the following SQL script:

```sql
INSERT INTO Currency (`code`, `name`, `exchange_rate`) VALUES
    ('USD', 'US Dollar', 1.000000),
    ('EUR', 'Euro', 0.930000),
    ('GBP', 'British Pound', 0.810000),
    ('JPY', 'Japanese Yen', 149.000000),
    ('AUD', 'Australian Dollar', 1.540000),
    ('CAD', 'Canadian Dollar', 1.370000),
    ('CHF', 'Swiss Franc', 0.910000),
    ('CNY', 'Chinese Yuan', 7.300000);
```

Make sure this file (`data.sql`) is executed when setting up the database to ensure that the initial currency data is loaded into your system. You can use your database tool to run this script or configure it to run automatically when the application starts.

## Dependencies

- **Java 17** (or later)
- **MariaDB Database**
- **Maven** (for building)
- **Jakarta Persistence API (JPA)**

## Running the Application

1. **Database Setup**: Make sure MariaDB is running, and create the necessary schema based on the persistence unit defined in `persistence.xml`.
2. **Launch Application**: Run the `Main` class.
3. **GUI Interaction**: Use the graphical interface to convert currencies or add/update/delete currencies as needed.

## Future Improvements

1. **Logging**: Implement logging for better debugging and error tracking.
2. **Exception Handling**: Add more robust error handling for database issues and user input errors.
3. **Enhanced GUI**: Add more features, like historical exchange rate tracking and visualization of transaction history.

## License

This project is open-source. Feel free to modify and enhance it as per your needs.

## Contact

For questions or contributions, feel free to reach out via the project's GitHub repository.
