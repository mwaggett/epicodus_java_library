# Library Catalog

##### _Many-to-Many Relationships practice for Epicodus, 3 September 2015_

#### By **Maggie O'Neill & Molly Waggett**

## Description

This app allows a user to choose whether they are a librarian or a library patron and view the library's catalog accordingly. Librarians may view books, search books by title or author, add new books, edit books (including adding authors), and delete books. _**Patrons section is not complete. Eventually, patrons should be able to view books, search books by title or author, check books out, return books, and view their checkout history.**_

## Setup

* Set up the database in PostgreSQL by running the following commands in your terminal:
```
  psql
  CREATE DATABASE library;
  \c library;
  CREATE TABLE books (id serial PRIMARY KEY, title varchar, patron_id int);
  CREATE TABLE authors (id serial PRIMARY KEY, name varchar);
  CREATE TABLE books_authors (id serial PRIMARY KEY, book_id int, author_id int);
  CREATE TABLE patrons (id serial PRIMARY KEY, name varchar);
  CREATE TABLE checkouts (id serial PRIMARY KEY, book_id int, patron_id int);
```
* If you wish to run tests, create a test database:
```
  CREATE DATABASE library_test WITH TEMPLATE library;
```
* Clone this repository.
* Using the command line, navigate to the top level of the cloned directory.
* Make sure you have gradle installed. Then run the following command in your terminal:
```
  gradle run
```
* Go to localhost:4567.
* Go!

## Technologies Used

* Java
* PostgreSQL
* Spark
* Velocity
* Gradle
* JUnit
* FluentLenium

### Legal

Copyright (c) 2015 **Maggie O'Neill & Molly Waggett**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
