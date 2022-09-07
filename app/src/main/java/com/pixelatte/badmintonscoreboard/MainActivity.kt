package com.pixelatte.badmintonscoreboard

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.pixelatte.badmintonscoreboard.databinding.ActivityMain3Binding
// import com.pixelatte.badmintonscoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val DEBUG_TAG = "MainActivity"

    // variable declarations
    var player1: String = "Player 1"
    var player2: String = "Player 2"
    var score1:Int = 0
    var score2:Int = 0
    var set1:Int = 0
    var set2:Int = 0
    var game:Int = 0

    var deuceScore:Boolean = false
    val gamePoint = 21
    val maxScore = 29


    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main3)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        updateScore()
    }

    fun clickedButtonSwitch(view: View) {
//        binding.txtScore1.setText("$score2")
//        binding.txtScore2.setText("$score1")

        var score12 = score1
        var score21 = score2
        var set12 = set1
        var set21 = set2

        score1 = score21
        score2 = score12
        set1 = set21
        set2 = set12

        updateScore()
    }

    fun clickedButtonReset(view: View) {
        resetScore()
    }

    fun clickedButtonMinus1(view: View) {
        if(score1 > 0) score1 -= 1

        updateScore()
    }

    fun clickedButtonMinus2(view: View) {
        if(score2 > 0) score2 -= 1

        updateScore()
    }

    fun clickedScore1(view: View) {
        score1 += 1

        // deuceScore = score1 == score2 && score1 + score2 >= 40

        if (score1 == 20) {
            binding.txtScore1.setTextColor(Color.RED)
        }

        if ((score1 == gamePoint && score2 < 20) || (score1 > gamePoint && score1 == score2 + 2)) {
            showWinDialog(player1)
            set1 += 1
            updateScore()
            return
        }
        updateScore()
    }

    fun clickedScore2(view: View) {
        score2 += 1

        if (score2 == 20) {
            binding.txtScore2.setTextColor(Color.RED)
        }

        if ((score2 == gamePoint && score1 < 20) || (score2 > gamePoint && score2 == score1 + 2)) {
            showWinDialog(player2)
            set2 += 1
            updateScore()
            return
        }
        updateScore()
    }

    private fun showWinDialog(playerName:String) {
        game += 1
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Game Finished")
        builder.setMessage("$playerName win the match")
        builder.setPositiveButton("Ok") { dialog, which ->
            dialog.dismiss()
        }

        builder.setOnDismissListener {
            setImmersiveMode()
        //    resetScore()
        }
        builder.show()

    }

    private fun resetScore() {
        if(score1 == 0 && score2 == 0) {

            if(set1 == 0 && set2 == 0)
                return

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Reset Game")
            builder.setMessage("Reset the whole game?")
            builder.setPositiveButton("yes") {dialog, which ->
                set1 = 0
                set2 = 0
                game = 0
                dialog.dismiss()
                updateScore()
            }
            builder.setNegativeButton("cancel") {dialog, which ->
                dialog.dismiss()
                setImmersiveMode()
            }
            builder.setOnDismissListener {
                setImmersiveMode()
            }
            builder.show()
        }
        score1 = 0
        score2 = 0
        updateScore()
    }

    private fun updateScore() {

        if((score1 == 20 && score2 < score1) || (score1 >= 20 && score1 == score2 + 1))
            binding.txtScore1.setTextColor(Color.RED)
        else if ((score2 == 20 && score1 < score2) || (score2 >= 20 && score2 == score1 + 1))
            binding.txtScore2.setTextColor(Color.RED)
        else {
            binding.txtScore1.setTextColor(Color.WHITE)
            binding.txtScore2.setTextColor(Color.WHITE)
        }

        binding.txtScore1.setText("$score1")
        binding.txtScore2.setText("$score2")
        binding.txtSet1.setText("$set1")
        binding.txtSet2.setText("$set2")

    }

    fun clickedPlayer1(view: View) {
        TODO("open player selector with add new player option")

    }
    fun clickedPlayer2(view: View) {
        TODO("open player selector with add new player option")
    }
    fun clickedButtonSetting(view: View) {
        val i = Intent(this, SettingActivity::class.java)
        startActivity(i)
    }

    private fun setImmersiveMode() {

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.KEEP_SCREEN_ON
        }
    }

    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.navigationBars())
    }

    override fun onStart() {
        super.onStart()
        setImmersiveMode()
    }

    override fun onResume() {
        super.onResume()
        setImmersiveMode()
    }

}