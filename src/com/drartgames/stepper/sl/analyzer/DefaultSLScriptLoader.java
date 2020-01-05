package com.drartgames.stepper.sl.analyzer;

import com.drartgames.stepper.exceptions.AnalysisException;
import com.drartgames.stepper.exceptions.ParseException;
import com.drartgames.stepper.sl.lang.*;
import com.drartgames.stepper.sl.parser.Parser;
import com.drartgames.stepper.sl.parser.SLParser;
import com.drartgames.stepper.sl.processors.*;

import java.util.ArrayList;
import java.util.List;

public class DefaultSLScriptLoader implements SLScriptLoader {
    private Parser parser;
    private List<StatementAnalyzer> statementAnalyzers;

    private static final String SCENE_KEYWORD = "scene";
    private static final String ACTION_KEYWORD = "action";
    private static final String OPENING_CURLY_BRACKET = "{";
    private static final String CLOSING_CURLY_BRACKET = "}";
    private static final String ELSE_KEYWORD = "else";

    public DefaultSLScriptLoader() {
        parser = new SLParser();
        statementAnalyzers = new ArrayList<>();

        statementAnalyzers.add(new SingleStatementAnalyzer("load_image",
                LoadImageOperatorProcessor.LOAD_IMAGE_ID, LoadImageOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("load_audio",
                LoadAudioOperatorProcessor.LOAD_AUDIO_ID, LoadAudioOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("set_text",
                SetTextOperatorProcessor.SET_TEXT_ID, SetTextOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("set_background",
                SetBackgroundOperatorProcessor.SET_BACKGROUND_ID, SetBackgroundOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("play",
                PlayOperatorProcessor.PLAY_ID, PlayOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new ConditionalStatementAnalyzer("if_tag",
                IfTagOperatorProcessor.IF_TAG_ID, IfTagOperatorProcessor.ARGS_COUNT, ELSE_KEYWORD));
        statementAnalyzers.add(new ConditionalStatementAnalyzer("if_tag_n",
                IfTagOperatorProcessor.IF_TAG_N_ID, IfTagOperatorProcessor.ARGS_COUNT, ELSE_KEYWORD));
        statementAnalyzers.add(new SingleStatementAnalyzer("show",
                ShowOperatorProcessor.SHOW_ID, ShowOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("tag",
                TagOperatorProcessor.TAG_ID, TagOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("switch_dialog",
                SwitchDialogOperatorProcessor.SWITCH_DIALOG_ID, SwitchDialogOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("goto",
                GotoOperatorProcessor.GOTO_ID, GotoOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("add_dialog",
                AddDialogOperatorProcessor.ADD_DIALOG_ID, AddDialogOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("call",
                CallOperatorProcessor.CALL_ID, CallOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("counter_set",
                CounterSetOperatorProcessor.COUNTER_SET_ID, CounterSetOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("counter_add",
                CounterAddOperatorProcessor.COUNTER_ADD_ID, CounterAddOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("call_extern",
                CallExternOperatorProcessor.CALL_EXTERN_ID, CallExternOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new ConditionalStatementAnalyzer("if_counter",
                IfCounterOperatorProcessor.IF_COUNTER_ID, IfCounterOperatorProcessor.ARGS_COUNT, ELSE_KEYWORD));
        statementAnalyzers.add(new SingleStatementAnalyzer("show_motion",
                ShowMotionOperatorProcessor.SHOW_MOTION_ID, ShowMotionOperatorProcessor.ARGS_COUNT));
        statementAnalyzers.add(new SingleStatementAnalyzer("animate",
                AnimateOperatorProcessor.ANIMATE_ID, AnimateOperatorProcessor.ARGS_COUNT));
    }

    @Override
    public List<Scene> load(String content) throws ParseException, AnalysisException {
        parser.parse(content);

        List<Scene> scenes = loadScenes(parser);

        //@todo maybe add call checks as well as checks of lookups (scenes, resources, dialogs)
        return scenes;
    }

    //at scene; at none
    private List<Scene> loadScenes(Parser parser) throws ParseException, AnalysisException {
        List<Scene> scenes = new ArrayList<>();

        while (parser.next()) {
            if (parser.getTokenValue().equals(SCENE_KEYWORD)) {
                if (parser.next()) {
                    if (parser.getTokenType() == SLParser.KEYWORD_ID) {
                        String sceneName = parser.getTokenValue();
                        List<Action> actions = null;

                        if (parser.next() && parser.getTokenValue().equals(OPENING_CURLY_BRACKET)) {
                            if (parser.next())
                                actions = loadActions(parser);
                            else
                                throwException("Either actions or scene's end have been expected", parser);
                        } else
                            throwException("Opening curly bracket has been expected after scene header's declaration", parser);

                        Scene scene = new DefaultScene(sceneName, actions);

                        scenes.add(scene);

                        if (!parser.getTokenValue().equals(CLOSING_CURLY_BRACKET))
                            throwException("Closing curly bracket has been expected after scene actions declaration", parser);
                    } else
                        throwException("Scene's name must be an identifier", parser);
                } else
                    throwException("Scene's name has been expected after `" + SCENE_KEYWORD + "` keyword", parser);
            } else
                throwException(SCENE_KEYWORD + " has been expected", parser);
        }

        return scenes;
    }

    //at action; at after last action's };
    private List<Action> loadActions(Parser parser) throws ParseException, AnalysisException {
        List<Action> actions = new ArrayList<>();

        do {
            if (parser.getTokenValue().equals(ACTION_KEYWORD)) {
                if (parser.next()) {
                    if (parser.getTokenType() == SLParser.KEYWORD_ID) {
                        String actionName = parser.getTokenValue();
                        List<Operator> operators = null;

                        if (parser.next())
                            operators = analyzeBlock(parser);
                        else
                            throwException("Operators have been expected after action header's declaration", parser);

                        Action action = new DefaultAction(actionName, operators);

                        actions.add(action);

                        if (!parser.next())
                            throwException("End of block or another action have been expected", parser);
                    } else
                        throwException("Action's name must be an identifier", parser);
                } else
                    throwException("Action's name has been expected after `" + ACTION_KEYWORD + "` keyword", parser);
            } else
                throwException(ACTION_KEYWORD + " has been expected", parser);
        } while (!parser.getTokenValue().equals(CLOSING_CURLY_BRACKET));

        return actions;
    }

    //at {; at }
    private List<Operator> analyzeBlock(Parser parser) throws ParseException, AnalysisException {
        List<Operator> operators = new ArrayList<>();

        if (parser.getTokenValue().equals(OPENING_CURLY_BRACKET)) {
            if (!parser.next())
                throwException("Operator or closing curly bracket have been expected in action's block", parser);

            do {
                //at } or op_name
                if (parser.getTokenValue().equals(CLOSING_CURLY_BRACKET)) break;//is used when it's the end after the beginning
                else {
                    List<Operator> statementOperators = analyzeStatement(parser);

                    operators.addAll(statementOperators);
                }

            } while (!parser.getTokenValue().equals(CLOSING_CURLY_BRACKET));//is used when it's th end after statement
        } else
            throwException("Opening curly bracket has been expected at block begin", parser);

        return operators;
    }

    @Override
    //at statement's beginning; after all statement's elements
    public List<Operator> analyzeStatement(Parser parser) throws ParseException, AnalysisException {
        List<Operator> operators = null;
        boolean wasFound = false;

        String tokenValue = parser.getTokenValue();

        if (tokenValue.equals(OPENING_CURLY_BRACKET)) {
            operators = analyzeBlock(parser);

            wasFound = true;

            if (!parser.next())
                throwException("Another action or closing curly bracket have been expected", parser);
        } else {
            for (StatementAnalyzer analyzer : statementAnalyzers) {
                if (analyzer.getStatementBegin().equals(tokenValue)) {
                    operators = new ArrayList<>();
                    operators.add(analyzer.analyze(parser, this));

                    wasFound = true;
                    break;
                }
            }
        }

        if (!wasFound)
            throwException("Undefined statement's beginning: " + tokenValue, parser);

        return operators;
    }

    @Override
    public void throwException(String message, Parser parser) throws ParseException, AnalysisException {
        throw new AnalysisException(message + " at line " + parser.getCurrentLineNumber());
    }
}
