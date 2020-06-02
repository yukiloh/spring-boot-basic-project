package com.example.adminLET.service;

import com.example.adminLET.domain.PostsTable;
import com.github.pagehelper.PageInfo;

public interface PostService {

    PostsTable selectOne(String guid);

    PageInfo<PostsTable> page(int pageNum, int pageSize, PostsTable postsTable);
}
