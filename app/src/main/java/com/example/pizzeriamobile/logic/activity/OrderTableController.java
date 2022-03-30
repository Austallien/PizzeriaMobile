package com.example.pizzeriamobile.logic.activity;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pizzeriamobile.R;
import com.example.pizzeriamobile.activity.ActivityNavigation;
import com.example.pizzeriamobile.logic.model.Order;

import java.util.ArrayList;

public class OrderTableController {
    final static private int MenuItemLayout = R.layout.order_list_item;

    public static boolean InsertData(@Nullable Context context, @NonNull TextView[] headerTableRow, @NonNull TableLayout table, ArrayList<Order> Data) throws Exception {
        ArrayList<Order> data = new ArrayList<Order>();
        data.addAll(Data);
        data.addAll(Data);
        data.addAll(Data);
        data.addAll(Data);
        if(data == null)
            return false;

        if(table.getChildCount() > 0)
            table.removeAllViews();
        for(int i = 0; i < data.size(); i++){
            try {
                View tableRow = LayoutInflater.from(context).inflate(MenuItemLayout, null, false);

                Order orderData = data.get(i);
                TextView orderId = (TextView) tableRow.findViewById(R.id.textViewHeaderOrderId);
                TextView orderDate = (TextView) tableRow.findViewById(R.id.textViewHeaderOrderDate);
                TextView orderTotalPrice = (TextView) tableRow.findViewById(R.id.textViewHeaderOrderTotalPrice);
                //TextView orderStatus = (TextView) tableRow.findViewById(R.id.textViewRowOrderStatus);
                TextView orderReceivingMethod = (TextView) tableRow.findViewById(R.id.textViewHeaderOrderReceivingMethod);

                orderId.setText(String.valueOf(orderData.getId()));
                orderDate.setText(new java.sql.Date(orderData.getRegistrationDate()).toString());
                orderTotalPrice.setText(String.valueOf(orderData.getTotalPrice()));
                //orderStatus.setText(orderData.getStatus());
                orderReceivingMethod.setText(orderData.getReceivingMethod());

                if(i%2 == 1)
                    tableRow.setBackgroundColor(context.getResources().getColor(R.color.orderTableOddRowColor));

                table.addView(tableRow);

                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TableRow tableRow = (TableRow) view;
                        long orderId = Long.parseLong(((TextView)tableRow.getChildAt(0)).getText().toString());
                        Toast.makeText(context,"Not Implemented Method", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception ex){
                ex.printStackTrace();
                throw new Exception("TableRowAddingException_DataPosition="+i);
            }
        }

        //if data has been loaded faster than activity layout reflected


        //Relocates table header after onWindowFocusChanged event
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                TableRow firstTableRow = ((TableRow)table.getChildAt(0));
                for(int i = 0; i < headerTableRow.length;i++)
                {
                    TextView textViewHeader = headerTableRow[i];
                    TextView textViewTable = (TextView)firstTableRow.getChildAt(i);

                    int[] XY = new int[2];
                    textViewTable.getLocationInWindow(XY);
                    textViewHeader.setX(XY[0]);
                    textViewHeader.setWidth(textViewTable.getWidth());
                }
            }
        });
        return true;
    }

}
