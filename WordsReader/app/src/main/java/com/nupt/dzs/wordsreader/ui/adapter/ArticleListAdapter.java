package com.nupt.dzs.wordsreader.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nupt.dzs.wordsreader.R;
import com.nupt.dzs.wordsreader.impl.IArticleListView;
import com.nupt.dzs.wordsreader.model.ArticleModel;
import com.nupt.dzs.wordsreader.ui.activity.ArticleDetailActivity;

import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleItemVH> {


    private IArticleListView mActicleListView;
    private List<ArticleModel> articleModels;
    private Context mContext;

    public ArticleListAdapter(IArticleListView iArticleListView, List<ArticleModel> models) {
        mActicleListView = iArticleListView;
        mContext = iArticleListView.getContext();
        articleModels = models;
    }

    @Override
    public ArticleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, parent, false);
        return new ArticleItemVH(itemview);
    }

    @Override
    public void onBindViewHolder(ArticleItemVH holder, int position) {
        holder.setdata(articleModels.get(position));
    }

    @Override
    public int getItemCount() {
        return articleModels.size();
    }

    public final class ArticleItemVH extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvPreview;
        TextView tvTextCount;
        View itemview;
        public ArticleItemVH(View itemView) {
            super(itemView);
            if(itemview==null){
                itemview = itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvPreview = (TextView) itemView.findViewById(R.id.tvPreview);
                tvTextCount = (TextView) itemView.findViewById(R.id.tvText_count);
            }

        }

        public void setdata(final ArticleModel articleModel) {
            tvTitle.setText(articleModel.getEngTitle()+"");
            tvPreview.setText(articleModel.getEngContent()+"");
            tvTextCount.setText("单词量: " + articleModel.getWordCount());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActicleListView.viewDetail(articleModel);
                }
            });
        }
    }
}
