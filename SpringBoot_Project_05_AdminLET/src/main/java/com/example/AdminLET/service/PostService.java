package com.example.AdminLET.service;

import com.example.AdminLET.domain.PostsTable;
import com.github.pagehelper.PageInfo;

public interface PostService {

    PostsTable selectOne(String guid);

    PageInfo<PostsTable> page(int pageNum, int pageSize, PostsTable postsTable);
}
