package com.xxh.learn.ui.component

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import com.xxh.common.BaseActivity
import com.xxh.learn.ui.R
import com.xxh.learn.ui.databinding.ActivityStringTestBinding

class StringTestActivity : BaseActivity<ActivityStringTestBinding>() {

    override fun bindView(inflater: LayoutInflater): ActivityStringTestBinding {
        return ActivityStringTestBinding.inflate(inflater)
    }

    override fun initView() {
        super.initView()
        val strClick = getString(R.string.privacy_click)
        val str = String.format(getString(R.string.privacy_content), strClick)
        val start = str.indexOf(strClick)
        val end = str.indexOf(strClick) + strClick.length
        val sBuilder = SpannableStringBuilder(str)
        sBuilder.setSpan(UnderlineSpan(), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        sBuilder.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }
        }, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        sBuilder.setSpan(
            ForegroundColorSpan(getColor(R.color.red_700)),
            start,
            end,
            Spanned.SPAN_INCLUSIVE_INCLUSIVE
        )
        mBinding.str1Tv.text=sBuilder
        mBinding.str1Tv.highlightColor=getColor(android.R.color.transparent)
        mBinding.str1Tv.movementMethod=LinkMovementMethod.getInstance()
    }
}