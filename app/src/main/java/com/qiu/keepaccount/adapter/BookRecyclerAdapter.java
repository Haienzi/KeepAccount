package com.qiu.keepaccount.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiu.keepaccount.R;
import com.qiu.keepaccount.entity.Book;
import com.qiu.keepaccount.listener.RecyclerItemClickListener;

import java.util.List;

/**
 * @Author qiuhong.ma
 * @Date 2019/4/1 10:48
 * @Description
 */
public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookItemViewHolder> {

    private RecyclerItemClickListener mOnItemClickListener;//点击事件
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Book> mBookList;

    public BookRecyclerAdapter(Context context, List<Book> bookList){
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mBookList = bookList;
    }

    public void setData(List<Book> books){
        mBookList = books;
    }
    public void setOnItemClickListener(RecyclerItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.item_book_list,null);
        BookItemViewHolder bookItemViewHolder = new BookItemViewHolder(view);
        return bookItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemViewHolder bookItemViewHolder, int i) {
        bookItemViewHolder.bindData(mBookList.get(i));
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    public static class BookItemViewHolder extends RecyclerView.ViewHolder{



        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bindData(Book book){

        }
    }
}
