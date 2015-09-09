package com.musicplayer;  
  
import android.app.Activity;  
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.SeekBar;
import android.widget.Toast;  
  
public class test_musicplayer extends Activity {  
    private Button btnPause, btnPlayUrl, btnStop;  
    private SeekBar skbProgress;  
    private Player player;  
    private long exitTime = 0;
    
    boolean canplay=true;
  
    /** Called when the activity is first created. */  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main);  
    
        this.setTitle("MediaPlayer+HttpProxy");  
  
        btnPlayUrl = (Button) this.findViewById(R.id.btnPlayUrl);  
        btnPlayUrl.setOnClickListener(new ClickEvent());  
  
        btnPause = (Button) this.findViewById(R.id.btnPause);  
        btnPause.setOnClickListener(new ClickEvent());  
  
//        btnStop = (Button) this.findViewById(R.id.btnStop);  
//        btnStop.setOnClickListener(new ClickEvent());  
 
        skbProgress = (SeekBar) this.findViewById(R.id.skbProgress);  
        skbProgress.setOnSeekBarChangeListener(new SeekBarChangeEvent());  
        player = new Player(skbProgress);  
    }
  
//    public void onStop(){
//    	super.onStop();
//    	System.exit(0);
//    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出!", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
		return super.onKeyDown(keyCode, event);
	}
    
    class ClickEvent implements OnClickListener {  
  
        @Override  
        public void onClick(View arg0) {  
        	if (arg0 == btnPause) {
                player.pause();
            } else if (arg0 == btnPlayUrl) {
            	if(canplay==true)
            	{
                String url="http://www.kfybsf.com/mp3/liangzhu.mp3";
//            		String url="http://conteadiwagner.com/audio/sf.mp3"; //长
                player.playUrl(url);  
                canplay=false;
            	}
            	else
            		player.play();
            } else if (arg0 == btnStop) {
                player.stop();
                canplay=true;
            }
        }
    }  
  
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {  
        int progress;  
  
        @Override  
        public void onProgressChanged(SeekBar seekBar, int progress,  
                boolean fromUser) {  
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()  
            this.progress = progress * player.mediaPlayer.getDuration() / seekBar.getMax();  
        }  
  
        @Override  
        public void onStartTrackingTouch(SeekBar seekBar) { }  
  
        @Override  
        public void onStopTrackingTouch(SeekBar seekBar) {  
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字  
            player.mediaPlayer.seekTo(progress);  
        }  
    }  
}  