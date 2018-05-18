package com.liao310.www.activity.mian4.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liao310.www.MainActivity;
import com.liao310.www.R;
import com.liao310.www.domain.zhuanjia.ZhuanJia;
import com.liao310.www.net.https.xUtilsImageUtils;
import com.liao310.www.widget.ShapedImageView;

import java.util.List;
import java.util.Map;

import static com.liao310.www.R.id.head;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    public String[] groups;
    public Map<String, List<ZhuanJia>> children;

    public ExpandableAdapter(Context context, String[] groups, Map<String, List<ZhuanJia>> children) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.groups = groups;
        this.children = children;
    }

    //获取与给定的组相关的数据，得到数组groups中元素的数据
    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    //获取与孩子在给定的组相关的数据,得到数组children中元素的数据
    public Object getChild(int groupPosition, int childPosition) {
        if (children.get(groups[groupPosition]) != null)
            return children.get(groups[groupPosition]).get(childPosition);
        return null;
    }

    //获取的群体数量，得到groups里元素的个数
    public int getGroupCount() {
        return groups.length;
    }

    //取得指定组中的children个数，就是groups中每一个条目中的个数
    public int getChildrenCount(int groupPosition) {
        if (children.get(groups[groupPosition]) != null)
            return children.get(groups[groupPosition]).size();
        return 0;
    }

    //获取组在给定的位置编号，即groups中元素的ID
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取在给定的组的children的ID，也就是children中元素的ID
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //获取一个视图显示给定组，存放groups
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        View view = mInflater.inflate(R.layout.main_attention_parent, null);
        TextView textView = view.findViewById(R.id.Main_Attention_ParentTxt);
        ImageView imageView = view.findViewById(R.id.Main_Attention_ParentImg);
        textView.setText(getGroup(groupPosition).toString());
        if (isExpanded) {
            imageView.setImageResource(R.drawable.down_arrow);
        } else {
            imageView.setImageResource(R.drawable.up_arrow);
        }
        return view;
    }

    //获取一个视图显示在给定的组 的儿童的数据，就是存放children
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        View view;
        if (groupPosition == 0) {
            view = mInflater.inflate(R.layout.main_attention_child, null);
            TextView textView = view.findViewById(R.id.ZJ_JX_NickName);
            TextView ying = view.findViewById(R.id.ZJ_JX_Ying);
            TextView yingBan = view.findViewById(R.id.ZJ_JX_YingBan);
            TextView zou = view.findViewById(R.id.ZJ_JX_Zou);
            TextView shu = view.findViewById(R.id.ZJ_JX_Shu);
            TextView number = view.findViewById(R.id.ZJ_JX_MsgHintText);
            TextView shuBan = view.findViewById(R.id.ZJ_JX_ShuBan);
            ShapedImageView head = view.findViewById(R.id.ZJ_JX_Head);
            ZhuanJia data = (ZhuanJia) getChild(groupPosition, childPosition);
            if (data != null) {
                textView.setText(data.getNickname());
                xUtilsImageUtils.display(head, R.drawable.defaultpic, data.getAvatar());
                if(data.getWeikaishi()!=0){
                    number.setVisibility(View.VISIBLE);
                    number.setText(data.getWeikaishi()+"");
                }else {
                    number.setVisibility(View.GONE);
                }
                if (data.getDays7() != null && !data.getDays7().isEmpty()) {
                    String[] day7 = data.getDays7().split(",");
                    if (day7.length >= 5 ) {
                        ying.setText(day7[0] + "赢");
                        yingBan.setText(day7[1] + "赢半");
                        zou.setText(day7[2] + "走");
                        shu.setText(day7[3] + "输");
                        shuBan.setText(day7[4] + "输半");
                    }
                }
            }
        } else {
            view = mInflater.inflate(R.layout.main_attention_parent_jc, null);
            TextView name = view.findViewById(R.id.ZJ_JC_NickName);
            TextView day7 = view.findViewById(R.id.ZJ_JC_Day7);
            TextView day7Y = view.findViewById(R.id.ZJ_JC_Day7Y);
            ShapedImageView head = view.findViewById(R.id.ZJ_JC_Head);
            TextView number = view.findViewById(R.id.ZJ_JC_MsgHintText);
            ZhuanJia data = (ZhuanJia) getChild(groupPosition, childPosition);
            if (data != null) {
                name.setText(data.getNickname());
                xUtilsImageUtils.display(head, R.drawable.defaultpic, data.getAvatar());
                if(data.getWeikaishi()!=0){
                    number.setVisibility(View.VISIBLE);
                    number.setText(data.getWeikaishi()+"");
                }else {
                    number.setVisibility(View.GONE);
                }
                if (data.getDays7() != null && !data.getDays7().isEmpty()) {
                    String[] str = data.getDays7().split(",");
                    if (str.length >= 3) {
                        day7.setText(str[0] + "中"+str[1]);
                        day7Y.setText(str[2] + "%");
                    }
                }
            }
        }
        return view;
    }

    //孩子在指定的位置是可选的，即：children中的元素是可点击的
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //表示孩子是否和组ID是跨基础数据的更改稳定
    public boolean hasStableIds() {
        return true;
    }


    //自定义的创建TextView
   /* public TextView getGenericView(int mTextSize) {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView textView = new TextView(mContext);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(42, 12, 12, 12);
        textView.setTextSize(mTextSize);
        textView.setTextColor(Color.BLACK);
        return textView;
    }*/

}