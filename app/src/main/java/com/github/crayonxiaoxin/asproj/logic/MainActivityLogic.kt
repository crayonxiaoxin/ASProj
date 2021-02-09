package com.github.crayonxiaoxin.asproj.logic

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.github.crayonxiaoxin.asproj.R
import com.github.crayonxiaoxin.asproj.fragment.*
import com.github.crayonxiaoxin.common.tab.HiFragmentTabView
import com.github.crayonxiaoxin.common.tab.HiTabViewAdapter
import com.github.crayonxiaoxin.hi.library.util.HiDisplayUtil
import com.github.crayonxiaoxin.hi.ui.tab.bottom.HiTabBottomInfo
import com.github.crayonxiaoxin.hi.ui.tab.bottom.HiTabBottomLayout

class MainActivityLogic(
    private var activityProvider: ActivityProvider,
    savedInstanceState: Bundle?
) {
    lateinit var hiTabBottomLayout: HiTabBottomLayout
    lateinit var hiFragmentTabView: HiFragmentTabView
    var infoList: MutableList<HiTabBottomInfo<*>> = ArrayList()
    private var currentItemIndex = 0
    private val SAVE_INSTANCE_STATE_KEY = "currentItemIndex"

    init {
        // 修复 开发者模式 - 不保留活动 导致的重叠问题
        savedInstanceState?.let {
            currentItemIndex = it.getInt(SAVE_INSTANCE_STATE_KEY)
        }
        initBottomView()
    }

    interface ActivityProvider {
        fun <T : View?> findViewById(@IdRes id: Int): T
        fun getResources(): Resources
        fun getSupportFragmentManager(): FragmentManager
        fun getString(@StringRes resId: Int): String
    }

    private fun initBottomView() {
        hiTabBottomLayout = activityProvider.findViewById(R.id.hiTabBottomLayout)
        val homeInfo = HiTabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        homeInfo.fragment = HiHomeFragment::class.java

        val infoFavorite = HiTabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )
        infoFavorite.fragment = HiFavoriteFragment::class.java

        val bitmap =
            BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.fire, null)
        val infoCategory = HiTabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )
        infoCategory.fragment = HiCategoryFragment::class.java

        val infoChat = HiTabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        infoChat.fragment = HiRecommendFragment::class.java

        val infoProfile = HiTabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )
        infoProfile.fragment = HiProfileFragment::class.java

        infoList.add(homeInfo)
        infoList.add(infoFavorite)
        infoList.add(infoCategory)
        infoList.add(infoChat)
        infoList.add(infoProfile)

        initFragmentTabView()
        hiTabBottomLayout.setTabAlpha(0.6f)
        hiTabBottomLayout.inflateInfo(infoList)
        hiTabBottomLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            hiFragmentTabView.currentItem = index
            currentItemIndex = index
        }
        hiTabBottomLayout.defaultSelected(infoList[currentItemIndex])
        hiTabBottomLayout.findTab(infoList[2]).resetHeight(HiDisplayUtil.dp2px(66f))
    }

    private fun initFragmentTabView() {
        hiFragmentTabView = activityProvider.findViewById(R.id.hiFragmentTabView)
        hiFragmentTabView.adapter =
            HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList)
        hiFragmentTabView.currentItem = currentItemIndex
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVE_INSTANCE_STATE_KEY, currentItemIndex)
    }
}