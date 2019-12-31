package com.drartgames.stepper.sl;

import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.Scene;
import com.drartgames.stepper.sl.parser.Parser;
import com.drartgames.stepper.sl.parser.SLParser;

import java.util.ArrayList;
import java.util.List;

public class DefaultScriptLoader implements ScriptLoader {
    private Parser parser;

    public DefaultScriptLoader() {
        parser = new SLParser();
    }

    @Override
    public List<Scene> load(String content) throws ParseException {
        parser.parse(content);

        try {
            while (parser.next()) {
                System.out.println(parser.getTokenValue() + "->" + parser.getTokenType());
            }
        } catch (com.drartgames.stepper.exceptions.ParseException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
