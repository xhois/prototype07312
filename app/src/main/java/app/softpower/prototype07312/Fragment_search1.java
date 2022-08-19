package app.softpower.prototype07312;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.softpower.prototype07312.databinding.FragmentSearch1Binding;

public class Fragment_search1 extends Fragment {
    private FragmentSearch1Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearch1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView mRecyclerView = binding.recyclerView1;

        // initiate adapter
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter();

        // initiate recyclerView
        mRecyclerView.setAdapter(myRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // adapt data
        myRecyclerAdapter.setStringList(getResources().getStringArray(R.array.tips));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
// 레이아웃에 리사이클러뷰 삽입
// 리사이클러뷰의 개별뷰 레이아웃 작성
// 리사이클러뷰 어댑터 클래스 작성
// 뷰홀더 클래스를 리사이클러뷰 어댑터 클래스 내부에 작성
// 데이터 클래스 작성 (혹은 문자열 리스트 라던가...)
// onCreate 에서 리사이클러뷰에 리사이클러뷰어댑터 등록
//              레이아웃 매니저 등록
//              리사이클러뷰어뎁터로 각 아이템 생성