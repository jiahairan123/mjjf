package com.mingjie.jf.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingjie.jf.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称：CunGuanKX
 * <p>包   名： com.minghao.cgkx.activity
 * <p>版   权： 深圳市铭淏网络科技有限公司 2016
 * <p>描   述： 存管指引 - 对应的图片流程界面
 * <p>创 建 人： shenfei
 * <p>创建时间： 2016-12-09 16:09
 * <p>当前版本： V1.0.0
 * <p>修订历史： (版本、修改时间、修改人、修改内容)
 */
public class AdvFlowActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener
{
    private ListView viewById;
    private int type;
    private MyAdapter adapter;

    private List<Integer> pic = new ArrayList();
    private TextView title;
    private ImageView back;

    @Override
    protected void initView()
    {
        setContentView(R.layout.activity_adv_flow);
        viewById = (ListView) findViewById(R.id.lv_adv_flow);
        title = (TextView) findViewById(R.id.tv_content_public);
        back = (ImageView) findViewById(R.id.iv_Left_public);
        viewById.setOnItemClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    protected void initData()
    {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1)
        {
            pic.clear();
            pic.add(R.mipmap.adv_2);
            pic.add(R.mipmap.adv_3);
            pic.add(R.mipmap.adv_4);
            pic.add(R.mipmap.adv_5);
            title.setText("开户关联个人E账户");
        } else if (type == 2)
        {
            pic.clear();
            pic.add(R.mipmap.adv_6);
            pic.add(R.mipmap.adv_7);
            pic.add(R.mipmap.adv_8);
            title.setText("充值");
        } else if (type == 3)
        {
            pic.clear();
            pic.add(R.mipmap.adv_9);
            pic.add(R.mipmap.adv_10);
            pic.add(R.mipmap.adv_21);
            pic.add(R.mipmap.adv_11);
            pic.add(R.mipmap.adv_12);
            pic.add(R.mipmap.adv_13);
            pic.add(R.mipmap.adv_14);
            pic.add(R.mipmap.adv_15);
            pic.add(R.mipmap.adv_16);
            title.setText("提现");
        } else if (type == 4)
        {
            pic.clear();
            pic.add(R.mipmap.adv_17);
            pic.add(R.mipmap.adv_18);
            pic.add(R.mipmap.adv_19);
            pic.add(R.mipmap.adv_20);
            title.setText("投资");
        }
        adapter = new MyAdapter();
        viewById.setAdapter(adapter);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_Left_public:
                finish();
                break;
            default:
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if (type == 3 && position == 2)
        {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("http://www.ghbibank.com.cn:8813/ebank/download/");
            intent.setData(content_url);
            startActivity(intent);
        }
    }

    public class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return pic.size();
        }

        @Override
        public Object getItem(int position)
        {
            return pic.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            if (convertView == null)
            {
                convertView = View.inflate(AdvFlowActivity.this, R.layout.item_pic, null);
                holder = new ViewHolder();

                holder.imageView = (ImageView) convertView.findViewById(R.id.iv_adv_pic);
                convertView.setTag(holder);
            } else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            // 加载图片
            Glide.with(AdvFlowActivity.this).load(pic.get(position)).placeholder(R.mipmap.default_userinfo).crossFade
                    ().into(holder.imageView);
//            holder.imageView.setImageResource(pic.get(position));    OutOfMemoryError内存溢出
            return convertView;
        }
    }

    static class ViewHolder
    {
        ImageView imageView;   //
    }
}
