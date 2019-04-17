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
import com.qiu.keepaccount.entity.Account;
import com.qiu.keepaccount.entity.AccountType;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;
import com.qiu.keepaccount.model.type.AccountTypeModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author qiuhong.ma
 * @Date 2019/3/24 14:07
 * @Description 记账页面recyclerView cardview adapter
 *
 */
public class AccountRecyclerAdapter extends RecyclerView.Adapter<AccountRecyclerAdapter.AccountItemHolder>{

    private RecyclerItemClickListener mOnItemClickListener;//点击事件
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Account> mAccountsList;

    public AccountRecyclerAdapter(Context context){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Account> accountList){
        this.mAccountsList = accountList;
    }

    public Account getData(int position){
        return mAccountsList.get(position);
    }

    public void setOnItemClickListener(RecyclerItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AccountItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view = mInflater.inflate(R.layout.item_account_list,viewGroup,false);
        AccountItemHolder accountItemHolder = new AccountItemHolder(view);
        accountItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view,i);
            }
        });
        return accountItemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountItemHolder accountItemViewHolder, int i) {
        accountItemViewHolder.bindData(mAccountsList.get(i));
    }

    @Override
    public int getItemCount() {
        return mAccountsList.size();
    }

    public static class AccountItemHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.account_img_type)
        ImageView mTypeImg;  //记录类型（支出、收入）
        @BindView(R.id.account_txt_money)
        TextView mMoneyText; //金额
        @BindView(R.id.account_txt_type)
        TextView mTypeText;  //记录所属分类
        @BindView(R.id.account_txt_date)
        TextView mDateText;  //创建日期
        @BindView(R.id.account_txt_note)
        TextView mNoteText;  //备注
        @BindView(R.id.account_img_creater)
        ImageView mCreaterImage; //创建人头像
        @BindView(R.id.account_txt_username)
        TextView mUserText; //创建人昵称
        public static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        private DateFormat mDateFormat;
        private AccountTypeModel mAccountTypeModel;

        public AccountItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mDateFormat = new SimpleDateFormat(DATE_FORMAT);
            mAccountTypeModel = new AccountTypeModel();
        }

        public void bindData(Account account){
            //获取支付类型
            int type = account.getType();
            AccountType  accountType= account.getAccountType();
            //AccountType accountType = mAccountTypeModel.getAccountType(account.getTypeId());
            mTypeImg.setImageResource(type==1 ? R.mipmap.ic_type_cost : R.mipmap.ic_type_income);
            mMoneyText.setText(String.valueOf("¥"+account.getAmount()));
            mTypeText.setText(accountType.getName());
            mDateText.setText(mDateFormat.format(account.getCreateTime()));
            mNoteText.setText(account.getRemark());
            mCreaterImage.setImageResource(R.mipmap.ic_def_icon);
            //mUserText.setText(account.getUser().getNickName());

        }
    }

}
