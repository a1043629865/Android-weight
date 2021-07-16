package com.example.practiceproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    val fragmentList = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //获取登陆Activity传过来的值
        val userNumber = intent.getStringExtra("person").toString()
        //向碎片集合中放入需要用到的几个碎片
        fragmentList.add(HomeFragment())
        fragmentList.add(DataActivity(userNumber))
        fragmentList.add(WeightActivity(userNumber))
        fragmentList.add(JudgeActivity(userNumber))
        fragmentList.add(UserActivity(userNumber))


        //获取底部导航栏
        val contentViewPager = findViewById<ViewPager>(R.id.content_viewpaper)
        //设置 fragment页面的缓存数量 , 这里设置成缓存所有的页面！！！！！
        contentViewPager.offscreenPageLimit = fragmentList.size
        //创建导航栏的适配器
        contentViewPager.adapter = MyAdapter(supportFragmentManager)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bootom_nav)
        //导航栏点击事件
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> contentViewPager.currentItem = 0
                R.id.nav_data -> contentViewPager.currentItem = 1
                R.id.nav_weight -> contentViewPager.currentItem = 2
                R.id.nav_judge -> contentViewPager.currentItem = 3
                R.id.nav_user -> contentViewPager.currentItem = 4
            }
            false
        }

        contentViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // 将对应的底部导航栏菜单项设置为选中状态
                bottomNav.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    //Fragment适配器
    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
    }
}




