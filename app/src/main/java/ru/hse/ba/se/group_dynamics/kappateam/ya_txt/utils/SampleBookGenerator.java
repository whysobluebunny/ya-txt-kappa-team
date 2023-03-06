package ru.hse.ba.se.group_dynamics.kappateam.ya_txt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Book;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Node;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.StaticNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Trigger;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.TriggerNode;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.TriggerType;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.Variable;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.VariableType;
import ru.hse.ba.se.group_dynamics.kappateam.ya_txt.book_model.VariableTypeClass;

public class SampleBookGenerator {
    private HashMap<String, Variable> vars;
    private HashMap<String, Node> nodes;
    private Book book;

    String getRandomID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Сгенерировать пример книжки.
     *
     * @return настоящая книжка-пример!!!
     */
    public Book generateSampleBook(String bookID) {
        // генерируем переменные для книжки
        vars = new HashMap<>();
        // генерируем ноды у книжки
        this.nodes = new HashMap<>();
        int counter = 0;
        StaticNode varNode = null;
        
        StringBuilder stringBuilder = new StringBuilder();
        for (VariableType variableType : VariableType.values()) {
            String id = getRandomID();

            stringBuilder.append("<p>Getting value from variable...</p>");
            stringBuilder.append("<p>" + variableType.getTypeString() + "</p>");
            stringBuilder.append("<p>" + "$$$" + id + "$$$" + "</p>");
            vars.put(id, new Variable(id, variableType, "Default: " + variableType.name()));
        }
        // генерируем ноды у книжки
        this.nodes = new HashMap<>();

        varNode = new StaticNode(getRandomID(), "0", stringBuilder.toString());
        nodes.put(varNode.getId(), varNode);

        for (TriggerType triggerType : TriggerType.values()) {
            stringBuilder = new StringBuilder();
            for (int i = 0; i < 25; i++) {
                stringBuilder.append("<p> THE BEST JOB EVER </p>");
            }
            StaticNode pathwayA = new StaticNode(getRandomID(), String.valueOf(counter + 1), triggerType.toString() + "is TRUE");
            StaticNode pathwayB = new StaticNode(getRandomID(), String.valueOf(counter + 1), triggerType.toString() + "is FALSE");

            ArrayList<Trigger> triggers = new ArrayList<>();
            String triggerdata = "21";
            if (triggerType == TriggerType.GEO_NEAR_LOCATION)
                triggerdata = "21,21";
            triggers.add(new Trigger(pathwayA.getId(), triggerType, triggerdata));
            triggers.add(new Trigger(pathwayB.getId(), TriggerType.ALWAYS_TRUE, triggerdata));

            TriggerNode triggerNode = new TriggerNode(getRandomID(), triggers);

            StaticNode newBeginning = new StaticNode(String.valueOf(counter), triggerNode.getId(), stringBuilder.toString());
            nodes.put(newBeginning.getId(), newBeginning);
            nodes.put(pathwayA.getId(), pathwayA);
            nodes.put(pathwayB.getId(), pathwayB);
            nodes.put(triggerNode.getId(), triggerNode);
            counter++;
        }
        // генерируем саму книжку
        this.book = new Book(bookID, varNode, nodes, vars);
        return this.book;
    }
}
