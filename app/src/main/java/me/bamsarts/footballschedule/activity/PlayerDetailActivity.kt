package me.bamsarts.footballschedule.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import me.bamsarts.footballschedule.R
import kotlinx.android.synthetic.main.player_detail.*
import me.bamsarts.footballschedule.utils.parse
import me.bamsarts.footballschedule.utils.parseComma

class PlayerDetailActivity : AppCompatActivity(){

    private lateinit var namePlayer: String
    private lateinit var imagePlayer: String
    private lateinit var descPlayer: String
    private lateinit var positionPlayer: String
    private lateinit var heightPlayer: String
    private lateinit var weightPlayer: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail)
        val intent = intent
        namePlayer = intent.getStringExtra("playerName")
        imagePlayer = intent.getStringExtra("playerImage")
        descPlayer = intent.getStringExtra("playerDesc")
        heightPlayer = intent.getStringExtra("playerHeight")
        weightPlayer = intent.getStringExtra("playerWeight")
        positionPlayer = intent.getStringExtra("playerPosition")


        supportActionBar?.title = namePlayer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showPlayerDetail()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun showPlayerDetail() {

        Glide.with(this).load(imagePlayer).apply(RequestOptions().placeholder(R.drawable.placeholder)).into(playerImage)
        playerDesc.text = descPlayer
        playerPosition.text = positionPlayer
        playerWeight.text = weightPlayer.parseComma()
        playerHeight.text = heightPlayer

    }
}
