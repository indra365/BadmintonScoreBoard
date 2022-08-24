package com.pixelatte.badmintonscoreboard

import android.app.AlertDialog
import android.content.Context
import android.view.WindowManager

/**
 * Created by Pixelatte Dev on 11/08/22
 */
class WinDialog(context: Context?, player:String) : AlertDialog(context) {

    var playerName = player

    override fun show() {
        // Set the dialog to not focusable.
        getWindow()?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        // Show the dialog with NavBar hidden.
        super.show();
        // Set the dialog to focusable again.
        getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

    }
}