package brainwiz.gobrainwiz.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 */
public class DatePickerFragment extends DialogFragment {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private Date currentDate;
    private DateListener listener = null;
    private boolean isFutureDateEnable = true;
    private boolean isPastDateEnable = true;
    private String currentDateFormat = DATE_FORMAT;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int year;
        int dayOfMonth;
        int month;
        Calendar instance = Calendar.getInstance();
        if (currentDate != null) {
            instance.setTime(currentDate);
        }
        year = instance.get(Calendar.YEAR);
        month = instance.get(Calendar.MONTH);
        dayOfMonth = instance.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), onDateSetListener, year, month, dayOfMonth);
        if (!isFutureDateEnable) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }

        if (!isPastDateEnable) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        return datePickerDialog;
    }

    public void setInputDate(String date) {
        setInputDate(date, DATE_FORMAT);
    }

    public void setInputDate(String date, String dateFormat) {
        currentDateFormat = dateFormat;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            currentDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            currentDate = null;
        }

    }

    public void setListener(DateListener listener) {
        this.listener = listener;
    }


    private final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(currentDateFormat, Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            if (listener != null) {
                listener.onDateSelected(simpleDateFormat.format(calendar.getTime()), String.valueOf(calendar.getTimeInMillis()));
            }
//            Toast.makeText(getActivity(), simpleDateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
        }
    };

    public void setFutureDateEnable(boolean futureDateEnable) {
        isFutureDateEnable = futureDateEnable;
    }

    public void setPastDateEnable(boolean pastDateEnable) {
        isPastDateEnable = pastDateEnable;
    }

    public interface DateListener {
        void onDateSelected(String value, String timeStamp);
    }

}
