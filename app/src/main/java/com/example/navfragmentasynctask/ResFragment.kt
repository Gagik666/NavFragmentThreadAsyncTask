package com.example.navfragmentasynctask

import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class ResFragment : Fragment() {
    private val arg: ResFragmentArgs by navArgs()

    var progressBar: ProgressBar? = null

    var tvInfo: TextView? = null
    var tvCorrent: TextView? = null
    var tvP: TextView? = null
    var tvRNum: TextView? = null

    var btnAdd: Button? = null
    var btnRed: Button? = null
    var btnReset: Button? = null

    var imgRevresh: ImageView? = null
    var img1: ImageView? = null
    var img2: ImageView? = null
    var img3: ImageView? = null

    var timerAdd: CountDownTimer? = null
    var timerRed: CountDownTimer? = null

    var maxNum = 0
    var i = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_res, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd = view.findViewById(R.id.btnAdd)
        btnRed = view.findViewById(R.id.btnRed)
        btnReset = view.findViewById(R.id.btnReset)
        progressBar = view.findViewById(R.id.progressBar)

        maxNum = arg.maxNum
        var num = arg.num

        tvCorrent?.text =num.toString()

        img1 = view.findViewById(R.id.img1)
        img2 = view.findViewById(R.id.img2)
        img3 = view.findViewById(R.id.img3)
        imgRevresh = view.findViewById(R.id.imgReversh)

        tvRNum = view.findViewById(R.id.tvRNum)
        tvCorrent = view.findViewById(R.id.tvCurrent)
        tvInfo = view.findViewById(R.id.tvInfo)
        tvP = view.findViewById(R.id.tvP)
        tvCorrent?.text = num.toString()
        tvRNum?.text = num.toString()

        btnAdd?.setOnClickListener {
            tvInfo?.text = "Adds apples . . ."
            num++
            MyAsyncTask().execute(num)
            timerAdd?.cancel()
            timerAdd = object : CountDownTimer(5000, 300) {
                override fun onTick(p0: Long) {
                    i++
                    if (i== 8 ) i = 0
                    if (i == 2) img3?.visibility = View.VISIBLE else img3?.visibility = View.INVISIBLE
                    if (i == 4) img2?.visibility = View.VISIBLE else img2?.visibility = View.INVISIBLE
                    if (i == 6) img1?.visibility = View.VISIBLE else img1?.visibility = View.INVISIBLE
                }
                override fun onFinish() {

                }
            }.start()
        }

        btnRed?.setOnClickListener {
            tvInfo?.text = "Reduces apples . . ."
            num--
            MyAsyncTask().execute(num)
            timerRed?.cancel()
            timerRed = object : CountDownTimer(5000, 300) {
                override fun onTick(p0: Long) {
                    i++
                    if (i == 8 ) i = 0
                    if (i == 2) img1?.visibility = View.VISIBLE else img1?.visibility = View.INVISIBLE
                    if (i == 4) img2?.visibility = View.VISIBLE else img2?.visibility = View.INVISIBLE
                    if (i == 6) img3?.visibility = View.VISIBLE else img3?.visibility = View.INVISIBLE
                }
                override fun onFinish() {

                }
            }.start()
        }

        btnReset?.setOnClickListener {
            tvInfo?.text = "Refreshes the box . . ."
            num =  arg.num
            MyAsyncTask().execute(num)
        }
    }

    inner class MyAsyncTask: AsyncTask<Int, Int, Int>() {
        var progresBarValue = 0

        override fun onPreExecute() {
            super.onPreExecute()
            btnAdd?.isClickable = false
            btnRed?.isClickable = false
            imgRevresh?.visibility = View.VISIBLE
            tvCorrent?.visibility = View.INVISIBLE
        }

        override fun onPostExecute(result: Int?) {
            super.onPostExecute(result)
            if (result != null) {
                if (result == 0 || result >= maxNum) {
                    btnReset?.visibility = View.VISIBLE
                    btnAdd?.visibility = View.GONE
                    btnRed?.visibility = View.GONE
                } else {
                    btnReset?.visibility = View.GONE
                    btnAdd?.visibility = View.VISIBLE
                    btnRed?.visibility = View.VISIBLE
                }
            }
            btnAdd?.isClickable = true
            btnRed?.isClickable = true
            imgRevresh?.visibility = View.INVISIBLE
            tvCorrent?.visibility = View.VISIBLE
            tvCorrent?.text =  result.toString()
            img1?.visibility = View.INVISIBLE
            img2?.visibility = View.INVISIBLE
            img3?.visibility = View.INVISIBLE
            tvInfo?.visibility = View.INVISIBLE
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            progressBar?.progress = values[0]!!
            tvP?.text = "${values[0]} %"
            tvInfo?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Int?): Int? {
            while (progresBarValue < 100) {
                progresBarValue++
                publishProgress(progresBarValue)
                SystemClock.sleep(50)

            }
            return p0[0]
        }
    }

}