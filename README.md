# LessonSchedulingSystem

## Project Overview
The **LessonSchedulingSystem** is a project for **SOEN 342 - Section II** that manages group and private lessons. Clients can browse, book, and manage classes, while instructors and administrators oversee scheduling, availability, and offerings. Users access the system based on their roles (Client, Instructor, or Admin).

## Team Members
- **Abdelrahman Alkhabbaz** - 40258582
- **Gabriel Derhy** - 40247254

## Sprint 1 Goals
- Set up team and project repository.
- Developed a use case diagram to outline core functionalities.
- Established the foundational system structure.

## Sprint 2 Goals
- Created a UML domain model and package diagram.
- Designed system sequence diagrams, handling lesson conflicts and instructor availability.
- Defined system operations and operation contracts for role-specific tasks.
- Developed UML interaction diagrams showing collaborations among users, offerings, and the system.
- Completed a class diagram illustrating relationships between users, lessons, bookings, and schedules.
- Implemented core classes in Java, integrating methods for lesson creation, booking, and offering selection with user input.

## Sprint 3 Goals
- Refined existing artifacts based on updated requirements for lesson booking and scheduling.
- Enhanced system functionality for client bookings, adding validations for schedule conflicts and availability.
- Updated diagrams (domain model, package, sequence, interaction, and class) to reflect refined system requirements and functionality.
- Modified Java classes to align with the updated class structure, ensuring consistency across the system.

## Sprint 4 Goals
- **Database Integration**:
  - Designed and implemented a relational database model consistent with the class diagrams.
  - Developed a fully functional **DAO (Data Access Object)** package, including `DatabaseHandler` and `DatabaseInit` classes for database connection, table creation, and data handling.
  - Created methods for CRUD operations (insert, update, delete) and querying the database for users, lessons, bookings, and instructor availability.

- **System Enhancements**:
  - Enhanced the booking and scheduling process with validations for underaged clients, lesson conflicts, and instructor availability.
  - Implemented OCL constraints to enforce business rules like unique offerings, no overlapping bookings, and role-specific operations.
  - Ensured lessons adhere to valid durations and booking capacities.

- **Refinements**:
  - Updated all UML diagrams (class, domain, package, sequence, interaction) to reflect the finalized system structure and logic.
  - Refined operation contracts and system operations for clarity and consistency with the implementation.
  - Improved the Java implementation to align with the refined class diagrams, ensuring seamless interaction between the application and the database.

- **Additional Deliverables**:
  - Defined precise **OCL** expressions to ensure consistency.
  - Developed a complete and consistent **relational data model** based on the finalized class diagrams and system requirements.

- **Final Demonstration**:
  - Delivered a 5-minute video showcasing the entire system functionality, including database integration and system workflows.
  - **[Watch the Video Here](https://www.youtube.com/watch?v=3X3uxteTc4Y&ab_channel=GabrielDerhy)**

