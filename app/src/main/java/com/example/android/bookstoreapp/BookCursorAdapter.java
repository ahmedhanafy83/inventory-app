package com.example.android.bookstoreapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.BookContract;
import com.example.android.bookstoreapp.data.BookContract.BookEntry;

import static android.content.ContentValues.TAG;

class BookCursorAdapter extends CursorAdapter {
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity_text_view);
        Button saleButton = (Button) view.findViewById(R.id.sale_button);


        // Find the columns of book attributes that we're interested in
        final int columnIdIndex = cursor.getColumnIndex(BookEntry._ID);
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRICE);
        final int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_QUANTITY);

        // Read the book attributes from the Cursor for the current book
        final String productID = cursor.getString(columnIdIndex);
        String bookName = cursor.getString(nameColumnIndex);
         final int bookQuantity = cursor.getInt(quantityColumnIndex);
        String bookPrice = cursor.getString(priceColumnIndex);

        // If the book breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(bookPrice)) {
            summaryTextView.setVisibility(View.GONE);
        }

        // Update the TextViews with the attributes for the current book
        nameTextView.setText(bookName);
        summaryTextView.setText(bookPrice);
        quantityTextView.setText(String.valueOf(bookQuantity));

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity Activity = (MainActivity) context;
                Activity.productSaleCount(Integer.valueOf(productID), Integer.valueOf(bookQuantity));

            }

            private void adjustQuantity(Context context, Uri bookUri, int bookQuantity) {
                // Subtract 1 from current value if quantity of product >= 1
                int newQuantityValue = (bookQuantity >= 1) ? bookQuantity - 1 : 0;

                if (bookQuantity == 0) {
                    Toast.makeText(context.getApplicationContext(), R.string.toast_out_of_stock_msg, Toast.LENGTH_SHORT).show();
                }

                // Update table by using new value of quantity
                ContentValues contentValues = new ContentValues();
                contentValues.put(BookEntry.COLUMN_QUANTITY, newQuantityValue);
                int numRowsUpdated = context.getContentResolver().update(bookUri, contentValues, null, null);
                if (numRowsUpdated > 0) {
                    // Show error message in Logs with info about pass update.
                    Log.i(TAG, context.getString(R.string.sale_msg_confirm));
                } else {
                    Toast.makeText(context.getApplicationContext(), R.string.no_product_in_stock, Toast.LENGTH_SHORT).show();
                    // Show error message in Logs with info about fail update.
                    Log.e(TAG, context.getString(R.string.error_msg_stock_update));
                }
            }
        });
    }
}
