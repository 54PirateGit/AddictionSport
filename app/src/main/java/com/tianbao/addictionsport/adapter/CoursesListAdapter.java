package com.tianbao.addictionsport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianbao.addictionsport.R;
import com.tianbao.addictionsport.mode.CourseSchedule.DataBean.Course4DayBean.CourseBean;
import com.tianbao.addictionsport.utils.L;
import com.tianbao.addictionsport.utils.StringUtil;
import com.tianbao.addictionsport.widget.CircleTransform;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 课程列表适配器
 * Created by edianzu on 2017/9/11.
 */
public class CoursesListAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseBean> mList;// 数据
    private CircleTransform circleTransform;

    private View.OnClickListener onClickListener;// Btn 点击事件处理

    public CoursesListAdapter(Context context, List<CourseBean> list, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.mList = list;
        circleTransform = new CircleTransform();
        this.onClickListener = onClickListener;
    }

    // 更新数据
    public void setList(List<CourseBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_courses, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        try {
            CourseBean item = mList.get(position);

            // 课程封面图片
            String coverUrl = item.getCoach().getAvatar();
            if (coverUrl != null && !coverUrl.equals("")) {
                Picasso.with(mContext).load(coverUrl).transform(circleTransform).into(holder.imageCover);
            }

            // btn 显示文字
            CourseBean.ButtonBean buttonBean = item.getButton();
            String btnTitle = buttonBean.getTitle();
            if (btnTitle == null || btnTitle.equals("")) {
                btnTitle = "预约";
            }
            holder.btnPurchase.setText(btnTitle);

            // 按钮是否可用
            boolean disable = buttonBean.isDisable();
            if (disable) {// 按钮可用
                holder.btnPurchase.setEnabled(true);
                holder.btnPurchase.setClickable(true);
                holder.btnPurchase.setBackground(mContext.getDrawable(R.drawable.btn_blue_white_background));
                holder.btnPurchase.setTextColor(mContext.getColor(R.color.blue));

                holder.btnPurchase.setTag(position);
                holder.btnPurchase.setOnClickListener(this.onClickListener);
            } else {// 按钮不可用
                holder.btnPurchase.setEnabled(false);
                holder.btnPurchase.setClickable(false);
                holder.btnPurchase.setBackground(mContext.getDrawable(R.drawable.btn_gray_white_background));
                holder.btnPurchase.setTextColor(mContext.getColor(R.color.gray));

                holder.btnPurchase.setTag(position);
                holder.btnPurchase.setOnClickListener(null);
            }

            // 课程
            String coursesTitle = item.getTitle();
            if (coursesTitle == null || coursesTitle.equals("")) {
                coursesTitle = "Yin-Yang瑜伽";
            }
            holder.textCourses.setText(coursesTitle);

            // 课程关键词
            List<String> tags = item.getTags();
            String keyWord = StringUtil.listToString(tags);
            if (keyWord == null || keyWord.equals("")) {
                keyWord = "冲瘾健身";
            }
            holder.textCoursesKey.setText(keyWord);

            // 课程时间
            String coursesTime = item.getTime() + " " + item.getPrice();
            if (coursesTime.equals(" ")) {
                coursesTime = "20:00-20:30 ￥45";
            }
            holder.textCoursesTime.setText(coursesTime);

            // 课程瘾支付
            String yinPay = item.getYenPrice();
            if (yinPay == null || yinPay.equals("")) {
                yinPay = "（瘾卡 ￥40.25）";
            } else {
                yinPay = "（瘾卡" + yinPay + "）";
            }
            holder.textCoursesPrice.setText(yinPay);
        } catch (Exception e) {
            e.printStackTrace();
            L.w("run exception");
        }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.image_cover)
        ImageView imageCover;// 封面图片
        @BindView(R.id.btn_purchase)
        TextView btnPurchase;// 预约
        @BindView(R.id.text_courses)
        TextView textCourses;// 课程
        @BindView(R.id.text_courses_key)
        TextView textCoursesKey;// 课程关键词
        @BindView(R.id.text_courses_time)
        TextView textCoursesTime;// 课程时间
        @BindView(R.id.text_courses_price)
        TextView textCoursesPrice;// 瘾卡支付课程价格

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    static class ViewHolder {
//        ImageView imageCover;// image_cover  封面图片
//        Button btnPurchase;// btn_purchase  预约
//        TextView textCourses;// text_courses  课程
//        TextView textCoursesKey;// text_courses_key  课程关键词
//        TextView textCoursesTime;// text_courses_time  课程时间
//        TextView textCoursesPrice;// text_courses_price  瘾卡支付课程价格
//    }
}
