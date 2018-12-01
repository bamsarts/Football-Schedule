package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.R.id.*
import me.bamsarts.footballschedule.db.FavouriteTeam
import org.jetbrains.anko.*

class FavouriteTeamAdapter(private val favorite: List<FavouriteTeam>, private val listener: (FavouriteTeam) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_club, parent, false)
        return FavoriteViewHolder(view)
//        return FavoriteViewHolder(FavouriteUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

//class FavouriteUI : AnkoComponent<ViewGroup> {
//    override fun createView(ui: AnkoContext<ViewGroup>): View {
//        return with(ui) {
//            linearLayout{
//                lparams(width = matchParent, height = wrapContent)
//                padding = dip(16)
//                orientation = LinearLayout.HORIZONTAL
//
//                imageView {
//                    id = team_badge
//                }.lparams{
//                    height = dip(50)
//                    width = dip(50)
//                }
//
//                textView {
//                    id = team_name
//                    textSize = 16f
//                }.lparams{
//                    margin = dip(15)
//                }
//
//            }
//        }
//    }
//
//}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val teamBadge: ImageView = view.find(clubBadge)
    private val teamName: TextView = view.find(clubName)

    fun bindItem(favorite: FavouriteTeam, listener: (FavouriteTeam) -> Unit) {
//        Picasso.get().load(favorite.teamBadge).into(teamBadge)
        Glide.with(itemView.context).load(favorite.teamBadge).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(teamBadge)
        teamName.text = favorite.teamName
        itemView.setOnClickListener { listener(favorite) }
    }
}