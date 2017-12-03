package com.teste.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.teste.app.R
import com.teste.app.model.FeedModel
import java.util.*

class FeedRecyclyAdapter(private val mFeeds: ArrayList<FeedModel>)
    : RecyclerView.Adapter<FeedRecyclyAdapter.TodosViewHolder>() {

    /**
     * Método que adiciona um item do adptador
     * @param feed
     * @param position
     * @return
     */
    fun addListItem(feed: FeedModel, position: Int) {
        this.mFeeds.add(feed)
        notifyItemInserted(position)
    }

    /**
     * Método que remove um item do adptador a partir de uma posição
     * @param position
     * @return
     */
    fun removeListItem(position: Int) {
        this.mFeeds.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Método que retorna o tamanho da lista de itens do adaptador
     * @return Int
     */
    override fun getItemCount(): Int {
        return this.mFeeds.size
    }

    /**
     * Método executado na criação do view holder
     * @param viewGroup
     * @param position
     * @return TodosViewHolder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): TodosViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_feed,
                viewGroup, false)
        return TodosViewHolder(itemView)
    }

    /**
     * Método executado na atualização do view holder
     * @param todosViewHolder
     * @param position
     * @return Int
     */
    override fun onBindViewHolder(todosViewHolder: TodosViewHolder, position: Int) {
        val feed = this.mFeeds[position]

        val type = feed.type
        when (type) {
            2022 -> {

                todosViewHolder.mFeed2022.visibility = View.VISIBLE
                todosViewHolder.mFeed1999.visibility = View.GONE
                todosViewHolder.mFeed1994.visibility = View.GONE
                todosViewHolder.mFeed1997.visibility = View.GONE

                todosViewHolder.mTitleTextView2022.text = feed.title
                todosViewHolder.mDescriptionTextView2022.text = feed.publicationDescription
                todosViewHolder.mAuthorNameTextView2022.text = feed.author.name
                todosViewHolder.mCountLikeTextView2022.text = String.format("%s gostei", feed.publicationCountLike.toString())
                if (feed.feed_status == 1) {
                    todosViewHolder.mExclamacaoImageView2022.visibility = View.VISIBLE
                } else {
                    todosViewHolder.mExclamacaoImageView2022.visibility = View.GONE
                }
            }
            1999 -> {

                todosViewHolder.mFeed2022.visibility = View.GONE
                todosViewHolder.mFeed1999.visibility = View.VISIBLE
                todosViewHolder.mFeed1994.visibility = View.GONE
                todosViewHolder.mFeed1997.visibility = View.GONE

                todosViewHolder.mTitleTextView1999.text = feed.title
                todosViewHolder.mCommunityAuthorNameTextView1999.text = String.format(
                        "%s - %s",
                        feed.community.name,
                        feed.author.name
                )

                if (feed.feed_status == 1) {
                    todosViewHolder.mExclamacaoImageView1999.visibility = View.VISIBLE
                } else {
                    todosViewHolder.mExclamacaoImageView1999.visibility = View.GONE
                }
            }
            1994 -> {

                todosViewHolder.mFeed2022.visibility = View.GONE
                todosViewHolder.mFeed1999.visibility = View.GONE
                todosViewHolder.mFeed1994.visibility = View.VISIBLE
                todosViewHolder.mFeed1997.visibility = View.GONE

                todosViewHolder.mTitleTextView1994.text = feed.title
                todosViewHolder.mCommunityAuthorNameTextView1994.text = String.format(
                        "%s - %s",
                        feed.community.name,
                        feed.author.name
                )

                if (feed.feed_status == 1) {
                    todosViewHolder.mExclamacaoImageView1994.visibility = View.VISIBLE
                } else {
                    todosViewHolder.mExclamacaoImageView1994.visibility = View.GONE
                }
            }
            1997 -> {

                todosViewHolder.mFeed2022.visibility = View.GONE
                todosViewHolder.mFeed1999.visibility = View.GONE
                todosViewHolder.mFeed1994.visibility = View.GONE
                todosViewHolder.mFeed1997.visibility = View.VISIBLE

                todosViewHolder.mTitleTextView1997.text = feed.title
                todosViewHolder.mMasterNameTextView1997.text = String.format("%s", feed.master.name)

                if (feed.feed_status == 1) {
                    todosViewHolder.mExclamacaoImageView1997.visibility = View.VISIBLE
                } else {
                    todosViewHolder.mExclamacaoImageView1997.visibility = View.GONE
                }
            }
        }

    }

    inner class TodosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mFeed2022: View = itemView.findViewById(R.id.feed2022)
        val mFeed1999: View = itemView.findViewById(R.id.feed1999)
        val mFeed1994: View = itemView.findViewById(R.id.feed1994)
        val mFeed1997: View = itemView.findViewById(R.id.feed1997)

        val mTitleTextView2022: TextView = itemView.findViewById(R.id.titleTextView2022)
        val mDescriptionTextView2022: TextView = itemView.findViewById(R.id.descriptionTextView2022)
        val mAuthorNameTextView2022: TextView = itemView.findViewById(R.id.authorNameTextView2022)
        val mCountLikeTextView2022: TextView = itemView.findViewById(R.id.countLikeTextView2022)
        val mExclamacaoImageView2022: ImageView = itemView.findViewById(R.id.exclamacaoImageView2022)

        val mTitleTextView1999: TextView = itemView.findViewById(R.id.titleTextView1999)
        val mExclamacaoImageView1999: ImageView = itemView.findViewById(R.id.exclamacaoImageView1999)
        val mCommunityAuthorNameTextView1999: TextView = itemView.findViewById(R.id.communityAuthorNameTextView1999)

        val mTitleTextView1994: TextView = itemView.findViewById(R.id.titleTextView1994)
        val mExclamacaoImageView1994: ImageView = itemView.findViewById(R.id.exclamacaoImageView1994)
        val mCommunityAuthorNameTextView1994: TextView = itemView.findViewById(R.id.communityAuthorNameTextView1994)

        val mTitleTextView1997: TextView = itemView.findViewById(R.id.titleTextView1997)
        val mExclamacaoImageView1997: ImageView = itemView.findViewById(R.id.exclamacaoImageView1997)
        val mMasterNameTextView1997: TextView = itemView.findViewById(R.id.masterNameTextView1997)
    }

}