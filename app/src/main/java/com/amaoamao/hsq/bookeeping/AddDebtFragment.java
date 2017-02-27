package com.amaoamao.hsq.bookeeping;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.webkit.WebHistoryItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Account;
import com.amaoamao.hsq.bookeeping.Entity.Debt;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;


public class AddDebtFragment extends Fragment {
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat format = new SimpleDateFormat("MM - dd", Locale.getDefault());
    private Account account = Account.findFirst(Account.class);
    private String description = "";
    private boolean isIn = false;
    private OnFragmentInteractionListener mListener;
    private View v;
    private TextView tv_debt_date;
    private TextView tv_debt_description;
    private TextView tv_debt_account;
    private int which = 0;
    private EditText et_debt_amount;

    public AddDebtFragment() {
    }

    public static AddDebtFragment newInstance() {
        return new AddDebtFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (v == null) {
            v = inflater.inflate(R.layout.fragment_add_debt, container, false);
            v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(final View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    Animator circularReveal = ViewAnimationUtils.createCircularReveal(v, v.getRight(), v.getBottom(), 0, (float) Math.hypot(v.getRight(), v.getBottom()));
                    circularReveal.setInterpolator(new DecelerateInterpolator(2f));
                    circularReveal.setDuration(700);
                    circularReveal.start();
                    ValueAnimator anim = new ValueAnimator();
                    anim.setIntValues(Color.parseColor("#FF4081"), Color.parseColor("#FFFFFF"));
                    anim.setEvaluator(new ArgbEvaluator());
                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            v.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
                        }
                    });
                    anim.setDuration(700);
                    anim.start();
                }
            });
            init(v);
        }
        return v;
    }

    private void init(View v) {
        tv_debt_account = (TextView) v.findViewById(R.id.tv_debt_account);
        tv_debt_date = (TextView) v.findViewById(R.id.tv_debt_date);
        tv_debt_description = (TextView) v.findViewById(R.id.tv_debt_description);
        tv_debt_date.setText(format.format(c.getTime()));
        tv_debt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        c.set(year, month, dayOfMonth);
                        tv_debt_date.setText(format.format(c.getTime()));
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        tv_debt_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext()).title("备注").input(null, description, true, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        description = input.toString();
                    }
                }).show();
            }
        });
        tv_debt_account.setText(account.getName());
        tv_debt_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(v.getContext()).title("支付账户").items(Account.findAll(Account.class)).itemsCallbackSingleChoice(AddDebtFragment.this.which, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        AddDebtFragment.this.which = which;
                        account = Account.find(Account.class, which + 1);
                        tv_debt_account.setText(account.getName());
                        return true;
                    }
                }).show();
            }
        });
        final Spinner spinner = (Spinner) v.findViewById(R.id.spinner_debt_isIn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    isIn = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_debt_amount = (EditText) v.findViewById(R.id.et_debt_amount);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean save(FloatingActionButton fab) {
        if (et_debt_amount.getText().toString().isEmpty()) {
            Snackbar.make(fab, "填写金额", Snackbar.LENGTH_SHORT).show();
            return false;
        }
        Debt debt = new Debt();
        debt.setTimeCreated(c.getTime());
        debt.setAmount((isIn ? 1 : -1) * Double.parseDouble(et_debt_amount.getText().toString()));
        debt.setDescription(description);
        debt.setPayedBy(account);
        debt.setIn(isIn);
        return debt.save();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
