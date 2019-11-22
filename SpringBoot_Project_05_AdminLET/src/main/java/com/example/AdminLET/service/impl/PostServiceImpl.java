package com.example.AdminLET.service.impl;

import com.example.AdminLET.domain.PostsTable;
import com.example.AdminLET.mapper.PostsTableMapper;
import com.example.AdminLET.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsTableMapper postsTableMapper;

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
        pageHelper.startPage(pageNum,pageSize);
        PageInfo<PostsTable> pageInfo = new PageInfo<>(postsTableMapper.select(postsTable));
        /*后续可以通过pageInfo获取getSize getTotal等*/
        return pageInfo;
    }
}
