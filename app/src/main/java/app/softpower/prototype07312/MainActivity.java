package app.softpower.prototype07312;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;

import app.softpower.prototype07312.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private ViewPager2 viewPageSetup;

    ColorStateList def;

    boolean isMenuPageOpen = false;
    boolean isSearchPageOpen = false;
    boolean isSettingPageOpen = false;
    boolean isNotiPageOpen = false;
    boolean isAvatarChecked = false;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView textSupport = binding.textSupport;
        String textTmp = "고객지원 : 02-2135-6877 (또는 채팅 상담)";
        SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 23, textTmp.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 23, textTmp.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new AbsoluteSizeSpan(35), 21, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 사이즈
        textSupport.setText(ssb);

        ///////////////////////////spinner

//        Spinner spinnerUser = binding.spinnerUser;
//        String[] itemsSpinnerUser = {"xhois1", "xhois2", "xhois3"};
//        spinnerUser.setPrompt("관리할 사용자 전환");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, itemsSpinnerUser);
//        spinnerUser.setAdapter(adapter);

        Spinner spinnerUser2 = binding.include1.spinnerUser2;
        String[] itemsSpinnerUser2 = {" 11월15일,2021-11월15일,2021", " 11월14일,2021-11월14일,2021", " 11월13일,2021-11월13일,2021"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item2, itemsSpinnerUser2);
        adapter2.setDropDownViewResource(R.layout.spinner_item2);
        spinnerUser2.setAdapter(adapter2);

        Spinner spinnerToday = binding.include1.spinnerToday;
        String[] itemsSpinnerToday = {" 오늘", " 어제", " 그저께"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, R.layout.spinner_item3, itemsSpinnerToday);
        adapter3.setDropDownViewResource(R.layout.spinner_item3);
        spinnerToday.setAdapter(adapter3);

        Spinner spinnerToday2 = binding.include1.spinnerToday2;
        spinnerToday2.setAdapter(adapter3);

        Spinner spinnerToday3 = binding.include1.spinnerToday3;
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(
                this, R.layout.spinner_item4, itemsSpinnerToday);
        adapter4.setDropDownViewResource(R.layout.spinner_item4);
        spinnerToday3.setAdapter(adapter4);

        Spinner spinnerToday4 = binding.include1.spinnerToday4;
        spinnerToday4.setAdapter(adapter4);

        Spinner spinnerLanguage = binding.spinnerLanguage;
        String[] itemsSpinnerLanguage = {"한국어", "English", "Japanese"};
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(
                this, R.layout.spinner_item4, itemsSpinnerLanguage);
        adapterLanguage.setDropDownViewResource(R.layout.spinner_item4);
        spinnerLanguage.setAdapter(adapterLanguage);

        ////////////item2 layout***************************************************************************************************

        Spinner spinnerUser2_2 = binding.include1.spinnerUser22;
        String[] itemsSpinnerUser2_2 = {" 11월15일,2021", " 11월14일,2021", " 11월13일,2021"};
        ArrayAdapter<String> adapter2_2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item2, itemsSpinnerUser2_2);
        adapter2_2.setDropDownViewResource(R.layout.spinner_item2);
        spinnerUser2_2.setAdapter(adapter2_2);

        // 자녀추가 레이아웃*****************************************************************************************************************
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterAddChild = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, years);
        binding.includeLayoutAppendChild.spinner.setAdapter(adapterAddChild);


        ///////////////////////////spinner

        TextView textView_noti_1 = binding.textViewNoti1;
        textTmp = "  공지 [2021.10.28] - \"위젯 추가차단\" 기능 추가";
        ssb = new SpannableStringBuilder(textTmp);
        int divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_noti_1.setText(ssb);

        TextView textView_noti_2 = binding.textViewNoti2;
        textTmp = "  공지 [2021.8.31] - \"외부 키보드 차단\" 기능 추가";
        ssb = new SpannableStringBuilder(textTmp);
        divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_noti_2.setText(ssb);

        TextView textView_noti_3 = binding.textViewNoti3;
        textTmp = "  공지 [2021.8.31] - \"유해 키워드 차단\" 기능 추가";
        ssb = new SpannableStringBuilder(textTmp);
        divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_noti_3.setText(ssb);

        TextView textView_usageTime = binding.include1.textViewUsageTime;
        textTmp = "이용 시간(분)";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new AbsoluteSizeSpan(35), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 사이즈
        textView_usageTime.setText(ssb);

        TextView textView_more = binding.include1.textViewMore;
        textTmp = "  전화 문자 이력이 안 보이나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_more.setText(ssb);

        TextView textView_copyright = binding.textViewCopyright;
        textTmp = "T2014-2021 Ⓒ Mobile Fence, Inc. All rights reserved.\n고객 센터 : 02 - 2135 - 6877 (평일 오전 9시 ~오후 6시)\n광고 / 제휴 : partner @mobilefence.com\n팔로우하여 최신 소식과 할인 코드를 놓치지 마세요 :";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 52, 95, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 13, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 61, 77, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 103, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_copyright.setText(ssb);

        TextView textView_more_2 = binding.include1.textViewMore2;
        textTmp = "  로그가 보이지 않나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_more_2.setText(ssb);

        TextView textView_menu_family = binding.textViewMenuFamily;
        textTmp = "패밀리   (2)";
        ssb = new SpannableStringBuilder(textTmp);
        Drawable drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_family);
        drawableTmp.setBounds(0, 0, 40, 40);
        VerticalImageSpan verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_family.setText(ssb);

        TextView textView_menu_smartDevice = binding.textViewMenuSmartDevice;
        textTmp = "스마트기기    (1)";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_smart_device);
        drawableTmp.setBounds(0, 0, 30, 40);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_smartDevice.setText(ssb);

        TextView textView_menu_setting = binding.textViewMenuSetting;
        textTmp = "설정  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.ic_baseline_settings_24);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_setting.setText(ssb);

        TextView textView_menu_payment = binding.textViewMenuPayment;
        textTmp = "결제 내역  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_payment);
        drawableTmp.setBounds(0, 0, 20, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_payment.setText(ssb);

        TextView textView_menu_baseOfKnowledge = binding.textViewMenuBaseOfKnowledge;
        textTmp = "지식 베이스  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_knowledge);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_baseOfKnowledge.setText(ssb);

        TextView textView_menu_help = binding.textViewMenuHelp;
        textTmp = "도움말  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_help);
        drawableTmp.setBounds(0, 0, 10, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_help.setText(ssb);

        TextView textView_menu_support = binding.textViewMenuSupport;
        textTmp = "사용자 지원  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_support);
        drawableTmp.setBounds(0, 0, 40, 30);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_support.setText(ssb);

        TextView textView_menu_customerCenter = binding.textViewMenuCustomerCenter;
        textTmp = "고객센터    02-2135-6877";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_customer);
        drawableTmp.setBounds(0, 0, 30, 30);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 8, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
        textView_menu_customerCenter.setText(ssb);

        TextView textView_menu_evaluation = binding.textViewMenuEvaluation;
        textTmp = "평가/리뷰하기  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_evaluation);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.RED);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_evaluation.setText(ssb);

        TextView textView_menu_notice = binding.textViewMenuNotice;
        textTmp = "공지사항  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.ic_noti);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_notice.setText(ssb);

        TextView textView_menu_news = binding.textViewMenuNews;
        textTmp = "최신소식  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = getBaseContext().getResources().getDrawable(R.drawable.image_menu_news);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_news.setText(ssb);


        ///////////////////////SpannableStringBuilder

        def = binding.include1.item2.getTextColors();


        binding.include1.item1.setOnClickListener(this);
        binding.include1.item2.setOnClickListener(this);
        binding.buttonMenu.setOnClickListener(this);
        binding.layoutMenuClose.setOnClickListener(this);
        binding.imageButtonMenuClose.setOnClickListener(this);
        binding.buttonSearchCancel.setOnClickListener(this);
        binding.includeLayoutSetting.buttonSettingClose.setOnClickListener(this);
        binding.includeLayoutSetting.imageButtonSettingClose.setOnClickListener(this);
        binding.includeLayoutNoti.buttonNotiClose.setOnClickListener(this);
        binding.spinnerUser.setOnClickListener(this);

        binding.bottomNavigationView.setOnItemSelectedListener(this);

        // 장치설정 화면, 스크롤 했을때 중간에서 멈춤
        binding.includeLayoutSetting.scrollViewSetting2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScroll(v);
                        }
                    }, 100);
                }
                return false;
            }
        });

        binding.includeLayoutSetting.scrollViewSetting.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScroll(v);
                        }
                    }, 100);
                }
                return false;
            }
        });

        // 알림 화면, 스크롤 했을때 중간에서 멈춤
        binding.includeLayoutNoti.scrollViewNoti2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScroll(v);
                        }
                    }, 100);
                }
                return false;
            }
        });

        binding.includeLayoutNoti.scrollViewNoti.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            stopScroll(v);
                        }
                    }, 100);
                }
                return false;
            }
        });

        binding.includeLayoutAppendChild.radioGroupAvatar1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isAvatarChecked) {
                    isAvatarChecked = false;
                    binding.includeLayoutAppendChild.radioGroupAvatar2.clearCheck();
                }
                isAvatarChecked = true;
            }
        });

        binding.includeLayoutAppendChild.radioGroupAvatar2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1 && isAvatarChecked) {
                    isAvatarChecked = false;
                    binding.includeLayoutAppendChild.radioGroupAvatar1.clearCheck();
                }
                isAvatarChecked = true;
            }
        });

        setInit(); // 검색화면 ViewPager2
        // 뷰페이저2 사용 순서
        // xml layout 에 ViewPager2 등록
        // 각각의 페이지용 layout xml 작성
        // FragmentStateAdapter class 만들고
        // 생성자 만들고, 오버라이드(createFragment, getItemId, getItemCount)
        // onCreate 에서 초기화
        // FragmentStateAdapter 인스턴스 생성
        // viewPager2.setAdapter(fragmentStateAdapter); ViewPager2 에 FragmentStateAdapter 인스턴스를 어댑터로 설정
        // 이러면 끝인데, 기타 여러가지 설정들을 해준다. (스크롤 방향, 페이지 갯수, 처음 보여질 페이지, 옆 페이지 보이게 등등)
        // viewPager2.registerOnPageChangeCallback 리스너 등록하면서
        // 메소드 오버라이드(onPageScrolled, onPageSelected, onPageScrollStateChanged
        // 끝

    }

    private void stopScroll(View v) {
        if (v.getId() == R.id.scrollViewSetting || v.getId() == R.id.scrollViewSetting2) {
            int y = binding.includeLayoutSetting.scrollViewSetting.getScrollY();
            if (y < 700) {
                binding.includeLayoutSetting.scrollViewSetting.smoothScrollTo(0, 0);
                onClick(binding.includeLayoutSetting.buttonSettingClose);
            } else if (y < 1690) {
                binding.includeLayoutSetting.scrollViewSetting.smoothScrollTo(0, 1400);
            } else {
                binding.includeLayoutSetting.scrollViewSetting.smoothScrollTo(0, 1979);
            }
        }
        if (v.getId() == R.id.scrollViewNoti || v.getId() == R.id.scrollViewNoti2) {
            int y = binding.includeLayoutNoti.scrollViewNoti.getScrollY();
            if (y < 700) {
                binding.includeLayoutNoti.scrollViewNoti.smoothScrollTo(0, 0);
                onClick(binding.includeLayoutNoti.buttonNotiClose);
            } else if (y < 1690) {
                binding.includeLayoutNoti.scrollViewNoti.smoothScrollTo(0, 1400);
            } else {
                binding.includeLayoutNoti.scrollViewNoti.smoothScrollTo(0, 1979);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item1) {                                       // 활동요약 화면
            binding.include1.select.animate().x(0).setDuration(100);

            binding.include1.item1.setTextColor(Color.BLACK);
            binding.include1.item2.setTextColor(def);
            binding.include1.item1Layout.setVisibility(View.VISIBLE);
            binding.include1.item2Layout.setVisibility(View.GONE);
        } else if (view.getId() == R.id.item2) {                                // 타임라인 화면
            binding.include1.item1.setTextColor(def);
            binding.include1.item2.setTextColor(Color.BLACK);

            int size = binding.include1.item2.getWidth();
            binding.include1.select.animate().x(size).setDuration(100);

            binding.include1.item2Layout.setVisibility(View.VISIBLE);
            binding.include1.item1Layout.setVisibility(View.GONE);
        }
        if (view.getId() == R.id.buttonMenu) {                                   // 메뉴 화면
            int size = binding.layoutMenu.getWidth();
            if (isMenuPageOpen) {
                binding.layoutMenu.animate().x(-size).setDuration(500);
                binding.layoutMenuMask.animate().alpha(0.0f).setDuration(500);
                binding.layoutMenuClose.setClickable(false);
                isMenuPageOpen = false;
            } else {
                binding.layoutMenu.setTranslationX(-(float) size);
                binding.layoutMenu.setVisibility(View.VISIBLE);
                binding.layoutMenu.animate().x(0).setDuration(500);
                binding.layoutMenuMask.animate().alpha(0.5f).setDuration(500);
                binding.layoutMenuClose.setClickable(true);
                isMenuPageOpen = true;
            }
        }
        if (view.getId() == R.id.layout_menu_close) {                             // 메뉴 화면 닫기 (검은색 부분)
            onClick(binding.buttonMenu);
        }
        if (view.getId() == R.id.imageButton_menu_close) {                        // 메뉴 화면 닫기 (X 버튼)
            onClick(binding.buttonMenu);
        }
        if (view.getId() == R.id.button_search_cancel) {                          // 검색 화면 닫기 (X 버튼)
            int size = binding.layoutSearch.getHeight();
            if (isSearchPageOpen) {
                binding.layoutSearch.animate().yBy(size).setDuration(500);
                binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                binding.layoutSearch.setClickable(false);
                binding.layoutSearchMask.setClickable(false);
                isSearchPageOpen = false;
            } else {
                binding.layoutSearch.setTranslationY((float) size);
                binding.layoutSearchMask.setAlpha(0.0f);
                binding.layoutSearchMask.setVisibility(View.VISIBLE);
                binding.layoutSearch.setVisibility(View.VISIBLE);

                binding.layoutSearch.animate().yBy(-size).setDuration(500);
                binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                binding.layoutSearch.setClickable(true);
                binding.layoutSearchMask.setClickable(true);
                isSearchPageOpen = true;
            }
        }
        if (view.getId() == R.id.buttonSettingClose) {                             // 장치 설정 화면 닫기 (닫기 버튼)
            int layoutRootHeight = binding.layoutRoot.getHeight();
            if (isSettingPageOpen) {
                binding.includeLayoutSetting.layoutSetting.animate().yBy(layoutRootHeight).setDuration(500);
                binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                binding.includeLayoutSetting.layoutSetting.setClickable(false);
                binding.layoutSearchMask.setClickable(false);
                isSettingPageOpen = false;

            } else {
                binding.includeLayoutSetting.layoutSetting.setTranslationY((float) layoutRootHeight);
                binding.layoutSearchMask.setAlpha(0.0f);
                binding.layoutSearchMask.setVisibility(View.VISIBLE);
                binding.includeLayoutSetting.layoutSetting.setVisibility(View.VISIBLE);

                binding.includeLayoutSetting.layoutSetting.animate().yBy(-layoutRootHeight).setDuration(500);
                binding.includeLayoutSetting.scrollViewSetting.fullScroll(ScrollView.FOCUS_DOWN);
                binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                binding.includeLayoutSetting.layoutSetting.setClickable(true);
                binding.layoutSearchMask.setClickable(true);
                isSettingPageOpen = true;
            }
        }
        if (view.getId() == R.id.imageButtonSettingClose) {                          // 장치 설정 화면 닫기 (X 버튼)
            onClick(binding.includeLayoutSetting.buttonSettingClose);
        }
        if (view.getId() == R.id.buttonNotiClose) {                             // 알림 화면 닫기 (닫기 버튼)
            int layoutRootHeight = binding.layoutRoot.getHeight();
            if (isNotiPageOpen) {
                Log.i("cis", "isNotiPageOpen true");
                binding.includeLayoutNoti.layoutNoti.animate().yBy(layoutRootHeight).setDuration(500);
                binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                binding.includeLayoutNoti.layoutNoti.setClickable(false);
                binding.layoutSearchMask.setClickable(false);
                isNotiPageOpen = false;

            } else {
                Log.i("cis", "isNotiPageOpen false");
                binding.includeLayoutNoti.layoutNoti.setTranslationY((float) layoutRootHeight);
                binding.layoutSearchMask.setAlpha(0.0f);
                binding.layoutSearchMask.setVisibility(View.VISIBLE);
                binding.includeLayoutNoti.layoutNoti.setVisibility(View.VISIBLE);

                binding.includeLayoutNoti.layoutNoti.animate().yBy(-layoutRootHeight).setDuration(500);
                binding.includeLayoutNoti.scrollViewNoti.fullScroll(ScrollView.FOCUS_DOWN);
                binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                binding.includeLayoutNoti.layoutNoti.setClickable(true);
                binding.layoutSearchMask.setClickable(true);
                isNotiPageOpen = true;
            }
        }
        if (view.getId() == R.id.spinner_user) {  // 관리 대상 변경
            Dialog dialog01;
            dialog01 = new Dialog(MainActivity.this);
            dialog01.setContentView(R.layout.custom_dialog_user_change);
            dialog01.show();

//            RadioGroup rg = dialog01.findViewById(R.id.radioGroup);
            RadioButton rb = dialog01.findViewById(R.id.radioButton);
            RadioButton rb2 = dialog01.findViewById(R.id.radioButton2);
            RadioButton rb3 = dialog01.findViewById(R.id.radioButton3);

            Button noBtn = dialog01.findViewById(R.id.buttonDialogClose);
            noBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog01.dismiss();

                    if (rb.isChecked()) binding.spinnerUser.setText(rb.getText());
                    if (rb2.isChecked()) binding.spinnerUser.setText(rb2.getText());
                    if (rb3.isChecked()) binding.spinnerUser.setText(rb3.getText());
                }
            });

        }
        if (view.getId() == R.id.button_child_add) {  // 자녀 추가 버튼

        }
    }

    @Override
    public void onBackPressed() {
        if (isMenuPageOpen) {
            onClick(binding.buttonMenu);
            return;
        }
        if (isSearchPageOpen) {
            onClick(binding.buttonSearchCancel);
            return;
        }
        if (isSettingPageOpen) {
            onClick(binding.includeLayoutSetting.buttonSettingClose);
            return;
        }
        if (isNotiPageOpen) {
            onClick(binding.includeLayoutNoti.buttonNotiClose);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // bottomNavigationView 의 버튼들
        switch (item.getItemId()) {
            case R.id.tab1_home:
                return true;
            case R.id.tab2_menu:
                onClick(binding.buttonMenu);
                return true;
            case R.id.tab3_search:
                onClick(binding.buttonSearchCancel);
                return true;
            case R.id.tab4_setting:
                onClick(binding.includeLayoutSetting.buttonSettingClose);
                return true;
            case R.id.tab5_notice:
                onClick(binding.includeLayoutNoti.buttonNotiClose);
                return true;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 장치 설정 화면 관련 초기화
        int settingViewHeight = binding.includeLayoutSetting.layoutSetting.getMeasuredHeight();
        ViewGroup.LayoutParams params;

        // 장치설정 화면
        params = binding.includeLayoutSetting.viewSettingFirst.getLayoutParams();
        params.height = settingViewHeight;
        binding.includeLayoutSetting.viewSettingFirst.setLayoutParams(params);

        params = binding.includeLayoutSetting.layoutSettingSecond.getLayoutParams();
        params.height = settingViewHeight;
        binding.includeLayoutSetting.layoutSettingSecond.setLayoutParams(params);

        // 알림 화면
        params = binding.includeLayoutNoti.viewNotiFirst.getLayoutParams();
        params.height = settingViewHeight;
        binding.includeLayoutNoti.viewNotiFirst.setLayoutParams(params);

        params = binding.includeLayoutNoti.layoutNotiSecond.getLayoutParams();
        params.height = settingViewHeight;
        binding.includeLayoutNoti.layoutNotiSecond.setLayoutParams(params);
    }

    private void setInit() {  // 뷰페이저2 실행 메서드
        viewPageSetup = binding.viewPagerSearch;  // 여기서 뷰페이저를 참조한다.
        FragPagerAdapter SetupPagerAdapter = new FragPagerAdapter(this); // 프래그먼트에서는 getActivity 로 참조하고, 액티비티에서는 this 를 사용한다.
        viewPageSetup.setAdapter(SetupPagerAdapter);  //FragPagerAdapter 를 파라미터로 받고 ViewPager2 에 전달 받는다.
        viewPageSetup.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);  // 방향은 가로
        viewPageSetup.setOffscreenPageLimit(3);  // 페이지 한계 갯수 지정
        viewPageSetup.setCurrentItem(1, false); // 처음 보여질 페이지 지정
        setCurrentIndicator(1);
        viewPageSetup.setClipToPadding(false);
        viewPageSetup.setClipChildren(false);
        viewPageSetup.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
//        viewPageSetup.setPageTransformer(new ViewPager2.PageTransformer(){
//            @Override
//            public void transformPage(@NonNull View page, float position){
//
//            }
//        });
    }

    private void setCurrentIndicator(int position) {
        int childCount = binding.pagerIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.pagerIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_active));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bg_indicator_inactive));
            }
        }
    }

    class FragPagerAdapter extends FragmentStateAdapter {  // 뷰페이저2에서는 FragmentStateAdapter 를 사용한다.

        private final int mSetItemCount = 3;  // 프래그먼트 갯수 지정

        public FragPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            int iViewIdx = getRealPosition(position);
            switch (iViewIdx) {
                case 0: {
                    return new Fragment_search1(); // 프래그먼트 순서에 맞게 넣어주기
                }
                case 1: {
                    return new Fragment_search2();
                }
                case 2: {
                    return new Fragment_search3();
                }
                default: {
                    return new Fragment_search2();  // 기본으로 나와있는 프래그먼트
                }
            }
        }

        public int getRealPosition(int _iPosition) {
            return _iPosition % mSetItemCount;
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getItemCount() {
            return mSetItemCount;
        }
    }
}