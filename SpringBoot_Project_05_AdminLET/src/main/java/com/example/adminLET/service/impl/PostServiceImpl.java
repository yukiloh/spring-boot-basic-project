package com.example.adminLET.service.impl;

import com.example.adminLET.domain.PostsTable;
import com.example.adminLET.mapper.PostsTableMapper;
import com.example.adminLET.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsTableMapper postsTableMapper;  //因为使用tk.mybatis,此处会报错

    @Override
    public PostsTable selectOne(String guid) {
        PostsTable postsTable = new PostsTable();
        postsTable.setPostGuid(guid);
        PostsTable obj = postsTableMapper.selectOne(postsTable);

        return obj;
    }

    @Override
    public PageInfo<PostsTable> page(int pageNum, int pageSize, PostsTable postsTable) {
        PageHelper pageHelper = new PageHelper();
        PageMethod.startPage(pageNum,pageSize);
        PageInfo<PostsTable> pageInfo = new PageInfo<>(postsTableMapper.select(postsTable));
        /*后续可以通过pageInfo获取getSize getTotal等*/
        return pageInfo;
    }
}
