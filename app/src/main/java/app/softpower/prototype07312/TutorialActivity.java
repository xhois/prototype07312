package app.softpower.prototype07312;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import app.softpower.prototype07312.databinding.ActivityTutorialBinding;
import app.softpower.prototype07312.databinding.CustomDialogIntroBinding;

public class TutorialActivity extends AppCompatActivity {

    private ActivityTutorialBinding binding;
    private ViewPager2 viewPageSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTutorialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setInit(); // 검색화면 ViewPager2
        // 뷰페이저2 사용 순서
        // xml layout 에 ViewPager2 등록
        // 각각의 페이지용 layout xml 작성
        // 각각의 페이지용 Fragment.java 작성
        // FragmentStateAdapter class 만들고
        // 생성자 만들고, 오버라이드(createFragment, getItemId, getItemCount)
        // onCreate 에서 초기화
        // FragmentStateAdapter 인스턴스 생성
        // viewPager2.setAdapter(fragmentStateAdapter); ViewPager2 에 FragmentStateAdapter 인스턴스를 어댑터로 설정
        // 이러면 끝인데, 기타 여러가지 설정들을 해준다. (스크롤 방향, 페이지 갯수, 처음 보여질 페이지, 옆 페이지 보이게 등등)
        // viewPager2.registerOnPageChangeCallback 리스너 등록하면서
        // 메소드 오버라이드(onPageScrolled, onPageSelected, onPageScrollStateChanged
        // 끝

        binding.backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPage = viewPageSetup.getCurrentItem() - 1;
                if (newPage == -1) newPage = 0;
                viewPageSetup.setCurrentItem(newPage, true);
            }
        });

        binding.forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newPage = viewPageSetup.getCurrentItem() + 1;
                if (newPage < 4) {
                    viewPageSetup.setCurrentItem(newPage, true);
                } else {
                    Dialog dialog01;
                    dialog01 = new Dialog(TutorialActivity.this);
                    dialog01.setContentView(R.layout.custom_dialog_intro);
                    dialog01.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    WindowManager.LayoutParams params = dialog01.getWindow().getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog01.getWindow().setAttributes(params);

                    dialog01.show();
                    Button agree = dialog01.findViewById(R.id.agree);
                    agree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();

                        }
                    });
                    Button disagree = dialog01.findViewById(R.id.disagree);
                    disagree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog01.dismiss();
                        }
                    });
                }
            }
        });
    }



    private void setInit() {  // 뷰페이저2 실행 메서드
        viewPageSetup = binding.viewPagerTutorial;  // 여기서 뷰페이저를 참조한다.
        TutorialActivity.FragPagerAdapter SetupPagerAdapter = new TutorialActivity.FragPagerAdapter(this); // 프래그먼트에서는 getActivity 로 참조하고, 액티비티에서는 this 를 사용한다.
        viewPageSetup.setAdapter(SetupPagerAdapter);  //FragPagerAdapter 를 파라미터로 받고 ViewPager2 에 전달 받는다.
        viewPageSetup.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);  // 방향은 가로
        viewPageSetup.setOffscreenPageLimit(4);  // 페이지 한계 갯수 지정
        viewPageSetup.setCurrentItem(0, false); // 처음 보여질 페이지 지정
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
                if (position == 3) {
                    binding.forward.setText("시작");
                } else {
                    binding.forward.setText("다음");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        binding.indicator.attachTo(viewPageSetup);
    }


    class FragPagerAdapter extends FragmentStateAdapter {  // 뷰페이저2에서는 FragmentStateAdapter 를 사용한다.

        private final int mSetItemCount = 4;  // 프래그먼트 갯수 지정

        public FragPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            int iViewIdx = getRealPosition(position);
            switch (iViewIdx) {
                case 0: {
                    return new Fragment_tutorial1(); // 프래그먼트 순서에 맞게 넣어주기
                }
                case 1: {
                    return new Fragment_tutorial2();
                }
                case 2: {
                    return new Fragment_tutorial3();
                }
                case 3: {
                    return new Fragment_tutorial4();
                }
                default: {
                    return new Fragment_tutorial1();  // 기본으로 나와있는 프래그먼트
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