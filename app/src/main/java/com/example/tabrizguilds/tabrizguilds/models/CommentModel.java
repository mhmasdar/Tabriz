package com.example.tabrizguilds.tabrizguilds.models;

import java.util.List;

/**
 * Created by EHSAN on 12/18/2017.
 */

public class CommentModel {

    public int id;
    public int likeCount;
    public int date;
    public String name;
    public String body;
    public List<CommentModel> answers;

}
