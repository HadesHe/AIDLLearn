// IBookManager.aidl
package com.hzjytech.hades.aidllearn;
import com.hzjytech.hades.aidllearn.Book;
interface IBookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   List<Book> getBookList();
   void addBook(in Book book);
}
