package com.github.crayonxiaoxin.asproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.crayonxiaoxin.common.ui.component.HiBaseActivity

class MainActivity : HiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}