package com.example.dysaniazzz.chapter06;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dysaniazzz.R;
import com.example.dysaniazzz.bean.Album;
import com.example.dysaniazzz.bean.Song;
import com.example.dysaniazzz.common.BaseActivity;
import com.example.dysaniazzz.utils.UIUtils;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by DysaniazzZ on 17/01/2017.
 * 第六章：使用LitePal来操作数据库
 */
public class LitePalUsageActivity extends BaseActivity {

    @BindView(R.id.tv_litepal_database_info)
    TextView mTvLitePalDatabaseInfo;

    private int i;
    private Unbinder mUnbinder;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LitePalUsageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal_usage);
        mUnbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_litepal_create_database, R.id.btn_litepal_delete_database, R.id.btn_litepal_insert_data, R.id.btn_litepal_update_data, R.id.btn_litepal_delete_data, R.id.btn_litepal_query_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_litepal_create_database:
                //创建数据库
                try {
                    //方式一：在assets/litepal.xml中配置数据库的信息，然后在代码中创建
                    LitePal.getDatabase();
                    //方式二：直接在代码中配置数据库的所有信息并创建
//                    LitePalDB litePalDemo = new LitePalDB("LitePalDemo", 1);
//                    litePalDemo.addClassName(Album.class.getName());
//                    litePalDemo.addClassName(Song.class.getName());
//                    LitePal.use(litePalDemo);
//                    LitePal.getDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                }
                break;
            case R.id.btn_litepal_delete_database:
                //删除数据库
                try {
                    LitePal.deleteDatabase("LitePalDemo");
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                }
                break;
            case R.id.btn_litepal_insert_data:
                //插入数据
                try {
                    Album album = new Album();
                    album.setName("Unorthodox Jukebox");
                    album.setPrice(39.99f);
                    album.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse("2012-12-11"));
                    album.save();                                       //底层处理异常

                    Song song1 = new Song();
                    song1.setName("Young Girls");
                    song1.setDuration(228);
                    song1.setAlbum(album);
                    song1.saveIfNotExist("name = ?", "Young Girls");    //如果没有该数据则插入

                    Song song2 = new Song();
                    song2.setName("When I Was Your Man");
                    song2.setDuration(213);
                    song2.setAlbum(album);
                    song2.saveThrows();                                 //由调用者去处理异常
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                }
                break;
            case R.id.btn_litepal_update_data:
                //更改数据
                try {
                    //更新单条数据
                    i++;
                    //方式一：
                    Album albumToUpdate = DataSupport.find(Album.class, 1);
                    albumToUpdate.setPrice(24.99f + i);
                    albumToUpdate.save();
                    //方式二：
//                    Album albumToUpdate = new Album();
//                    albumToUpdate.setPrice(24.99f + i);
//                    albumToUpdate.update(1);
                    //更新符合条件的所有数据
//                    albumToUpdate.updateAll("name = ?", "album");
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                }
                break;
            case R.id.btn_litepal_delete_data:
                //删除数据
                try {
                    //删除单条数据
//                    DataSupport.delete(Song.class, 1);
                    //删除符合条件的所有数据
                    DataSupport.deleteAll(Song.class, "duration > ?", "220");
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                }
                break;
            case R.id.btn_litepal_query_data:
                //查询数据
                try {
                    StringBuilder builder = new StringBuilder();
                    //根据指定的Id查找单条数据
                    Album albumFound = DataSupport.find(Album.class, 1);
                    builder.append("Album Name: " + albumFound.getName() + "\t Price: " + albumFound.getPrice() + "\n\t");
                    //查询一个表中的所有数据
                    List<Song> allSongs = DataSupport.findAll(Song.class);
//                    DataSupport.where("name like ?", "song%").order("duration").find(Song.class);
                    for (Song song : allSongs) {
                        builder.append("Song Name: " + song.getName() + "\t Duration: " + song.getDuration() + "\n\t");
                    }
                    mTvLitePalDatabaseInfo.setText(builder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    UIUtils.createToast(mContext, e.getMessage());
                    mTvLitePalDatabaseInfo.setText("");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
