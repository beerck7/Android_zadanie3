package com.example.todoapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public static final String ARG_DATE = "arg_date";
    public static final String REQUEST_KEY = "request_date";
    public static final String RESULT_DATE = "result_date";

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date = (Date) (getArguments() != null ? getArguments().getSerializable(ARG_DATE) : new Date());

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(requireContext(), (view, y, m, d) -> {
            Date resultDate = new GregorianCalendar(y, m, d).getTime();
            Bundle result = new Bundle();
            result.putSerializable(RESULT_DATE, resultDate);
            getParentFragmentManager().setFragmentResult(REQUEST_KEY, result);
        }, year, month, day);
    }
}