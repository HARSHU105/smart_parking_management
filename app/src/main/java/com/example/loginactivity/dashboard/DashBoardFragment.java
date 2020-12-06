package com.example.loginactivity.dashboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.loginactivity.BaseFragment;
import com.example.loginactivity.MapService.MapServiceActivity;
import com.example.loginactivity.PrefManager;
import com.example.loginactivity.R;
import com.example.loginactivity.feedbackActivity.FeedBackActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DashBoardFragment extends BaseFragment implements View.OnClickListener {


    ViewPager viewPager;
    List<Integer> banners;
    CardView cvService,  cvfeedback,cvService1,  cvfeedback1;
    ImageView imgService;
    CustomPagerAdapter mBannerAdapter;
    CirclePageIndicator circlePageIndicator;
    TextView txtName, txtVehicle;

    PrefManager prefManager;
    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dash_board, container, false);

        prefManager =new PrefManager(this.getActivity());
        initialize(view);
        setBanner();
        return view;
    }

    private void initialize(View view) {


        viewPager = (ViewPager) view.findViewById(R.id.pager);
        circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);

        cvService = (CardView) view.findViewById(R.id.cvService);

        cvfeedback = (CardView) view.findViewById(R.id.cvfeedback);

        cvService1 = (CardView) view.findViewById(R.id.cvService1);

        cvfeedback1 = (CardView) view.findViewById(R.id.cvfeedback1);
        imgService = (ImageView) view.findViewById(R.id.imgService);


        txtName = view.findViewById(R.id.txtName);
        txtVehicle = view.findViewById(R.id.txtVehicle);

        txtName.setText(" Hi " + prefManager. geUserName());

        cvService.setOnClickListener(this);

        cvfeedback.setOnClickListener(this);

        cvfeedback1.setOnClickListener(this);

        cvService1.setOnClickListener(this);
    }

    private void setBanner() {
        banners = new ArrayList<>();

        banners.add(R.drawable.logo);
        banners.add(R.drawable.banner2);

        banners.add(R.drawable.banner3);
        banners.add(R.drawable.banner4);
        banners.add(R.drawable.banner5);

        banners.add(R.drawable.banner6);
        banners.add(R.drawable.banner7);
        mBannerAdapter = new CustomPagerAdapter(getContext(), DashBoardFragment.this, banners);


        if (viewPager != null && circlePageIndicator != null) {
            viewPager.setAdapter(mBannerAdapter);
            circlePageIndicator.setViewPager(viewPager);

            Timer timer = new Timer();
            timer.schedule(new RemindTask(banners.size(), viewPager), 0, 1500);


        }
    }


    class RemindTask extends TimerTask {
        private int numberOfPages;
        private ViewPager mViewPager;
        private int page = 0;

        public RemindTask(int numberOfPages, ViewPager mViewPager) {
            this.numberOfPages = numberOfPages;
            this.mViewPager = mViewPager;
        }

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        if (page > numberOfPages) { // In my case the number of pages are 5
                            mViewPager.setCurrentItem(0);
                            page = 0;
                        } else {
                            mViewPager.setCurrentItem(page++);
                        }
                    }
                });
            }

        }
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.cvService:

                startActivity(new Intent(getActivity(), MapServiceActivity.class));
                break;



            case R.id.cvfeedback:

                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;



            case R.id.cvfeedback1:

                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;

        }


//        switch (showcase_counter) {
//            case 0:
//
//                showcaseView = new ShowcaseView.Builder(getActivity())
//                        .setTarget(Target.NONE)
//                        .setContentTitle("Request")
//                        .setContentText("You can track your Request and Upload the Related Document here")
//                        .setStyle(R.style.ShowcaseViewStyle)
//                       // .singleShot(SHOWCASEVIEW_ID1)
//                        .hideOnTouchOutside();
//                showcaseView.build();
//                showcase_counter++;
//                break;
//
//            case 1:
//                showcaseView.setTarget(Target.NONE);
//                showcaseView.setOnClickListener(this);
//                showcaseView.setContentTitle("Title");
//                showcaseView.setContentText("some text");
//              //  showcaseView.singleShot(SHOWCASEVIEW_ID2);
//                showcaseView.hideOnTouchOutside();
//                showcase_counter++;
//                break;
//
//
//            case 2:
//                showcaseView.hideOnTouchOutside();
//                break;
//        }


    }


}