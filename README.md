# 💻OOP-Mini-Project
For this project, we created a simple code demonstration of a **Library Management System**.

Group Member:
  1. Choh Jing Yi
  2. Neo Li Xin


# 📄Project Description
In this system there are 3 main users: **Student, Staff and Librarian**. Student and Staff are categoried as Member that can **check account status, search, borrow, return and view all books**. The Librarian serve as the admin who manages tasks such as **add book, remove book, check book availability, edit book, view all book, check member details and view all member details**. The key feature that separates Student and Teacher are their **ID** and the **limit of book they can borrow, which are 3 and 5 respectively**. 

The Library Management System serves as a centralized platform for Student, Staff and Librarian to effectively manage their respective tasks and responsibilities.

Below is a **UML Class Diagram** that shows the relationship between different classes in the Library Management System.

![UML Class Diagram](./images/UML%20Class%20Diagram.png)
# 💡How each topic (Ch5-Ch9) is implemented

# Chapter 5: ArrayList/Vector
ArrayList used for:
  1. Storing collection of books (ArrayList<Book>)
  2. Managing registered members (ArrayList<Member>)
  3. HashMap used for tracking borrow records (HashMap<String, BorrowRecord>)

Operations used:
  1. add()/remove() for book management

# Chapter 6: Class Relationships
Association:
  1. Library has Books 
  2. Library has Members 

Aggregation:
  1. Librarian manages Books 

Composition:
  1. BorrowRecord composed of Member and Book 

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

# 🖼️Project Output
**1. Library Management System Login Interface**<br>
![Library Management System Login Interface](./images/Library%20Management%20System%20Login%20Interface.png)

**2a(i). Login as Librarian**<br>
![Librarian Correct Password](./images/Library%20Correct%20Password.png)

**2a(ii). If the input password is wrong, access will be denied.**<br>
![Librarian Wrong Password](./images/Librarian%20Wrong%20Password.png)

**2b. Add New Book**<br>
![Add Book](./images/Add%20Book.png)

**2c. Remove Book**<br>
![Remove Book](./images/Remove%20Book.png)

**2d. Check Book Availability**<br>
![Check Book Availability](./images/Check%20Book%20Availability.png)

**2e. Edit Book Details**<br>
![Edit Book Details](./images/Edit%20Book.png)

**2f. View All Books**<br>
![View All Books](./images/Librarian%20View%20All%20Books.png)

**2g. Check Member Details**<br>
![Check Member Details](./images/Check%20Member%20Details.png)

**2h. View All Member Details**<br>
![View All Member Details](./images/View%20All%20Member%20Details.png)

**3a(i). Login as Student Member**<br>
![Student Register](./images/Student%20Register.png)

**3a(ii). Login as Staff Member**<br>
![Staff Register](./images/Staff%20Register.png)

**3b(i). Check Account Status as Student Member**<br>
![Student Check Account](./images/Student%20Check%20Account.png)

**3b(i). Check Account Status as Staff Member**<br>
![Staff Check Account](./images/Staff%20Check%20Account.png)

**3c. Search Book**<br>
![Search Books](./images/Search%20Book.png)

**3d(i). Borrow Book**<br>
![Borrow Book](./images/Borrow%20Book.png)

**3d(ii). Borrow Book Limit**<br>
**Since the limit of book allowed to be borrowed by a student member is 3, if a student try to borrow more than 3, below message will be shown.**<br>
![Borrow Book](./images/Book%20Limit%20Reached.png)

**Same goes for staff member except the limit is 5, if a staff try to borrow more than 5 below message will be shown.**<br>
![Borrow Book](./images/Book%20Limit%20Reached%202.png)

**3e. Return Book**<br>
![Return Book](./images/Return%20Book.png)

**3f. View All Books**<br>
![View All Books](./images/Librarian%20View%20All%20Books.png)<br>
**After books are borrowed, the copies will automatically update.**<br>
![Updated View All Books](./images/Updated%20Member%20View%20All%20Books.png)

**4. Exit System**<br>
![Exit System](./images/Exit%20System.png)<br>
