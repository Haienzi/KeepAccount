package com.qiu.keepaccount.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:43
 * @Description
 */
public class AccountTypeAdapter extends RecyclerView.Adapter<AccountTypeAdapter.TypeItemViewHolder> {


    private RecyclerItemClickListener mOnItemClickListener;//点击事件
    private Context mContext;
    private LayoutInflater mInflater;
    private List<AccountType> mAccountType;

    public AccountTypeAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setTypeList(List<AccountType> typeList ){
        mAccountType = typeList;
    }
    public void setOnItemClickListener(RecyclerItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public TypeItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,  int i) {
        View view = mInflater.inflate(R.layout.item_account_type,viewGroup,false);
        TypeItemViewHolder typeItemViewHolder = new TypeItemViewHolder(view);

        return typeItemViewHolder;
    }

    public AccountType getData(int position){
        return mAccountType.get(position);
    }

    /**
     * 绑定数据
     * @param typeItemViewHolder viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull TypeItemViewHolder typeItemViewHolder, final int i) {
        typeItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v,i);
            }
        });
        typeItemViewHolder.bindData(mAccountType.get(i),mContext);
    }

    @Override
    public int getItemCount() {
        return mAccountType.size();
    }

    public static class TypeItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.account_type_img)
        ImageView mTypeImg;
        @BindView(R.id.account_type_text)
        TextView mTypeText;

        public TypeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void bindData(AccountType type,Context context){
            mTypeImg.setImageDrawable(context.getResources().getDrawable(type.getTypeIcon()));
            mTypeText.setText(type.getName());
        }
    }
}
