package com.amaoamao.hsq.bookeeping;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.amaoamao.hsq.bookeeping.View.MyViewPager;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.ToDoubleFunction;

import java.util.List;


public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View v;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        initViewPager();
        initTopBar();
        return v;
    }

    private void initTopBar() {
        List<Debt> all = Debt.findAll(Debt.class);
        double sumAll = Stream.of(all).map(new Function<Debt, Double>() {
            @Override
            public Double apply(Debt debt) {
                return (debt.getIn() ? -1 : 1) * debt.getAmount();
            }
        }).mapToDouble(new ToDoubleFunction<Double>() {
            @Override
            public double applyAsDouble(Double aDouble) {
                return aDouble;
            }
        }).sum();
        ((TextView) v.findViewById(R.id.tv_amount_in)).setText(String.valueOf(sumAll));
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    private void initViewPager() {
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tl_main);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tabLayout.setTabTextColors(getResources().getColor(R.color.normalColor), getResources().getColor(R.color.white));
        MyViewPager pager = (MyViewPager) v.findViewById(R.id.vp_main);
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            int[] titleList = {R.string.tab_detail, R.string.tab_type, R.string.tab_account};

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(titleList[position]);
            }

            @Override
            public Fragment getItem(int position) {
                return DetailFragment.newInstance();
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

    }

}
