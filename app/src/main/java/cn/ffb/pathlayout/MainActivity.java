package cn.ffb.pathlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.ffb.path.PathLayout;

public class MainActivity extends AppCompatActivity implements PathLayout.PathOnClickListener {
    private PathLayout mPathLayout;
    private ListView mListView;
    private FileAdapter fileAdapter;
    private List<FileEntity> fileEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPathLayout= (PathLayout) findViewById(R.id.path_layout);
        mPathLayout.setPathOnClickListener(this);
        mListView= (ListView) findViewById(R.id.listView);
        fileAdapter = new FileAdapter(this);
    }

    @Override
    public void onPathClick(int position) {
        int pathTotal = fileEntities.size();
        for (int i = 0; i < pathTotal - position; i++) {
            fileEntities.remove(fileEntities.size() - 1);
        }
//        this.refreshPageData();
    }
    private void initListData(){
        fileAdapter.setListData(fileEntities);
    }
}
