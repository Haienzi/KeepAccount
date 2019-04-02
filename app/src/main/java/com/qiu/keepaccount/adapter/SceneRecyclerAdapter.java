package com.qiu.keepaccount.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.qiu.keepaccount.entity.SceneData;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:48
 * @Description
 */
public class SceneRecyclerAdapter extends RecyclerView.Adapter<SceneRecyclerAdapter.SceneItemViewHolder>{

    private RecyclerItemClickListener mOnItemClickListener;//点击事件
    private Context mContext;
    private LayoutInflater mInflater;
    private List<SceneData> mSceneList;

    public SceneRecyclerAdapter(Context context, List<SceneData> sceneDataList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mSceneList = sceneDataList;
    }

    public void setOnItemClickListener(RecyclerItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public SceneItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_scene_list,null);
        SceneItemViewHolder sceneItemViewHolder = new SceneItemViewHolder(view);
        return sceneItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SceneItemViewHolder sceneItemViewHolder, int i) {
        sceneItemViewHolder.bindData(mSceneList.get(i),mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSceneList.size();
    }

    public static class SceneItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.scene_img)
        ImageView mSceneImg;
        @BindView(R.id.scene_title)
        TextView mSceneTitle;
        @BindView(R.id.scene_desc)
        TextView mSceneDesc;
        @BindView(R.id.btn_ok)
        AppCompatRadioButton mOkBtn;

        public SceneItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindData(SceneData scene, final RecyclerItemClickListener itemClickListener){
            mSceneImg.setImageResource(scene.getResid());
            mSceneTitle.setText(scene.getTitle());
            mSceneDesc.setText(scene.getDesc());
            mOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(v);
                }
            });
        }
    }
}
