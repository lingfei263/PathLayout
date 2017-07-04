package cn.ffb.pathlayout;

import android.content.Context;


import cn.ffb.adapter.core.ViewHolder;
import cn.ffb.adapter.list.ItemViewProvider2;
import cn.ffb.adapter.list.ListAdapter;

/**
 * Created by Administrator on 2017/7/4.
 */

public class FileAdapter extends ListAdapter<FileEntity> {
    public FileAdapter(Context context) {
        super(context);
        register(new ItemViewProvider2<FileEntity>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item;
            }

            @Override
            public void setupView(ViewHolder viewHolder, int position, FileEntity entity) {
                viewHolder.setText(R.id.file_title, entity.getTitle());
//                if (entity.isFile()) {
//                    viewHolder.setImage(R.mipmap.files);
//                } else {
//                    viewHolder.setImage(R.mipmap.folder);
//                }
            }

            @Override
            public boolean isForProvider(int position, FileEntity entity) {
                return true;
            }
        });
    }


}
