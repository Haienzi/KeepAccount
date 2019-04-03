package com.qiu.keepaccount.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.qiu.keepaccount.entity.Type;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:43
 * @Description
 */
public class AccountTypeRecyclerAdapter extends RecyclerView.Adapter<AccountTypeRecyclerAdapter.TypeItemViewHolder> {


    private RecyclerItemClickListener mOnItemClickListener;//点击事件
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Type> mAccountType;

    public AccountTypeRecyclerAdapter(Context context, List<Type> mAccountType){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mAccountType = mAccountType;
    }

    public void setOnItemClickListener(RecyclerItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TypeItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_account_type,viewGroup,false);
        TypeItemViewHolder typeItemViewHolder = new TypeItemViewHolder(view);

        typeItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,i);
            }
        });

        return typeItemViewHolder;
    }

    /**
     * 绑定数据
     * @param typeItemViewHolder viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull TypeItemViewHolder typeItemViewHolder, int i) {
        typeItemViewHolder.bindData(mAccountType.get(i));
    }

    @Override
    public int getItemCount() {
        return mAccountType.size();
    }

    public static class TypeItemViewHolder extends RecyclerView.ViewHolder {

        ImageView mTypeImg;

        TextView mTypeText;

        public TypeItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(Type type){
            mTypeImg.setImageResource(type.getResid());
            mTypeText.setText(type.getDesc());
        }
    }
}
