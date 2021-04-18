package aut.bme.bookmanager.ui.favorite

import android.content.Context
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import aut.bme.bookmanager.R
import aut.bme.bookmanager.interactor.event.TitleChangeEvent
import com.google.android.material.textfield.TextInputEditText
import org.greenrobot.eventbus.EventBus

class PopUpClass {
    @RequiresApi(Build.VERSION_CODES.M)
    fun showPopupWindow(view: View, position: Int, currentTitle: String) {
        val inflater = view
            .context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val popupView = inflater.inflate(R.layout.card_popup, null)
        popupView.findViewById<TextInputEditText>(R.id.title_iet).setText(currentTitle)

        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.isOutsideTouchable = false
        popupWindow.setOnDismissListener {
            view.foreground.alpha = 0
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val buttonEdit = popupView.findViewById<Button>(R.id.ok_btn)
        buttonEdit.setOnClickListener {
            val titleChangeEvent =
                TitleChangeEvent(
                    popupView.findViewById<TextInputEditText>(R.id.title_iet).text.toString(),
                    position
                )
            EventBus.getDefault().post(titleChangeEvent)

            popupWindow.dismiss()
            Toast.makeText(view.context, "Title changed", Toast.LENGTH_SHORT)
                .show()
        }
    }
}