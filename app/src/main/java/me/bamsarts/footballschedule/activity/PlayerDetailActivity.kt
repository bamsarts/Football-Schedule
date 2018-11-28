package me.bamsarts.footballschedule.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.R.attr.colorAccent
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import me.bamsarts.footballschedule.model.Player
import org.jetbrains.anko.*

class PlayerDetailActivity : AppCompatActivity(){

    private lateinit var player: Player
    private lateinit var namePlayer: String
    private lateinit var imagePlayer: String
    private lateinit var descPlayer: String

    private lateinit var playerImage: ImageView
    private lateinit var playerName: TextView
    private lateinit var playerDesc: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        namePlayer = intent.getStringExtra("playerName")
        imagePlayer = intent.getStringExtra("playerImage")
        descPlayer = intent.getStringExtra("playerDesc")

        supportActionBar?.title = namePlayer

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            scrollView {
                isVerticalScrollBarEnabled = false
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    linearLayout{
                        lparams(width = matchParent, height = wrapContent)
                        padding = dip(10)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER_HORIZONTAL

                        playerImage =  imageView {}.lparams(height = dip(125))

                        playerName = textView{
                            this.gravity = Gravity.CENTER
                            textSize = 20f
                            textColor = ContextCompat.getColor(context, colorAccent)
                        }.lparams{
                            topMargin = dip(5)
                        }

                        playerDesc = textView().lparams{
                            topMargin = dip(20)
                        }
                    }
                }
            }
        }

        showPlayerDetail()
    }


    fun showPlayerDetail() {
//        Picasso.get().load(imagePlayer).into(playerImage)
        Glide.with(this).load(imagePlayer).into(playerImage)
        playerName.text = namePlayer
        playerDesc.text = descPlayer
    }
}
