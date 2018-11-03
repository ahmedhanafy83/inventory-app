package com.example.android.bookstoreapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BookContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private BookContract() {}

    public static final String CONTENT_AUTHORITY = "com.example.android.bookstoreapp";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.books/books/ is a valid path for
     * looking at book data. content://com.example.android.books/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_BOOKS = "books";
    /**
     * Inner class that defines constant values for the books database table.
     * Each entry in the table represents a single book.
     */
    public static final class BookEntry implements BaseColumns {

        /** The content URI to access the book data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of books.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single book.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        /** Name of database table for Books */
        public final static String TABLE_NAME = "books";

        /**
         * Unique ID number for the books (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * product Name of the books.
         *
         * Type: TEXT
         */
        public final static String COLUMN_PRODUCT_NAME ="ProductName";

        /**
         * price Of Books.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_PRICE = "Price";

        /**
         * price of the books.
         *

         * Type: INTEGER
         */
        public final static String COLUMN_QUANTITY = "Quantity";

        /**
         * supplier name  of the books.
         *
         * Type: TEXT
         */
        public final static String COLUMN_SUPPLIER_NAME = "SupplierName";
/**
         * supplier phone number  of the books.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "SupplierPhoneNumber";

        // SUPPLIER_NAME LIST VALUES
        public final static int SUPPLIER_UNKNOWN = 0;
        public final static int SUPPLIER_AMAZON = 1;
        public final static int SUPPLIER_JARIRR = 2;
        public final static int SUPPLIER_OBEIKAN = 3;

        /**
         * Returns whether or not the given supplier is {@link #SUPPLIER_UNKNOWN}, {@link #SUPPLIER_AMAZON},
         * or {@link #SUPPLIER_JARIRR}.
         * or {@link #SUPPLIER_OBEIKAN}.
         */
        public static boolean isValidSupplier(int supplier) {
            if (supplier == SUPPLIER_UNKNOWN || supplier == SUPPLIER_AMAZON || supplier == SUPPLIER_JARIRR|| supplier == SUPPLIER_OBEIKAN) {
                return true;
            }
            return false;
        }
    }

}
