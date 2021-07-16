
package com.example.practiceproject

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    private lateinit var newsRecyclerView: RecyclerView

    private lateinit var refresh: SwipeRefreshLayout

    private val newsList = ArrayList<News>()
    private fun refresh() {
        thread {
            val request =
                Request.Builder()
                    .url("http://v.juhe.cn/toutiao/index?type=tiyu&key=" + MyApplication.KEY1)
                    .build()

            val response = OkHttpClient().newCall(request).execute()
            val json = response.body?.string()
            val newsResponse = Gson().fromJson(json, NewsResponse::class.java)
            if (newsResponse != null) {
                val data = newsResponse.result.data
                newsList.clear()
                newsList.addAll(data)
                activity?.runOnUiThread {
                    newsRecyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_fragment, container, false)
        newsRecyclerView = view.findViewById(R.id.recycleView)
        refresh = view.findViewById(R.id.news_swipeRefresh)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newsRecyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
        newsRecyclerView.adapter = NewsAdapter(newsList)
        refresh()

        refresh.setColorSchemeColors(Color.parseColor("#009688"))
        //对refresh的监听
        refresh.setOnRefreshListener {
            thread {
                Thread.sleep(500)
                activity?.runOnUiThread {
                    refresh()
                    refresh.isRefreshing = false
                }
            }
        }
    }

    //新闻列表适配器
    inner class NewsAdapter(private val newsList: List<News>) :
        RecyclerView.Adapter<NewsAdapter.MyviewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.news_item_one_image, parent, false)
            return MyviewHolder(view)
        }

        override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
            val news = newsList[position]
            holder.title.text = news.title
            holder.description.text = news.author_name
            Glide.with(MyApplication.context).load(news.thumbnail_pic_s).into(holder.image)

            //每一条新闻的点击事件
            holder.itemView.setOnClickListener {
//                Toast.makeText(MyApplication.context, "succeed！"+news.url, Toast.LENGTH_SHORT).show()
                val intent = Intent(MyApplication.context, NewsDetailActivity::class.java)
                //向目标activity传值
                intent.putExtra("url", news.url)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return newsList.size
        }

        inner class MyviewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.news_title)
            val description: TextView = view.findViewById(R.id.news_desc)
            val image: com.makeramen.roundedimageview.RoundedImageView =
                view.findViewById(R.id.news_image)
        }
    }
}

