package com.amaoamao.hsq.bookeeping;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.amaoamao.hsq.bookeeping.Utils.Utils;
import com.amaoamao.hsq.bookeeping.View.MyViewPager;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private View v;
    public static SimpleDateFormat formateYYMM = new SimpleDateFormat("YYYY年MM月", Locale.getDefault());
    public static SimpleDateFormat formateYYYY = new SimpleDateFormat("YYYY年", Locale.getDefault());
    public static SimpleDateFormat formateMM = new SimpleDateFormat("MM月", Locale.getDefault());
    public static Calendar calendar = Calendar.getInstance();
    private List<Debt> all = new ArrayList<>();
    private Map<String, List<Debt>> monthMap;

    public MainFragment() {
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
        initTopBar(0);

        v.findViewById(R.id.tv_month_selector_month).setOnClickListener(v2 -> {
            monthMap = Stream.of(all)
                    .collect(Collectors.groupingBy(debt -> formateYYMM.format(debt.getTimeCreated())));
            new MaterialDialog.Builder(getContext())
                    .items(
                            monthMap.keySet()
                    ).itemsCallback((dialog, itemView, position, text) -> {
                calendar.setTime(monthMap.get(text).get(0).getTimeCreated());
                initTopBar(0);
                for (Fragment f : getActivity().getSupportFragmentManager().getFragments()) {
                    if (f instanceof DetailFragment) {
                        if (f.getView() != null) {
                            ((MyDetailRecyclerViewAdapter) ((RecyclerView) f.getView()).getAdapter()).getmValues().clear();
                            ((MyDetailRecyclerViewAdapter) ((RecyclerView) f.getView()).getAdapter()).getmValues().addAll(all);
                            ((DetailFragment) f).refreshRV();
                        }
                    } else if (f instanceof AccountFragment) {
                        ((AccountFragment) f).refreshRV();
                    }
                }
            }).show();
        });

        return v;
    }

    public void initTopBar(int position) {
        all.clear();
        all.addAll(Debt.findAll(Debt.class));
        double sumAll = Utils.sumAll(all);
        double inThisMonth = Utils.inThisMonth(all, calendar);
        double outThisMonth = Utils.outThisMonth(all, calendar);
        double leftThisMonth = inThisMonth + outThisMonth;
        ((TextView) v.findViewById(R.id.tv_month_selector_month)).setText(formateMM.format(calendar.getTime()));
        ((TextView) v.findViewById(R.id.tv_month_selector_year)).setText(formateYYYY.format(calendar.getTime()));
        if (position == 0) {
            ((TextView) v.findViewById(R.id.tv_top_bar_1_hint)).setText("支出");
            ((TextView) v.findViewById(R.id.tv_top_bar_1_content)).setText(String.valueOf(outThisMonth));
            ((TextView) v.findViewById(R.id.tv_top_bar_2_hint)).setText("收入（元）");
            ((TextView) v.findViewById(R.id.tv_top_bar_2_content)).setText(String.valueOf(inThisMonth));
        } else {
            ((TextView) v.findViewById(R.id.tv_top_bar_1_hint)).setText("本月结余");
            ((TextView) v.findViewById(R.id.tv_top_bar_1_content)).setText(String.valueOf(leftThisMonth));
            ((TextView) v.findViewById(R.id.tv_top_bar_2_hint)).setText("总余额");
            ((TextView) v.findViewById(R.id.tv_top_bar_2_content)).setText(String.valueOf(sumAll));
        }

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
            int[] titleList = {R.string.tab_detail, R.string.tab_account};

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(titleList[position]);
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return DetailFragment.newInstance();
//                    case 1:
//                        return TypeChartFragment.newInstance();
                    case 1:
                        return AccountFragment.newInstance();
                    default:
                        return DetailFragment.newInstance();
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initTopBar(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(pager);

    }

}
