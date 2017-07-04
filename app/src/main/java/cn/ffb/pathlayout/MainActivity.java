package cn.ffb.pathlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ffb.adapter.core.ViewHolder;
import cn.ffb.adapter.list.SimpleListAdapter;
import cn.ffb.path.PathLayout;

public class MainActivity extends AppCompatActivity implements PathLayout.PathOnClickListener, AdapterView.OnItemClickListener {
    private PathLayout mPathLayout;
    private ListView mListView;
    private SimpleListAdapter fileAdapter;
    private List<FileEntity> fileEntities = new ArrayList<>();
    private Map<String, List<FileEntity>> datas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPathLayout = (PathLayout) findViewById(R.id.path_layout);
        mPathLayout.setPathOnClickListener(this);
        mPathLayout.onForward("根目录");
        mListView = (ListView) findViewById(R.id.listView);
        List<FileEntity> fileList = getDatas().get("根目录");
        fileAdapter = SimpleListAdapter.create(this, fileList, R.layout.list_item, new SimpleListAdapter.IItemViewSetup<FileEntity>() {
            @Override
            public void setupView(ViewHolder viewHolder, int position, FileEntity entity) {
                viewHolder.setText(R.id.file_title, entity.getTitle());
                if (entity.isFile()) {
                    viewHolder.setImage(R.id.type, R.mipmap.files);
                } else {
                    viewHolder.setImage(R.id.type, R.mipmap.folder);
                }
            }
        });
        mListView.setAdapter(fileAdapter);
        initListData("根目录");
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onPathClick(int position) {
        int pathTotal = fileEntities.size();
        for (int i = 0; i < pathTotal - position; i++) {
            fileEntities.remove(fileEntities.size() - 1);
        }
        if (fileEntities.size() > 0) {
            initListData(fileEntities.get(fileEntities.size() - 1).getTitle());
        } else {
            initListData("根目录");
        }
    }

    private void initListData(String title) {
        List<FileEntity> fileList = getDatas().get(title);
        fileAdapter.setListData(fileList);
        fileAdapter.notifyDataSetChanged();
    }

    private Map<String, List<FileEntity>> getDatas() {
        List<FileEntity> list1 = new ArrayList<>();
        list1.add(new FileEntity("第一级"));
        list1.add(new FileEntity("文件", true));
        datas.put("根目录", list1);
        List<FileEntity> list2 = new ArrayList<>();
        list2.add(new FileEntity("第二级"));
        list2.add(new FileEntity("第二级文件", true));
        datas.put("第一级", list2);
        List<FileEntity> list3 = new ArrayList<>();
        list3.add(new FileEntity("第三级"));
        list3.add(new FileEntity("第三级文件", true));
        datas.put("第二级", list3);
        List<FileEntity> list4 = new ArrayList<>();
        list4.add(new FileEntity("第四级文件", true));
        datas.put("第三级", list4);
        return datas;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FileEntity entity = (FileEntity) fileAdapter.getItem(position);
        if (entity.isFile()) {
            Toast.makeText(this, "这是一个文件,文件名" + entity.getTitle(), Toast.LENGTH_LONG).show();
        } else {
            mPathLayout.onForward(entity.getTitle());
            fileEntities.add(entity);
            initListData(entity.getTitle());
        }
    }
}
