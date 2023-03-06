package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.core;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.StaticNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.TriggerNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils.RuntimeTypeAdapterFactory;

public class YaParser {


    public static String toJson(Book book) {
        RuntimeTypeAdapterFactory<Node> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                .of(Node.class, "nodeSubtype")
                .registerSubtype(TriggerNode.class, "triggerNode")
                .registerSubtype(StaticNode.class, "staticNode");

        Gson gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();

        return gson.toJson(book);
    }
}
