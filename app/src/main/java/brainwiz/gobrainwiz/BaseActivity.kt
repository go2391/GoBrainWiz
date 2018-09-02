package brainwiz.gobrainwiz

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import brainwiz.gobrainwiz.utils.KeyBoardUtils


/**
 */
open class BaseActivity : AppCompatActivity() {
    private var dialog: ProgressDialog? = null


    /**
     * @param context
     * *
     * @param activityClass  - target fragment
     * *
     * @param bundle
     * *
     * @param finish         - is this current activity can be finish
     * *
     * @param animationStyle - animation style : left to right...
     */
    fun launchActivity(context: Activity, activityClass: Class<*>, bundle: Bundle?, finish: Boolean, animationStyle: Int) {
        KeyBoardUtils.hideKeyboard(context)
        val intent = Intent(this as Context, activityClass)

        intent.putExtras(bundle ?: Bundle())
        startActivity(intent)
        if (finish) {
            context.finish()
        }
    }


    fun fragmentTransaction(newFragment: Fragment, container: Int, addToStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, newFragment)
        if (addToStack) {
            transaction.addToBackStack(newFragment.javaClass.simpleName)
        }

        transaction.commit()
    }

    fun fragmentTransaction(newFragment: Fragment, container: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(container, newFragment)
        transaction.commit()
    }


    fun fragmentTransaction(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame, newFragment)
        transaction.addToBackStack(newFragment.javaClass.simpleName);
        transaction.commit()
    }

    /**
     * @param editText
     * *
     * @return check is fiels is empty  if empty request focus to that field
     */
    internal fun isEmptyField(editText: EditText): Boolean {
        val empty = TextUtils.isEmpty(getFieldText(editText))
        if (empty)
            editText.requestFocus()
        return empty
    }

    override fun onResume() {
        super.onResume()
    }

    internal fun getFieldText(editText: EditText): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    /**
     * show progress dialog for api calls
     */
    fun showProgress() {
        dialog = ProgressDialog(this as Context)
        dialog!!.setMessage("Please wait...")
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)
        dialog!!.show()

    }

    /**
     * dismiss progress dialog after api calls
     */
    fun dismissProgress() {
        if (dialog != null) {
            dialog!!.cancel()
            dialog!!.dismiss()
        }
    }


    /**
     * animation styles
     */
    interface IAnimations {
        companion object {
            val LeftToRight = 0
            val RightToLeft = 1
            val TopToBottom = 2
            val BottomToTop = 3
        }
    }

    /**
     * @param view use this method to dissmiss keyboard on taping outside and call this method where you have the edit fields on onResume method.
     */
    protected fun setupUI(view: View) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {

            view.setOnTouchListener { v, event ->
                KeyBoardUtils.hideKeyboard(this@BaseActivity as Activity)
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {

            for (i in 0..view.childCount - 1) {

                val innerView = view.getChildAt(i)

                setupUI(innerView)
            }
        }
    }


}
