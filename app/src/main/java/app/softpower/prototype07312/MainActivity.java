package app.softpower.prototype07312;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.FieldClassification;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;

import app.softpower.prototype07312.databinding.ActivityMainBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutNotiBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutSettingBinding;
import app.softpower.prototype07312.databinding.ViewstubLayoutSearchBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {

    private ActivityMainBinding binding;
    private ViewstubLayoutSearchBinding layoutSearchBinding;
    private IncludeLayoutSettingBinding layoutSettingBinding;
    private IncludeLayoutNotiBinding layoutNotiBinding;
    private ViewPager2 viewPageSetup;

    ColorStateList def;

    boolean isMenuPageOpen = false;
    boolean isSearchPageOpen = false;
    boolean isSettingPageOpen = false;
    boolean isNotiPageOpen = false;
    boolean isAvatarChecked = false;
    boolean isAppendChild = false;
    boolean isDeviceStatusOpen = false;
    static int appWidth;
    static int appHeight;
    static float appDensity;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkForFirstRun();

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        spinnerSetup();
        spannableSetup();

        def = binding.include1.item2.getTextColors();

        binding.include1.item1.setOnClickListener(this);
        binding.include1.item2.setOnClickListener(this);
        binding.buttonMenu.setOnClickListener(this);
        binding.layoutMenuClose.setOnClickListener(this);
        binding.imageButtonMenuClose.setOnClickListener(this);
        binding.spinnerUser.setOnClickListener(this);
        binding.buttonChildAdd.setOnClickListener(this);
        binding.includeLayoutAppendChild.imageButtonAppendChildClose.setOnClickListener(this);
        binding.buttonModiChild.setOnClickListener(this);
        binding.include2.item12.setOnClickListener(this);
        binding.include2.item22.setOnClickListener(this);
        binding.include2.item32.setOnClickListener(this);
        binding.include2.item42.setOnClickListener(this);
        binding.include2.item52.setOnClickListener(this);
        binding.include1.buttonSettingRull2.setOnClickListener(this);
        binding.include2.buttonBackToSummary.setOnClickListener(this);
        binding.textViewDeviceStatus.setOnClickListener(this);
        binding.textViewMenuFamily.setOnClickListener(this);
        binding.textViewMenuSmartDevice.setOnClickListener(this);
        binding.textViewMenuSetting.setOnClickListener(this);
        binding.menuPayment.setOnClickListener(this);
        binding.textViewMenuBaseOfKnowledge.setOnClickListener(this);
        binding.textViewMenuHelp.setOnClickListener(this);
        binding.textViewMenuSupport.setOnClickListener(this);
        binding.textViewMenuCustomerCenter.setOnClickListener(this);
        binding.textViewMenuEvaluation.setOnClickListener(this);
        binding.textViewMenuNotice.setOnClickListener(this);
        binding.textViewMenuNews.setOnClickListener(this);
        binding.bottomNavigationView.setOnItemSelectedListener(this);




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

        binding.viewStubLayoutSearch.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutSearchBinding = ViewstubLayoutSearchBinding.bind(inflated);

                ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) layoutSearchBinding.layoutSearch.getLayoutParams();
                param.setMargins(0, DpToPx(50), 0, 0);
                layoutSearchBinding.layoutSearch.setLayoutParams(param);

                layoutSearchBinding.buttonSearchCancel.setOnClickListener(MainActivity.this);
            }
        });

        binding.viewStubLayoutSetting.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutSettingBinding = IncludeLayoutSettingBinding.bind(inflated);

                layoutSettingBinding.buttonSettingClose.setOnClickListener(MainActivity.this);
                layoutSettingBinding.imageButtonSettingClose.setOnClickListener(MainActivity.this);

                // 장치설정 화면, 스크롤 했을때 중간에서 멈춤
                layoutSettingBinding.scrollViewSetting2.setOnTouchListener(new View.OnTouchListener() {
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

                layoutSettingBinding.scrollViewSetting.setOnTouchListener(new View.OnTouchListener() {
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

//                ConstraintLayout.LayoutParams param = (ConstraintLayout.LayoutParams) layoutSettingBinding.layoutSetting.getLayoutParams();
//                param.setMargins(0, 0, 0, 0);
//                layoutSettingBinding.layoutSetting.setLayoutParams(param);
            }
        });

        binding.viewStubLayoutNoti.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutNotiBinding = IncludeLayoutNotiBinding.bind(inflated);

                layoutNotiBinding.buttonNotiClose.setOnClickListener(MainActivity.this);

                // 알림 화면, 스크롤 했을때 중간에서 멈춤
                layoutNotiBinding.scrollViewNoti2.setOnTouchListener(new View.OnTouchListener() {
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

                layoutNotiBinding.scrollViewNoti.setOnTouchListener(new View.OnTouchListener() {
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

            }
        });


//        setInit(); // 검색화면 ViewPager2
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

    private void checkForFirstRun() {
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", true);
        if (checkFirst) {
            // 앱 최초 실행시 하고 싶은 작업
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("checkFirst", false);
            editor.commit();
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        }
    }

    private void getAppDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        appDensity = getResources().getDisplayMetrics().density;
        appHeight = binding.layoutRoot.getHeight();
        appWidth = binding.layoutRoot.getWidth();
//        appWidth = outMetrics.widthPixels;
//        appHeight = outMetrics.heightPixels;
//        Log.i("cis", String.valueOf("widthPixels: "+outMetrics.widthPixels + ", getWidth: " + appWidth));
    }

    private void spinnerSetup() {
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

        Spinner spinnerUser2_rule = binding.include2.includeApp.spinnerUser2;
        String[] itemsSpinnerUser2_rule = {" 오락/소셜 앱만 보기 (14)", " 전체보기 (20)"};
        ArrayAdapter<String> adapter2_rule = new ArrayAdapter<String>(
                this, R.layout.spinner_item5, itemsSpinnerUser2_rule);
        adapter2_rule.setDropDownViewResource(R.layout.spinner_item5);
        spinnerUser2_rule.setAdapter(adapter2_rule);


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

        Spinner spinnerChild2 = binding.includeLayoutAppendChild.spinner2;
        String[] itemsSpinnerChild2 = {"1", "2", "3"};
        ArrayAdapter<String> adapterChild2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, itemsSpinnerChild2);
        spinnerChild2.setAdapter(adapterChild2);

        Spinner spinnerChild3 = binding.includeLayoutAppendChild.spinner3;
        String[] itemsSpinnerChild3 = {"가져오지 않음", "최성준", "최연이", "정화일"};
        ArrayAdapter<String> adapterChild3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, itemsSpinnerChild3);
        spinnerChild3.setAdapter(adapterChild3);

        Spinner spinnerLocationSpan = binding.include2.includeLocation.spinnerUser3;
        String[] itemsSpinnerLocationSpan = {"1 시간", "2 시간", "3 시간"};
        ArrayAdapter<String> adapterLocationSpan = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, itemsSpinnerLocationSpan);
        spinnerLocationSpan.setAdapter(adapterLocationSpan);


        ///////////////////////////spinner
    }

    private void spannableSetup() {
        //        TextView textSupport = binding.textSupport;
//        String textTmp = "고객지원 : 02-2135-6877 (또는 채팅 상담)";
//        SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
//        ssb.setSpan(new StyleSpan(Typeface.BOLD), 23, textTmp.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
//        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 23, textTmp.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 칼라
//        ssb.setSpan(new AbsoluteSizeSpan(35), 21, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 사이즈
//        textSupport.setText(ssb);

        TextView textView_noti_1 = binding.textViewNoti1;
        String textTmp = "  공지 [2021.10.28] - \"위젯 추가차단\" 기능 추가";
        SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
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
        Drawable drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_family);
        drawableTmp.setBounds(0, 0, 40, 40);
        VerticalImageSpan verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_family.setText(ssb);

        TextView textView_menu_smartDevice = binding.textViewMenuSmartDevice;
        textTmp = "스마트기기    (1)";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_smart_device);
        drawableTmp.setBounds(0, 0, 30, 40);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_smartDevice.setText(ssb);

        TextView textView_menu_setting = binding.textViewMenuSetting;
        textTmp = "설정  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.ic_baseline_settings_24);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_setting.setText(ssb);

        TextView textView_menu_payment = binding.textViewMenuPayment;
        textTmp = "결제 내역  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_payment);
        drawableTmp.setBounds(0, 0, 20, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_payment.setText(ssb);

        TextView textView_menu_baseOfKnowledge = binding.textViewMenuBaseOfKnowledge;
        textTmp = "지식 베이스  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_knowledge);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_baseOfKnowledge.setText(ssb);

        TextView textView_menu_help = binding.textViewMenuHelp;
        textTmp = "도움말  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_help);
        drawableTmp.setBounds(0, 0, 10, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_help.setText(ssb);

        TextView textView_menu_support = binding.textViewMenuSupport;
        textTmp = "사용자 지원  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_support);
        drawableTmp.setBounds(0, 0, 40, 30);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_support.setText(ssb);

        TextView textView_menu_customerCenter = binding.textViewMenuCustomerCenter;
        textTmp = "고객센터    02-2135-6877";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_customer);
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
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_evaluation);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.RED);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_evaluation.setText(ssb);

        TextView textView_menu_notice = binding.textViewMenuNotice;
        textTmp = "공지사항  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.ic_noti);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_notice.setText(ssb);

        TextView textView_menu_news = binding.textViewMenuNews;
        textTmp = "최신소식  ";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.image_menu_news);
        drawableTmp.setBounds(0, 0, 40, 40);
        drawableTmp.setTint(Color.BLACK);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView_menu_news.setText(ssb);

        TextView textViewAppUpdate = binding.textViewAppUpdate;
        textTmp = "  모바일펜스 앱 업데이트 필요한 장치가 있습니다. 확인하기";
        ssb = new SpannableStringBuilder(textTmp);
        drawableTmp = ContextCompat.getDrawable(this, R.drawable.layerlist_ic_app_update);
        drawableTmp.setBounds(0, 0, 40, 40);
        verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("확인하기 클릭됨");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan, 29, 33, 0);
        textViewAppUpdate.setText(ssb);
        textViewAppUpdate.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textViewSafeMode = binding.textViewSafeMode;
        textTmp = "[중요!] \"안전모드 차단\"이 되어 있지 않습니다. 자녀가 모바일펜스를 삭제하거나 기능을 중지하지 못하도록 지금 바로 차단 설정을 해주세요. 설정 방법 보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("설정 방법 보기 클릭됨");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan2, 79, 87, 0);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        textViewSafeMode.setText(ssb);
        textViewSafeMode.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "※ 설치된 앱이 보이지 않나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("알아보기 클릭됨");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan3, 18, 22, 0);
        ssb.setSpan(new AbsoluteSizeSpan(50), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 사이즈
        binding.include2.includeApp.textViewAppList.setText(ssb);
        binding.include2.includeApp.textViewAppList.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 특정 어플은 항상 허용할 수 있나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan4 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("특정 어플은 항상 허용할 수 있습니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan4, 23, 27, 0);
        binding.include2.includeApp.textView36.setText(ssb);
        binding.include2.includeApp.textView36.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 변경된 규칙이 기기에 반영되지 않나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan5 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("다시 한번 적용해보세요.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan5, 24, 28, 0);
        binding.include2.includeApp.textView37.setText(ssb);
        binding.include2.includeApp.textView37.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 게임 차단 방법에는 어떤 것들이 있나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan6 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("이런 저런 방법들이 있습니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan6, 25, 29, 0);
        binding.include2.includeApp.textView38.setText(ssb);
        binding.include2.includeApp.textView38.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "︎⚑ 제한 조건의 우선 순위 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan7 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("순서가 뭐가 중요해요~");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan7, 16, 20, 0);
        binding.include2.includeApp.textView39.setText(ssb);
        binding.include2.includeApp.textView39.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "︎모바일펜스를 삭제하거나 무력화하지 못하도록 기기의 설정 진입 및 램 관리(알약,V3,클린마스터,듀얼앱 등) 유형의 앱을 차단합니다. 자녀들의 불평이 있어도 잠시라도 이 설정을 풀어주어서는 안됩니다. 모니터링/제어가 안 되는 상황이 반드시 생깁니다. 무력화방지를 참고하세요. 이 옵션이 켜져 있어도 WiFi설정은 가능하며 여기를 참고하세요.";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan8 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("무력화방지가 뭐가 중요해요~");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan8, 139, 145, 0);
        ClickableSpan clickableSpan9 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("메롱~ 속았지롱~");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan9, 179, 182, 0);
        binding.include2.includeApp.textViewBlockSetting.setText(ssb);
        binding.include2.includeApp.textViewBlockSetting.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "︎GPS를 항상 활성화 상태로 두고 끄지 못하도록 합니다. MDM에이전트 활성화가 먼저 필요합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan10 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("MDM에이전트가 뭐야?");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan10, 32, 40, 0);
        ClickableSpan clickableSpan11 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아 볼 필요가 있나요?");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan11, 55, 62, 0);
        binding.include2.includeApp.textViewGps.setText(ssb);
        binding.include2.includeApp.textViewGps.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "제조사 기본 런처(Launcher)외에 외부 런처 앱 설치를 차단 합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan12 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아보지 마십시오.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan12, 42, 48, 0);
        binding.include2.includeApp.textViewLauncher.setText(ssb);
        binding.include2.includeApp.textViewLauncher.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "기본 키보드만 사용하도록 외부 키보드 앱 설치를 차단합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan13 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아보시면 다칠 수 있습니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan13, 34, 40, 0);
        binding.include2.includeApp.textViewKeyboard.setText(ssb);
        binding.include2.includeApp.textViewKeyboard.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "VPN 연결을 차단하여 해외 프록시 서버로 우회하여 유해사이트에 접속하는 것을 차단합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan14 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("어버버");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan14, 51, 57, 0);
        binding.include2.includeApp.textViewVPN.setText(ssb);
        binding.include2.includeApp.textViewVPN.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "실험실은 아직 연구중인 기능들을 위한 공간입니다. 실험실 기능은 언제든지 변경 또는 다운되거나 개발 중단될 수 있습니다. 기능 사용전에, 자녀 기기에 설치된 모바일펜스 앱 버전이 최신인지 확인하세요. 실험실 기능 설명 자세히 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan15 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("어버버 알쏭달쏭");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan15, 112, 130, 0);
        binding.include2.includeApp.textViewLaboratory.setText(ssb);
        binding.include2.includeApp.textViewLaboratory.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "화면이 꺼진 상태에서도 동작하는 백그라운드 음악 재생 시간을 앱 사용시간으로 집계합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan16 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("어버버 알쏭달쏭 오라가락");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan16, 50, 56, 0);
        binding.include2.includeApp.textViewBackgroundMusic.setText(ssb);
        binding.include2.includeApp.textViewBackgroundMusic.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "유튜브 앱 내의 설정 메뉴를 차단하여, \"컨텐츠 제한모드\"를 자녀가 변경하지 못하도록 합니다 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan17 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("동영상에 중독될 수 있습니다. 조심하십시오.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan17, 52, 58, 0);
        binding.include2.includeApp.textViewYoutube.setText(ssb);
        binding.include2.includeApp.textViewYoutube.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "전화 설정메뉴 진입을 차단하여 통화차단번호 등을 자녀가 변경하지 못하도록 합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan18 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아보지 마십시오.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan18, 46, 52, 0);
        binding.include2.includeApp.textViewPhone.setText(ssb);
        binding.include2.includeApp.textViewPhone.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "메시지 설정메뉴 진입을 차단하여 메시지차단번호, 차단문구등을 자녀가 변경하지 못하도록 합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan19 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아볼 것 없습니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan19, 53, 59, 0);
        binding.include2.includeApp.textViewMessage.setText(ssb);
        binding.include2.includeApp.textViewMessage.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "Play스토어 설정메뉴 진입을 차단하여 자녀보호기능 등을 자녀가 변경하지 못하도록 합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan20 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("더 알아볼 내용이 없습니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan20, 51, 57, 0);
        binding.include2.includeApp.textViewPlayStore.setText(ssb);
        binding.include2.includeApp.textViewPlayStore.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "홈 화면에 위젯을 추가하지 못하도록 차단합니다. 더 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan21 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("위젯을 추가하면 안됩니다.");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan21, 27, 33, 0);
        binding.include2.includeApp.textViewWidget.setText(ssb);
        binding.include2.includeApp.textViewWidget.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "자녀 스마트기기의 한주간 사용계획을 수립해서, 제한된 시간대에 자녀가 다른 생산적인 활동에 더욱 집중하도록 할 수 있습니다. (제한할 시간대의 칸을 클릭하거나, 마우스로 제한할 시간대 영역을 끌어서 붉은색으로 마킹하고, 이 시간대에 \"제한할 기능\"을 활성화 하세요.)";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan22 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("제한할 기능 화면으로 이동");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan22, 131, 137, 0);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 111, 148, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // 스타일
        binding.include2.includeUsageTime.textView7.setText(ssb);
        binding.include2.includeUsageTime.textView7.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 제한 시간대인데 앱이 차단되지 않나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan23 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("제한 시간대인데 앱이 차단되지 않는 경우가 뭐가 있을까?");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan23, 24, 28, 0);
        binding.include2.includeUsageTime.textView.setText(ssb);
        binding.include2.includeUsageTime.textView.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "위 빨간 \"제한된 시간대\"에서 차단할 기능을 선택하세요. 일부 어플은 예외적으로 \"항상허용\" 또는 \"허가시간대 항상허용\"으로 설정할수도 있습니다.";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan24 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("제한된 시간대 설명화면");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan24, 6, 13, 0);
        ClickableSpan clickableSpan25 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("항상허용 설명화면");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan25, 46, 50, 0);
        ClickableSpan clickableSpan26 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("허가시간대 항상허용 설명화면");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan26, 56, 66, 0);
        binding.include2.includeUsageTime.textViewRestrictFunction.setText(ssb);
        binding.include2.includeUsageTime.textViewRestrictFunction.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "하루에 최대 몇 시간 스마트기기 사용을 허가할지를 설정합니다. 더 알아보기>>";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan27 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("기기 사용시간 설명화면");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan27, 35, 43, 0);
        binding.include2.includeUsageTime.textViewDeviceUsageTime.setText(ssb);
        binding.include2.includeUsageTime.textViewDeviceUsageTime.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ '기기시간,게임시간'이 초과되어 사용되나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan28 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("기기시간,게임시간 설명 화면으로");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan28, 27, 31, 0);
        binding.include2.includeUsageTime.textView31.setText(ssb);
        binding.include2.includeUsageTime.textView31.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 변경된 규칙이 기기에 반영되지 않나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan29 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("변경된 규칙 화면으로");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan29, 24, 28, 0);
        binding.include2.includeUsageTime.textView35.setText(ssb);
        binding.include2.includeUsageTime.textView35.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "⚑ 시간을 다 써도 전화는 되게 할 수 있나요? 알아보기";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan30 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("전화 화면으로");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan30, 27, 31, 0);
        binding.include2.includeUsageTime.textView40.setText(ssb);
        binding.include2.includeUsageTime.textView40.setMovementMethod(LinkMovementMethod.getInstance());
        //------------------------------------------------------------------------------
        textTmp = "하루 이용 가능한 게임 시간을 설정합니다. 더 알아보기>>";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan31 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("하루 이용 가능한 게임 시간 화면으로");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan31, 24, 32, 0);
        binding.include2.includeUsageTime.textViewMoreTime.setText(ssb);
        binding.include2.includeUsageTime.textViewMoreTime.setMovementMethod(LinkMovementMethod.getInstance());


        ///////////////////////SpannableStringBuilder
    }

    private void stopScroll(View v) {
        if (v.getId() == R.id.scrollViewSetting || v.getId() == R.id.scrollViewSetting2) {
            int y = layoutSettingBinding.scrollViewSetting.getScrollY();
            if (y < 700) {
                layoutSettingBinding.scrollViewSetting.smoothScrollTo(0, 0);
                onClick(layoutSettingBinding.buttonSettingClose);
            } else if (y < 1690) {
                layoutSettingBinding.scrollViewSetting.smoothScrollTo(0, 1400);
            } else {
                layoutSettingBinding.scrollViewSetting.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }
        if (v.getId() == R.id.scrollViewNoti || v.getId() == R.id.scrollViewNoti2) {
            int y = layoutNotiBinding.scrollViewNoti.getScrollY();
            if (y < 700) {
                layoutNotiBinding.scrollViewNoti.smoothScrollTo(0, 0);
                onClick(layoutNotiBinding.buttonNotiClose);
            } else if (y < 1690) {
                layoutNotiBinding.scrollViewNoti.smoothScrollTo(0, 1400);
            } else {
                layoutNotiBinding.scrollViewNoti.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item1:                                             // 활동요약 화면
                binding.include1.select.animate().x(0).setDuration(100);
                binding.include1.item1.setTextColor(Color.BLACK);
                binding.include1.item2.setTextColor(def);
                binding.include1.item1Layout.setVisibility(View.VISIBLE);
                binding.include1.item2Layout.setVisibility(View.GONE);
                break;
            case R.id.item2:                                             // 타임라인 화면
                binding.include1.item1.setTextColor(def);
                binding.include1.item2.setTextColor(Color.BLACK);

                int size = binding.include1.item2.getWidth();
                binding.include1.select.animate().x(size).setDuration(100);

                binding.include1.item2Layout.setVisibility(View.VISIBLE);
                binding.include1.item1Layout.setVisibility(View.GONE);
                break;
            case R.id.buttonMenu:                                                 // 메뉴 화면
                size = binding.layoutRoot.getWidth();
//                Log.i("cis", String.valueOf("appWidth: " + appWidth));
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
//                    Log.i("cis", String.valueOf("getWidth: " + binding.layoutMenu.getWidth()));
                }
                break;
            case R.id.layout_menu_close:                                             // 메뉴 화면 닫기 (검은색 부분)
                onClick(binding.buttonMenu);
                break;
            case R.id.imageButton_menu_close:                                        // 메뉴 화면 닫기 (X 버튼)
                onClick(binding.buttonMenu);
                break;
            case R.id.button_search_cancel:                                          // 검색 화면 닫기 (X 버튼)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(2));
                break;
            case R.id.buttonSettingClose:                                             // 장치 설정 화면 닫기 (닫기 버튼)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(3));
                break;
            case R.id.imageButtonSettingClose:                                        // 장치 설정 화면 닫기 (X 버튼)
                onClick(layoutSettingBinding.buttonSettingClose);
                break;
            case R.id.buttonNotiClose:                                                // 알림 화면 닫기 (닫기 버튼)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(4));
                break;
            case R.id.spinner_user:                                                // 관리 대상 변경
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
                break;
            case R.id.button_child_add:                                                       // 자녀 추가 버튼
                if (isAppendChild) {
                    binding.includeLayoutAppendChild.layoutAppendChild.animate().yBy(appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    binding.includeLayoutAppendChild.layoutAppendChild.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isAppendChild = false;

                } else {
                    binding.includeLayoutAppendChild.textView23.setText("  자녀 추가");
                    binding.includeLayoutAppendChild.buttonAppendChildDelete.setVisibility(View.INVISIBLE);
                    binding.includeLayoutAppendChild.layoutAppendChild.setTranslationY((float) appHeight);
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);
                    binding.includeLayoutAppendChild.layoutAppendChild.setVisibility(View.VISIBLE);

                    binding.includeLayoutAppendChild.layoutAppendChild.animate().yBy(-appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    binding.includeLayoutAppendChild.layoutAppendChild.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isAppendChild = true;
                }
                break;
            case R.id.imageButtonAppendChildClose:                                                   // 자녀추가 화면 속의 x버튼
                onClick(binding.buttonChildAdd);
                break;
            case R.id.button_modi_child:                                                             // 자녀 수정 버튼
                if (isAppendChild) {
                    binding.includeLayoutAppendChild.layoutAppendChild.animate().yBy(appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    binding.includeLayoutAppendChild.layoutAppendChild.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isAppendChild = false;

                } else {
                    binding.includeLayoutAppendChild.textView23.setText("  수정");
                    binding.includeLayoutAppendChild.buttonAppendChildDelete.setVisibility(View.VISIBLE);
                    binding.includeLayoutAppendChild.layoutAppendChild.setTranslationY((float) appHeight);
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);
                    binding.includeLayoutAppendChild.layoutAppendChild.setVisibility(View.VISIBLE);

                    binding.includeLayoutAppendChild.layoutAppendChild.animate().yBy(-appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    binding.includeLayoutAppendChild.layoutAppendChild.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isAppendChild = true;
                }
                break;
            case R.id.item1_2:                                                    // 규칙설정 어플 화면

                int x = (int) binding.include2.item12.getX();
                binding.include2.select2.animate().x(x).setDuration(50);
                binding.include2.item12.setTextColor(Color.BLACK);
                binding.include2.item22.setTextColor(def);
                binding.include2.item32.setTextColor(def);
                binding.include2.item42.setTextColor(def);
                binding.include2.item52.setTextColor(def);
                binding.include2.includeApp.item1Layout.setVisibility(View.VISIBLE);
                binding.include2.includeUsageTime.item2Layout.setVisibility(View.GONE);
                binding.include2.includeWeb.item3Layout.setVisibility(View.GONE);
                binding.include2.includePhone.item4Layout.setVisibility(View.GONE);
                binding.include2.includeLocation.item5Layout.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = binding.include2.select2.getLayoutParams();
                params.width = binding.include2.item12.getWidth();
                binding.include2.select2.setLayoutParams(params);
                break;
            case R.id.item2_2:                                                    // 규칙설정 사용시간 화면

                x = (int) binding.include2.item22.getX();
                binding.include2.select2.animate().x(x).setDuration(50);
                binding.include2.item12.setTextColor(def);
                binding.include2.item22.setTextColor(Color.BLACK);
                binding.include2.item32.setTextColor(def);
                binding.include2.item42.setTextColor(def);
                binding.include2.item52.setTextColor(def);
                binding.include2.includeApp.item1Layout.setVisibility(View.GONE);
                binding.include2.includeUsageTime.item2Layout.setVisibility(View.VISIBLE);
                binding.include2.includeWeb.item3Layout.setVisibility(View.GONE);
                binding.include2.includePhone.item4Layout.setVisibility(View.GONE);
                binding.include2.includeLocation.item5Layout.setVisibility(View.GONE);
                params = binding.include2.select2.getLayoutParams();
                params.width = binding.include2.item22.getWidth();
                binding.include2.select2.setLayoutParams(params);
                break;
            case R.id.item3_2:                                                    // 규칙설정 웹 화면

                x = (int) binding.include2.item32.getX();
                binding.include2.select2.animate().x(x).setDuration(50);
                binding.include2.item12.setTextColor(def);
                binding.include2.item22.setTextColor(def);
                binding.include2.item32.setTextColor(Color.BLACK);
                binding.include2.item42.setTextColor(def);
                binding.include2.item52.setTextColor(def);
                binding.include2.includeApp.item1Layout.setVisibility(View.GONE);
                binding.include2.includeUsageTime.item2Layout.setVisibility(View.GONE);
                binding.include2.includeWeb.item3Layout.setVisibility(View.VISIBLE);
                binding.include2.includePhone.item4Layout.setVisibility(View.GONE);
                binding.include2.includeLocation.item5Layout.setVisibility(View.GONE);
                params = binding.include2.select2.getLayoutParams();
                params.width = binding.include2.item32.getWidth();
                binding.include2.select2.setLayoutParams(params);
                break;
            case R.id.item4_2:                                                    // 규칙설정 전화 화면

                x = (int) binding.include2.item42.getX();
                binding.include2.select2.animate().x(x).setDuration(50);
                binding.include2.item12.setTextColor(def);
                binding.include2.item22.setTextColor(def);
                binding.include2.item32.setTextColor(def);
                binding.include2.item42.setTextColor(Color.BLACK);
                binding.include2.item52.setTextColor(def);
                binding.include2.includeApp.item1Layout.setVisibility(View.GONE);
                binding.include2.includeUsageTime.item2Layout.setVisibility(View.GONE);
                binding.include2.includeWeb.item3Layout.setVisibility(View.GONE);
                binding.include2.includePhone.item4Layout.setVisibility(View.VISIBLE);
                binding.include2.includeLocation.item5Layout.setVisibility(View.GONE);
                params = binding.include2.select2.getLayoutParams();
                params.width = binding.include2.item42.getWidth();
                binding.include2.select2.setLayoutParams(params);
                break;
            case R.id.item5_2:                                                    // 규칙설정 위치 화면

                x = (int) binding.include2.item52.getX();
                binding.include2.select2.animate().x(x).setDuration(50);
                binding.include2.item12.setTextColor(def);
                binding.include2.item22.setTextColor(def);
                binding.include2.item32.setTextColor(def);
                binding.include2.item42.setTextColor(def);
                binding.include2.item52.setTextColor(Color.BLACK);
                binding.include2.includeApp.item1Layout.setVisibility(View.GONE);
                binding.include2.includeUsageTime.item2Layout.setVisibility(View.GONE);
                binding.include2.includeWeb.item3Layout.setVisibility(View.GONE);
                binding.include2.includePhone.item4Layout.setVisibility(View.GONE);
                binding.include2.includeLocation.item5Layout.setVisibility(View.VISIBLE);
                params = binding.include2.select2.getLayoutParams();
                params.width = binding.include2.item52.getWidth();
                binding.include2.select2.setLayoutParams(params);
                break;
            case R.id.button_setting_rull2:                                         // 규칙설정 버튼
                binding.include1.layoutSumActivities.setVisibility(View.GONE);
                binding.include2.layoutSetRules.setVisibility(View.VISIBLE);
                binding.layoutNotification.setVisibility(View.GONE);
                break;
            case R.id.button_back_to_summary:                                       // 활동요약으로 돌아가기
                binding.include2.layoutSetRules.setVisibility(View.GONE);
                binding.include1.layoutSumActivities.setVisibility(View.VISIBLE);
                binding.layoutNotification.setVisibility(View.VISIBLE);
                break;
            case R.id.textViewDeviceStatus:                                         // 기기상태 텍스트뷰
                if (isDeviceStatusOpen) {
                    binding.layoutMain.setVisibility(View.VISIBLE);
                    binding.includeDeviceStatus.layoutDeviceStatus.setVisibility(View.GONE);
                    isDeviceStatusOpen = false;
                } else {
                    binding.layoutMain.setVisibility(View.GONE);
                    binding.includeDeviceStatus.layoutDeviceStatus.setVisibility(View.VISIBLE);
                    isDeviceStatusOpen = true;
                }
                break;
            case R.id.textView_menu_family:                                         // 메뉴화면 안의 패밀리 텍스트뷰
                size = binding.layoutMenu.getWidth();
                if (isMenuPageOpen) {
                    binding.layoutMenu.animate().x(-size).setDuration(500);
                    binding.layoutMenuMask.animate().alpha(0.0f).setDuration(500);
                    binding.layoutMenuClose.setClickable(false);
                    isMenuPageOpen = false;
                }

        }
    }

    @Override
    public void onBackPressed() {
        if (isMenuPageOpen) {
            onClick(binding.buttonMenu);
            return;
        }
        if (isSearchPageOpen) {
            onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(2));
            return;
        }
        if (isSettingPageOpen) {
            onClick(layoutSettingBinding.buttonSettingClose);
            return;
        }
        if (isNotiPageOpen) {
            onClick(layoutNotiBinding.buttonNotiClose);
            return;
        }
        if (isAppendChild) {
            onClick(binding.buttonChildAdd);
            return;
        }
        if (isDeviceStatusOpen) {
            onClick(binding.textViewDeviceStatus);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // bottomNavigationView 의 버튼들
        switch (item.getItemId()) {
            case R.id.tab1_home:
                onClick(binding.textViewMenuFamily);
                return true;
            case R.id.tab2_menu:
                onClick(binding.buttonMenu);
                return true;
            case R.id.tab3_search:
                if (binding.viewStubLayoutSearch.getVisibility() == View.GONE) {
                    setInit();
                }
                int size = appHeight - DpToPx(50);
                if (isSearchPageOpen) {
                    layoutSearchBinding.layoutSearch.animate().yBy(size).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    layoutSearchBinding.layoutSearch.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isSearchPageOpen = false;
                } else {
                    if (layoutSearchBinding.layoutSearch.getVisibility() == View.GONE) {
                        layoutSearchBinding.layoutSearch.setVisibility(View.VISIBLE);
                        layoutSearchBinding.layoutSearch.setTranslationY((float) size);
                    }
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);

                    layoutSearchBinding.layoutSearch.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutSearchBinding.layoutSearch.animate().yBy(-size).setDuration(500);
                        }
                    });

                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    layoutSearchBinding.layoutSearch.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isSearchPageOpen = true;
                }
                return true;
            case R.id.tab4_setting:
                if (binding.viewStubLayoutSetting.getVisibility() == View.GONE) {
                    binding.viewStubLayoutSetting.setVisibility(View.VISIBLE);
                }
                if (isSettingPageOpen) {
                    layoutSettingBinding.layoutSetting.animate().yBy(appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    layoutSettingBinding.layoutSetting.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isSettingPageOpen = false;

                } else {
                    if (layoutSettingBinding.layoutSetting.getVisibility() == View.GONE) {
                        layoutSettingBinding.layoutSetting.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) layoutSettingBinding.viewSettingFirst.getLayoutParams();
                        p.height = appHeight - DpToPx(20);
                        layoutSettingBinding.viewSettingFirst.setLayoutParams(p);
                        p = (LinearLayout.LayoutParams) layoutSettingBinding.layoutSettingSecond.getLayoutParams();
                        p.height = appHeight - DpToPx(20);
                        layoutSettingBinding.layoutSettingSecond.setLayoutParams(p);

                        layoutSettingBinding.layoutSetting.setTranslationY((float) appHeight);
                    }
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);

                    layoutSettingBinding.layoutSetting.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutSettingBinding.layoutSetting.animate().yBy(-appHeight).setDuration(500);
                            layoutSettingBinding.scrollViewSetting.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    layoutSettingBinding.layoutSetting.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isSettingPageOpen = true;
                }
                return true;
            case R.id.tab5_notice:
                if (binding.viewStubLayoutNoti.getVisibility() == View.GONE) {
                    binding.viewStubLayoutNoti.setVisibility(View.VISIBLE);
                }
                if (isNotiPageOpen) {
                    layoutNotiBinding.layoutNoti.animate().yBy(appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    layoutNotiBinding.layoutNoti.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isNotiPageOpen = false;

                } else {
                    if (layoutNotiBinding.layoutNoti.getVisibility() == View.GONE){
                        layoutNotiBinding.layoutNoti.setVisibility(View.VISIBLE);
                        // 알림 화면
                        int settingViewHeight = appHeight - DpToPx(20);
                        ViewGroup.LayoutParams params;
                        params = layoutNotiBinding.viewNotiFirst.getLayoutParams();
                        params.height = settingViewHeight;
                        layoutNotiBinding.viewNotiFirst.setLayoutParams(params);

                        params = layoutNotiBinding.layoutNotiSecond.getLayoutParams();
                        params.height = settingViewHeight;
                        layoutNotiBinding.layoutNotiSecond.setLayoutParams(params);

                        layoutNotiBinding.layoutNoti.setTranslationY((float) appHeight);
                    }
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);

                    layoutNotiBinding.layoutNoti.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutNotiBinding.layoutNoti.animate().yBy(-appHeight).setDuration(500);
                            layoutNotiBinding.scrollViewNoti.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    layoutNotiBinding.layoutNoti.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isNotiPageOpen = true;
                }
                return true;
        }
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (binding.layoutRoot.isShown()) {
            getAppDisplaySize();
        }
    }

    private void setInit() {  // 뷰페이저2 실행 메서드
        binding.viewStubLayoutSearch.setVisibility(View.VISIBLE);

        viewPageSetup = layoutSearchBinding.viewPagerSearch;  // 여기서 뷰페이저를 참조한다.
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
        int childCount = layoutSearchBinding.pagerIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutSearchBinding.pagerIndicator.getChildAt(i);
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

    void showAlertDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage(str);
        Log.i("cis", "여기오나?");
        Log.i("cis", str);
        builder.show();
    }

    private static int DpToPx(int dp) {
        return Math.round((float) dp * appDensity);
    }

}