package me.bamsarts.footballschedule.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import me.bamsarts.footballschedule.R
import me.bamsarts.footballschedule.model.Player
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.player_detail.*
//import kotlinx.android.synthetic.main.spinner_right_aligned.*

//import kotlinx.android.synthetic.main.match_detail.*



class PlayerDetailActivity : AppCompatActivity(){

    private lateinit var namePlayer: String
    private lateinit var imagePlayer: String
    private lateinit var descPlayer: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail)
        val intent = intent
        namePlayer = intent.getStringExtra("playerName")
        imagePlayer = intent.getStringExtra("playerImage")
        descPlayer = intent.getStringExtra("playerDesc")


//        supportActionBar?.title = namePlayer

//        linearLayout {
//            lparams(width = matchParent, height = wrapContent)
//            orientation = LinearLayout.VERTICAL
//            backgroundColor = Color.WHITE
//
//            scrollView {
//                isVerticalScrollBarEnabled = false
//                relativeLayout {
//                    lparams(width = matchParent, height = wrapContent)
//
//                    linearLayout{
//                        lparams(width = matchParent, height = wrapContent)
//                        padding = dip(10)
//                        orientation = LinearLayout.VERTICAL
//                        gravity = Gravity.CENTER_HORIZONTAL
//
//                        playerImage =  imageView {}.lparams(height = dip(125))
//
//                        playerName = textView{
//                            this.gravity = Gravity.CENTER
//                            textSize = 20f
//                            textColor = ContextCompat.getColor(context, R.color.colorAccent)
//                        }.lparams{
//                            topMargin = dip(5)
//                        }
//
//                        playerDesc = textView().lparams{
//                            topMargin = dip(20)
//                        }
//                    }
//                }
//            }
//        }

        showPlayerDetail()
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }


    fun showPlayerDetail() {
//        Picasso.get().load(imagePlayer).into(playerImage)
//        val dialogView = layoutInflater.inflate(R.layout.player_detail, null) ?: return

        Glide.with(this).load(imagePlayer).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(playerImage)
//        dialogView.findViewById<TextView>(R.id.namePlayer)

//        val namePlayer: TextView = view.find(R.id.namePlayer)
//        val descPlayer: TextView = view.find(R.id.descPlayer)


        playerName.text = namePlayer
        playerDesc.text = descPlayer

    }
}
