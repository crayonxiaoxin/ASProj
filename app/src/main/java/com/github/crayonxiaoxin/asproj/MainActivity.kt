package com.github.crayonxiaoxin.asproj

import android.os.Bundle
import com.github.crayonxiaoxin.asproj.logic.MainActivityLogic
import com.github.crayonxiaoxin.common.ui.component.HiBaseActivity

class MainActivity : HiBaseActivity(), MainActivityLogic.ActivityProvider {

    lateinit var mainActivityLogic: MainActivityLogic


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // MainActivityLogic 接口方法已被Activity本身实现
        mainActivityLogic = MainActivityLogic(this, savedInstanceState)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mainActivityLogic.onSaveInstanceState(outState)
    }

}