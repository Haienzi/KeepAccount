package com.qiu.keepaccount.mvp.books;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.adapter.SceneRecyclerAdapter;
import com.qiu.keepaccount.base.BaseFragment;
import com.qiu.keepaccount.entity.SceneData;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 */
public class AddBookFragment extends BaseFragment implements BookContract.AddBookView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.edt_book_name)
    AppCompatEditText mEditBookName;
    @BindView(R.id.txt_book_scene)
    TextView mSceneText;
    @BindView(R.id.txt_book_name_size)
    TextView mNameSizeTxt;
    @BindView(R.id.scene_recycler_view)
    RecyclerView mRecyclerView;

    private SceneRecyclerAdapter mSceneRecyclerAdapter;
    private List<SceneData> mSceneList = new ArrayList<>();
    private BookContract.BookPresenter mPresenter;


    public AddBookFragment() {
        // Required empty public constructor
    }


    public static AddBookFragment newInstance() {
        AddBookFragment fragment = new AddBookFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 获取 Layout 布局
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    @Override
    public void lazyLoad() {

    }

    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        mEditBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = 0;
                setBookNameSize(charSequence != null ? charSequence.length() : length);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        setBookNameSize(0);

        initAdapter();
    }

    private void setBookNameSize(int length){
        mNameSizeTxt.setText("(".concat(String.valueOf(length)).concat("/10)"));
    }
    private void initSceneData(){
        mSceneList.clear();
        String[] sceneName = getResources().getStringArray(R.array.scene_type);
        String[] sceneNote = getResources().getStringArray(R.array.scene_note);
        TypedArray sceneIcon = getResources().obtainTypedArray(R.array.scene_icon);
        for(int i=0;i<sceneName.length;i++){
            SceneData sceneData = new SceneData();
            sceneData.setTitle(sceneName[i]);
            sceneData.setDesc(sceneNote[i]);
            sceneData.setResid(sceneIcon.getResourceId(i,0));
            mSceneList.add(sceneData);
        }
        sceneIcon.recycle();

    }
    private void initAdapter(){
        initSceneData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.
                VERTICAL,false));
        mSceneRecyclerAdapter = new SceneRecyclerAdapter(getContext(),mSceneList);
        mRecyclerView.setAdapter(mSceneRecyclerAdapter);
        mSceneRecyclerAdapter.setOnItemClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SceneData sceneData = mSceneList.get(position);
                mSceneText.setText(sceneData.getTitle());
            }
        });
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void save() {

    }


    /**
     * 在view层获取相应的Presenter实例进行交互
     *
     * @param presenter
     */
    @Override
    public void setPresenter(BookContract.BookPresenter presenter) {
        mPresenter = presenter;
    }
}
