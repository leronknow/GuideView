package com.leehong;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

/**
 * @author virgil
 *  向导页面
 */
public class GuideActivity extends Activity {
	 private ViewPager viewPager;  
	 private ArrayList<View> pageViews;  
	 private ImageView imageView;  
	 private ImageView[] imageViews;
	 private int currentItem = 0; // 当前图片的索引号 
	 private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3
	 
	// 包裹滑动图片LinearLayout
	private ViewGroup main;
    // 包裹小圆点的LinearLayout
    private ViewGroup group;
    private GestureDetector gestureDetector; // 用户滑动
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置无标题窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
     // 获取分辨率  
        DisplayMetrics dm = new DisplayMetrics();
        LayoutInflater inflater = getLayoutInflater(); 
        View guide_one = inflater.inflate(R.layout.guide_one, null);
        View guide_two = inflater.inflate(R.layout.guide_two, null);
        View guide_three = inflater.inflate(R.layout.guide_three, null);
        View guide_four = inflater.inflate(R.layout.guide_four, null);
        View guide_five = inflater.inflate(R.layout.guide_five, null);
        gestureDetector = new GestureDetector(new GuideViewTouch());
        flaggingWidth = dm.widthPixels / 5; 
        
        pageViews = new ArrayList<View>();  
        pageViews.add(guide_one);
        pageViews.add(guide_two);
        pageViews.add(guide_three);
        pageViews.add(guide_four);
        pageViews.add(guide_five);
        
        imageViews = new ImageView[pageViews.size()];  
        main = (ViewGroup)inflater.inflate(R.layout.guide, null); 
        
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages);
        
        for (int i = 0; i < pageViews.size(); i++) {  
            imageView = new ImageView(GuideActivity.this);  
            imageView.setLayoutParams(new LayoutParams(20,20));  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            
            if (i == 0) {  
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.page_indicator_focused);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
            }  
            
            group.addView(imageViews[i]);  
        }  
        
        setContentView(main);
        
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }
    
 // 指引页面数据适配器
    private class GuidePageAdapter extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    // 指引页面更改事件监听器
    private class GuidePageChangeListener implements OnPageChangeListener {  
    	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            //表示在前一个页面滑动到后一个页面的时候，在前一个页面滑动前调用的方法。 
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
        	//arg0是表示你当前选中的页面，这事件是在你页面跳转完毕的时候调用的。
        	  currentItem = arg0;
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0].setBackgroundResource(R.drawable.page_indicator_focused);
                
                if (arg0 != i) {  
                    imageViews[i].setBackgroundResource(R.drawable.page_indicator);  
                }  
            }
        }  
    }
   
    //滑动的事件处理 
    @Override  
    public boolean dispatchTouchEvent(MotionEvent event) {  
        if (gestureDetector.onTouchEvent(event)) {  
            event.setAction(MotionEvent.ACTION_CANCEL);  
        }  
        return super.dispatchTouchEvent(event);  
    } 
    
    //滑动到最后一张图片并跳转到指定的activity
    private class GuideViewTouch extends SimpleOnGestureListener {  
        @Override  
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,  
                float velocityY) {  
        	
        	GuidePageAdapter pageAdapter = new GuidePageAdapter();
            if (currentItem == (pageAdapter.getCount() - 1)) {  //判断当前是否是最后一张
                if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY()  
                        - e2.getY())  
                        && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1  
                                .getX() - e2.getX() >= flaggingWidth)) {  
                    if (e1.getX() - e2.getX() >= flaggingWidth) {  
                        GoToTimelineActivity();  
                        return true;  
                    }  
                }  
            }  
            return false;  
        }  
    } 
    
    public void GoToTimelineActivity(){
    	Intent intent = new Intent(GuideActivity.this,
				GuideViewActivity.class);
		startActivity(intent);
		GuideActivity.this.finish();
    }
}
