package gr.phisakel.newsfeed.presentation.ui.newssources

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import gr.phisakel.newsfeed.utils.SourceDiffCallback
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_source.view.*

class NewsSourcesAdapter(private val mContext: Context, private var mItems: MutableList<SourceUI>)
    : RecyclerView.Adapter<NewsSourcesAdapter.ViewHolder>() {

    private var mOnItemClickSubject: PublishSubject<SourceUI> = PublishSubject.create()

    fun getOnItemClickObservable(): Observable<SourceUI> = mOnItemClickSubject

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.tv_name
        val description: TextView = view.tv_description
        val category: TextView = view.tv_category
        val country: ImageView = view.im_country
        val enabled: CheckBox = view.cb_enable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_source, parent, false)
        return NewsSourcesAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mItems[position]

        RxView.clicks(holder.itemView).subscribe({
            holder.enabled.isChecked = !holder.enabled.isChecked
            mOnItemClickSubject.onNext(item)
        })

        holder.name.text = item.name
        holder.description.text = item.description
        holder.category.text = item.category
        holder.enabled.isChecked = item.isEnabled

        when (item.country.toLowerCase()) {
            "au" -> loadImage(holder.country, R.drawable.ic_australia_24dp)
            "de" -> loadImage(holder.country, R.drawable.ic_germany_24dp)
            "gb" -> loadImage(holder.country, R.drawable.ic_united_kingdom_24dp)
            "in" -> loadImage(holder.country, R.drawable.ic_india_24dp)
            "it" -> loadImage(holder.country, R.drawable.ic_italy_24dp)
            "us" -> loadImage(holder.country, R.drawable.ic_united_states_24dp)
            "gr" -> loadImage(holder.country, R.drawable.flag_of_greece_with_border)
        }
    }

    override fun getItemCount(): Int = mItems.size

    fun replace(items: List<SourceUI>) {
        val diffCallback = SourceDiffCallback(mItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.mItems.clear()
        this.mItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun loadImage(imageView: ImageView, @DrawableRes resId: Int) {
        Glide.with(mContext).load("").error(resId).into(imageView)
    }
}
