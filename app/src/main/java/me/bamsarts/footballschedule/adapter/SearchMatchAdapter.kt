package me.bamsarts.footballschedule.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Match
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SearchMatchAdapter(private val events: List<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<SearchMatchViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SearchMatchViewHolder {
        return SearchMatchViewHolder(SearchMatchUI().createView(AnkoContext.create(p0.context, p0)))
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(p0: SearchMatchViewHolder, p1: Int) {
        p0.bindItem(events[p1], listener)
    }

}

class SearchMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout{
                lparams(width = matchParent, height = wrapContent)
                padding = dip(10)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.match_date
                    textSize = 16f
                }.lparams{
                    margin = dip(4)
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(4)
                    orientation = LinearLayout.HORIZONTAL

                    textView {
                        id = R.id.home_name
                        textSize = 16f
                    }.lparams{
                        margin = dip(2)
                    }

                    textView {
                        id = R.id.home_score
                        textSize = 16f
                    }.lparams{
                        margin = dip(2)
                    }

                    textView {
                        id = R.id.away_score
                        textSize = 16f
                    }.lparams{
                        margin = dip(2)
                    }

                    textView {
                        id = R.id.away_name
                        textSize = 16f
                    }.lparams{
                        margin = dip(2)
                    }
                }
            }
        }
    }
}

class SearchMatchViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val homeName: TextView = view.find(R.id.home_name)
    private val homeScore: TextView = view.find(R.id.home_score)
    private val awayName: TextView = view.find(R.id.away_name)
    private val awayScore: TextView = view.find(R.id.away_score)
    private val matchDate: TextView = view.find(R.id.match_date)

    fun bindItem(matches: Match, listener: (Match) -> Unit){
        homeName.text = matches.homeTeam
        homeScore.text = matches.homeScore
        awayName.text = matches.awayTeam
        awayScore.text = matches.awayScore
        matchDate.text = matches.eventDate

        itemView.onClick { listener(matches) }
    }
}