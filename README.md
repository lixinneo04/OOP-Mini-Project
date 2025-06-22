# OOP-Mini-Project
The system we chose is Library Management System.

Group Member:
1. Choh Jing Yi
2. Neo Li Xin

# Project Description
In this system there are 3 main users: **Student, Staff and Librarian**. Student and Staff are categoried as Member that can **check account status, search, borrow, return and view all books**. The Librarian serve as the admin who manages tasks such as **add book, remove book, check book availability, edit book, view all book, check member details and view all member details**. The key feature that separates Student and Teacher are their **ID** and the **limit of book they can borrow, which are 3 and 5 respectively**. 

The Library Management System serves as a centralized platform for Student, Staff and Librarian to effectively manage their respective tasks and responsibilities.

# How each topic (Ch5-Ch9) is implemented

# Project Output

# Chapter 5: ArrayList/Vector
ArrayList used for:
1. Storing collection of books (ArrayList<Book>)
2. Managing registered members (ArrayList<Member>)
3. HashMap used for tracking borrow records (HashMap<String, BorrowRecord>)

Operations used:
1. add()/remove() for book management

# Chapter 6: Class Relationships
Association:
Library has Books 
Library has Members 

Aggregation:
Librarian manages Books 

Composition:
BorrowRecord composed of Member and Book 

# Chapter 7: Inheritance

Member
1. extends StudentMember
2. extends StaffMember

Key differences:
Students: 3 book limit, 14-day loan period
Staff: 5 book limit, 28-day loan period

Abstract methods in Member:
1. getMemberType()
2. getMaxBooks()
3. getLoanPeriodDays()

# Chapter 8: Polymorphism

Method overriding:
1. Different implementations of getMemberType() in subclasses
2. Custom toString() methods throughout
3. Dynamic method invocation in borrowing/returning operations

# Chapter 9: Exception Handling

Exception handling for:
1. Invalid user input
2. Book availability checks
3. Member borrowing limits
4. Removing non-existent books
5. Try-catch blocks in all user interaction points
