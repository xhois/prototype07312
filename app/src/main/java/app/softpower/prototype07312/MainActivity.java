package app.softpower.prototype07312;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import app.softpower.prototype07312.databinding.ActivityMainBinding;
import app.softpower.prototype07312.databinding.IncludeDeviceStatusBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutAppendChildBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutNotiBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutSetRulesBinding;
import app.softpower.prototype07312.databinding.IncludeLayoutSettingBinding;
import app.softpower.prototype07312.databinding.ViewstubLayoutMenuBinding;
import app.softpower.prototype07312.databinding.ViewstubLayoutPaymentBinding;
import app.softpower.prototype07312.databinding.ViewstubLayoutSearchBinding;
import app.softpower.prototype07312.databinding.ViewstubLayoutSetting2Binding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationBarView.OnItemSelectedListener {
    public static Context mContext;

    private ActivityMainBinding binding;
    private ViewstubLayoutSearchBinding layoutSearchBinding;
    private IncludeLayoutSettingBinding layoutSettingBinding;
    private IncludeLayoutNotiBinding layoutNotiBinding;
    private IncludeLayoutSetRulesBinding layoutSetRulesBinding;
    private IncludeLayoutAppendChildBinding layoutAppendChildBinding;
    private ViewstubLayoutMenuBinding layoutMenuBinding;
    private IncludeDeviceStatusBinding deviceStatusBinding;
    private ViewstubLayoutSetting2Binding layoutSetting2Binding;
    private ViewstubLayoutPaymentBinding layoutPaymentBinding;
    private ViewPager2 viewPageSetup;

    ColorStateList def;

    boolean isMenuPageOpen = false;
    boolean isSearchPageOpen = false;
    boolean isSettingPageOpen = false;
    boolean isNotiPageOpen = false;
    boolean isAvatarChecked = false;
    boolean isAppendChild = false;
    boolean isDeviceStatusOpen = false;
    boolean isSetting2PageOpen = false;
    boolean isPaymentPageOpen = false;
    private static int appWidth;
    private static int appHeight;
    private static float appDensity;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    initWeeklySchedule();
                }
            });

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
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
        binding.spinnerUser.setOnClickListener(this);
        binding.buttonChildAdd.setOnClickListener(this);
        binding.buttonModiChild.setOnClickListener(this);
        binding.include1.buttonSettingRull2.setOnClickListener(this);
        binding.textViewDeviceStatus.setOnClickListener(this);

        binding.bottomNavigationView.setOnItemSelectedListener(this);

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

                // ???????????? ??????, ????????? ????????? ???????????? ??????
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

                // ?????? ??????, ????????? ????????? ???????????? ??????
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

        binding.viewStubLayoutSetRules.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutSetRulesBinding = IncludeLayoutSetRulesBinding.bind(inflated);

                layoutSetRulesBinding.item12.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.item22.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.item32.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.item42.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.item52.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.buttonBackToSummary.setOnClickListener(MainActivity.this);
                layoutSetRulesBinding.includeUsageTime.buttonModifyWeeklySchedule.setOnClickListener(MainActivity.this);

                initWeeklySchedule();
                AppInfoUtil appInfoUtil = new AppInfoUtil();
                appInfoUtil.getAppList(getApplicationContext());

                LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) layoutSetRulesBinding.layoutSetRules.getLayoutParams();
                p.setMargins(DpToPx(10), DpToPx(5), DpToPx(10), 0);
                layoutSetRulesBinding.layoutSetRules.setLayoutParams(p);

                Spinner spinnerUser2_rule = layoutSetRulesBinding.includeApp.spinnerUser2;
                String[] itemsSpinnerUser2_rule = {" ?????? ????????? ?????? (75)", " ????????? ?????? ??? ??????(29)", " ?????? ?????? ?????? (6)", " ??????/?????? ?????? ??????(16)", " ?????? ?????? ?????? ??? (5)", " ??????????????? ???????????? (1)", " ?????? ?????? ???(4)"};
                ArrayAdapter<String> adapter2_rule = new ArrayAdapter<String>(getBaseContext(), R.layout.spinner_item5, itemsSpinnerUser2_rule);
                adapter2_rule.setDropDownViewResource(R.layout.spinner_item5_dropdown);
                spinnerUser2_rule.setAdapter(adapter2_rule);

                Spinner spinnerLocationSpan = layoutSetRulesBinding.includeLocation.spinnerUser3;
                String[] itemsSpinnerLocationSpan = {"1 ??????", "2 ??????", "3 ??????"};
                ArrayAdapter<String> adapterLocationSpan = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_spinner_dropdown_item, itemsSpinnerLocationSpan);
                spinnerLocationSpan.setAdapter(adapterLocationSpan);

                //------------------------------------------------------------------------------
                String textTmp = "??? ????????? ?????? ????????? ?????????? ????????????";
                SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan3 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("???????????? ?????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan3, 18, 22, 0);
                ssb.setSpan(new AbsoluteSizeSpan(50), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
                layoutSetRulesBinding.includeApp.textViewAppList.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewAppList.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ?????? ????????? ?????? ????????? ??? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan4 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ????????? ?????? ????????? ??? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan4, 23, 27, 0);
                layoutSetRulesBinding.includeApp.textView36.setText(ssb);
                layoutSetRulesBinding.includeApp.textView36.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ????????? ????????? ????????? ???????????? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan5 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ?????? ??????????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan5, 24, 28, 0);
                layoutSetRulesBinding.includeApp.textView37.setText(ssb);
                layoutSetRulesBinding.includeApp.textView37.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ?????? ?????? ???????????? ?????? ????????? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan6 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ?????? ???????????? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan6, 25, 29, 0);
                layoutSetRulesBinding.includeApp.textView38.setText(ssb);
                layoutSetRulesBinding.includeApp.textView38.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "?????? ?????? ????????? ?????? ?????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan7 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ?????? ????????????~");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan7, 16, 20, 0);
                layoutSetRulesBinding.includeApp.textView39.setText(ssb);
                layoutSetRulesBinding.includeApp.textView39.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????????????????? ??????????????? ??????????????? ???????????? ????????? ?????? ?????? ??? ??? ??????(??????,V3,???????????????,????????? ???) ????????? ?????? ???????????????. ???????????? ????????? ????????? ???????????? ??? ????????? ?????????????????? ????????????. ????????????/????????? ??? ?????? ????????? ????????? ????????????. ?????????????????? ???????????????. ??? ????????? ?????? ????????? WiFi????????? ???????????? ????????? ???????????????.";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan8 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????????????????? ?????? ????????????~");
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
                        showAlertDialog("??????~ ????????????~");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan9, 179, 182, 0);
                layoutSetRulesBinding.includeApp.textViewBlockSetting.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewBlockSetting.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "???GPS??? ?????? ????????? ????????? ?????? ?????? ???????????? ?????????. MDM???????????? ???????????? ?????? ???????????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan10 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("MDM??????????????? ???????");
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
                        showAlertDialog("??? ?????? ??? ????????? ??????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan11, 55, 62, 0);
                layoutSetRulesBinding.includeApp.textViewGps.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewGps.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????? ?????? ??????(Launcher)?????? ?????? ?????? ??? ????????? ?????? ?????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan12 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("??? ???????????? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan12, 42, 48, 0);
                layoutSetRulesBinding.includeApp.textViewLauncher.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewLauncher.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "?????? ???????????? ??????????????? ?????? ????????? ??? ????????? ???????????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan13 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("??? ??????????????? ?????? ??? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan13, 34, 40, 0);
                layoutSetRulesBinding.includeApp.textViewKeyboard.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewKeyboard.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "VPN ????????? ???????????? ?????? ????????? ????????? ???????????? ?????????????????? ???????????? ?????? ???????????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan14 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan14, 51, 57, 0);
                layoutSetRulesBinding.includeApp.textViewVPN.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewVPN.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "???????????? ?????? ???????????? ???????????? ?????? ???????????????. ????????? ????????? ???????????? ?????? ?????? ??????????????? ?????? ????????? ??? ????????????. ?????? ????????????, ?????? ????????? ????????? ??????????????? ??? ????????? ???????????? ???????????????. ????????? ?????? ?????? ????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan15 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan15, 112, 130, 0);
                layoutSetRulesBinding.includeApp.textViewLaboratory.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewLaboratory.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????? ?????? ??????????????? ???????????? ??????????????? ?????? ?????? ????????? ??? ?????????????????? ???????????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan16 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ???????????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan16, 50, 56, 0);
                layoutSetRulesBinding.includeApp.textViewBackgroundMusic.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewBackgroundMusic.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????? ??? ?????? ?????? ????????? ????????????, \"????????? ????????????\"??? ????????? ???????????? ???????????? ????????? ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan17 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("???????????? ????????? ??? ????????????. ??????????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan17, 52, 58, 0);
                layoutSetRulesBinding.includeApp.textViewYoutube.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewYoutube.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "?????? ???????????? ????????? ???????????? ?????????????????? ?????? ????????? ???????????? ???????????? ?????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan18 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("??? ???????????? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan18, 46, 52, 0);
                layoutSetRulesBinding.includeApp.textViewPhone.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewPhone.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????? ???????????? ????????? ???????????? ?????????????????????, ?????????????????? ????????? ???????????? ???????????? ?????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan19 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("??? ????????? ??? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan19, 53, 59, 0);
                layoutSetRulesBinding.includeApp.textViewMessage.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewMessage.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "Play????????? ???????????? ????????? ???????????? ?????????????????? ?????? ????????? ???????????? ???????????? ?????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan20 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("??? ????????? ????????? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan20, 51, 57, 0);
                layoutSetRulesBinding.includeApp.textViewPlayStore.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewPlayStore.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ????????? ????????? ???????????? ???????????? ???????????????. ??? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan21 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ???????????? ????????????.");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan21, 27, 33, 0);
                layoutSetRulesBinding.includeApp.textViewWidget.setText(ssb);
                layoutSetRulesBinding.includeApp.textViewWidget.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "?????? ?????????????????? ????????? ??????????????? ????????????, ????????? ???????????? ????????? ?????? ???????????? ????????? ?????? ??????????????? ??? ??? ????????????. (????????? ???????????? ?????? ???????????????, ???????????? ????????? ????????? ????????? ????????? ??????????????? ????????????, ??? ???????????? \"????????? ??????\"??? ????????? ?????????.)";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan22 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ?????? ???????????? ??????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan22, 131, 137, 0);
                ssb.setSpan(new StyleSpan(Typeface.BOLD), 111, 148, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
                layoutSetRulesBinding.includeUsageTime.textView7.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textView7.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ?????? ??????????????? ?????? ???????????? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan23 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ??????????????? ?????? ???????????? ?????? ????????? ?????? ??????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan23, 24, 28, 0);
                layoutSetRulesBinding.includeUsageTime.textView.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textView.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ?????? \"????????? ?????????\"?????? ????????? ????????? ???????????????. ?????? ????????? ??????????????? \"????????????\" ?????? \"??????????????? ????????????\"?????? ??????????????? ????????????.";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan24 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ????????? ????????????");
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
                        showAlertDialog("???????????? ????????????");
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
                        showAlertDialog("??????????????? ???????????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan26, 56, 66, 0);
                layoutSetRulesBinding.includeUsageTime.textViewRestrictFunction.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textViewRestrictFunction.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "????????? ?????? ??? ?????? ??????????????? ????????? ??????????????? ???????????????. ??? ????????????>>";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan27 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ???????????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan27, 35, 43, 0);
                layoutSetRulesBinding.includeUsageTime.textViewDeviceUsageTime.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textViewDeviceUsageTime.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? '????????????,????????????'??? ???????????? ???????????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan28 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????????,???????????? ?????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan28, 27, 31, 0);
                layoutSetRulesBinding.includeUsageTime.textView31.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textView31.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ????????? ????????? ????????? ???????????? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan29 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("????????? ?????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan29, 24, 28, 0);
                layoutSetRulesBinding.includeUsageTime.textView35.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textView35.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "??? ????????? ??? ?????? ????????? ?????? ??? ??? ?????????? ????????????";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan30 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan30, 27, 31, 0);
                layoutSetRulesBinding.includeUsageTime.textView40.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textView40.setMovementMethod(LinkMovementMethod.getInstance());
                //------------------------------------------------------------------------------
                textTmp = "?????? ?????? ????????? ?????? ????????? ???????????????. ??? ????????????>>";
                ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan31 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ?????? ????????? ?????? ?????? ????????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan31, 24, 32, 0);
                layoutSetRulesBinding.includeUsageTime.textViewMoreTime.setText(ssb);
                layoutSetRulesBinding.includeUsageTime.textViewMoreTime.setMovementMethod(LinkMovementMethod.getInstance());

            }
        });

        binding.viewStubLayoutAppendChild.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutAppendChildBinding = IncludeLayoutAppendChildBinding.bind(inflated);

                layoutAppendChildBinding.imageButtonAppendChildClose.setOnClickListener(MainActivity.this);

                layoutAppendChildBinding.radioGroupAvatar1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId != -1 && isAvatarChecked) {
                            isAvatarChecked = false;
                            layoutAppendChildBinding.radioGroupAvatar2.clearCheck();
                        }
                        isAvatarChecked = true;
                    }
                });

                layoutAppendChildBinding.radioGroupAvatar2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId != -1 && isAvatarChecked) {
                            isAvatarChecked = false;
                            layoutAppendChildBinding.radioGroupAvatar1.clearCheck();
                        }
                        isAvatarChecked = true;
                    }
                });

                // ???????????? ????????????*****************************************************************************************************************
                ArrayList<String> years = new ArrayList<String>();
                int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int i = 2000; i <= thisYear; i++) {
                    years.add(Integer.toString(i));
                }
                ArrayAdapter<String> adapterAddChild = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_spinner_dropdown_item, years);
                layoutAppendChildBinding.spinner.setAdapter(adapterAddChild);

                Spinner spinnerChild2 = layoutAppendChildBinding.spinner2;
                String[] itemsSpinnerChild2 = {"1", "2", "3"};
                ArrayAdapter<String> adapterChild2 = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_spinner_dropdown_item, itemsSpinnerChild2);
                spinnerChild2.setAdapter(adapterChild2);

                Spinner spinnerChild3 = layoutAppendChildBinding.spinner3;
                String[] itemsSpinnerChild3 = {"???????????? ??????", "?????????", "?????????", "?????????"};
                ArrayAdapter<String> adapterChild3 = new ArrayAdapter<String>(
                        getBaseContext(), android.R.layout.simple_spinner_dropdown_item, itemsSpinnerChild3);
                spinnerChild3.setAdapter(adapterChild3);

            }
        });

        binding.viewStubLayoutMenu.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutMenuBinding = ViewstubLayoutMenuBinding.bind(inflated);

                layoutMenuBinding.layoutMenuClose.setOnClickListener(MainActivity.this);
                layoutMenuBinding.imageButtonMenuClose.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuFamily.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuSmartDevice.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuSetting.setOnClickListener(MainActivity.this);
                layoutMenuBinding.menuPayment.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuBaseOfKnowledge.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuHelp.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuSupport.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuCustomerCenter.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuEvaluation.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuNotice.setOnClickListener(MainActivity.this);
                layoutMenuBinding.textViewMenuNews.setOnClickListener(MainActivity.this);

                TextView textView_menu_family = layoutMenuBinding.textViewMenuFamily;
                String textTmp = "?????????   (2)";
                SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
                Drawable drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_family);
                drawableTmp.setBounds(0, 0, 40, 40);
                VerticalImageSpan verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_family.setText(ssb);

                TextView textView_menu_smartDevice = layoutMenuBinding.textViewMenuSmartDevice;
                textTmp = "???????????????    (1)";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_smart_device);
                drawableTmp.setBounds(0, 0, 30, 40);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_smartDevice.setText(ssb);

                TextView textView_menu_setting = layoutMenuBinding.textViewMenuSetting;
                textTmp = "??????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_baseline_settings_24);
                drawableTmp.setBounds(0, 0, 40, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_setting.setText(ssb);

                TextView textView_menu_payment = layoutMenuBinding.textViewMenuPayment;
                textTmp = "?????? ??????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_payment);
                drawableTmp.setBounds(0, 0, 20, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_payment.setText(ssb);

                TextView textView_menu_baseOfKnowledge = layoutMenuBinding.textViewMenuBaseOfKnowledge;
                textTmp = "?????? ?????????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_knowledge);
                drawableTmp.setBounds(0, 0, 40, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_baseOfKnowledge.setText(ssb);

                TextView textView_menu_help = layoutMenuBinding.textViewMenuHelp;
                textTmp = "?????????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_help);
                drawableTmp.setBounds(0, 0, 10, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_help.setText(ssb);

                TextView textView_menu_support = layoutMenuBinding.textViewMenuSupport;
                textTmp = "????????? ??????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_support);
                drawableTmp.setBounds(0, 0, 40, 30);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 7, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_support.setText(ssb);

                TextView textView_menu_customerCenter = layoutMenuBinding.textViewMenuCustomerCenter;
                textTmp = "????????????    02-2135-6877";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_customer);
                drawableTmp.setBounds(0, 0, 30, 30);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
                ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 8, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
                textView_menu_customerCenter.setText(ssb);

                TextView textView_menu_evaluation = layoutMenuBinding.textViewMenuEvaluation;
                textTmp = "??????/????????????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_evaluation);
                drawableTmp.setBounds(0, 0, 40, 40);
                drawableTmp.setTint(Color.RED);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 8, 9, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_evaluation.setText(ssb);

                TextView textView_menu_notice = layoutMenuBinding.textViewMenuNotice;
                textTmp = "????????????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_noti);
                drawableTmp.setBounds(0, 0, 40, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_notice.setText(ssb);

                TextView textView_menu_news = layoutMenuBinding.textViewMenuNews;
                textTmp = "????????????  ";
                ssb = new SpannableStringBuilder(textTmp);
                drawableTmp = ContextCompat.getDrawable(getBaseContext(), R.drawable.image_menu_news);
                drawableTmp.setBounds(0, 0, 40, 40);
                drawableTmp.setTint(Color.BLACK);
                verticalImageSpan = new VerticalImageSpan(drawableTmp);
                ssb.setSpan(verticalImageSpan, 5, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                textView_menu_news.setText(ssb);

            }
        });

        binding.viewStubDeviceStatus.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                deviceStatusBinding = IncludeDeviceStatusBinding.bind(inflated);
            }
        });

        binding.viewStubLayoutSetting2.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutSetting2Binding = ViewstubLayoutSetting2Binding.bind(inflated);

                Spinner spinnerUser1 = layoutSetting2Binding.spinner1;
                String[] itemsSpinnerUser1 = getResources().getStringArray(R.array.country);
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                        getBaseContext(), R.layout.spinner_item, itemsSpinnerUser1);
                adapter1.setDropDownViewResource(R.layout.spinner_item);
                spinnerUser1.setAdapter(adapter1);

                Spinner spinnerUser2 = layoutSetting2Binding.spinner2;
                String[] itemsSpinnerUser2 = getResources().getStringArray(R.array.time);
                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                        getBaseContext(), R.layout.spinner_item, itemsSpinnerUser2);
                adapter2.setDropDownViewResource(R.layout.spinner_item);
                spinnerUser2.setAdapter(adapter2);

                TextView textViewCustomerSupport = layoutSetting2Binding.textView1;
                String textTmp = "****** [??????]";
                SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
                ClickableSpan clickableSpan1 = new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        showAlertDialog("?????? ?????????");
                    }

                    @Override
                    public void updateDrawState(TextPaint textPaint) {
                        textPaint.setColor(getColor(R.color.textColor));
                    }
                };
                ssb.setSpan(clickableSpan1, 7, 11, 0);
                textViewCustomerSupport.setText(ssb);
                textViewCustomerSupport.setMovementMethod(LinkMovementMethod.getInstance());
            }
        });

        binding.viewStubLayoutPayment.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                layoutPaymentBinding = ViewstubLayoutPaymentBinding.bind(inflated);


            }
        });
    }

    private void initWeeklySchedule(){
        SharedPreferences pref = getSharedPreferences("weeklySchedule", Activity.MODE_PRIVATE);
        Set<String> mySet = new HashSet<String>(Arrays.asList(getResources().getStringArray(R.array.weeklySchedule)));
        Set<String> weeklySchedule = pref.getStringSet("weeklySchedule", mySet);

        boolean[] booleans = new boolean[336];
        Arrays.fill(booleans, false);

        for (String value : weeklySchedule){
            int i = Integer.parseInt(value);
            booleans[i] = true;
        }

        List<View> viewList = new ArrayList<View>();
        LinearLayout tl = layoutSetRulesBinding.includeUsageTime.include.linearLayoutViewList;

        for (int i = 0; i < tl.getChildCount(); i++) {
            LinearLayout tr = (LinearLayout) tl.getChildAt(i);
            for (int j = 0; j < tr.getChildCount(); j++) {
                if (!(tr.getChildAt(j) instanceof com.google.android.material.divider.MaterialDivider)) {
                    viewList.add(tr.getChildAt(j));
                }
            }
        }
        int j = 0;
        for (int i = 0; i < booleans.length; i++) {
            if (i != 0) j= j + 48;
            if (j > 335) j = j - 335;
            View vv = viewList.get(j);
            Log.e("cis j: ", String.valueOf(j));
            if (booleans[i]) {
                vv.setBackground(AppCompatResources.getDrawable(this, R.drawable.weekly_red));
            } else {
                vv.setBackground(AppCompatResources.getDrawable(this, R.drawable.weekly_white));
            }
        }
    }

    private void checkForFirstRun() {
        SharedPreferences pref = getSharedPreferences("checkFirst", Activity.MODE_PRIVATE);
        boolean checkFirst = pref.getBoolean("checkFirst", true);
        if (checkFirst) {
            // ??? ?????? ????????? ?????? ?????? ??????
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
//        spinnerUser.setPrompt("????????? ????????? ??????");
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, itemsSpinnerUser);
//        spinnerUser.setAdapter(adapter);

        Spinner spinnerUser2 = binding.include1.spinnerUser2;
        String[] itemsSpinnerUser2 = {" 11???15???,2021-11???15???,2021", " 11???14???,2021-11???14???,2021", " 11???13???,2021-11???13???,2021"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item2, itemsSpinnerUser2);
        adapter2.setDropDownViewResource(R.layout.spinner_item2);
        spinnerUser2.setAdapter(adapter2);

        Spinner spinnerToday = binding.include1.spinnerToday;
        String[] itemsSpinnerToday = {" ??????", " ??????", " ?????????"};
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
        String[] itemsSpinnerLanguage = {"?????????", "English", "Japanese"};
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(
                this, R.layout.spinner_item4, itemsSpinnerLanguage);
        adapterLanguage.setDropDownViewResource(R.layout.spinner_item4);
        spinnerLanguage.setAdapter(adapterLanguage);

        ////////////item2 layout***************************************************************************************************

        Spinner spinnerUser2_2 = binding.include1.spinnerUser22;
        String[] itemsSpinnerUser2_2 = {" 11???15???,2021", " 11???14???,2021", " 11???13???,2021"};
        ArrayAdapter<String> adapter2_2 = new ArrayAdapter<String>(
                this, R.layout.spinner_item2, itemsSpinnerUser2_2);
        adapter2_2.setDropDownViewResource(R.layout.spinner_item2);
        spinnerUser2_2.setAdapter(adapter2_2);


        ///////////////////////////spinner
    }

    private void spannableSetup() {
        //        TextView textSupport = binding.textSupport;
//        String textTmp = "???????????? : 02-2135-6877 (?????? ?????? ??????)";
//        SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
//        ssb.setSpan(new StyleSpan(Typeface.BOLD), 23, textTmp.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
//        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 23, textTmp.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
//        ssb.setSpan(new AbsoluteSizeSpan(35), 21, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
//        textSupport.setText(ssb);
        TextView textView_noti_1 = binding.textViewNoti1;
        String textTmp = "  ?????? [2021.10.28] - \"?????? ????????????\" ?????? ??????";
        SpannableStringBuilder ssb = new SpannableStringBuilder(textTmp);
        int divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_noti_1.setText(ssb);

        TextView textView_noti_2 = binding.textViewNoti2;
        textTmp = "  ?????? [2021.8.31] - \"?????? ????????? ??????\" ?????? ??????";
        ssb = new SpannableStringBuilder(textTmp);
        divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_noti_2.setText(ssb);

        TextView textView_noti_3 = binding.textViewNoti3;
        textTmp = "  ?????? [2021.8.31] - \"?????? ????????? ??????\" ?????? ??????";
        ssb = new SpannableStringBuilder(textTmp);
        divide = textTmp.indexOf(" - ");
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 2, divide, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 2, divide, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), divide + 3, textTmp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_noti_3.setText(ssb);

        TextView textView_usageTime = binding.include1.textViewUsageTime;
        textTmp = "?????? ??????(???)";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new AbsoluteSizeSpan(35), 5, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        textView_usageTime.setText(ssb);

        TextView textView_more = binding.include1.textViewMore;
        textTmp = "  ?????? ?????? ????????? ??? ????????????? ????????????";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_more.setText(ssb);

        TextView textView_copyright = binding.textViewCopyright;
        textTmp = "T2014-2021 ??? Mobile Fence, Inc. All rights reserved.\n?????? ?????? : 02 - 2135 - 6877 (?????? ?????? 9??? ~?????? 6???)\n?????? / ?????? : partner @mobilefence.com\n??????????????? ?????? ????????? ?????? ????????? ????????? ????????? :";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 52, 95, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 13, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 61, 77, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 103, 130, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_copyright.setText(ssb);

        TextView textView_more_2 = binding.include1.textViewMore2;
        textTmp = "  ????????? ????????? ?????????? ????????????";
        ssb = new SpannableStringBuilder(textTmp);
        ssb.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.textColor)), 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // ??????
        textView_more_2.setText(ssb);

        TextView textViewAppUpdate = binding.textViewAppUpdate;
        textTmp = "  ??????????????? ??? ???????????? ????????? ????????? ????????????. ????????????";
        ssb = new SpannableStringBuilder(textTmp);
        Drawable drawableTmp = ContextCompat.getDrawable(this, R.drawable.layerlist_ic_app_update);
        drawableTmp.setBounds(0, 0, 40, 40);
        VerticalImageSpan verticalImageSpan = new VerticalImageSpan(drawableTmp);
        ssb.setSpan(verticalImageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("???????????? ?????????");
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
        textTmp = "[??????!] \"???????????? ??????\"??? ?????? ?????? ????????????. ????????? ?????????????????? ??????????????? ????????? ???????????? ???????????? ?????? ?????? ?????? ????????? ????????????. ?????? ?????? ??????";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("?????? ?????? ?????? ?????????");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan2, 79, 87, 0);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 1, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        textViewSafeMode.setText(ssb);
        textViewSafeMode.setMovementMethod(LinkMovementMethod.getInstance());

        TextView textViewCustomerSupport = binding.textViewCustomerSupport;
        textTmp = " ???????????? : 02-2135-6877 (?????? ?????? ??????)";
        ssb = new SpannableStringBuilder(textTmp);
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                showAlertDialog("?????? ?????? ?????????");
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setColor(getColor(R.color.textColor));
            }
        };
        ssb.setSpan(clickableSpan3, 25, 30, 0);
        ssb.setSpan(new StyleSpan(Typeface.BOLD), 25, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // ?????????
        ssb.setSpan(new RelativeSizeSpan(0.8f), 22, 24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  // ?????????
        textViewCustomerSupport.setText(ssb);
        textViewCustomerSupport.setMovementMethod(LinkMovementMethod.getInstance());

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
            case R.id.item1:                                             // ???????????? ??????
                binding.include1.select.animate().x(0).setDuration(100);
                binding.include1.item1.setTextColor(Color.BLACK);
                binding.include1.item2.setTextColor(def);
                binding.include1.item1Layout.setVisibility(View.VISIBLE);
                binding.include1.item2Layout.setVisibility(View.GONE);
                break;
            case R.id.item2:                                             // ???????????? ??????
                binding.include1.item1.setTextColor(def);
                binding.include1.item2.setTextColor(Color.BLACK);

                int size = binding.include1.item2.getWidth();
                binding.include1.select.animate().x(size).setDuration(100);

                binding.include1.item2Layout.setVisibility(View.VISIBLE);
                binding.include1.item1Layout.setVisibility(View.GONE);
                break;
            case R.id.buttonMenu:                                                 // ?????? ??????
                if (binding.viewStubLayoutMenu.getVisibility() == View.GONE) {
                    binding.viewStubLayoutMenu.setVisibility(View.VISIBLE);
                }
                size = binding.layoutRoot.getWidth();
                if (isMenuPageOpen) {
                    layoutMenuBinding.layoutMenu.animate().x(-size).setDuration(500);
                    binding.layoutMenuMask.animate().alpha(0.0f).setDuration(500);
                    layoutMenuBinding.layoutMenuClose.setClickable(false);
                    isMenuPageOpen = false;
                } else {
                    if (layoutMenuBinding.layoutMenu.getVisibility() == View.GONE) {
                        layoutMenuBinding.layoutMenu.setVisibility(View.VISIBLE);
                    }
                    layoutMenuBinding.layoutMenu.setTranslationX(-(float) size);
                    layoutMenuBinding.layoutMenu.setVisibility(View.VISIBLE);
                    layoutMenuBinding.layoutMenu.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutMenuBinding.layoutMenu.animate().x(0).setDuration(500);
                        }
                    });
                    binding.layoutMenuMask.animate().alpha(0.5f).setDuration(500);
                    layoutMenuBinding.layoutMenuClose.setClickable(true);
                    isMenuPageOpen = true;
                }
                break;
            case R.id.layout_menu_close:                                             // ?????? ?????? ?????? (????????? ??????)
                onClick(binding.buttonMenu);
                break;
            case R.id.imageButton_menu_close:                                        // ?????? ?????? ?????? (X ??????)
                onClick(binding.buttonMenu);
                break;
            case R.id.button_search_cancel:                                          // ?????? ?????? ?????? (X ??????)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(2));
                break;
            case R.id.buttonSettingClose:                                             // ?????? ?????? ?????? ?????? (?????? ??????)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(3));
                break;
            case R.id.imageButtonSettingClose:                                        // ?????? ?????? ?????? ?????? (X ??????)
                onClick(layoutSettingBinding.buttonSettingClose);
                break;
            case R.id.buttonNotiClose:                                                // ?????? ?????? ?????? (?????? ??????)
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(4));
                break;
            case R.id.spinner_user:                                                   // ?????? ?????? ??????
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
            case R.id.button_child_add:                                                       // ?????? ?????? ??????
                if (binding.viewStubLayoutAppendChild.getVisibility() == View.GONE) {
                    binding.viewStubLayoutAppendChild.setVisibility(View.VISIBLE);
                }
                if (isAppendChild) {
                    layoutAppendChildBinding.layoutAppendChild.animate().yBy(appHeight).setDuration(500);
                    binding.layoutSearchMask.animate().alpha(0.0f).setDuration(500);
                    layoutAppendChildBinding.layoutAppendChild.setClickable(false);
                    binding.layoutSearchMask.setClickable(false);
                    isAppendChild = false;

                } else {
                    if (layoutAppendChildBinding.layoutAppendChild.getVisibility() == View.GONE) {
                        layoutAppendChildBinding.layoutAppendChild.setVisibility(View.VISIBLE);
                        ConstraintLayout.LayoutParams p = (ConstraintLayout.LayoutParams) layoutAppendChildBinding.layoutAppendChild.getLayoutParams();
                        int d = DpToPx(10);
                        p.setMargins(d, d, d, d);
                        layoutAppendChildBinding.layoutAppendChild.setLayoutParams(p);
                    }
                    layoutAppendChildBinding.textView23.setText("  ?????? ??????");
                    layoutAppendChildBinding.buttonAppendChildDelete.setVisibility(View.INVISIBLE);
                    layoutAppendChildBinding.layoutAppendChild.setTranslationY((float) appHeight);
                    binding.layoutSearchMask.setAlpha(0.0f);
                    binding.layoutSearchMask.setVisibility(View.VISIBLE);
                    layoutAppendChildBinding.layoutAppendChild.setVisibility(View.VISIBLE);

                    layoutAppendChildBinding.layoutAppendChild.post(new Runnable() {
                        @Override
                        public void run() {
                            layoutAppendChildBinding.layoutAppendChild.animate().yBy(-appHeight).setDuration(500);
                        }
                    });
                    binding.layoutSearchMask.animate().alpha(0.5f).setDuration(500);
                    layoutAppendChildBinding.layoutAppendChild.setClickable(true);
                    binding.layoutSearchMask.setClickable(true);
                    isAppendChild = true;
                }
                break;
            case R.id.imageButtonAppendChildClose:                                                   // ???????????? ?????? ?????? x??????
                onClick(binding.buttonChildAdd);
                break;
            case R.id.button_modi_child:                                                             // ?????? ?????? ??????
                onClick(binding.buttonChildAdd);
                layoutAppendChildBinding.textView23.setText("  ??????");
                layoutAppendChildBinding.buttonAppendChildDelete.setVisibility(View.VISIBLE);
                break;
            case R.id.item1_2:                                                    // ???????????? ?????? ??????
                int x = (int) layoutSetRulesBinding.item12.getX();
                layoutSetRulesBinding.select2.animate().x(x).setDuration(50);
                layoutSetRulesBinding.item12.setTextColor(Color.BLACK);
                layoutSetRulesBinding.item22.setTextColor(def);
                layoutSetRulesBinding.item32.setTextColor(def);
                layoutSetRulesBinding.item42.setTextColor(def);
                layoutSetRulesBinding.item52.setTextColor(def);
                layoutSetRulesBinding.includeApp.item1Layout.setVisibility(View.VISIBLE);
                layoutSetRulesBinding.includeUsageTime.item2Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeWeb.item3Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includePhone.item4Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeLocation.item5Layout.setVisibility(View.GONE);
                ViewGroup.LayoutParams params = layoutSetRulesBinding.select2.getLayoutParams();
                params.width = layoutSetRulesBinding.item12.getWidth();
                layoutSetRulesBinding.select2.setLayoutParams(params);
                break;
            case R.id.item2_2:                                                    // ???????????? ???????????? ??????
                x = (int) layoutSetRulesBinding.item22.getX();
                layoutSetRulesBinding.select2.animate().x(x).setDuration(50);
                layoutSetRulesBinding.item12.setTextColor(def);
                layoutSetRulesBinding.item22.setTextColor(Color.BLACK);
                layoutSetRulesBinding.item32.setTextColor(def);
                layoutSetRulesBinding.item42.setTextColor(def);
                layoutSetRulesBinding.item52.setTextColor(def);
                layoutSetRulesBinding.includeApp.item1Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeUsageTime.item2Layout.setVisibility(View.VISIBLE);
                layoutSetRulesBinding.includeWeb.item3Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includePhone.item4Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeLocation.item5Layout.setVisibility(View.GONE);
                params = layoutSetRulesBinding.select2.getLayoutParams();
                params.width = layoutSetRulesBinding.item22.getWidth();
                layoutSetRulesBinding.select2.setLayoutParams(params);
                break;
            case R.id.item3_2:                                                    // ???????????? ??? ??????
                x = (int) layoutSetRulesBinding.item32.getX();
                layoutSetRulesBinding.select2.animate().x(x).setDuration(50);
                layoutSetRulesBinding.item12.setTextColor(def);
                layoutSetRulesBinding.item22.setTextColor(def);
                layoutSetRulesBinding.item32.setTextColor(Color.BLACK);
                layoutSetRulesBinding.item42.setTextColor(def);
                layoutSetRulesBinding.item52.setTextColor(def);
                layoutSetRulesBinding.includeApp.item1Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeUsageTime.item2Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeWeb.item3Layout.setVisibility(View.VISIBLE);
                layoutSetRulesBinding.includePhone.item4Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeLocation.item5Layout.setVisibility(View.GONE);
                params = layoutSetRulesBinding.select2.getLayoutParams();
                params.width = layoutSetRulesBinding.item32.getWidth();
                layoutSetRulesBinding.select2.setLayoutParams(params);
                break;
            case R.id.item4_2:                                                    // ???????????? ?????? ??????
                x = (int) layoutSetRulesBinding.item42.getX();
                layoutSetRulesBinding.select2.animate().x(x).setDuration(50);
                layoutSetRulesBinding.item12.setTextColor(def);
                layoutSetRulesBinding.item22.setTextColor(def);
                layoutSetRulesBinding.item32.setTextColor(def);
                layoutSetRulesBinding.item42.setTextColor(Color.BLACK);
                layoutSetRulesBinding.item52.setTextColor(def);
                layoutSetRulesBinding.includeApp.item1Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeUsageTime.item2Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeWeb.item3Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includePhone.item4Layout.setVisibility(View.VISIBLE);
                layoutSetRulesBinding.includeLocation.item5Layout.setVisibility(View.GONE);
                params = layoutSetRulesBinding.select2.getLayoutParams();
                params.width = layoutSetRulesBinding.item42.getWidth();
                layoutSetRulesBinding.select2.setLayoutParams(params);
                break;
            case R.id.item5_2:                                                    // ???????????? ?????? ??????
                x = (int) layoutSetRulesBinding.item52.getX();
                layoutSetRulesBinding.select2.animate().x(x).setDuration(50);
                layoutSetRulesBinding.item12.setTextColor(def);
                layoutSetRulesBinding.item22.setTextColor(def);
                layoutSetRulesBinding.item32.setTextColor(def);
                layoutSetRulesBinding.item42.setTextColor(def);
                layoutSetRulesBinding.item52.setTextColor(Color.BLACK);
                layoutSetRulesBinding.includeApp.item1Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeUsageTime.item2Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeWeb.item3Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includePhone.item4Layout.setVisibility(View.GONE);
                layoutSetRulesBinding.includeLocation.item5Layout.setVisibility(View.VISIBLE);
                params = layoutSetRulesBinding.select2.getLayoutParams();
                params.width = layoutSetRulesBinding.item52.getWidth();
                layoutSetRulesBinding.select2.setLayoutParams(params);
                break;
            case R.id.button_setting_rull2:                                         // ???????????? ??????
                binding.include1.layoutSumActivities.setVisibility(View.GONE);
                if (binding.viewStubLayoutSetRules.getVisibility() == View.GONE) {
                    binding.viewStubLayoutSetRules.setVisibility(View.VISIBLE);
                }
                layoutSetRulesBinding.layoutSetRules.setVisibility(View.VISIBLE);
//                binding.layoutNotification.setVisibility(View.GONE);
                break;
            case R.id.buttonModifyWeeklySchedule:                                   // ???????????? -> ???????????? -> ?????????????????? ??????
//                Intent intent = new Intent(this, WeeklyScheduleModification.class);
//                startActivity(intent);



                Intent intent = new Intent(this, WeeklyScheduleModification.class);
                launcher.launch(intent);

                break;
            case R.id.button_back_to_summary:                                       // ?????????????????? ????????????
                layoutSetRulesBinding.layoutSetRules.setVisibility(View.GONE);
                binding.include1.layoutSumActivities.setVisibility(View.VISIBLE);
//                binding.layoutNotification.setVisibility(View.VISIBLE);
                break;
            case R.id.textViewDeviceStatus:                                         // ???????????? ????????????
                if (binding.viewStubDeviceStatus.getVisibility() == View.GONE) {
                    binding.viewStubDeviceStatus.setVisibility(View.VISIBLE);
                }
                if (isDeviceStatusOpen) {
                    binding.layoutMain.setVisibility(View.VISIBLE);
                    deviceStatusBinding.layoutDeviceStatus.setVisibility(View.GONE);
                    isDeviceStatusOpen = false;
                } else {
                    binding.layoutMain.setVisibility(View.GONE);
                    if (binding.viewStubLayoutSetting2.getVisibility() != View.GONE) {
                        layoutSetting2Binding.layoutSetting2.setVisibility(View.GONE);
                    }
                    deviceStatusBinding.layoutDeviceStatus.setVisibility(View.VISIBLE);
                    isDeviceStatusOpen = true;
                }
                break;
            case R.id.textView_menu_family:                                         // ???????????? ?????? ????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
                break;
            case R.id.textView_menu_smartDevice:                                    // ???????????? ?????? ??????????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
                onClick(binding.textViewDeviceStatus);
                break;
            case R.id.textView_menu_setting:                                    // ???????????? ?????? ?????? ????????????
                if (binding.viewStubLayoutSetting2.getVisibility() == View.GONE) {
                    binding.viewStubLayoutSetting2.setVisibility(View.VISIBLE);
                }
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
                binding.layoutMain.setVisibility(View.GONE);
                layoutSetting2Binding.layoutSetting2.setVisibility(View.VISIBLE);
                isSetting2PageOpen = true;
                break;

            case R.id.menuPayment:                                    // ???????????? ?????? ?????? ?????????????????????
                if (binding.viewStubLayoutPayment.getVisibility() == View.GONE) {
                    binding.viewStubLayoutPayment.setVisibility(View.VISIBLE);
                }
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
                binding.layoutMain.setVisibility(View.GONE);
                layoutPaymentBinding.layoutPayment.setVisibility(View.VISIBLE);
                isPaymentPageOpen = true;
                break;

            case R.id.textView_menu_baseOfKnowledge:                 // ???????????? ?????? ??????????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("?????? ????????? ?????????");
                break;

            case R.id.textView_menu_help:                 // ???????????? ?????? ????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("????????? ?????????");
                break;

            case R.id.textView_menu_support:                 // ???????????? ?????? ??????????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("??????????????? ?????????");
                break;

            case R.id.textView_menu_customerCenter:                 // ???????????? ?????? ???????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("???????????? ?????????");
                break;

            case R.id.textView_menu_evaluation:                 // ???????????? ?????? ??????/???????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("??????/???????????? ?????????");
                break;

            case R.id.textView_menu_notice:                 // ???????????? ?????? ???????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("???????????? ?????????");
                break;

            case R.id.textView_menu_news:                 // ???????????? ?????? ???????????? ????????????
                onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(1));
                showAlertDialog("???????????? ?????????");
                break;
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
            onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
            return;
        }
        if (isSetting2PageOpen) {
            onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
            return;
        }
        if (isPaymentPageOpen) {
            onNavigationItemSelected(binding.bottomNavigationView.getMenu().getItem(0));
            return;
        }

        Dialog dialog01;
        dialog01 = new Dialog(MainActivity.this);
        dialog01.setContentView(R.layout.custom_dialog_exit);
        dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog01.getWindow().setAttributes(params);

        dialog01.show();
        Button agree = dialog01.findViewById(R.id.exit_agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                finishAndRemoveTask();
                System.exit(0);
            }
        });
        Button disagree = dialog01.findViewById(R.id.exit_disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog01.dismiss();
            }
        });

//        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // bottomNavigationView ??? ?????????
        switch (item.getItemId()) {
            case R.id.tab1_home:
                if (isMenuPageOpen) {
                    onClick(binding.buttonMenu);
                }
                if (isDeviceStatusOpen) {
                    onClick(binding.textViewDeviceStatus);
                }
                if (isSetting2PageOpen) {
                    layoutSetting2Binding.layoutSetting2.setVisibility(View.GONE);
                    isSetting2PageOpen = false;
                }
                if (isPaymentPageOpen) {
                    layoutPaymentBinding.layoutPayment.setVisibility(View.GONE);
                    isSetting2PageOpen = false;
                }
                binding.layoutMain.setVisibility(View.VISIBLE);
                binding.mainScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.mainScrollView.scrollTo(0, 0);
                    }
                });
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
                    if (layoutNotiBinding.layoutNoti.getVisibility() == View.GONE) {
                        layoutNotiBinding.layoutNoti.setVisibility(View.VISIBLE);
                        // ?????? ??????
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

    private void setInit() {  // ????????????2 ?????? ?????????
        binding.viewStubLayoutSearch.setVisibility(View.VISIBLE);

        viewPageSetup = layoutSearchBinding.viewPagerSearch;  // ????????? ??????????????? ????????????.
        FragPagerAdapter SetupPagerAdapter = new FragPagerAdapter(this); // ???????????????????????? getActivity ??? ????????????, ????????????????????? this ??? ????????????.
        viewPageSetup.setAdapter(SetupPagerAdapter);  //FragPagerAdapter ??? ??????????????? ?????? ViewPager2 ??? ?????? ?????????.
        viewPageSetup.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);  // ????????? ??????
        viewPageSetup.setOffscreenPageLimit(3);  // ????????? ?????? ?????? ??????
        viewPageSetup.setCurrentItem(1, false); // ?????? ????????? ????????? ??????
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

    void showAlertDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog");
        builder.setMessage(str);
        Log.i("cis", "?????????????");
        Log.i("cis", str);
        builder.show();
    }

    private static int DpToPx(int dp) {
        return Math.round((float) dp * appDensity);
    }

    private class FragPagerAdapter extends FragmentStateAdapter {  // ????????????2????????? FragmentStateAdapter ??? ????????????.

        private final int mSetItemCount = 3;  // ??????????????? ?????? ??????

        public FragPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            int iViewIdx = getRealPosition(position);
            switch (iViewIdx) {
                case 0: {
                    return new Fragment_search1(); // ??????????????? ????????? ?????? ????????????
                }
                case 1: {
                    return new Fragment_search2();
                }
                case 2: {
                    return new Fragment_search3();
                }
                default: {
                    return new Fragment_search2();  // ???????????? ???????????? ???????????????
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